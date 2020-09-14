/**
 * a hash-set based on closed-hashing with quadratic probing. Extends SimpleHashSet.
 */
public class ClosedHashSet extends SimpleHashSet{

    private static final int INDEX_NOT_FOUND = -1;
    private String[] CloseHTable;

    /*-----Constructors-----*/
    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16), upper load
     * factor (0.75) and lower load factor (0.25).
     */
    public ClosedHashSet() {
        super();
        CloseHTable = new String[INITIAL_CAPACITY];
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upLoadFactor The upper load factor of the hash table.
     * @param lowLoadFactor The lower load factor of the hash table.
     */
    public ClosedHashSet(float upLoadFactor, float lowLoadFactor){
        super(upLoadFactor, lowLoadFactor);
        CloseHTable = new String[INITIAL_CAPACITY];

    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
     * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75), and
     * lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public ClosedHashSet(String[] data) {
        this();
        for (String value : data) {
            this.add(value);
        }
    }


    /*-----Methods-----*/
    /**
     * find the index of a specify value in the closed hash table
     * @param value that we looking in the table
     * @return index of the value or -1 if it isn't in the table
     */
    private int find_index(String value){
        for (int i = 0; i < capacity; i++){
            int index = clamp(value.hashCode() + (i + i * i) / CST_INVERSE_QUADRA);
            if (CloseHTable[index] == null){
                break;
            }
            else if(CloseHTable[index].equals(value) && value != DELETED_CELL){
                return index;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public boolean add(String newValue) {
        if(contains(newValue)){
            return false;
        } else {
            currentSize++;
            if (check_factor(UP_REHASHING)){
                rehash();
            }
            int index = closed_clamping(newValue.hashCode());
            CloseHTable[index] = newValue;
        }
        return true;
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public boolean delete(String toDelete) {
        int index = find_index(toDelete);
        if (index != -1){
            CloseHTable[index] = DELETED_CELL;
            currentSize--;

            if (check_factor(DOWN_REHASHING)){
                rehash();
            }
            return true;
        }
        return false;
    }


    /**
     * Look for a specified value in the set
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public boolean contains(String searchVal) {
        return find_index(searchVal) != INDEX_NOT_FOUND;
    }

    /**
     * rehash the table when the load factor is bad, increase or decrease the capacity of the hash table
     */
    private void rehash(){
        String[] oldHTable = CloseHTable;
        CloseHTable = new String[capacity];

        for (String value : oldHTable) {
            if (value != null && value != DELETED_CELL) {
                CloseHTable[closed_clamping(value.hashCode())] = value;
            }
        }
    }

    /**
     * do the clamping for the closed hash table
     * @param index to clamp
     * @return valid index
     */
    private int closed_clamping(int index){
        for (int i = 0; i < capacity; i++){
            int new_index = clamp(index + (i + i * i) / CST_INVERSE_QUADRA);
            if (CloseHTable[new_index] == null || CloseHTable[new_index] == DELETED_CELL){
                return new_index;
            }
        }
        return index;
    }

}
