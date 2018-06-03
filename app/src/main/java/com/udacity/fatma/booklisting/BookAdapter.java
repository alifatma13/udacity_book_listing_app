package com.udacity.fatma.booklisting;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Array adapter for the books
 */

public class BookAdapter extends ArrayAdapter<Book> {

    private ViewHolderBooks viewHolderBooks = null;

    /**
     * Constructs a new {@link BookAdapter}.
     *
     * @param context of the app
     * @param books   is the list of books, which is the data source of the adapter
     */
    BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    /**
     * Returns a list item view that displays information about the book at the given position
     * in the list of books.
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.


        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);

            viewHolderBooks = new ViewHolderBooks();
            viewHolderBooks.bookNameTextView = listItemView.findViewById(R.id.book_name);
            viewHolderBooks.authorNameTextView = listItemView.findViewById(R.id.author_name);
            viewHolderBooks.bookCoverImageView = listItemView.findViewById(R.id.image);
            viewHolderBooks.bookDescriptionTextView = listItemView.findViewById(R.id.book_description);
            listItemView.setTag(viewHolderBooks);
        } else {
            viewHolderBooks = (ViewHolderBooks) listItemView.getTag();
        }

        // Find the book at the given position in the list of books
        Book currentBook = getItem(position);

        // Get the Books name from the currentBook object and set this text on
        // the bookNameTextView.
        if (currentBook.getBookName() != null) {
            viewHolderBooks.bookNameTextView.setText(currentBook.getBookName());
        }
        // Get the Book's Author name from the currentBook object and set this text on
        // the authorNameTextView.
        viewHolderBooks.authorNameTextView.setText(currentBook.getAuthor());

        //get the bitmap of the book's cover image
        Bitmap imageBitmap = currentBook.getImageBitmap();

        // Set the bitmap to bookCoverImageView
        viewHolderBooks.bookCoverImageView.setImageBitmap(imageBitmap);


        //get the book's description from the current book and set this text on
        // the bookDescriptionTextView
        viewHolderBooks.bookDescriptionTextView.setText(currentBook.getDescription());

        // Return the list item view that is now showing the appropriate data
        return listItemView;

    }

}
