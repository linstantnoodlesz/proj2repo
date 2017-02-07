/**
 * Created by Joseph on 2/4/2017.
 */
public interface Deque<Item> {

    void addFirst(Item item);

    void addLast(Item item);

    boolean isEmpty();

    public int size();

    void printDeque();

    Item removeFirst();

    Item removeLast();

    Item get(int index);

}
