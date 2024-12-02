import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

interface ArrivalController
{
    static void ArrivalSchedule(TripSchedule trip_schedule, FileWriter outputWriter)
    {
        try
        {
            ArrayList<Integer> allTimes = new ArrayList<>();
            for (Trip eachTrip: trip_schedule.trips)
            {
                if (eachTrip != null)
                {
                    eachTrip.setState("IDLE");
                    int currentTime = 0;
                    currentTime += 60 * eachTrip.getArrivalTime().get(Calendar.HOUR_OF_DAY);
                    currentTime += eachTrip.getArrivalTime().get(Calendar.MINUTE);
                    allTimes.add(currentTime);
                }
            }
            ArrayList<Integer> sortedTimes = new ArrayList<>();
            for (int eachList: allTimes)
                sortedTimes.add(eachList);
            Collections.sort(sortedTimes);
            int[] order = new int[allTimes.size()];
            for (int i = 0; i < allTimes.size(); i++)
            {
                int newIndex = allTimes.indexOf(sortedTimes.get(i));
                order[i] = newIndex;
                allTimes.set(newIndex, 0);
            }
            outputWriter.append("\nArrival order:\n");
            int outerNum = -1;
            for (Trip eachTrip: trip_schedule.trips)
            {
                outerNum += 1;
                int innerNum = -1;
                for (Trip otherTrip: trip_schedule.trips)
                {
                    innerNum += 1;
                    if (eachTrip != null && otherTrip != null && innerNum != outerNum)
                    {
                        if (eachTrip.arrivalTime.get(Calendar.HOUR_OF_DAY) == otherTrip.arrivalTime.get(Calendar.HOUR_OF_DAY))
                        {
                            if (eachTrip.arrivalTime.get(Calendar.MINUTE) == otherTrip.arrivalTime.get(Calendar.MINUTE))
                            {
                                eachTrip.setState("DELAYED");
                            }
                        }
                    }
                }
            }
            ArrayList<String> usedNames = new ArrayList<>();
            ArrayList<String> allOutputs = new ArrayList<>();
            for (Trip eachTrip: trip_schedule.trips)
            {
                if ((eachTrip != null) && (!usedNames.contains(eachTrip.tripName)))
                {
                    String preHour = "";
                    if (eachTrip.getDepartureTime().get(Calendar.HOUR_OF_DAY) < 10)
                        preHour = "0";
                    String preMinute = "";
                    String currentOutput = "";
                    currentOutput += eachTrip.tripName + " " + "arrive at ";
                    currentOutput += preHour + eachTrip.getArrivalTime().get(Calendar.HOUR_OF_DAY) + ":" + preMinute +
                            eachTrip.getArrivalTime().get(Calendar.MINUTE) + "\t";
                    currentOutput += "Trip State: " + eachTrip.state + "\n";
                    allOutputs.add(currentOutput);
                    usedNames.add(eachTrip.tripName);
                }
            }
            for (int x: order)
            {
                outputWriter.append(allOutputs.get(x));
            }
        }
        catch (IOException e){}
    }
}
