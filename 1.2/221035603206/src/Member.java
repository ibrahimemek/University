import java.time.LocalDate;
import java.util.ArrayList;

public class Member
{
    public static ArrayList<Member> allMembers = new ArrayList<>();
    private int memberId;
    private int currentMemberNumber = 1;
    public ArrayList<Integer> extendedBooks = new ArrayList<>();
    public ArrayList<Integer> borrowedBooks = new ArrayList<>();
    public ArrayList<Integer> inLibraryBooks = new ArrayList<>();



    /**
     Extends the current date by the specified time limit in days.
     @param currentDate The current date
     @param timeLimit The number of days to extend the date by
     @return The extended date
     **/
    public LocalDate extendBook(LocalDate currentDate, int timeLimit)
    {
        return currentDate.plusDays(timeLimit);
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
