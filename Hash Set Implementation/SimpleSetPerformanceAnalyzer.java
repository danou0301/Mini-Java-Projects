import java.util.TreeSet;
import java.util.LinkedList;
import java.util.HashSet;

/**
 * This class has a main method that measures the run-times of some data structures for some actions.
 */
public class SimpleSetPerformanceAnalyzer {

    private static final int NUMBER_OF_DATA_STRUCTURES = 5;

    private static SimpleSet[] dataStructures = new SimpleSet[NUMBER_OF_DATA_STRUCTURES];

    private static String[] dataStructuresNames = {"closedHashSet", "openHashSet", "treeSet",
            "linkedLIst", "hashSet"};

    private static String[] data1 = Ex3Utils.file2array("src/data1.txt");
    private static String[] data2 = Ex3Utils.file2array("src/data2.txt");

    private static final int INVERSE_NS_TO_MS = 1000000;

    private static final int WARMS_UP_TIME = 7000;

    /**
     * Initialise dataStructures
     */
    private static void InitialiseDataStructures(){

        dataStructures[0] = new ClosedHashSet();
        dataStructures[1] = new OpenHashSet();
        dataStructures[2] = new CollectionFacadeSet(new TreeSet<>());
        dataStructures[3] = new CollectionFacadeSet(new LinkedList<>());
        dataStructures[4] = new CollectionFacadeSet(new HashSet<>());

    }
    /**
     * Adding array of words to a data structure given.
     * @param words array of element to add on the data structure
     * @param dataStructure ClosedHashSet, OpenHashSet, treeSet, linkedLIst, or hashSet
     * @return running time for adding words
     */
    private static long AddingWords (String[] words, SimpleSet dataStructure){
        long timeBefore = System.nanoTime();
        for (String element : words){
            dataStructure.add(element);
        }
        return (System.nanoTime() - timeBefore) / INVERSE_NS_TO_MS;

    }


    /**
     *  Execute many times the action contains to measure the running time.
     * @param word : we are interesting to check
     * @param dataStructure the data structure we are within
     * @return running time for looking a value
     */
    private static long containsData(String word, SimpleSet dataStructure){
        long timeBefore = System.nanoTime();
        for (int i= 0; i < WARMS_UP_TIME; i++){
            dataStructure.contains(word);
        }
        return (System.nanoTime() - timeBefore) / WARMS_UP_TIME;

    }


    /**
     * Measure the run times  and print the results on the screen. The actions are : adding data1,
     * adding data2, contain("hi") when it is initialized with data1 and data2, contain("-13170890158") when
     * it is initialized with data1 and data2.
     * @param args
     */
    public static void main(String[] args) {
        InitialiseDataStructures();

        for (int i = 0; i < 5; i++){ // for each data structures with data1
            //1
            System.out.println("insert data1 to the " + dataStructuresNames[i] + " is "
                    + AddingWords(data1, dataStructures[i]) + " ms.");
            //3
            System.out.println("search 'hi' in " + dataStructuresNames[i] + " is "
                    + containsData("hi", dataStructures[i]) + " ns.");
            //4
            System.out.println("search '-13170890158' in " + dataStructuresNames[i] + " is "
                    + containsData("-13170890158", dataStructures[i]) + " ns.");
        }
        System.out.println();

        InitialiseDataStructures();
        for (int i = 0; i < 5; i++){  // for each data structures with data2
            //2

            System.out.println("insert data2 to the " + dataStructuresNames[i] + " is "
                    + AddingWords(data2, dataStructures[i]) + " ms.");
            //5
            System.out.println("search '23' in " + dataStructuresNames[i] + " is "
                    + containsData("23", dataStructures[i]) + " ns.");
            //6
            System.out.println("search 'hi' in " + dataStructuresNames[i] + " is "
                    + containsData("hi", dataStructures[i]) + " ns.");

        }

    }

}
