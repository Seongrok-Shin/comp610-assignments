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

    private final List<Element> neighbours;
    private double currentTemp;
    private final double heatConstant;
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
        double temperatureAvg = 0;

        while (!stopRequested) {
            synchronized (this) {
                for (int i = 0; i < neighbours.size(); i++) {
                    temperatureAvg += this.neighbours.get(i).getTemperature();
                }
                temperatureAvg /= this.neighbours.size() + 1;
            }
            this.currentTemp += (temperatureAvg - this.currentTemp) * this.heatConstant;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
    }

    public void addNeighbour(Element element) {
        System.out.println("Checking the new element added");
        this.neighbours.add(element);
    }

    public void applyTempToElement(double appliedTemp) {
        this.currentTemp += (appliedTemp - this.currentTemp) * this.heatConstant;
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

            try {
                tempArray[0] = element.getTemperature();
                tempArray[1] = anotherElement.getTemperature();
                Thread.sleep(1000);
                if (Math.abs(tempArray[0] - tempArray[1]) < 0.05) {
                    element.requestStop();
                    anotherElement.requestStop();
                }
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }

    }
}
