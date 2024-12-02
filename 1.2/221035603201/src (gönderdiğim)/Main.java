import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        try
        {

            File input_file = new File(String.format("%s", args[0]));
            Scanner line_reader = new Scanner(input_file);
            FileWriter file_writer = new FileWriter("output.txt");
            file_writer.write("");
            while(line_reader.hasNextLine())
            {
                String line = line_reader.nextLine();
                line = line.trim();
                if (line.equals("Armstrong numbers up to:"))
                {
                    String arms_until = line_reader.nextLine();
                    arms_until = arms_until.trim();
                    ArrayList<Integer> arms_output = Armstrong.check_arms(arms_until);
                    String all_arms = arms_output.toString().substring(1, arms_output.toString().length() - 1);
                    file_writer.append("Armstrong numbers up to "+ arms_until + ":\n" + all_arms.replace(",", "") + "\n\n");
                }
                if (line.equals("Emirp numbers up to:"))
                {
                    String emirps_until = line_reader.nextLine();
                    emirps_until = emirps_until.trim();
                    ArrayList<Integer> emirps_output = Emirp.check_emirps(emirps_until);
                    String all_emirps = emirps_output.toString().substring(1, emirps_output.toString().length() - 1);
                    file_writer.append("Emirp numbers up to "+ emirps_until + ":\n" + all_emirps.replace(",", "") + "\n\n");
                }
                if (line.equals("Abundant numbers up to:"))
                {
                    String abuns_until = line_reader.nextLine();
                    abuns_until = abuns_until.trim();
                    ArrayList<Integer> abundant_output = Abundant.check_abuns(abuns_until);
                    String all_emirps = abundant_output.toString().substring(1, abundant_output.toString().length() - 1);
                    file_writer.append("Abundant numbers up to "+ abuns_until + ":\n" + all_emirps.replace(",", "") + "\n\n");
                }
                if (line.equals("Ascending order sorting:"))
                {
                    ArrayList <Integer> ascend_list = new ArrayList<>();
                    file_writer.append("Ascending order sorting:\n");
                    while (true)
                    {
                        String cur_line = line_reader.nextLine().trim();
                        if (cur_line.equals("-1")) break;
                        ascend_list.add(Integer.parseInt(cur_line));
                        ascend_list.sort(Comparator.naturalOrder());
                        String ascend_output = ascend_list.toString().substring(1, ascend_list.toString().length() - 1);
                        file_writer.append(ascend_output.replace(",", "") + "\n");
                    }
                    file_writer.append("\n");
                }
                if (line.equals("Descending order sorting:")) {
                    ArrayList<Integer> descending_list = new ArrayList<>();
                    file_writer.append("Descending order sorting:\n");
                    while (true) {
                        String cur_line = line_reader.nextLine().trim();
                        if (cur_line.equals("-1")) break;
                        descending_list.add(Integer.parseInt(cur_line));
                        descending_list.sort(Comparator.reverseOrder());
                        String descend_output = descending_list.toString().substring(1, descending_list.toString().length() - 1);
                        file_writer.append(descend_output.replace(",", "") + "\n");
                    }
                    file_writer.append("\n");
                }
                if (line.equals("Exit"))
                {
                    file_writer.append("Finished...");
                    break;
                }
            }
            file_writer.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("The file is incorrectr.");
        }
        catch(IOException e)
        {
            System.out.println("The file is incorrect.");
        }
    }
}