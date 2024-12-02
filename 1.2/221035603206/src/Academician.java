import java.time.LocalDate;

public class Academician extends Member
{
    private int maxBookNumber = 4;
    private int currentBookNumber = 0;

    public int getCurrentBookNumber() {
        return currentBookNumber;
    }

    public void setCurrentBookNumber(int currentBookNumber) {
        this.currentBookNumber = currentBookNumber;
    }

    private int timeLimit = 14;

    public int getMaxBookNumber() {
        return maxBookNumber;
    }

    public void setMaxBookNumber(int maxBookNumber) {
        this.maxBookNumber = maxBookNumber;
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
