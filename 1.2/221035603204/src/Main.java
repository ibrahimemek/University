import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
/**
 * Main class that reads input from a file and writes output to another file.
 * This is the main class for controlling smart devices.
 */
public class Main
{
    static FileWriter fileWriter;
    /**
     * Main method to run the program.
     * Main method that reads input arguments, sets up file writers,
     * reads commands from input file, and executes the commands
     *
     * @param args Command-line arguments. Expected input file path as the first argument and output file path as the second argument.
     * @throws ParseException If there is an error parsing the input arguments.
     * @throws IOException If there is an error in reading or writing files
     */
    public static void main(String[] args) throws ParseException, IOException {

        try
        {
            String inputArg = args[0];
            String outputArg = args[1];
            File inputFile = new File(inputArg);
            try {
                fileWriter = new FileWriter(outputArg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scanner scanner = new Scanner(inputFile);
            String firstLine;
            while(true)
            {
                firstLine = scanner.nextLine();
                if(!firstLine.trim().equals("")) break;
            }
            Main.fileWriter.write("COMMAND: " + firstLine + "\n");
            String[] lineItems = firstLine.split("\t");
            if(!lineItems[0].equals("SetInitialTime") || lineItems.length == 1 || lineItems[1].trim().equals(""))
            {
                Main.fileWriter.append("ERROR: First command must be set initial time! Program is going to terminate!\n");
                fileWriter.flush();
                return;
            }
            if(!ControlTime.isValidDate(lineItems[1]))
            {
                Main.fileWriter.append("ERROR: Format of the initial date is wrong! Program is going to terminate!\n");
                fileWriter.flush();
                return;
            } else
            {
                ControlTime.currentTime = lineItems[1];
                Main.fileWriter.append("SUCCESS: Time has been set to " + lineItems[1] + "!\n");
            }

            String command = "";
            while (scanner.hasNextLine())
            {
                String currentLine = scanner.nextLine();
                if(currentLine.trim().equals("")) continue;
                String[] currentLineItems = currentLine.split("\t");
                Main.fileWriter.append("COMMAND: " + currentLine + "\n");
                String name;
                AllDevices currentObject;
                command = currentLineItems[0];
                switch (command)
                {
                    case "SetTime":
                        if(currentLineItems.length != 2) {Main.fileWriter.append("ERROR: Erroneous command!\n"); break;}
                        if (!ControlTime.isValidDate(currentLineItems[1])){Main.fileWriter.append("ERROR: Time format is not correct!\n"); break;}
                        if (currentLineItems[1].compareTo(ControlTime.currentTime) > 0) ControlTime.currentTime = currentLineItems[1];
                        else {Main.fileWriter.append("ERROR: Time cannot be reversed!\n"); break;}
                        ControlTime.controlSwitchs();
                        break;

                    case "SkipMinutes":
                        try
                        {
                            if(currentLineItems.length != 2) {Main.fileWriter.append("ERROR: Erroneous command!\n"); break;}
                            int minuteInfo = Integer.parseInt(currentLineItems[1]);
                            if(minuteInfo < 0) {Main.fileWriter.append("ERROR: Time cannot be reversed!\n"); break;}
                            if(minuteInfo == 0) {Main.fileWriter.append("ERROR: There is nothing to skip!\n"); break;}
                            ControlTime.addMinute(minuteInfo);
                        } catch (NumberFormatException e) {Main.fileWriter.append("ERROR: Erroneous command!\n");}
                        break;

                    case "Nop":
                        if(currentLineItems.length != 1) {Main.fileWriter.append("ERROR: Erroneous command!\n"); break;}
                        ControlTime.nop();
                        break;

                    case "Add":
                        switch (currentLineItems[1])
                        {
                            case "SmartLamp":
                                SmartLamp smartLamp = new SmartLamp();
                                smartLamp.addDevice(currentLineItems, smartLamp, true);
                                break;
                            case "SmartColorLamp":
                                SmartColorLamp smartColorLamp = new SmartColorLamp();
                                smartColorLamp.addDevice(currentLineItems, smartColorLamp);
                                break;
                            case "SmartPlug":
                                SmartPlug smartPlug = new SmartPlug();
                                smartPlug.addDevice(currentLineItems, smartPlug);
                                break;
                            case "SmartCamera":
                                SmartCamera smartCamera = new SmartCamera();
                                smartCamera.addDevice(currentLineItems, smartCamera);
                                break;
                            default:
                                Main.fileWriter.append("There is no device with this name!\n");
                        }
                        break;

                    case "Remove":
                        if(currentLineItems.length != 2) {Main.fileWriter.append("ERROR: Erroneous command!\n"); break;}
                        name = currentLineItems[1];
                        if (!AllDevices.allNames.contains(name)) {Main.fileWriter.append("ERROR: There is not such a device!\n"); break;}
                        currentObject = findObject(name);
                        if (currentObject instanceof SmartPlug)
                        {
                            if (currentObject.getInitialStatus().equals("On")) ((SmartPlug) currentObject).switchStatus("Off");
                            ((SmartPlug) currentObject).remove();
                            SmartPlug.plugObjects.remove(SmartPlug.plugObjects.indexOf(currentObject));
                        }
                        else if (currentObject instanceof SmartColorLamp)
                        {
                            if (currentObject.getInitialStatus().equals("On")) ((SmartColorLamp) currentObject).switchStatus("Off");
                            ((SmartColorLamp) currentObject).remove();
                            SmartColorLamp.colorLampObjects.remove(SmartColorLamp.colorLampObjects.indexOf(currentObject));
                        }
                        else if (currentObject instanceof SmartLamp)
                        {
                            if (currentObject.getInitialStatus().equals("On")) ((SmartLamp) currentObject).switchStatus("Off");
                            ((SmartLamp) currentObject).remove(false);
                            SmartLamp.lampObjects.remove(SmartLamp.lampObjects.indexOf(currentObject));
                        }
                        else
                        {
                            if (currentObject.getInitialStatus().equals("On")) ((SmartCamera) currentObject).switchStatus("Off");
                            ((SmartCamera) currentObject).remove();
                            SmartCamera.cameraObjects.remove(SmartCamera.cameraObjects.indexOf(currentObject));
                        }
                        break;

                    case "SetSwitchTime":
                        if(currentLineItems.length != 3) {Main.fileWriter.append("ERROR: Erroneous command!\n"); break;}
                        name = currentLineItems[1];
                        if (!AllDevices.allNames.contains(name)) {Main.fileWriter.append("ERROR: There is not such a device!\n"); break;}
                        if (!ControlTime.isValidDate(currentLineItems[2])) {Main.fileWriter.append("ERROR: Time format is not correct!\n"); break;}
                        currentObject = findObject(name);
                        currentObject.setSwitchTime(currentLineItems[2]);
                        AllDevices.switchTimes.add(currentLineItems[2]);
                        AllDevices.objectsWithSwitchTimes.add(currentObject);
                        break;

                    case "Switch":
                        if(currentLineItems.length != 3) {Main.fileWriter.append("ERROR: Erroneous command!\n"); break;}
                        name = currentLineItems[1];
                        if (!AllDevices.allNames.contains(name)) {Main.fileWriter.append("ERROR: There is not such a device!\n"); break;}
                        currentObject = findObject(name);
                        if (currentObject.getInitialStatus().equals(currentLineItems[2]))
                        {
                            Main.fileWriter.append(String.format("ERROR: This device is already switched %s!\n", currentLineItems[2].toLowerCase()));
                            break;
                        }
                        if (currentObject instanceof SmartPlug) ((SmartPlug) currentObject).switchStatus(currentLineItems[2]);
                        else if (currentObject instanceof SmartCamera) ((SmartCamera) currentObject).switchStatus(currentLineItems[2]);
                        else currentObject.switchStatus(currentLineItems[2]);
                        break;

                    case "ChangeName":
                        if (currentLineItems.length != 3) {Main.fileWriter.append("ERROR: Erroneous command!\n"); break;}
                        if (currentLineItems[1].equals(currentLineItems[2])) {Main.fileWriter.append("ERROR: Both of the names are the same, nothing changed!\n");break;}
                        if (!AllDevices.allNames.contains(currentLineItems[1])){Main.fileWriter.append("ERROR: There is not such a device!\n"); break;}
                        if (AllDevices.allNames.contains(currentLineItems[2])) {Main.fileWriter.append("ERROR: There is already a smart device with same name!\n");break;}
                        currentObject = findObject(currentLineItems[1]);
                        currentObject.changeName(currentLineItems[1], currentLineItems[2]);
                        break;

                    case "PlugIn":
                        if (currentLineItems.length != 3) {Main.fileWriter.append("ERROR: Erroneous command!\n"); break;}
                        if (!SmartPlug.plugNames.contains(currentLineItems[1])) {Main.fileWriter.append("ERROR: This device is not a smart plug!\n"); break;}
                        try
                        {
                            if (Integer.parseInt(currentLineItems[2]) < 1) {Main.fileWriter.append("ERROR: Ampere value must be a positive number!\n"); break;}

                        } catch (NumberFormatException e) {Main.fileWriter.append("ERROR: Ampere value must be a positive number!\n"); break;}
                        currentObject = SmartPlug.plugObjects.get(SmartPlug.plugNames.indexOf(currentLineItems[1]));
                        if (((SmartPlug) currentObject).isPluggedIn()) {Main.fileWriter.append("ERROR: There is already an item plugged in to that plug!\n"); break;}
                        ((SmartPlug) currentObject).setPluggedIn(true);
                        ((SmartPlug) currentObject).setPlugInTime(ControlTime.currentTime);
                        ((SmartPlug) currentObject).setAmpere(Integer.parseInt(currentLineItems[2]));
                        break;

                    case "PlugOut":
                        if (currentLineItems.length != 2) {Main.fileWriter.append("ERROR: Erroneous command!\n"); break;}
                        if (!SmartPlug.plugNames.contains(currentLineItems[1])) {Main.fileWriter.append("ERROR: This device is not a smart plug!\n"); break;}
                        currentObject = SmartPlug.plugObjects.get(SmartPlug.plugNames.indexOf(currentLineItems[1]));
                        if (!((SmartPlug) currentObject).isPluggedIn()) {Main.fileWriter.append("ERROR: This plug has no item to plug out from that plug!\n"); break;}
                        ((SmartPlug) currentObject).plugOut();
                        ((SmartPlug) currentObject).setPluggedIn(false);
                        break;

                    case "SetKelvin":
                        if (currentLineItems.length != 3) {Main.fileWriter.append("ERROR: Erroneous command!\n"); break;}
                        name = currentLineItems[1];
                        if (!SmartColorLamp.colorLampNames.contains(name) && !SmartLamp.lampNames.contains(name))
                        {
                            Main.fileWriter.append("ERROR: This device is not a smart lamp!\n");
                            break;
                        }
                        currentObject = findObject(name);
                        if (currentObject instanceof SmartColorLamp) ((SmartColorLamp) currentObject).setKelvin(currentLineItems[2]);
                        else ((SmartLamp) currentObject).setKelvin(currentLineItems[2]);
                        break;

                    case "SetBrightness":
                        if (currentLineItems.length != 3) {Main.fileWriter.append("ERROR: Erroneous command!\n"); break;}
                        name = currentLineItems[1];
                        if (!SmartColorLamp.colorLampNames.contains(name) && !SmartLamp.lampNames.contains(name))
                        {
                            Main.fileWriter.append("ERROR: This device is not a smart lamp!\n");
                            break;
                        }
                        currentObject = findObject(name);
                        if (currentObject instanceof SmartColorLamp) ((SmartColorLamp) currentObject).setBrightness(currentLineItems[2]);
                        else ((SmartLamp) currentObject).setBrightness(currentLineItems[2]);
                        break;

                    case "SetColorCode":
                        if (currentLineItems.length != 3) {Main.fileWriter.append("ERROR: Erroneous command!\n"); break;}
                        if (!SmartColorLamp.colorLampNames.contains(currentLineItems[1])) {Main.fileWriter.append("ERROR: This device is not a smart color lamp!\n"); break;}
                        SmartColorLamp object = (SmartColorLamp) findObject(currentLineItems[1]);
                        object.setColorCode(currentLineItems[2]);
                        break;

                    case "SetWhite":
                        if (currentLineItems.length != 4) {Main.fileWriter.append("ERROR: Erroneous command!\n"); break;}
                        name = currentLineItems[1];
                        if (!SmartColorLamp.colorLampNames.contains(name) && !SmartLamp.lampNames.contains(name))
                        {
                            Main.fileWriter.append("ERROR: This device is not a smart lamp!\n");
                            break;
                        }
                        currentObject = findObject(name);
                        if (currentObject instanceof SmartColorLamp) ((SmartColorLamp) currentObject).setWhite(currentLineItems);
                        else ((SmartLamp) currentObject).setWhite(currentLineItems);
                        break;

                    case "SetColor":
                        if (currentLineItems.length != 4) {Main.fileWriter.append("ERROR: Erroneous command!\n"); break;}
                        if (!SmartColorLamp.colorLampNames.contains(currentLineItems[1])) {Main.fileWriter.append("ERROR: This device is not a smart color lamp!\n"); break;}
                        currentObject = findObject(currentLineItems[1]);
                        ((SmartColorLamp) currentObject).setColor(currentLineItems);
                        break;

                    case "ZReport":
                        AllDevices.ZReport();
                        break;

                    default:
                        Main.fileWriter.append("ERROR: Erroneous command!\n");
                        break;
                }
            }
            if (!command.equals("ZReport"))
            {
                fileWriter.append("ZReport:\n");
                AllDevices.ZReport();
            }

        }
        catch (IOException e){System.out.println("Wrong File!");}
        fileWriter.flush();
    }

    /**
     Finds an object of type AllDevices based on the provided name.
     @param name The name of the object to find.
     @return The object of type AllDevices with the provided name, or null if not found.
     */
    public static AllDevices findObject (String name)
    {
        if (SmartCamera.cameraNames.contains(name))
            return SmartCamera.cameraObjects.get(SmartCamera.cameraNames.indexOf(name));
        else if (SmartLamp.lampNames.contains(name))
            return SmartLamp.lampObjects.get(SmartLamp.lampNames.indexOf(name));
        else if (SmartColorLamp.colorLampNames.contains(name))
            return SmartColorLamp.colorLampObjects.get(SmartColorLamp.colorLampNames.indexOf(name));
        else
            return SmartPlug.plugObjects.get(SmartPlug.plugNames.indexOf(name));
    }
}