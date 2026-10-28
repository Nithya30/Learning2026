package com.dsa.LRU;

import java.util.HashMap;
import java.util.Map;

public class LFUCache {

    private static class Node {
        int key;
        int value;
        int freq;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }

    private static class DoublyLinkedList {
        Node head;
        Node tail;
        int size;

        DoublyLinkedList() {
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }

        // Add as most recently used in this frequency list.
        void addFirst(Node node) {
            Node first = head.next;

            node.next = first;
            node.prev = head;
            head.next = node;
            first.prev = node;

            size++;
        }

        void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }

        // Evict the least recently used node in this list.
        Node removeLast() {
            if (size == 0) {
                return null;
            }

            Node last = tail.prev;
            remove(last);
            return last;
        }

        boolean isEmpty() {
            return size == 0;
        }
    }

    private final int capacity;
    private int minFreq;

    private final Map<Integer, Node> keyToNode;
    private final Map<Integer, DoublyLinkedList> freqToList;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;

        keyToNode = new HashMap<>();
        freqToList = new HashMap<>();
    }

    public int get(int key) {
        Node node = keyToNode.get(key);

        if (node == null) {
            return -1;
        }

        increaseFrequency(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity <= 0) {
            return;
        }

        Node node = keyToNode.get(key);

        // Existing key: update value and count this use.
        if (node != null) {
            node.value = value;
            increaseFrequency(node);
            return;
        }

        // Evict one node if the cache is full.
        if (keyToNode.size() == capacity) {
            DoublyLinkedList minList = freqToList.get(minFreq);
            Node evicted = minList.removeLast();

            keyToNode.remove(evicted.key);

            if (minList.isEmpty()) {
                freqToList.remove(minFreq);
            }
        }

        // A new key starts with frequency 1.
        Node newNode = new Node(key, value);
        keyToNode.put(key, newNode);

        freqToList
            .computeIfAbsent(1, f -> new DoublyLinkedList())
            .addFirst(newNode);

        minFreq = 1;
    }

    private void increaseFrequency(Node node) {
        int oldFreq = node.freq;
        DoublyLinkedList oldList = freqToList.get(oldFreq);

        oldList.remove(node);

        if (oldList.isEmpty()) {
            freqToList.remove(oldFreq);

            if (minFreq == oldFreq) {
                minFreq++;
            }
        }

        node.freq++;

        freqToList
            .computeIfAbsent(
                node.freq, f -> new DoublyLinkedList()
            )
            .addFirst(node);
    }

    public static void main(String[] args) {
        LFUCache cache = new LFUCache(2);

        cache.put(1, 100);
        cache.put(2, 200);

        System.out.println(cache.get(1)); // 100, freq of 1 becomes 2

        cache.put(3, 300); // Evicts key 2 (frequency 1)

        System.out.println(cache.get(2)); // -1
        System.out.println(cache.get(3)); // 300
        System.out.println(cache.get(1)); // 100
    }
}
