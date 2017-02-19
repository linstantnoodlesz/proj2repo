package synthesizer;

import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    //Private class for array ring buffer that implements iterator
    private class ArrayRingBufferIterator implements Iterator {

        //Gives the current index of the array ring buffer for the iterator,
        //Initialized at first
        private int currentIndex;

        private ArrayRingBufferIterator() {
            currentIndex = first;
        }

        public boolean hasNext() {
            return currentIndex < capacity();
        }

        public T next() {
            T item = rb[currentIndex];
            currentIndex += 1;
            return item;
        }
    }

    /**
     * Iterator method for array ring buffer.; returns array ring buffer iterator
     */
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        //Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
        rb = (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        } else {
            rb[last] = (T) x;
            last = (last + 1) % capacity;
            fillCount += 1;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            T dequeuedItem = rb[first];
            rb[first] = null;
            first = (first + 1) % capacity;
            fillCount -= 1;
            return dequeuedItem;
        }
    }
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

}
