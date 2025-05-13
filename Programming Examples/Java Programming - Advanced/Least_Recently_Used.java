
/*Implement an LRU (Least Recently Used) Cache in Java using a HashMap and a/* Doubly Linked List to achieve efficient get and put operations in O(1) time/* complexity.
import java.util.*;

Pseudocode for LRU Cache Implementation
BEGIN
    Define NODE class:
        Variables: key, value
        Pointers: prev, next (doubly linked list)
    
    Define LRUCache CLASS:
        Initialize capacity, HashMap, head, and tail
        
        FUNCTION GET(key):
            IF key not in cache:
                RETURN -1
            Move node to front (most recently used)
            RETURN node.value
        
        FUNCTION PUT(key, value):
            IF key exists:
                Update node value
                Move node to front
            ELSE:
                Create new node
                Add node to cache and linked list
                
                IF cache size exceeds capacity:
                    Remove least recently used (LRU) node
        
        FUNCTION MOVE_TO_HEAD(node):
            Remove node from its current position
            Insert node at front
        
        FUNCTION REMOVE_LRU():
            Remove the last node (least used)
            Remove entry from cache
        
    MAIN():
        Create LRUCache object with given capacity
        Perform PUT and GET operations
END

 Java - LRU Cache Expected Output
 10
-1
30

Explanation:
- put(1, 10) → Stores key 1 with value 10.
- put(2, 20) → Stores key 2 with value 20.
- get(1) → Returns 10 (key 1 was recently used).
- put(3, 30) → Adds 3, 30 → Evicts key 2 because it’s least recently used.
- get(2) → Returns -1 (Not Found because key 2 was removed).
- get(3) → Returns 30 (key 3 is present).


*/

class LRUCache {
    private class Node {
        int key, value;
        Node prev, next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private Map<Integer, Node> cache;
    private Node head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>(capacity);
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        Node node = cache.get(key);
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.value = value;
            moveToHead(node);
        } else {
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            addNode(newNode);

            if (cache.size() > capacity) {
                removeLRU();
            }
        }
    }

    private void moveToHead(Node node) {
        removeNode(node);
        addNode(node);
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void addNode(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void removeLRU() {
        Node lru = tail.prev;
        cache.remove(lru.key);
        removeNode(lru);
    }
}

public class Least_Recently_Used {
    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(2); // Capacity of 2
        lruCache.put(1, 10);
        lruCache.put(2, 20);
        System.out.println(lruCache.get(1)); // Output: 10
        lruCache.put(3, 30); // Evicts key 2
        System.out.println(lruCache.get(2)); // Output: -1 (not found)
        System.out.println(lruCache.get(3)); // Output: 30
    }
}