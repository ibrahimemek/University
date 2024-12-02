import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SmartCamera extends AllDevices
{
    static ArrayList<SmartCamera> cameraObjects = new ArrayList<>();
    static ArrayList<String> cameraNames = new ArrayList<>();

    private double megabyteConsumption;
    private double totalConsumption = 0;
    private String switchOnTime;

    public String getSwitchOnTime() {return switchOnTime;}

    public void setSwitchOnTime(String switchOnTime) {this.switchOnTime = switchOnTime;}

    public double getMegabyteConsumption() {
        return megabyteConsumption;
    }
    public void setMegabyteConsumption(double megabyteConsumption) {
        this.megabyteConsumption = megabyteConsumption;
    }

    public double getTotalConsumption() {
        return totalConsumption;
    }
    public void setTotalConsumption(double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }




    /**
     * Adds a new SmartCamera device to the system with the provided parameters.
     * Validates the input parameters and performs error checking before adding the device.
     * @param strArray The array of input parameters for the device.
     * @param object The device object to be added.
     * @throws IOException If there is an I/O error while writing to the file.
     */
    public void addDevice(String[] strArray, AllDevices object) throws IOException {
        if(strArray.length < 4 || strArray.length > 5)
        {
            Main.fileWriter.append("ERROR: Erroneous command!\n");
            return;
        }
        object.setName(strArray[2]);

        if(allNames.contains(object.getName()))
        {
            Main.fileWriter.append("ERROR: There is already a smart device with same name!\n");
            return;
        }
        try
        {
            double consumption = Double.parseDouble(strArray[3]);
            if(consumption <= 0)
            {
                Main.fileWriter.append("ERROR: Megabyte value must be a positive number!\n");
                return;
            }
            ((SmartCamera) object).setMegabyteConsumption(consumption);
        }
        catch (NumberFormatException e)
        {
            Main.fileWriter.append("ERROR: Megabyte value must be an integer!\n");
            return;
        }

        if(strArray.length == 5)
        {
            if (!strArray[4].equals("On") && !strArray[4].equals("Off"))
            {
                Main.fileWriter.append("ERROR: Erroneous command!\n");
                return;
            }
            if (strArray[4].equals("On"))  switchOnTime = ControlTime.currentTime;
            object.setInitialStatus(strArray[4]);
        }
        allNames.add(object.getName());
        cameraNames.add(object.getName());
        cameraObjects.add((SmartCamera) object);
    }

    /**
     * Switches the status of the device to the provided nextStatus.
     * Performs necessary calculations for updating the device status and consumption.
     * @param nextStatus The next status ("On" or "Off") to set for the device.
     * @throws ParseException If there is an error while parsing date and time.
     */
    public void switchStatus(String nextStatus) throws ParseException {
        setInitialStatus(nextStatus);
        if (nextStatus.equals("On")) switchOnTime = ControlTime.currentTime;
        else
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            Date d1 = sdf.parse(getSwitchOnTime());
            Date d2 = sdf.parse(ControlTime.currentTime);
            long difference_In_Time = d2.getTime() - d1.getTime();
            double duration = ((double) difference_In_Time / (1000 * 60));
            double additionalConsumption = duration * getMegabyteConsumption();
            setTotalConsumption(getTotalConsumption() + additionalConsumption);
        }
    }

    /**
     * Removes the device and performs necessary cleanup operations.
     * Calls the superclass remove() method, generates a report, and removes the device from the cameraNames list.
     *
     * @throws IOException If there is an error with I/O operations.
     */
    public void remove() throws IOException {
        super.remove();
        report();
        cameraNames.remove(cameraNames.indexOf(getName()));
    }

    /**
     * Reverses the status of the device.
     * If the initial status is "On", switches it to "Off", and vice versa.
     *
     * @throws ParseException If there is an error with parsing date/time.
     */
    public void reverseStatus() throws ParseException {
        if (getInitialStatus().equals("On")) switchStatus("Off");
        else switchStatus("On");
    }

    /**
     * Generates a report about the device's status, total consumption, and switch time,
     * and writes it to the file using the Main.fileWriter.
     *
     * @throws IOException If there is an error with writing to the file.
     */
    public void report() throws IOException {
        DecimalFormat energyFormat = new DecimalFormat("0.00");
        Main.fileWriter.append(String.format("Smart Camera %s is %s and used %s MB of storage so far (excluding " +
                "current status), and its time to switch its status is %s.\n", getName(), getInitialStatus().toLowerCase(),
                energyFormat.format(getTotalConsumption()), getSwitchTime()));
    }
}
