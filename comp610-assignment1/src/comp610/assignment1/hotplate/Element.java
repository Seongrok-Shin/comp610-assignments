/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp610.assignment1.hotplate;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ssr7324
 */
public class Element implements Runnable {

    private List<Element> neighbours;
    private double currentTemp;
    private double heatConstant;
    private boolean stopRequested;

    public Element(double startTemp, double heatConstant) {
        this.currentTemp = startTemp;
        this.heatConstant = heatConstant;
        this.neighbours = new ArrayList<>();
    }

    public void start() {
        Thread thread0 = new Thread(this);
        thread0.start();
    }

    public double getTemperature() {
        return this.currentTemp;
    }

    public void requestStop() {
        this.stopRequested = true;
    }

    @Override
    public void run() {
        double avarageTemperature = 0;

        while (!stopRequested) {
            synchronized (this) {
                for (int i = 0; i < this.neighbours.size(); i++) {
                    avarageTemperature += this.neighbours.get(i).getTemperature();
                }
                avarageTemperature /= this.neighbours.size() + 1;
            }
            this.currentTemp += (avarageTemperature - this.currentTemp) * this.heatConstant;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
    }

    public void addNeighbour(Element element) {
        this.neighbours.add(element);
    }

    public void applyTempToElement(double appliedTemp) {
        this.currentTemp += (appliedTemp - this.currentTemp) * this.heatConstant;
    }

    public static void main(String[] args) {
        Element element = new Element(100.0, 0.5);
        Element anotherElement = new Element(0.0, 0.5);

        element.addNeighbour(anotherElement);
        anotherElement.addNeighbour(element);

        element.start();
        anotherElement.start();
    }
}
