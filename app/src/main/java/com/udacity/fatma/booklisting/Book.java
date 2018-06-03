package com.udacity.fatma.booklisting;

import android.graphics.Bitmap;

/**
 * Book class having properties of book
 */

public class Book {

    /**
     * Name of the book
     */
    private String mBookName;

    /**
     * Name of the Author
     **/

    private String mAuthor;

    /**
     * URL of book's image
     */

    private Bitmap mImage;

    /**
     * URL of book's description
     */

    private String mDescription;

    /**
     * Constructs a new {@link Book} object.
     *
     * @param bookName is the name of the book
     * @param author   is the author of the book
     * @param image    is the URL of the image of book's cover
     */
    public Book(String bookName, String author, Bitmap image, String description) {
        mBookName = bookName;
        mAuthor = author;
        mImage = image;
        mDescription = description;
    }

    /**
     * Returns the name of the book
     */
    String getBookName() {
        return mBookName;

    }

    /**
     * Returns the name of the author
     */
    String getAuthor() {
        return mAuthor;
    }

    /**
     * Returns the URL of the book's cover image
     */

    Bitmap getImageBitmap() {
        return mImage;
    }

    /**
     * Returns the URL of the book's description
     */

    String getDescription() {
        return mDescription;
    }
}
