import java.lang.String;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.String.valueOf;

public class Main
{
    public static void main(String[] args)
    {
        String output_path = "src\\output.txt";
        FileOutput.writeToFile(output_path,"",false,false);
        String input_file = args[0];
        String input_path = String.format("src\\%s", input_file);
        String[] input_info = FileInput.readFile(input_path,
                true, true);
        for (int at_num = 0; at_num < input_info.length; at_num++)
        {
            String line = input_info[at_num];


            if (line.equals("Armstrong numbers up to:"))
            {
                List<Integer> all_armstongs = new ArrayList<>();
                int number1 = Integer.parseInt(input_info[at_num + 1]) + 1;
                for (int i = 0; i < number1; i++ )
                {
                    int digit_num = valueOf(i).length();
                    int sumofdigits = 0;
                    for(int a = 0; a < digit_num; a++)
                    {
                        char currentDigit = String.valueOf(i).charAt(a);
                        int currentNumber = currentDigit - '0';
                        sumofdigits += Math.pow(currentNumber, digit_num);
                    }
                    if (sumofdigits == i) all_armstongs.add(i);
                }
                String string_output = all_armstongs.toString();
                FileOutput.writeToFile(output_path, "Armstrong " +
                        "numbers up to " + (number1 - 1) + ":\n" + string_output.substring(1, string_output.length()-1) + "\n", true, true);
                System.out.println("Armstrong numbers up to " + (number1 - 1) + ":\n" + string_output.substring(1, string_output.length()-1) + "\n");
            }


            if (line.equals("Emirp numbers up to:"))
            {
                int number2 = Integer.parseInt(input_info[at_num + 1]) + 1;
                List<Integer> all_emirps = new ArrayList<>();
                for (int value = 10; value < number2; value++)
                {
                    Boolean is_prime = true;
                    for (int nums = 2; nums < value / 2 + 1; nums++ )
                    {
                        if (value % nums == 0) is_prime = false;

                    }
                    if (is_prime)
                    {
                        int digit_num = valueOf(value).length();
                        String reverse_num = "";
                        for (int s = digit_num -1; s > -1; s--)
                        {
                            reverse_num += valueOf(value).charAt(s);
                        }
                        int reverse_number = Integer.parseInt(reverse_num);
                        if (reverse_number == value) continue;

                        Boolean is_Prime = true;
                        for (int nums = 2; nums < reverse_number / 2 + 1; nums++ )
                        {
                            if (reverse_number % nums == 0) is_Prime = false;

                        }
                        if (is_Prime) all_emirps.add(value);
                    }
                }
                String string_output1 = all_emirps.toString();
                FileOutput.writeToFile(output_path, "Emirp " +
                        "numbers up to " + (number2 - 1) + ":\n"+ string_output1.substring(1, string_output1.length()-1) + "\n", true, true);
                System.out.println("Emirp numbers up to " + (number2 - 1) + ":\n" + string_output1.substring(1, string_output1.length()-1) + "\n");
            }

            if (line.equals("Abundant numbers up to:"))
            {
                int number3 = Integer.parseInt(input_info[at_num + 1]) + 1;
                List<Integer> all_abundants = new ArrayList<>();
                for (int val = 1; val < number3; val++)
                {
                    int sum_of_divisors = 0;
                    for (int m = 1; m < val; m++)
                    {
                        if (val % m == 0) sum_of_divisors += m;
                    }
                    if (sum_of_divisors > val) all_abundants.add(val);
                }
                String string_output2 = all_abundants.toString();
                FileOutput.writeToFile(output_path, "Abundant " +
                        "numbers up to " + (number3 - 1) + ":\n"+ string_output2.substring(1, string_output2.length()-1) + "\n", true, true);
                System.out.println("Abundant numbers up to " + (number3 - 1) + ":\n" + string_output2.substring(1, string_output2.length()-1) + "\n");
            }

            if (line.equals("Ascending order sorting:"))
            {
                List<Integer> all_numbers = new ArrayList<>();
                int num_at = at_num + 1;
                FileOutput.writeToFile(output_path, "Ascending " +
                        "order sorting:",true, true);
                while (!input_info[num_at].equals("-1"))
                {
                    all_numbers.add(Integer.parseInt(input_info[num_at]));
                    all_numbers.sort(Comparator.naturalOrder());
                    String string_output3 = all_numbers.toString();
                    FileOutput.writeToFile(output_path,
                            string_output3.substring(1, string_output3.length()-1), true, true);
                    num_at += 1;
                }
                FileOutput.writeToFile(output_path, "\n",true, false);
            }

            if (line.equals("Descending order sorting:"))
            {
                List<Integer> number_info = new ArrayList<>();
                int number_at = at_num + 1;
                FileOutput.writeToFile(output_path, "Descending " +
                        "order sorting:",true, true);
                while (!input_info[number_at].equals("-1"))
                {
                    number_info.add(Integer.parseInt(input_info[number_at]));
                    number_info.sort(Comparator.reverseOrder());
                    String string_output3 = number_info.toString();
                    FileOutput.writeToFile(output_path,
                            string_output3.substring(1, string_output3.length()-1), true, true);
                    number_at += 1;
                }
                FileOutput.writeToFile(output_path, "\n",true, false);
            }

            if (line.equals("Exit")) FileOutput.writeToFile(output_path, "Finished...",true, true);
        }
    }
}
