package com.dsa.LRU;


import java.util.HashMap;
import java.util.Map;

public class LRUCache {

    private static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> map;

    private final Node head;
    private final Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();

        head = new Node(-1, -1);
        tail = new Node(-1, -1);

        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        Node node = map.get(key);

        if (node == null) {
            return -1;
        }

        moveToTail(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity <= 0) {
            return;
        }

        Node node = map.get(key);

        // Key already exists: update its value.
        if (node != null) {
            node.value = value;
            moveToTail(node);
            return;
        }

        // Cache is full: remove the least recently used node.
        if (map.size() == capacity) {
            Node lru = head.next;
            removeNode(lru);
            map.remove(lru.key);
        }

        // Insert the new node as most recently used.
        Node newNode = new Node(key, value);
        map.put(key, newNode);
        addToTail(newNode);
    }

    private void moveToTail(Node node) {
        removeNode(node);
        addToTail(node);
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void addToTail(Node node) {
        Node last = tail.prev;

        last.next = node;
        node.prev = last;

        node.next = tail;
        tail.prev = node;
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);

        cache.put(1, 100);
        cache.put(2, 200);

        System.out.println(cache.get(1)); // 100

        cache.put(3, 300); // Evicts key 2

        System.out.println(cache.get(2)); // -1
        System.out.println(cache.get(3)); // 300
    }
}
