import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

/**
 The Main class represents the entry point of the program.
 It reads input from command-line arguments, processes the commands,
 and writes the output to a file.
 **/
public class Main
{
 /**
 The main method of the program.
 @param args Command-line arguments containing input and output file paths
 @throws IOException If an error occurs while reading or writing the files
 **/
    public static void main(String[] args) throws IOException {
        String inputInfo = args[0];
        String outputInfo = args[1];
        File inputFile = new File(inputInfo);
        try {
            Scanner scanner = new Scanner(inputFile);
            FileWriter fileWriter = new FileWriter(outputInfo);
            fileWriter.write("");
            int memberId = 0;
            int bookId = 0;
            int printedBookNumber = 0;
            int handwrittenBookNumber = 0;
            int studentNumber = 0;
            int academicianNumber = 0;
            while (scanner.hasNextLine())
            {
                String currentLine = scanner.nextLine();
                String[] lineItems = currentLine.split("\t");
                Book currentBook;
                Member currentMember;
                switch (lineItems[0])
                {
                    case "addBook":
                        if (lineItems.length != 2) {fileWriter.append("Invalid command.\n"); break;}
                        switch (lineItems[1])
                        {
                            case "P":
                                bookId++;
                                Printed printed = new Printed();
                                printed.setBookId(bookId);
                                Book.allBooks.add(printed);
                                fileWriter.append("Created new book: Printed [id: " + bookId + "]\n");
                                printedBookNumber++;
                                break;
                            case "H":
                                bookId++;
                                Handwritten handwritten = new Handwritten();
                                handwritten.setBookId(bookId);
                                Book.allBooks.add(handwritten);
                                fileWriter.append("Created new book: Handwritten [id: " + bookId + "]\n");
                                handwrittenBookNumber++;
                                break;
                            default:
                                fileWriter.append("Invalid command.\n");
                                break;
                        }
                        break;

                    case "addMember":
                        switch (lineItems[1])
                        {
                            case "S":
                                memberId++;
                                Student student = new Student();
                                student.setMemberId(memberId);
                                Member.allMembers.add(student);
                                fileWriter.append("Created new member: Student [id: "+ memberId +"]\n");
                                studentNumber++;
                                break;
                            case "A":
                                memberId++;
                                Academician academician = new Academician();
                                academician.setMemberId(memberId);
                                Member.allMembers.add(academician);
                                fileWriter.append("Created new member: Academic [id: "+ memberId +"]\n");
                                academicianNumber++;
                                break;
                            default:
                                fileWriter.append("Invalid command.\n");
                                break;
                        }
                        break;

                    case "borrowBook":
                        currentBook = findBookObject(Integer.parseInt(lineItems[1]));
                        if (currentBook == null) {fileWriter.append("There is no book with that id\n"); break;}
                        currentMember = findMemberObject(Integer.parseInt(lineItems[2]));
                        if (currentBook instanceof Handwritten) {fileWriter.append("You cannot borrow this book!\n"); break;}
                        if (!( currentBook).isAvailable()){fileWriter.append("You cannot borrow this book!\n"); break;}
                        if (currentMember instanceof Academician)
                        {
                            if (((Academician) currentMember).getMaxBookNumber() == ((Academician) currentMember).getCurrentBookNumber())
                            {
                                fileWriter.append("You have exceeded the borrowing limit!\n");
                                break;
                            }
                            ((Academician) currentMember).setCurrentBookNumber(((Academician) currentMember).getCurrentBookNumber() + 1);
                        }
                        else
                        {
                            if (((Student) currentMember).getMaxBookNumber() == ((Student) currentMember).getCurrentBookNumber())
                            {
                                fileWriter.append("You have exceeded the borrowing limit!\n");
                                break;
                            }
                            ((Student) currentMember).setCurrentBookNumber(((Student) currentMember).getCurrentBookNumber() + 1);

                        }
                        ((Printed) currentBook).borrowBook(currentMember, lineItems[3]);
                        currentMember.borrowedBooks.add(currentBook.getBookId());
                        Book.borrowedBooks.add(currentBook);
                        fileWriter.append(String.format("The book [%d] was borrowed by member [%d] at %s\n",
                                currentBook.getBookId(), currentMember.getMemberId(), lineItems[3]));
                        currentBook.setBookInfo(String.format("The book [%d] was borrowed by member [%d] at %s",
                                currentBook.getBookId(), currentMember.getMemberId(), lineItems[3]));
                        break;

                    case "returnBook":
                        currentBook = findBookObject(Integer.parseInt(lineItems[1]));
                        if (currentBook == null) {fileWriter.append("There is no book with that id\n"); break;}
                        currentMember = findMemberObject(Integer.parseInt(lineItems[2]));
                        if (currentMember.inLibraryBooks.contains(currentBook.getBookId()))
                        {
                            currentBook.setAvailable(true);
                            currentMember.inLibraryBooks.remove(currentMember.inLibraryBooks.indexOf(currentBook.getBookId()));
                            Book.inLibraryBooks.remove(Book.inLibraryBooks.indexOf(currentBook));
                            fileWriter.append(String.format("The book [%d] was returned by member [%d] at %s Fee: 0\n", currentBook.getBookId(),
                                    currentMember.getMemberId(), lineItems[3]));
                        }
                        else if (currentMember.borrowedBooks.contains(currentBook.getBookId()))
                        {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // The format of the date string
                            LocalDate date = LocalDate.parse(lineItems[3], formatter);
                            if (date.compareTo(((Printed) currentBook).getDueDate()) > 0)
                            {
                                long fee = ChronoUnit.DAYS.between(((Printed) currentBook).getDueDate(), date);
                                fileWriter.append(String.format("The book [%d] was returned by member [%d] at %s Fee: %d\n", currentBook.getBookId(),
                                        currentMember.getMemberId(), lineItems[3], fee));
                            }
                            else
                            {
                                fileWriter.append(String.format("The book [%d] was returned by member [%d] at %s Fee: 0\n", currentBook.getBookId(),
                                        currentMember.getMemberId(), lineItems[3]));
                            }
                            currentBook.setAvailable(true);
                            currentMember.borrowedBooks.remove(currentMember.borrowedBooks.indexOf(currentBook.getBookId()));
                            Book.borrowedBooks.remove(Book.borrowedBooks.indexOf(currentBook));
                        }
                        else
                        {
                            fileWriter.append("You can't return this book\n");
                        }
                        break;

                    case "extendBook":
                        currentBook = findBookObject(Integer.parseInt(lineItems[1]));
                        if (currentBook == null) {fileWriter.append("There is no book with that id\n"); break;}
                        currentMember = findMemberObject(Integer.parseInt(lineItems[2]));
                        if (currentBook instanceof Handwritten) {fileWriter.append("You cannot extend this book!\n"); break;}
                        if (currentMember.extendedBooks != null && currentMember.extendedBooks.contains(currentBook.getBookId()))
                        {
                            fileWriter.append("You cannot extend the deadline!\n");
                            break;
                        }
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // The format of the date string
                        LocalDate date = LocalDate.parse(lineItems[3], formatter);
                        if (currentMember instanceof Student)
                        {
                            ((Printed) currentBook).setDueDate(((Student) currentMember).extendBook(date));
                        }
                        else {((Printed) currentBook).setDueDate(((Academician) currentMember).extendBook(date));}
                        currentMember.extendedBooks.add(currentBook.getBookId());
                        fileWriter.append(String.format("The deadline of book [%d] was extended by member [%d] at %s\n",
                                        currentBook.getBookId(), currentMember.getMemberId(), lineItems[3]));
                        fileWriter.append("New deadline of book [" + currentBook.getBookId() + "] is " + ((Printed) currentBook).getDueDate() + "\n");
                        break;

                    case "readInLibrary":
                        currentBook = findBookObject(Integer.parseInt(lineItems[1]));
                        if (currentBook == null) {fileWriter.append("There is no book with that id\n"); break;}
                        currentMember = findMemberObject(Integer.parseInt(lineItems[2]));
                        if (currentBook instanceof Handwritten && currentMember instanceof Student)
                        {
                            fileWriter.append("Students can not read handwritten books!\n");
                            break;
                        }
                        if (!currentBook.isAvailable())
                        {
                            fileWriter.append("You can not read this book!\n");
                            break;
                        }
                        currentBook.setAvailable(false);
                        currentMember.inLibraryBooks.add(currentBook.getBookId());
                        Book.inLibraryBooks.add(currentBook);
                        fileWriter.append(String.format("The book [%d] was read in library by member [%d] at %s\n",
                                currentBook.getBookId(), currentMember.getMemberId(), lineItems[3]));
                        currentBook.setBookInfo(String.format("The book [%d] was read in library by member [%d] at %s",
                                currentBook.getBookId(), currentMember.getMemberId(), lineItems[3]));
                        break;

                    case "getTheHistory":
                        fileWriter.append("History of library:\n");
                        fileWriter.append("\nNumber of students: " + studentNumber + "\n");
                        for (Member member : Member.allMembers)
                        {
                            if (member instanceof Student)
                            {fileWriter.append(String.format("Student [id: %d]\n", member.getMemberId()));}
                        }
                        fileWriter.append("\nNumber of academics: " + academicianNumber + "\n");
                        for (Member member : Member.allMembers)
                        {
                            if (member instanceof Academician)
                            {fileWriter.append(String.format("Academic [id: %d]\n", member.getMemberId()));}
                        }
                        fileWriter.append("\nNumber of printed books: " + printedBookNumber + "\n");
                        for (Book book : Book.allBooks)
                        {
                            if (book instanceof Printed)
                            {fileWriter.append(String.format("Printed [id: %d]\n", book.getBookId()));}
                        }
                        fileWriter.append("\nNumber of handwritten books: " + handwrittenBookNumber + "\n");
                        for (Book book : Book.allBooks)
                        {
                            if (book instanceof Handwritten)
                            {fileWriter.append(String.format("Handwritten [id: %d]\n", book.getBookId()));}
                        }
                        fileWriter.append("\nNumber of borrowed books: " + Book.borrowedBooks.size() + "\n");
                        for (Book eachBook : Book.borrowedBooks)
                        {
                            fileWriter.append(eachBook.getBookInfo() + "\n");
                        }
                        fileWriter.append("\nNumber of books read in library: " + Book.inLibraryBooks.size() + "\n");
                        for (Book eachBook : Book.inLibraryBooks)
                        {
                            fileWriter.append(eachBook.getBookInfo() + "\n");
                        }
                        break;
                }
            }
            fileWriter.close();


        } catch (FileNotFoundException e) {
            System.out.println("There is no file with that name.");
        }
    }

    /**
     Finds a book object with the specified ID.
     @param id The ID of the book to find
     @return The book object with the given ID, or null if not found
     **/
    public static Book findBookObject(int id)
    {
        for (Book eachBook : Book.allBooks)
        {
            if (eachBook.getBookId() == id)
            {
                return eachBook;
            }
        }
        return null;
    }

    /**
     Finds a member object with the specified ID.
     @param id The ID of the member to find
     @return The member object with the given ID, or null if not found
     **/
    public static Member findMemberObject(int id)
    {
        for (Member eachMember : Member.allMembers)
        {
            if (eachMember.getMemberId() == id)
            {
                return eachMember;
            }
        }
        return null;
    }
}