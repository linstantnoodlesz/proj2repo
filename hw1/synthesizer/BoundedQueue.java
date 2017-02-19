package synthesizer;

import java.util.Iterator;

/**
 * Created by Joseph on 2/17/2017.
 */
public interface BoundedQueue<T> extends Iterable<T> {

    int capacity();    // return size of the buffer

    int fillCount();   // return number of items currently in the buffer

    void enqueue(T x);    // add item x to the end

    T dequeue();    // delete and return item from the front

    T peek();    // return (but do not delete) item from the front

    Iterator<T> iterator();

    /* Checks if the buffer is empty by checking if fillCount equals zero */
    default boolean isEmpty()  {
        return fillCount() == 0;
    }

    /* Checks if buffer is full by checking if fillCount equals capacity */
    default boolean isFull() {
        return fillCount() == capacity();
    }
}
