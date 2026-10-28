package com.dsa.multithreading;


import java.util.ArrayDeque;
import java.util.Queue;

class SharedBuffer {
    private final Queue<Integer> queue = new ArrayDeque<>();
    private final int capacity = 3;

    public synchronized void produce(int item)
            throws InterruptedException {

        // Wait while the buffer is full
        while (queue.size() == capacity) {
            wait();
        }

        queue.add(item);
        System.out.println("Produced: " + item);

        // Notify waiting producers/consumers
        notifyAll();
    }

    public synchronized int consume()
            throws InterruptedException {

        // Wait while the buffer is empty
        while (queue.isEmpty()) {
            wait();
        }

        int item = queue.remove();
        System.out.println("Consumed: " + item);

        // Notify waiting producers/consumers
        notifyAll();

        return item;
    }
}

