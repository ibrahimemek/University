import java.io.IOException;
import java.util.ArrayList;

public class SmartColorLamp extends SmartLamp
{
    static ArrayList<SmartColorLamp> colorLampObjects = new ArrayList<>();
    static ArrayList<String> colorLampNames = new ArrayList<>();

    private int colorCode;

    private String colorValue;
    public int getColorCode() {return colorCode;}
    public void setColorCode(int colorCode) {this.colorCode = colorCode;}

    public String getColorValue() {
        return colorValue;
    }

    public void setColorValue(String colorValue) {
        this.colorValue = colorValue;
    }

    /**
     * Adds a new SmartColorLamp device to the list of devices, based on the input command and parameters
     * provided in the strArray. Handles different cases for different types of devices, such
     * as SmartColorLamp with color code or Kelvin value.
     * @param strArray The input command and parameters as an array of strings.
     * @param object The object representing the device to be added.
     * @throws IOException If there is an error with writing to the file.
     */
    public void addDevice(String[] strArray, AllDevices object) throws IOException {
        boolean isKelvin = true;
        if(strArray.length > 4)
        {
            if (strArray[4].contains("x"))
                isKelvin = false;
        }
        isReturned = false;
        super.addDevice(strArray, object, isKelvin);
        if(isReturned) return;
        if(!isKelvin && strArray.length == 6)
        {
            try
            {
                int codeInfo = Integer.parseInt(strArray[4].substring(2), 16);
                if (codeInfo < 0x000000 || codeInfo > 0xFFFFFF)
                {
                    Main.fileWriter.append("ERROR: Color code value must be in range of 0x0-0xFFFFFF!\n");
                    return;
                }
                ((SmartColorLamp) object).colorValue = strArray[4];
                ((SmartColorLamp) object).setColorCode(codeInfo);
            }  catch (NumberFormatException e)
            {
                Main.fileWriter.append("ERROR: Erroneous command!\n");
                return;
            }
        }
        if (isKelvin) ((SmartColorLamp) object).colorValue = getKelvin() + "K";
        allNames.add(object.getName());
        colorLampNames.add(object.getName());
        colorLampObjects.add((SmartColorLamp) object);
    }

    /**
     * Sets the color of the SmartColorLamp to white, based on the input line items.
     * Updates the color value and checks for changes in Kelvin value to update the color value.
     * @param lineItems The input line items containing the command and parameters.
     * @throws IOException If there is an error with writing to the file.
     */
    @Override
    public void setWhite(String[] lineItems) throws IOException {
        int previousKelvin = getKelvin();
        super.setWhite(lineItems);
        if (previousKelvin != getKelvin()) setColorValue(lineItems[2] + "K");
    }

    /**
     * Removes the SmartColorLamp device and writes a report to the file.
     * @throws IOException If there is an error with writing to the file.
     */
    public void remove() throws IOException {
        super.remove(true);
        Main.fileWriter.append(String.format("Smart Color Lamp %s is %s and its color value is %s with %d%% brightness, and its time" +
                " to switch its status is %s.\n", getName(), getInitialStatus().toLowerCase(), colorValue, getBrightness(), getSwitchTime()));
        colorLampNames.remove(colorLampNames.indexOf(getName()));
    }

    /**
     * Sets the color code value of the SmartColorLamp device.
     * @param stringValue The color code value as a string, in the format "0xRRGGBB".
     * @throws IOException If there is an error with writing to the file.
     */
    public void setColorCode(String stringValue) throws IOException {
        try
        {
            int codeValue = Integer.parseInt(stringValue.substring(2), 16);
            if (codeValue < 0x000000 || codeValue > 0xFFFFFF)
            {
                Main.fileWriter.append("ERROR: Color code value must be in range of 0x0-0xFFFFFF!\n");
                return;
            }
            setColorCode(codeValue);
            colorValue = stringValue;
            Main.fileWriter.append(colorValue);
        } catch (NumberFormatException e) {Main.fileWriter.append("ERROR: Erroneous command!\n");} catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the color and brightness of the SmartColorLamp device.
     * @param lineItems An array of strings containing the command line items.
     *                  Expected format: lineItems[0] = command, lineItems[1] = device name,
     *                  lineItems[2] = color code value, lineItems[3] = brightness level.
     * @throws IOException If there is an error with writing to the file.
     */
    public void setColor(String[] lineItems) throws IOException {
        if (lineItems.length != 4)
        {
            Main.fileWriter.append("ERROR: Erroneous command!\n");
            return;
        }
        try
        {
            int intValue = Integer.parseInt(lineItems[3]);
            int codeValue = Integer.parseInt(lineItems[2].substring(2), 16);
            if (codeValue < 0x000000 || codeValue > 0xFFFFFF)
            {
                Main.fileWriter.append("ERROR: Color code value must be in range of 0x0-0xFFFFFF!\n");
                return;
            }

            if (intValue < 0 || intValue > 100)
            {
                Main.fileWriter.append("ERROR: Brightness must be in range of 0%-100%!\n");
                return;
            }
            setBrightness(intValue);
            colorValue = lineItems[2];
            setColorCode(codeValue);
        } catch (NumberFormatException e) {Main.fileWriter.append("ERROR: Erroneous command!\n");}
    }

    /**
     * Reports the current status and settings of the SmartColorLamp device.
     * @throws IOException If there is an error with writing to the file.
     */
    public void report() throws IOException {
        Main.fileWriter.append(String.format("Smart Color Lamp %s is %s and its color value is %s with %d%% brightness, " +
                        "and its time to switch its status is %s.\n", getName(), getInitialStatus().toLowerCase(),
                getColorValue(), getBrightness(), getSwitchTime()));

    }


}
