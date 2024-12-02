import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TripController
{
    protected static TripSchedule trip_schedule = new TripSchedule();

    public static void TripController(String inputFile, String outputFile)
    {
        try
        {
        File readFile = new File(inputFile);
        TripSchedule.openFiles(readFile);
        FileWriter outputWriter = new FileWriter(outputFile);
        DepartureController.DepartureSchedule(trip_schedule, outputWriter);
        ArrivalController.ArrivalSchedule(trip_schedule, outputWriter);
        outputWriter.close();
        }
        catch (IOException e){}
    }

}
