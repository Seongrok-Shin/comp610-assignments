/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp610.assignment1.question1;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ssr7324
 */
public class Element implements Runnable {

    private final List<Element> neighbours;
    private double currentTemp;
    private double heatConstant;
    private boolean stopRequested;
    private Thread thread;

    public Element(double startTemp, double heatConstant) {
        this.currentTemp = startTemp;
        this.heatConstant = heatConstant;
        this.neighbours = new ArrayList<>();
    }

    public void start() {
        this.thread = new Thread(this);
        thread.start();
    }

    public synchronized double getTemperature() {
        return this.currentTemp;
    }

    public void requestStop() {
        this.stopRequested = true;
    }

    public void setHeatConstant(double heatConstant) {
        this.heatConstant = heatConstant;
    }

    @Override
    public void run() {
        double temperatureAvg = 0;

        while (!stopRequested) {
            for (int i = 0; i < neighbours.size(); i++) {
                temperatureAvg += this.neighbours.get(i).getTemperature();
            }
            temperatureAvg /= this.neighbours.size() + 1;
            this.currentTemp += (temperatureAvg - this.currentTemp) * this.heatConstant;
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
    }

    public void addNeighbour(Element element) {
        neighbours.add(element);
    }

    public synchronized void applyTempToElement(double appliedTemp) {
        this.currentTemp += (appliedTemp - this.currentTemp) * this.heatConstant;
    }

    public void setColor(Graphics g, int x, int y, int width, int height) {

        g.setColor(Color.BLUE);

        double pixel = this.getTemperature() / 1000.00;
        double red = 255.0 * pixel + 0 * (1.0 - pixel);
        double green = 0.0 * pixel + 0 * (1.0 - pixel);
        double blue = 0.0 * pixel + 255 * (1.0 - pixel);
        if (pixel >= 0 && pixel <= 1) {
            g.setColor(new Color((int) red, (int) green, (int) blue));
        }
        
        g.fillRect(x, y, width, height);
    }

    public static void main(String[] args) {

        Element element = new Element(500.0, 0.1);
        Element anotherElement = new Element(0.0, 0.1);

        element.addNeighbour(anotherElement);
        anotherElement.addNeighbour(element);

        element.start();
        anotherElement.start();

        double tempArray[] = new double[2];
        tempArray[0] = element.getTemperature();
        tempArray[1] = anotherElement.getTemperature();

        while (!element.stopRequested) {

            System.out.println("First Element Temperature: " + tempArray[0]);
            System.out.println("Another Element Temperature: " + tempArray[1]);
            System.out.println("");

            try {
                tempArray[0] = element.getTemperature();
                tempArray[1] = anotherElement.getTemperature();
                Thread.sleep(100);
                if (Math.abs(tempArray[0] - tempArray[1]) < 0.01) {
                    element.requestStop();
                    anotherElement.requestStop();
                }
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }

    }
}
