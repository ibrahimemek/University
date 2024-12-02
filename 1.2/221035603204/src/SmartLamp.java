import java.io.IOException;
import java.util.ArrayList;

public class SmartLamp extends AllDevices
{
    static ArrayList<SmartLamp> lampObjects = new ArrayList<>();
    static ArrayList<String> lampNames = new ArrayList<>();

    private int kelvin = 4000;
    private int brightness = 100;

    public int getKelvin() {return kelvin;}
    public void setKelvin(int kelvin) {this.kelvin = kelvin;}
    public void setKelvin(String stringValue) throws IOException {
        try
        {
            int intValue = Integer.parseInt(stringValue);
            if (intValue < 2000 || intValue > 6500)
            {
                Main.fileWriter.append("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                return;
            }
            setKelvin(intValue);
        } catch (NumberFormatException e) {Main.fileWriter.append("ERROR: Erroneous command!\n");}
    }

    public int getBrightness() {return brightness;}
    public void setBrightness(int brightness) {this.brightness = brightness;}
    public void setBrightness(String stringValue) throws IOException {
        try
        {
            int intValue = Integer.parseInt(stringValue);
            if (intValue < 0 || intValue > 100)
            {
                Main.fileWriter.append("ERROR: Brightness must be in range of 0%-100%!\n");
                return;
            }

            setBrightness(intValue);
        } catch (NumberFormatException e) {Main.fileWriter.append("ERROR: Erroneous command!\n");}
    }

    /**
     * Sets the color of the SmartColorLamp device to white with the specified Kelvin value and brightness level.
     * @param lineItems An array of string values representing the command line items.
     *                  Expected format: [0] - command, [1] - device name, [2] - Kelvin value, [3] - brightness value.
     * @throws IOException If there is an error with writing to the file.
     */
    public void setWhite(String[] lineItems) throws IOException {
        if (lineItems.length != 4)
        {
            Main.fileWriter.append("ERROR: Erroneous command!\n");
            return;
        }
        try
        {
            int kelvinValue = Integer.parseInt(lineItems[2]);
            int brightnessValue = Integer.parseInt(lineItems[3]);

            if (kelvinValue < 2000 || kelvinValue > 6500)
            {
                Main.fileWriter.append("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                return;
            }
            if (brightnessValue < 0 || brightnessValue > 100)
            {
                Main.fileWriter.append("ERROR: Brightness must be in range of 0%-100%!\n");
                return;
            }
            setBrightness(brightnessValue);
            setKelvin(kelvinValue);
        } catch (NumberFormatException e) {Main.fileWriter.append("ERROR: Erroneous command!\n");}
    }

    /**
     * Adds a device to the system with the specified parameters.
     *
     * @param strArray An array of string values representing the command line items.
     *                 Expected format: [0] - command, [1] - device type, [2] - device name,
     *                 [3] - initial status, [4] - Kelvin value (optional), [5] - brightness value (optional).
     * @param object   An instance of the AllDevices class representing the device to be added.
     * @param isKelvin A boolean flag indicating if the Kelvin value is provided for the device.
     * @throws IOException If there is an error with writing to the file.
     */
    public void addDevice(String[] strArray, AllDevices object, Boolean isKelvin) throws IOException {
        if (strArray.length == 5 || strArray.length > 6)
        {
            Main.fileWriter.append("ERROR: Erroneous command!\n");
            isReturned = true;
            return;
        }
        isReturned = false;
        super.addDevice(strArray, object);
        if(isReturned) return;
        if (strArray.length == 6)
        {
            try
            {
                if (isKelvin)
                {
                    int kelvinInfo = Integer.parseInt(strArray[4]);
                    if (kelvinInfo < 2000 || kelvinInfo > 6500)
                    {
                        Main.fileWriter.append("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                        isReturned = true;
                        return;
                    }
                    ((SmartLamp) object).setKelvin(kelvinInfo);
                }

                try
                {
                    int brightnessInfo = Integer.parseInt(strArray[5]);
                    if (brightnessInfo < 0 || brightnessInfo > 100)
                    {
                        Main.fileWriter.append("ERROR: Brightness must be in range of 0%-100%!\n");
                        isReturned = true;
                        return;
                    }
                    ((SmartLamp) object).setBrightness(brightnessInfo);
                } catch (NumberFormatException e)
                {
                    Main.fileWriter.append("ERROR: Brightness value must be an integer!\n");
                    isReturned = true;
                    return;
                }
            } catch (NumberFormatException e)
            {
                Main.fileWriter.append("ERROR: Kelvin value must be an integer!\n");
                isReturned = true;
                return;
            }
        }
        if(object instanceof SmartColorLamp) {return;}
        allNames.add(object.getName());
        lampNames.add(object.getName());
        lampObjects.add(((SmartLamp) object));
    }

    /**
     * Removes a device from the system.
     * @param isColor A boolean flag indicating if the device is a SmartColorLamp.
     * @throws IOException If there is an error with writing to the file.
     */
    public void remove(Boolean isColor) throws IOException {
        super.remove();
        if (!isColor)
        {
            Main.fileWriter.append(String.format("Smart Lamp %s is %s and its kelvin value is %dK with %d%% brightness, and its time" +
                    " to switch its status is %s.\n", getName(), getInitialStatus().toLowerCase(), getKelvin(), getBrightness(), getSwitchTime()));
            lampNames.remove(lampNames.indexOf(getName()));
        }
    }

    /**
     * Generates a report of the Smart Lamp's status and writes it to a file.
     *
     * @throws IOException If there is an error with writing to the file.
     */
    public void report() throws IOException {
        Main.fileWriter.append(String.format("Smart Lamp %s is %s and its kelvin value is %dK with %d%% brightness, " +
                "and its time to switch its status is %s.\n", getName(), getInitialStatus().toLowerCase(), getKelvin(),
                getBrightness(), getSwitchTime()));
    }

}
