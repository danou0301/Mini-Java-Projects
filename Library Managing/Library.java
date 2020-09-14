/**
 * This class represents a library, which hold a collection of books. Patrons can register at the library to
 * be able to check out books, if a copy of the requested book is available.
 */
class Library {

    /* The maximal number of books this library can hold. */
    final int maxBookCap;

    /* The maximal number of books this library allows a single patron to borrow at the same time. */
    final int maxBorrowed;

    /* The maximal number of registered patrons this library can handle. */
    final int maxPatronCap;

    /* The Book Array */
    Book[] bookArray;

    /* The Patron Array */
    Patron[] patronArray;


    /*----=  Constructors  =-----*/
    /**
     * Creates a new patron with the given characteristics.
     * @param maxBookCapacity   - The maximal number of books this library can hold.
     * @param maxBorrowedBooks  - The maximal number of books this library allows a single patron to borrow
     *                            at the same time.
     * @param maxPatronCapacity - The maximal number of registered patrons this library can handle.
     */
    Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity) {
        maxBookCap = maxBookCapacity;
        maxBorrowed = maxBorrowedBooks;
        maxPatronCap = maxPatronCapacity;
        bookArray = new Book[maxBookCap];
        patronArray = new Patron[maxPatronCap];
    }

    /*----= Instance Methods =-----*/
    /**
     * Adds the given book to this library, if there is place available, and it isn't already in the library.
     * @param book - The book to add to this library.
     * @return a non-negative id number for the book if there was a spot and the book was successfully added,
     * or if the book was already in the library; a negative number otherwise.
     */
    int addBookToLibrary(Book book) {

        int alreadyBookId = getBookId(book);
        int lastBook = getBookId(null);

        if (alreadyBookId != -1){  // if the book already in the library
            return alreadyBookId;
        } else if(lastBook != -1){
            bookArray[lastBook] = book;
            return lastBook;
        }
        return -1;
    }

    /**
     * Returns true if the given number is an id of some book in the library, false otherwise.
     * @param bookId - The id to check.
     * @return true if the given number is an id of some book in the library, false otherwise.
     */
    boolean isBookIdValid(int bookId) {
        return  (bookId <= maxBookCap && bookId >= 0 && bookArray[bookId] != null);

    }

    /**
     * Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     * @param book - The book for which to find the id number.
     * @return a non-negative id number of the given book if he is owned by this library, -1 otherwise.
     */
    int getBookId(Book book) {
        for (int i = 0; i < maxBookCap; i++) {
            if (bookArray[i] == book) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns true if the book with the given id is available, false otherwise.
     * @param bookId - The id number of the book to check.
     * @return true if the book with the given id is available, false otherwise.
     */
    boolean isBookAvailable(int bookId) {
        if (isBookIdValid(bookId)) {
            if (bookArray[bookId].getCurrentBorrowerId() == Book.NOTBORROWEDID) {
                return true;
            }
        }
        return false;
    }

    /**
     * Registers the given Patron to this library, if there is a spot available.
     *
     * @param patron - The patron to register to this library.
     * @return a non-negative id number for the patron if there was a spot and the patron was successfully
     * registered, a negative number otherwise.
     */
    int registerPatronToLibrary(Patron patron) {
        int alreadyPatronId = getPatronId(patron);
        int lastPatron = getPatronId(null);

        if (alreadyPatronId != -1){  // if the book already in the library
            return alreadyPatronId;
        } else if(lastPatron != -1){
            patronArray[lastPatron] = patron;
            return lastPatron;
        }
        return -1;
    }

    /**
     * Returns true if the given number is an id of a patron in the library, false otherwise.
     *
     * @param patronId - The id to check.
     * @return true if the given number is an id of a patron in the library, false otherwise.
     */
    boolean isPatronIdValid(int patronId) {
        return (patronId < maxPatronCap && patronId >= 0 && patronArray[patronId] != null);
    }

    /**
     * Returns the non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     *
     * @param patron - The patron for which to find the id number.
     * @return a non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     */
    int getPatronId(Patron patron) {
        for (int i = 0; i < maxPatronCap; i++) {
            if (patronArray[i] == patron) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id, if this
     * book is available, the given patron isn't already borrowing the maximal number of books allowed, and
     * if the patron will enjoy this book.
     *
     * @param bookId   - The id number of the book to borrow.
     * @param patronId - The id number of the patron that will borrow the book.
     * @return true if the book was borrowed successfully, false otherwise.
     */
    boolean borrowBook(int bookId, int patronId) {
        if (isPatronIdValid(patronId) && isBookAvailable(bookId)){
            if (patronArray[patronId].willEnjoyBook(bookArray[bookId])){
                if (patronArray[patronId].numBorrowed < maxBorrowed){
                    patronArray[patronId].numBorrowed++;
                    bookArray[bookId].setBorrowerId(patronId);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return the given book.
     *
     * @param bookId - The id number of the book to return.
     */
    void returnBook(int bookId) {
        if (isBookIdValid(bookId) && bookArray[bookId].currentBorrowerId != Book.NOTBORROWEDID) {
            patronArray[bookArray[bookId].getCurrentBorrowerId()].numBorrowed--;
            bookArray[bookId].returnBook();
        }
    }

    /**
     * Suggest the patron with the given id the book he will enjoy the most, out of all available books he
     * will enjoy, if any such exist.
     *
     * @param patronId - The id number of the patron to suggest the book to.
     * @return The available book the patron with the given will enjoy the most. Null if no book is available.
     */
    Book suggestBookToPatron(int patronId) {
        Book topBook = null;
        if (isPatronIdValid(patronId)) {
            Patron patron1 = patronArray[patronId];
            int topScore = patron1.enjoymentThreshold;
            for (int i = 0; i < maxBookCap; i++) {
                if (isBookAvailable(i) && patron1.getBookScore(bookArray[i]) > topScore) {
                    topBook = bookArray[i];
                    topScore = patron1.getBookScore(topBook);
                }
            }
        }
        return topBook;
    }


}
