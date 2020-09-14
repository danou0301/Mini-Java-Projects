import java.util.LinkedList;
/**
 * This is a class that holds a linked list. In the openhashset, we will have an array of that class.
 */
public class MyLinkedList {
    /** this represents the linked list. */
    private LinkedList<String> theLinkedList = new LinkedList<>();

    public LinkedList<String> getList(){
        return theLinkedList;
    }
}
