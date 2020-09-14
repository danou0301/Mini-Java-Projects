/**
 * an abstract class implementing SimpleSet.
 */
abstract public class SimpleHashSet implements SimpleSet {

    private final static float DEFAULT_HIGHER_CAPACITY = 0.75f;
    private final static float DEFAULT_LOWER_CAPACITY = 0.25f;
    protected final static int INITIAL_CAPACITY = 16;
    protected final static int REHASH_FACTOR = 2;
    protected final static int CST_INVERSE_QUADRA = 2;
    private final static int INITIAL_SIZE = 0;

    protected static final String DELETED_CELL = "deleted";
    protected static final String UP_REHASHING = "add";
    protected static final String DOWN_REHASHING = "delete";

    protected int capacity;
    protected float upperLoadFactor;
    protected float lowerLoadFactor;
    protected int currentSize;

    /*-----Constructors-----*/
    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16), upper load
     * factor (0.75) and lower load factor (0.25).
     */
    SimpleHashSet(){
        currentSize = INITIAL_SIZE;
        capacity = INITIAL_CAPACITY;
        upperLoadFactor = DEFAULT_HIGHER_CAPACITY;
        lowerLoadFactor = DEFAULT_LOWER_CAPACITY;
    }


    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upLoadFactor The upper load factor of the hash table.
     * @param lowLoadFactor The lower load factor of the hash table.
     */
    SimpleHashSet(float upLoadFactor, float lowLoadFactor){
        currentSize = INITIAL_SIZE;
        capacity = INITIAL_CAPACITY;
        upperLoadFactor = upLoadFactor;
        lowerLoadFactor = lowLoadFactor;
    }

    /*-----Methods-----*/
    /**
     * @return The current capacity (number of cells) of the table.
     */
    public int capacity() {
        return capacity;
    }

    /**
     * @return The number of elements currently in the set
     */
    public int size() {
        return currentSize;
    }


    /**
     * Clamps hashing indices to fit within the current table capacity
     * @param index the index before clamping
     * @return an index properly clamped
     */
    protected int clamp(int index) {
        return index&(capacity-1);
    }

    /**
     * @return The lower load factor of the table.
     */
    protected float getLowerLoadFactor(){
        return lowerLoadFactor;
    }

    /**
     * @return The higher load factor of the table.
     */
    protected float getUpperLoadFactor(){
        return  upperLoadFactor;
    }

    protected float getLoadFactor(){
        return (float)(currentSize)/(float)(capacity);
    }

    protected boolean check_factor(String addOrDelete){
        if (addOrDelete.equals(UP_REHASHING)){ // checks if the table isn’t too full
            if (getLoadFactor() > getUpperLoadFactor()) {
                capacity *= REHASH_FACTOR;
                return true;
            }
        } else if (addOrDelete.equals(DOWN_REHASHING)){ // check if the table isn't too empty
            if (getLoadFactor() < getLowerLoadFactor() && capacity > 1) {
                capacity /= REHASH_FACTOR;
                return true;
            }
        }
        return false;
    }
}
