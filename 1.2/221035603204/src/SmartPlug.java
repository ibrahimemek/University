import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SmartPlug extends AllDevices
{
    static ArrayList<SmartPlug> plugObjects = new ArrayList<>();
    static ArrayList<String> plugNames = new ArrayList<>();

    private int voltage = 220;
    private double ampere;

    private String plugInTime;
    private String switchOnTime;
    private double totalEnergyConsumption = 0.0;



    public String getPlugInTime() {
        return plugInTime;
    }
    public void setPlugInTime(String plugInTime) {
        this.plugInTime = plugInTime;
    }


    public String getSwitchOnTime() {
        return switchOnTime;
    }
    public void setSwitchOnTime(String switchOnTime) {
        this.switchOnTime = switchOnTime;
    }




    public double getTotalEnergyConsumption() {return totalEnergyConsumption;}
    public void setTotalEnergyConsumption(double totalEnergyConsumption) {this.totalEnergyConsumption = totalEnergyConsumption;}

    public int getVoltage() {return voltage;}

    public void setVoltage(int voltage) {this.voltage = voltage;}



    private boolean isPluggedIn = false;

    public boolean isPluggedIn() {return isPluggedIn;}

    public void setPluggedIn(boolean pluggedIn) {isPluggedIn = pluggedIn;}

    public double getAmpere() {return ampere;}
    public void setAmpere(double ampere) {this.ampere = ampere;}

    /**
     * Adds a Smart Plug device with the given parameters to the list of devices.
     *
     * @param strArray The array of input parameters.
     * @param object   The Smart Plug object to be added.
     * @throws IOException If there is an error with writing to the file.
     */
    public void addDevice(String[] strArray, AllDevices object) throws IOException {
        if(strArray.length > 5) {Main.fileWriter.append("ERROR: Erroneous command!\n"); return;}
        isReturned = false;
        super.addDevice(strArray, object);
        if(isReturned) return;
        if (getInitialStatus().equals("On")) setSwitchOnTime(ControlTime.currentTime);
        if(strArray.length > 4)
        {
            try
            {
                double ampereInfo = Double.parseDouble(strArray[4]);
                if(ampereInfo <= 0.0) {Main.fileWriter.append("ERROR: Ampere value must be a positive number!\n"); return;}
                setPluggedIn(true);
                setPlugInTime(ControlTime.currentTime);
                ((SmartPlug) object).setAmpere(ampereInfo);
            }
            catch (NumberFormatException e){Main.fileWriter.append("ERROR: Ampere value must be an integer!\n"); return;}
        }
        allNames.add(object.getName());
        plugNames.add(object.getName());
        plugObjects.add(((SmartPlug) object));
    }

    /**
     * Removes the Smart Plug device from the list of devices and generates a report.
     * @throws IOException If there is an error with writing to the file.
     */
    public void remove() throws IOException {
        super.remove();
        report();
        plugNames.remove(plugNames.indexOf(getName()));
    }

    /**
     * Switches the status of the Smart Plug device to the given next status ("On" or "Off").
     * @param nextStatus The next status to switch to ("On" or "Off").
     * @throws ParseException If there is an error with parsing date/time strings.
     */
    public void switchStatus(String nextStatus) throws ParseException
    {
        setInitialStatus(nextStatus);
        if (nextStatus.equals("On")) switchOnTime = ControlTime.currentTime;
        else
        {
            if (!isPluggedIn()) return;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            Date d1;
            if (switchOnTime.compareTo(plugInTime) > 0) d1 = sdf.parse(getSwitchOnTime());
            else d1 = sdf.parse(getPlugInTime());
            Date d2 = sdf.parse(ControlTime.currentTime);
            long difference_In_Time = d2.getTime() - d1.getTime();
            double duration =  ((double) difference_In_Time / (1000 * 60 * 60));
            double additionalConsumption = duration * getVoltage() * getAmpere();
            setTotalEnergyConsumption(getTotalEnergyConsumption() + additionalConsumption);
        }
    }
    /**
     * Reverses the status of the Smart Plug device. If it is currently "On", it will be switched to "Off", and vice versa.
     * @throws ParseException If there is an error with parsing date/time strings.
     */
    public void reverseStatus() throws ParseException {
        if (getInitialStatus().equals("On")) switchStatus("Off");
        else switchStatus("On");
    }

    /**
     * Performs the "plug out" operation for the Smart Plug device, which calculates and updates the total energy consumption
     * based on the duration the device has been "On" since it was plugged in.
     * @throws ParseException If there is an error with parsing date/time strings.
     * @throws IOException If there is an error with input/output operations.
     */
    public void plugOut() throws ParseException, IOException {
        if (getInitialStatus().equals("On"))
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            Date d1;
            if (switchOnTime.compareTo(plugInTime) > 0) d1 = sdf.parse(getSwitchOnTime());
            else d1 = sdf.parse(getPlugInTime());
            Date d2 = sdf.parse(ControlTime.currentTime);
            long difference_In_Time = d2.getTime() - d1.getTime();
            double duration = ((double) difference_In_Time / (1000 * 60 * 60));
            double additionalConsumption = duration * getVoltage() * getAmpere();
            setTotalEnergyConsumption(getTotalEnergyConsumption() + additionalConsumption);
        }
    }

    /**
     * Generates a report for the Smart Plug device, including its current status, total energy consumption, and time to
     * switch its status.
     * @throws IOException If there is an error with input/output operations.
     */
    public void report() throws IOException
    {
        DecimalFormat consumptionFormat = new DecimalFormat("0.00");
        Main.fileWriter.append(String.format("Smart Plug %s is %s and consumed %sW so far (excluding current device), " +
                "and its time to switch its status is %s.\n", getName(), getInitialStatus().toLowerCase(),
                consumptionFormat.format(getTotalEnergyConsumption()), getSwitchTime()));
    }
}
