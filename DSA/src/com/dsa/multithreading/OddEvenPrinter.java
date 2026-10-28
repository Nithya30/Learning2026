package com.dsa.multithreading;


public class OddEvenPrinter {

    private static int number = 1;
    private static final int LIMIT = 10;
    private static final Object lock = new Object();

    static void printNumbers(boolean printOdd) {
        while (true) {
            synchronized (lock) {

                while (number <= LIMIT
                        && (number % 2 == 1) != printOdd) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                if (number > LIMIT) {
                    lock.notifyAll();
                    return;
                }

                System.out.println(
                    Thread.currentThread().getName()
                    + ": " + number
                );

                number++;
                lock.notifyAll();
            }
        }
    }

    public static void main(String[] args)
            throws InterruptedException {

        Thread odd = new Thread(
            () -> printNumbers(true), "Odd"
        );

        Thread even = new Thread(
            () -> printNumbers(false), "Even"
        );

        odd.start();
        even.start();

        odd.join();
        even.join();
    }
}
