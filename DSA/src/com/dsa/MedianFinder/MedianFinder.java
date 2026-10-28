package com.dsa.MedianFinder;


import java.util.PriorityQueue;
import java.util.Collections;

public class MedianFinder {

    // Max-heap: stores the smaller half
    private PriorityQueue<Integer> lower =
            new PriorityQueue<>(Collections.reverseOrder());

    // Min-heap: stores the larger half
    private PriorityQueue<Integer> upper =
            new PriorityQueue<>();

    public void addNum(int num) {

        // Step 1: Decide which half should receive the number
        if (lower.isEmpty() || num <= lower.peek()) {
            lower.offer(num);
        } else {
            upper.offer(num);
        }

        // Step 2: Rebalance if one heap is too large
        if (lower.size() > upper.size() + 1) {
            upper.offer(lower.poll());
        } else if (upper.size() > lower.size() + 1) {
            lower.offer(upper.poll());
        }
    }

    public double findMedian() {

        // Odd number of elements
        if (lower.size() > upper.size()) {
            return lower.peek();
        }

        if (upper.size() > lower.size()) {
            return upper.peek();
        }

        // Even number of elements
        return ((double) lower.peek() + upper.peek()) / 2.0;
    }

    public static void main(String[] args) {
        MedianFinder mf = new MedianFinder();

        mf.addNum(5);
        System.out.println(mf.findMedian()); // 5.0

        mf.addNum(2);
        System.out.println(mf.findMedian()); // 3.5

        mf.addNum(8);
        System.out.println(mf.findMedian()); // 5.0

        mf.addNum(3);
        System.out.println(mf.findMedian()); // 4.0
    }
}

