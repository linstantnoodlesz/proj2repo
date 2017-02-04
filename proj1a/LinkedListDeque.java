public class LinkedListDeque<Item> {

    //This implementation of the linked list double ended queue uses a circular topology, so each
    //node has a previous and next pointer.

    //The size of the linked list deque cached.
    private int size;

    //Declares a sentinel node that "protects" the naked linked list data structure.
    private Node sentinel;

    //Inner class used to create node objects that link together. These are the primary objects
    //that make up the linked list deque. Marked private to block invalid manipulation.
    private class Node {

        //The node's value of type Item.
        private Item value;

        //Each node points to a previous node and a next node. Allows each node to reference
        // the next and previous nodes.
        private Node prev, next;

        /* Node constructor */
        private Node(Item val, Node prev, Node next) {
            value = val;
            this.prev = prev;
            this.next = next;
        }
    }

    /**
     * Public constructor to create an empty linked list deque
     */
    public LinkedListDeque() {
        //Empty linked list deque starts with size 0
        size = 0;

        //Sentinel value is not accessed, so we give it value null. Note that this is arbitrary.
        //The deque is empty, so sentinel points to itself.
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    /**
     * Adds item to the front of the deque
     */
    public void addFirst(Item item) {
        size += 1;
        //The added node with value item has sentinel as its previous and the original sentinel.next
        //as its next. sentinel.next will now be the second item in the deque.
        Node nodeToAdd = new Node(item, sentinel, sentinel.next);
        //Assigns the previous pointer of the original first element to nodeToAdd
        sentinel.next.prev = nodeToAdd;
        //Assigns the next pointer of sentinel to nodeToAdd
        sentinel.next = nodeToAdd;
    }

    /**
     * Adds item to the end of the deque
     */
    public void addLast(Item item) {
        size += 1;
        //The added node is at the end of the deque, so its next will be the sentinel, and its
        //previous will be the original last node of the deque, which is accessed by sentinel.prev
        Node nodeToAdd = new Node(item, sentinel.prev, sentinel);
        //Assigns the next pointer of the original last element to nodeToAdd
        sentinel.prev.next = nodeToAdd;
        //Assigns the previous pointer of sentinel to nodeToAdd
        sentinel.prev = nodeToAdd;
    }

    /**
     * Checks if the deque is empty by checking if its size is 0
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Public selector method that returns the deque's size
     */
    public int size() {
        return size;
    }

    /**
     * Prints the elements of the deque from first to last separated by a space
     */
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println("");
    }

    /**
     * Removes the first node in the deque by removing all references to the node
     */
    public Item removeFirst() {
        //Catches the case where the deque is empty. In this case, return null.
        if (size == 0) {
            return null;
        }
        size -= 1;
        //Creates reference to the original first element since it will be removed, and references
        //to it will be reassigned
        Node nodeToRemove = sentinel.next;
        //Assigns previous pointer of second element to sentinel, removing the reference to
        //nodeToRemove. Consistent with case size = 1, assigns sentinel.prev to itself
        sentinel.next.next.prev = sentinel;
        //The next pointer of sentinel is reassigned to point to the original second node so that
        //this is the new first node.
        sentinel.next = sentinel.next.next;
        //Removes references of nodeToRemove
        nodeToRemove.prev = null;
        nodeToRemove.next = null;
        return nodeToRemove.value;
    }

    /**
     * Removes the last node in the deque by removing all references to the node
     */
    public Item removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        //Creates reference to the original last element since it will be removed, and references
        //to it will be reassigned
        Node nodeToRemove = sentinel.prev;
        //Assigns next pointer of second-to-last node to sentinel. Similar to removeLast, it still
        //works with size = 1
        sentinel.prev.prev.next = sentinel;
        //Assigns previous pointer of sentinel to the original second-to-last node,
        //removing references to nodeToRemove. This node is the new last node.
        sentinel.prev = sentinel.prev.prev;
        nodeToRemove.prev = null;
        nodeToRemove.next = null;
        return nodeToRemove.value;
    }

    /**
     * Returns the item of the node at the given index in the deque; implemented iteratively
     */
    public Item get(int index) {
        //Catches case where index is out of bounds and returns null
        if (index < 0 || index >= this.size) {
            return null;
        }
        //Creates reference to node at index 0
        Node p = sentinel.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.value;
    }

    /**
     * Get method implemented recursively
     */
    public Item getRecursive(int index) {
        if (index < 0 || index >= this.size) {
            return null;
        }
        Node pointer = sentinel.next;
        return helper(index, pointer);
    }

    /**
     * Helper method to implement get recursively; uses a tracker node as a parameter
     */
    private Item helper(int i, Node p) {
        if (i == 0) {
            return p.value;
        }
        return helper(i - 1, p.next);
    }

}
