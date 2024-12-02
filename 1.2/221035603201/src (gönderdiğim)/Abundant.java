import java.util.ArrayList;

public class Abundant
{
    public static Integer calculate_divisors(int number)
    {
        int sum = 0;
        for (int k = 2; k < number / 2 + 1; k++)
        {
            if (number % k == 0) sum += k;
        }
        return sum;
    }
    public static ArrayList<Integer> check_abuns(String number_last)
    {
        ArrayList<Integer> all_abuns = new ArrayList<>();
        for (int i = 4; i < Integer.parseInt(number_last) + 1; i++)
        {
            if (Abundant.calculate_divisors(i) > i) all_abuns.add(i);
        }
        return all_abuns;
    }
}
