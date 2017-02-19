package synthesizer;

/**
 * Created by Joseph on 2/17/2017.
 */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {

    protected int fillCount;
    protected int capacity;

    /*Selector method; returns queue's capacity */
    public int capacity() {
        return capacity;
    }

    /*Selector method; returns fill count of queue */
    public int fillCount() {
        return fillCount;
    }

}
