import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

public class TripSchedule
{
    static Trip[] trips = new Trip[100];
    public static void openFiles(File inputFile)
    {
        try
        {
            Scanner inputReader = new Scanner(inputFile);
            int i = 0;
            while (inputReader.hasNextLine())
            {
                String line = inputReader.nextLine().trim();
                String[] lineArray = line.split("\t");
                trips[i] = new Trip();
                trips[i].tripName = lineArray[0];
                String timeInput = lineArray[1];
                String[] timeArray = timeInput.split(":");
                Calendar calendar = new Calendar() {
                    @Override
                    protected void computeTime() {

                    }

                    @Override
                    protected void computeFields() {

                    }

                    @Override
                    public void add(int field, int amount) {

                    }

                    @Override
                    public void roll(int field, boolean up) {

                    }

                    @Override
                    public int getMinimum(int field) {
                        return 0;
                    }

                    @Override
                    public int getMaximum(int field) {
                        return 0;
                    }

                    @Override
                    public int getGreatestMinimum(int field) {
                        return 0;
                    }

                    @Override
                    public int getLeastMaximum(int field) {
                        return 0;
                    }
                };
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
                calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
                trips[i].departureTime = calendar;
                trips[i].duration = Integer.parseInt(lineArray[2].trim());
                trips[i].calculateArrival();
                i += 1;
            }


        }
        catch (IOException e)
        {}
    }
}
