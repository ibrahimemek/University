import java.time.LocalDate;
import java.util.ArrayList;

public class Student extends Member
{
    private int maxBookNumber = 2;
    private int currentBookNumber = 0;

    public int getCurrentBookNumber() {
        return currentBookNumber;
    }

    public void setCurrentBookNumber(int currentBookNumber) {
        this.currentBookNumber = currentBookNumber;
    }

    private int timeLimit = 7;


    public int getMaxBookNumber() {
        return maxBookNumber;
    }

    /**
     Extends the current date using the default time limit.
     @param currentDate The current date
     @return The extended date
     **/
    public LocalDate extendBook(LocalDate currentDate) {
        return super.extendBook(currentDate, timeLimit);
    }
}
