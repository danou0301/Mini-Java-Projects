/**
 * This class represents a library patron that has a name and assigns values to different
 * literary aspects of books.
 */
public class Patron {

    final String firstName;
    final String lastName;
    final int comicTend;
    final int dramaticTend;
    final int educationalTend;
    final int enjoymentThreshold;
    int numBorrowed;

    /*----=  Constructors  =-----*/
    /**
     * Creates a new patron with the given characteristics.
     * @param patronFirstName - The first name of the patron..
     * @param patronLastName - The last name of the patron.
     * @param comicTendency - The weight the patron assigns to the comic aspects of books.
     * @param dramaticTendency - The weight the patron assigns to the dramatic aspects of books.
     * @param educationalTendency - The weight the patron assigns to the educational aspects of books.
     * @param patronEnjoymentThreshold - The minimal literary value a book must have for this patron to enjoy it.
     */
    Patron(String patronFirstName, String patronLastName, int comicTendency, int dramaticTendency,
           int educationalTendency, int patronEnjoymentThreshold){

        firstName = patronFirstName;
        lastName = patronLastName;
        comicTend = comicTendency;
        dramaticTend = dramaticTendency;
        educationalTend = educationalTendency;
        enjoymentThreshold = patronEnjoymentThreshold;
        numBorrowed = 0;

    }

    /*----=  Instance Methods  =-----*/

    /**
     * Returns a string representation of the patron, which is a sequence of its first and last name,
     * separated by a single white space. For example, if the patron's first name is "Ricky" and his
     * last name is "Bobby", this method will return the String "Ricky Bobby".
     * @return the String representation of this patron.
     */
    String stringRepresentation(){

        return firstName + " " + lastName;
    }

    /**
     * Returns the literary value this patron assigns to the given book.
     * @param book - The book to asses.
     */
    int getBookScore(Book book){
        return comicTend * book.comicValue + dramaticTend * book.dramaticValue +
                educationalTend * book.educationalValue;
    }

    /**
     * Returns true of this patron will enjoy the given book, false otherwise.
     * @param book - The book to asses.
     * @return true of this patron will enjoy the given book, false otherwise.
     */
    boolean willEnjoyBook(Book book) {
        return getBookScore(book) > enjoymentThreshold;
    }

}
