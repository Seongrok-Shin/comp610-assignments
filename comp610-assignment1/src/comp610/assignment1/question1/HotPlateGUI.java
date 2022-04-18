/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp610.assignment1.question1;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author ssr7324
 */
public class HotPlateGUI extends JPanel implements ActionListener, ChangeListener, MouseMotionListener, MouseListener {

    private final JSlider temperatureJSlider;
    private final JSlider heatConstantJSlider;
    private final DrawPanel drawPanel;

    private Element[][] dimensionalElement;
    private final Timer timer;

    private int initializedTemperature = 500;
    private double initializedHeatConstant;

    private final int ROW = 20;
    private final int COL = 20;

    private int recX;
    private int recY;

    private int setX = 0;
    private int setY = 0;

    @Override
    public void mouseDragged(MouseEvent e) {
        recX = e.getX() / setX;
        recY = e.getY() / setY;

        try {
            dimensionalElement[recX][recY].applyTempToElement(initializedTemperature);
        } catch (ArrayIndexOutOfBoundsException err) {
            System.err.println(err);
        }
        drawPanel.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public boolean isElementAtPosition(int getX, int getY, int setRow, int setCol) {
        return getX >= setRow * 20 && getX <= (setRow * 20) + 20 && getY
                >= setCol * 20 && getY <= (setCol * 20) + 20;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

        this.mouseDragged(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == timer) {
            drawPanel.repaint();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();

        if (source == heatConstantJSlider) {
            initializedHeatConstant = source.getValue() / 100.0;
            for (Element[] dimensionalElement1 : dimensionalElement) {
                for (int col = 0; col < dimensionalElement.length; col++) {
                    dimensionalElement1[col].setHeatConstant(initializedHeatConstant);
                }
            }
        }
        if (source == temperatureJSlider) {
            initializedTemperature = source.getValue();
        }
    }

    private class DrawPanel extends JPanel {

        public DrawPanel() {
            super.setSize(new Dimension(500, 500));
            super.setBackground(Color.YELLOW);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setX = (getWidth() / ROW);
            setY = (getHeight() / COL);

            for (int row = 0; row < ROW; row++) {
                for (int col = 0; col < COL; col++) {
                    dimensionalElement[row][col].drawElement(g, row * setX,
                            col * setY, setX - 1,
                            setY - 1);
                }
            }
        }
    }

    private void setDimensionalElement() {
        dimensionalElement = new Element[ROW][COL];
        for (Element[] dimensionalElement1 : dimensionalElement) {
            for (int col = 0; col < dimensionalElement.length; col++) {
                dimensionalElement1[col] = new Element(0, 0.05);
            }
        }
    }

    private void addNeighbourToHotPlate() {
        for (int row = 0; row < dimensionalElement.length; row++) {
            for (int col = 0; col < dimensionalElement.length; col++) {
                Element element = new Element(0, 0.05);
                dimensionalElement[row][col] = element;

                if (row - 1 >= 0 && col - 1 >= 0) {
                    dimensionalElement[row - 1][col - 1].addNeighbour(element);
                    dimensionalElement[row][col].addNeighbour(
                            dimensionalElement[row - 1][col - 1]);
                } else if (row - 1 >= 0) {
                    dimensionalElement[row - 1][col].addNeighbour(element);
                    dimensionalElement[row][col].addNeighbour(
                            dimensionalElement[row - 1][col]);
                } else if (row - 1 >= 0 && col < COL - 1) {
                    dimensionalElement[row - 1][col + 1].addNeighbour(element);
                    dimensionalElement[row][col].addNeighbour(
                            dimensionalElement[row - 1][col + 1]);
                } else if (col - 1 >= 0) {
                    dimensionalElement[row][col - 1].addNeighbour(element);
                    dimensionalElement[row][col].addNeighbour(
                            dimensionalElement[row][col - 1]);
                }
                dimensionalElement[row][col].start();
            }
        }
    }

    public HotPlateGUI() {
        super();
        super.setLayout(new BorderLayout());

        this.setDimensionalElement();
        this.addNeighbourToHotPlate();

        JLabel temperatureJLabel = new JLabel("Sets temperature 0 to 1000");
        temperatureJLabel.setBorder(BorderFactory.createEmptyBorder());
        temperatureJSlider = new JSlider(0, 1000);
        temperatureJSlider.setMajorTickSpacing(100);
        temperatureJSlider.setMajorTickSpacing(10);
        temperatureJSlider.setPaintTicks(true);
        temperatureJSlider.addChangeListener(this);

        JLabel heatConstantJLabel = new JLabel("Sets heat constant 0.01 to 1");
        heatConstantJLabel.setBorder(BorderFactory.createEmptyBorder());
        heatConstantJSlider = new JSlider(1, 100);
        heatConstantJSlider.setMajorTickSpacing(10);
        heatConstantJSlider.setMajorTickSpacing(1);
        heatConstantJSlider.setPaintTicks(true);
        heatConstantJSlider.addChangeListener(this);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.add(temperatureJLabel);
        jPanel.add(temperatureJSlider);
        jPanel.add(heatConstantJLabel);
        jPanel.add(heatConstantJSlider);

        super.add(jPanel, BorderLayout.SOUTH);

        drawPanel = new DrawPanel();
        drawPanel.addMouseListener(this);
        drawPanel.addMouseMotionListener(this);
        super.add(drawPanel, BorderLayout.CENTER);

        timer = new Timer(20, this);
        timer.start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hotplate_Seongrok");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new HotPlateGUI());
        frame.pack();
        frame.setVisible(true);
    }
}
