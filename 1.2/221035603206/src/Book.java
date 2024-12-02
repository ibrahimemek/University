import java.util.ArrayList;

public class Book
{
    public static ArrayList<Book> allBooks = new ArrayList<>();
    public static ArrayList<Book> borrowedBooks = new ArrayList<>();
    public static ArrayList<Book> inLibraryBooks= new ArrayList<>();
    private int bookId;
    private boolean isAvailable = true;

    public String getBookInfo() {
        return bookInfo;
    }

    public void setBookInfo(String bookInfo) {
        this.bookInfo = bookInfo;
    }

    private String bookInfo;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }


}
