package main.interfaces;

public interface QueueInterface extends LinkedListInterface {
    void enqueue(int value);
    void dequeue();
    int sizeQ();
    boolean isEmpty();
    boolean isFull();
}
