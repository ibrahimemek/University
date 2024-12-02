import java.util.ArrayList;

import static java.lang.String.valueOf;

public class Emirp
{

    public static Boolean prime_or_not (Integer value)
    {
        boolean is_prime = true;
        for(int i = 2; i < value / 2 + 1; i++)
        {
            if (value % i == 0) is_prime = false;
        }
        return  is_prime;
    }
    public static ArrayList<Integer> check_emirps (String num)
    {
        int last_emirp = Integer.parseInt(num);
        ArrayList <Integer> all_emirs = new ArrayList<>();
        for (int k = 10; k < last_emirp + 1; k++)
        {
            if(Emirp.prime_or_not(k))
            {
                int number_of_digit = valueOf(k).length();
                String reverse_num = "";
                for (int s = number_of_digit -1; s > -1; s--)
                {
                    reverse_num += valueOf(k).charAt(s);
                }
                int reverse_number = Integer.parseInt(reverse_num);
                if (reverse_number != k && prime_or_not(reverse_number)) all_emirs.add(k);
            }
        }

        return all_emirs;
    }
}
