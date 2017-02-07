/**
 * Created by Joseph on 2/1/2017.
 */
public class ArrayDequeTest<Item> {

    public static void main(String[] args) {
        ArrayDeque<Integer> test = new ArrayDeque<>();
        test.addLast(0);
        test.addLast(1);
        test.addFirst(2);
        System.out.println(test.size());
        test.removeLast();
        test.printDeque();
    }
}
