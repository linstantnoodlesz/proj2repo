/**
 * Created by Joseph on 2/4/2017.
 */

import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDeque1B {

    @Test
    public void addAndRemoveRandom() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        OperationSequence opSequence = new OperationSequence();
        DequeOperation opAddFirst;
        DequeOperation opAddLast;
        DequeOperation opRemoveFirst;
        DequeOperation opRemoveLast;
        DequeOperation opGet;
        String message;

        for (int i = 0; i < 50; i++) {
            int randomInt = StdRandom.uniform(4);
            int index;
            switch (randomInt) {
                case 0:
                    sad.addFirst(i);
                    ads.addFirst(i);
                    opAddFirst = new DequeOperation("addFirst", i);
                    opGet = new DequeOperation("get", 0);
                    opSequence.addOperation(opAddFirst);
                    opSequence.addOperation(opGet);
                    message = opSequence.toString();
                    assertEquals(message, ads.get(0), sad.get(0));
                    index = StdRandom.uniform(ads.size());
                    opGet = new DequeOperation("get", index);
                    opSequence.addOperation(opGet);
                    message = opSequence.toString();
                    assertEquals(message, ads.get(index), sad.get(index));
                    break;
                case 1:
                    opRemoveFirst = new DequeOperation("removeFirst");
                    opSequence.addOperation(opRemoveFirst);
                    message = opSequence.toString();
                    assertEquals(message, ads.removeFirst(), sad.removeFirst());
                    if (ads.size() > 0) {
                        index = StdRandom.uniform(ads.size());
                        opGet = new DequeOperation("get", index);
                        opSequence.addOperation(opGet);
                        message = opSequence.toString();
                        assertEquals(message, ads.get(index), sad.get(index));
                    }
                    break;
                case 2:
                    sad.addLast(i);
                    ads.addLast(i);
                    opAddLast = new DequeOperation("addLast", i);
                    opGet = new DequeOperation("get", ads.size() - 1);
                    opSequence.addOperation(opAddLast);
                    opSequence.addOperation(opGet);
                    message = opSequence.toString();
                    assertEquals(message, ads.get(ads.size() - 1), sad.get(sad.size() - 1));
                    index = StdRandom.uniform(ads.size());
                    opGet = new DequeOperation("get", index);
                    opSequence.addOperation(opGet);
                    message = opSequence.toString();
                    assertEquals(message, ads.get(index), sad.get(index));
                    break;
                case 3:
                    opRemoveLast = new DequeOperation("removeLast");
                    opSequence.addOperation(opRemoveLast);
                    message = opSequence.toString();
                    assertEquals(message, ads.removeLast(), sad.removeLast());
                    if (ads.size() > 0) {
                        index = StdRandom.uniform(ads.size());
                        opGet = new DequeOperation("get", index);
                        opSequence.addOperation(opGet);
                        message = opSequence.toString();
                        assertEquals(message, ads.get(index), sad.get(index));
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
