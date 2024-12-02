import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

public abstract class AllDevices
{
    static ArrayList<String> allNames = new ArrayList<>();
    static ArrayList<AllDevices> objectsWithSwitchTimes = new ArrayList<>();
    static ArrayList<String> switchTimes = new ArrayList<>();

    private String name;
    private String initialStatus = "off";
    private String switchTime;
    public static boolean isReturned = false; // it means in the super class, there is an error, and it returned before completing all code in super class

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getInitialStatus() {return initialStatus;}
    public void setInitialStatus(String initialStatus) {this.initialStatus = initialStatus;}
    public String getSwitchTime() {return switchTime;}
    public void setSwitchTime(String switchTime) {this.switchTime = switchTime;}


    /**
     Adds a new device to the system.
     @param strArray A string array containing the command and parameters.
     @param object An instance of AllDevices representing the device to be added.
     @throws IOException If an I/O error occurs while writing to the file.
     */
    public void addDevice(String[] strArray, AllDevices object) throws IOException {
        if(strArray.length < 3)
        {
            Main.fileWriter.append("ERROR: Erroneous command!\n");
            isReturned = true;
            return;
        }
        object.setName(strArray[2]);
        if(allNames.contains(object.getName()))
        {
            Main.fileWriter.append("ERROR: There is already a smart device with same name!\n");
            isReturned = true;
            return;
        }

        if(strArray.length > 3)
        {
            if (!strArray[3].equals("On") && !strArray[3].equals("Off"))
            {
                Main.fileWriter.append("ERROR: Erroneous command!\n");
                isReturned = true;
                return;
            }
            object.setInitialStatus(strArray[3]);
        }
    }

    /**
     Removes a smart device from the system.
     @throws IOException If an I/O error occurs while writing to the file.
     */
    public void remove() throws IOException {
        Main.fileWriter.append("SUCCESS: Information about removed smart device is as follows:\n");
        allNames.remove(allNames.indexOf(getName()));
    }

    /**
     Switches the status of the smart device to the provided status.
     @param nextStatus A string representing the new status of the smart device.
     @throws ParseException If the provided status is not a valid status for the smart device.
     */
    public void switchStatus(String nextStatus) throws ParseException {setInitialStatus(nextStatus);}


    /**
     Changes the name of a smart device.
     @param currentName The current name of the smart device.
     @param nextName The new name for the smart device.
     */
    public void changeName(String currentName, String nextName)
    {
        if (SmartPlug.plugNames.contains(currentName))
        {
            setName(nextName);
            SmartPlug.plugNames.set(SmartPlug.plugNames.indexOf(currentName), nextName);
        }
        else if (SmartColorLamp.colorLampNames.contains(currentName))
        {
            setName(nextName);
            SmartColorLamp.colorLampNames.set(SmartColorLamp.colorLampNames.indexOf(currentName), nextName);
        }
        else if (SmartLamp.lampNames.contains(currentName))
        {
            setName(nextName);
            SmartLamp.lampNames.set(SmartLamp.lampNames.indexOf(currentName), nextName);
        }
        else
        {
            setName(nextName);
            SmartCamera.cameraNames.set(SmartCamera.cameraNames.indexOf(currentName), nextName);
        }
        allNames.set(allNames.indexOf(currentName), nextName);
    }

    /**
     Generates a Z-Report, including information about switch times and reports for all smart devices.
     @throws IOException If an I/O error occurs while writing to the file.
     */
    public static void ZReport() throws IOException {
        Main.fileWriter.append(String.format("Time is:\t%s\n", ControlTime.currentTime));
        Collections.sort(switchTimes);
        ArrayList<String> addedObjectNames = new ArrayList<>();
        for (String eachTime: switchTimes)
        {
            for (AllDevices eachObject: objectsWithSwitchTimes)
            {
                if (eachObject.switchTime.equals(eachTime))
                {
                    if (addedObjectNames.size() != 0 && addedObjectNames.contains(eachObject.getName())) continue;
                    if (eachObject instanceof SmartPlug) ((SmartPlug) eachObject).report();
                    else if (eachObject instanceof SmartColorLamp) ((SmartColorLamp) eachObject).report();
                    else if (eachObject instanceof SmartLamp) ((SmartLamp) eachObject).report();
                    else ((SmartCamera) eachObject).report();
                    addedObjectNames.add(eachObject.getName());
                }
            }
        }
        for (String name: AllDevices.allNames)
        {
            AllDevices currentObject = Main.findObject(name);
            if (objectsWithSwitchTimes.contains(currentObject)) continue;
            if (currentObject instanceof SmartPlug) ((SmartPlug) currentObject).report();
            else if (currentObject instanceof SmartColorLamp) ((SmartColorLamp) currentObject).report();
            else if (currentObject instanceof SmartLamp) ((SmartLamp) currentObject).report();
            else ((SmartCamera) currentObject).report();
        }
    }

    /**
     Reverses the status of the smart device, toggling between "On" and "Off".
     @throws ParseException If an error occurs while parsing the status.
     */
    public void reverseStatus() throws ParseException
    {
        if (getInitialStatus().equals("On")) switchStatus("Off");
        else switchStatus("On");
    }
}