import java.util.ArrayList;

public class Armstrong
{
    public static ArrayList<Integer> check_arms(String number)
    {
        int last_num = Integer.parseInt(number);
        ArrayList<Integer> all_arms = new ArrayList<>();
        for (int i = 0; i < last_num + 1; i++)
        {
            int digit_num = String.valueOf(i).length();
            int sum_of_powers = 0;
            for (int at_digit = 0; at_digit < digit_num; at_digit++)
            {
                int curr_digit = String.valueOf(i).charAt(at_digit) - '0';
                sum_of_powers += Math.pow(curr_digit, digit_num);
            }
            if (sum_of_powers == i) all_arms.add(i);
        }
        return all_arms;
    }
}
