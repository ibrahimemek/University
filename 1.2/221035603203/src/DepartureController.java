import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

interface DepartureController
{
    static void DepartureSchedule(TripSchedule trip_schedule, FileWriter outputWriter)
    {
        try
        {
            ArrayList<String> addedNames = new ArrayList<>();
            outputWriter.write("Departure order:\n");
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
                        if (eachTrip.departureTime.get(Calendar.HOUR_OF_DAY) == otherTrip.departureTime.get(Calendar.HOUR_OF_DAY))
                        {
                            if (eachTrip.departureTime.get(Calendar.MINUTE) == otherTrip.departureTime.get(Calendar.MINUTE))
                            {
                                eachTrip.setState("DELAYED");
                            }
                        }
                    }
                }
            }
            ArrayList<Integer> allTimes = new ArrayList<>();
            for (Trip eachTrip: trip_schedule.trips)
            {
                if (eachTrip != null)
                {
                    int currentTime = 0;
                    currentTime += 60 * eachTrip.getDepartureTime().get(Calendar.HOUR_OF_DAY);
                    currentTime += eachTrip.getDepartureTime().get(Calendar.MINUTE);
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
            ArrayList<String> allOutputs = new ArrayList<>();
            for (Trip eachTrip: trip_schedule.trips)
            {
                if ((eachTrip != null) && (!addedNames.contains(eachTrip.tripName)))
                {
                    String currentOutput = "";
                    String preHour = "";
                    if (eachTrip.getDepartureTime().get(Calendar.HOUR_OF_DAY) < 10)
                        preHour = "0";
                    String preMinute = "";
                    if (eachTrip.getDepartureTime().get(Calendar.MINUTE) < 10)
                        preMinute = "0";
                    currentOutput += eachTrip.tripName + " " + "depart at ";
                    currentOutput += preHour + eachTrip.getDepartureTime().get(Calendar.HOUR_OF_DAY) + ":" +
                            preMinute + eachTrip.getDepartureTime().get(Calendar.MINUTE) + "\t";
                    if (eachTrip.state == null)
                        eachTrip.state = "IDLE";
                    currentOutput += "Trip State: " + eachTrip.state + "\n";
                    allOutputs.add(currentOutput);
                    addedNames.add(eachTrip.tripName);
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

