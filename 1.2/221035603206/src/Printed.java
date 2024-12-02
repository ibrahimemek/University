import java.time.LocalDate;

public class Printed extends Book
{
    private LocalDate dueDate;

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     Borrows the book for the specified member and sets the due date.
     @param member The member borrowing the book
     @param date The date of borrowing in the format "YYYY-MM-DD"
     **/
    public void borrowBook(Member member, String date)
    {
        if (member instanceof Student)
        {
            dueDate = LocalDate.of(Integer.parseInt(date.substring(0,4)), Integer.parseInt(date.substring(5,7)),
                    Integer.parseInt(date.substring(8,10))).plusDays(7);
        }
        else
        {
            dueDate = LocalDate.of(Integer.parseInt(date.substring(0,4)), Integer.parseInt(date.substring(5,7)),
                    Integer.parseInt(date.substring(8,10))).plusDays(14);
        }
        setAvailable(false);
    }
}
