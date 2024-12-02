import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            FileWriter fileWriter = new FileWriter("output.txt", true);
            if (args.length != 1)
            {
                fileWriter.append("There should be only 1 paremeter\n");
                fileWriter.close();
                return;
            }
            String inputString = args[0];
            try
            {
                File inputFile = new File(inputString);
                Scanner scanner = new Scanner(inputFile);
                try
                {
                    String line = scanner.nextLine();
                    List<Character> allChars = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' ');

                    for (int i = 0; i < line.length(); i++)
                    {
                        if (!allChars.contains(Character.toLowerCase(line.charAt(i))))
                        {
                            fileWriter.append("The input file should not contains unexpected characters\n");
                            fileWriter.close();
                            return;
                        }
                    }
                    fileWriter.append(line + "\n");
                    fileWriter.close();
                } catch (NoSuchElementException e)
                {
                    fileWriter.append("The input file should not be empty\n");
                    fileWriter.close();
                    return;
                }
            } catch (Exception e)
            {
                fileWriter.append("There should be an input file in the specified path\n");
                fileWriter.close();
                return;
            }

        } catch (IOException e) {}
    }
}