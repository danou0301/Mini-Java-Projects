/**
 * a hash-set based on chaining. Extends SimpleHashSet.
 */
public class OpenHashSet extends SimpleHashSet {

    private MyLinkedList[] OpenHTable;

    /*-----Constructors-----*/
    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16), upper load
     * factor (0.75) and lower load factor (0.25).
     */
    public OpenHashSet(){
        super();
        OpenHTable = new MyLinkedList[capacity];
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upLoadFactor - The upper load factor of the hash table.
     * @param lowLoadFactor - The lower load factor of the hash table.
     */
    public OpenHashSet(float upLoadFactor, float lowLoadFactor){
        super(upLoadFactor, lowLoadFactor);
        OpenHTable = new MyLinkedList[capacity];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
     * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75),
     * and lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public OpenHashSet(String[] data){
        this();
        for (String value : data){
            add(value);
        }
    }

    /*-----Methods-----*/
    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public boolean add(String newValue) {
        if (!this.contains(newValue)){ // check if the value not in the hash table
            currentSize++;
            if (check_factor(UP_REHASHING)){  // check rehashing
                rehash();
            }
            add_item(newValue, OpenHTable); // add it
            return true;
        }
        return false;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public boolean contains(String searchVal) {
        int index = clamp(searchVal.hashCode());
        return OpenHTable[index] != null && OpenHTable[index].getList().contains(searchVal);

    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public boolean delete(String toDelete) {
        if (this.contains(toDelete)){
            int index = clamp(toDelete.hashCode());
            if (OpenHTable[index].getList().contains(toDelete)) { // checks if the value is in the table
                OpenHTable[index].getList().remove(toDelete);
                currentSize--;
                if (check_factor(DOWN_REHASHING)){
                    rehash();
                }
                return true;
            }
        }
        return false;
    }


    /**
     * add a value to a specify hash table
     * @param item value to add
     * @param HTable Hash table who want to add the value
     */
    private void add_item(String item, MyLinkedList[] HTable){
        int index = clamp(item.hashCode());
        if (HTable[index] == null){
            HTable[index] = new MyLinkedList();
        }
        HTable[index].getList().add(item);
    }

    /**
     * rehash the table when the load factor is bad, increase or decrease the capacity of the hash table
     */
    private void rehash(){
            MyLinkedList[] newOpenHTable = new MyLinkedList[capacity]; // creates a new table with the new capacity
            for (MyLinkedList data: OpenHTable){
                if (data != null){
                    for (String item : data.getList()){
                        add_item(item, newOpenHTable);
                    }
                }
            }
            OpenHTable = newOpenHTable;
    }

}
