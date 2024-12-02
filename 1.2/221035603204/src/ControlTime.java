import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class ControlTime
{
    static String currentTime;

    /**
     Checks if a given date string is valid and follows the format "yyyy-MM-dd_HH:mm:ss".
     @param dateData The date string to be checked.
     @return true if the date string is valid, false otherwise.
     @throws IOException If an error occurs while parsing the date string.
     */
    public static boolean isValidDate(String dateData) throws IOException {
        //2023-03-31_14:00:00
        Calendar temporaryCalendar = Calendar.getInstance(); // The calendar which I use when I check if the date is valid.
        try {
            int currentYear = Integer.parseInt(dateData.substring(0,4));
            int currentMonth = Integer.parseInt(dateData.substring(5,7));
            int currentDay = Integer.parseInt(dateData.substring(8,10));
            int currentHour = Integer.parseInt(dateData.substring(11,13));
            int currentMinute = Integer.parseInt(dateData.substring(14,16));
            int currentSecond = Integer.parseInt(dateData.substring(17,19));

            temporaryCalendar.set(Calendar.YEAR, currentYear);
            if(currentMonth < 1 || currentMonth > 12){return false;}
            switch (currentMonth)
            {
                case 1: temporaryCalendar.set(Calendar.MONTH, Calendar.JANUARY); break;
                case 2: temporaryCalendar.set(Calendar.MONTH, Calendar.FEBRUARY); break;
                case 3: temporaryCalendar.set(Calendar.MONTH, Calendar.MARCH); break;
                case 4: temporaryCalendar.set(Calendar.MONTH, Calendar.APRIL); break;
                case 5: temporaryCalendar.set(Calendar.MONTH, Calendar.MAY); break;
                case 6: temporaryCalendar.set(Calendar.MONTH, Calendar.JUNE); break;
                case 7: temporaryCalendar.set(Calendar.MONTH, Calendar.JULY); break;
                case 8: temporaryCalendar.set(Calendar.MONTH, Calendar.AUGUST); break;
                case 9: temporaryCalendar.set(Calendar.MONTH, Calendar.SEPTEMBER); break;
                case 10: temporaryCalendar.set(Calendar.MONTH, Calendar.OCTOBER); break;
                case 11: temporaryCalendar.set(Calendar.MONTH, Calendar.NOVEMBER); break;
                case 12: temporaryCalendar.set(Calendar.MONTH, Calendar.DECEMBER); break;
            }
            if(currentDay < 1 || currentDay > temporaryCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)){ return false;}
            if (currentHour < 0 || currentHour > 23 || currentMinute < 0 || currentMinute > 59 || currentSecond < 0
                    || currentSecond > 59) return false;
        } catch (NumberFormatException e){return false;}

        return true;
    }

    /**
     * This method finds the maximum number of days in a given month of a specified year.
     * @param year  The year for which to find the maximum days.
     * @param month The month for which to find the maximum days (1-12, where 1 represents January and 12 represents December).
     * @return The maximum number of days in the specified month of the given year.
     */
    public static int findMaxDay(int year, int month)
    {
        Calendar temporaryCalendar = Calendar.getInstance();
        temporaryCalendar.set(Calendar.YEAR, year);
        switch (month)
        {
            case 1: temporaryCalendar.set(Calendar.MONTH, Calendar.JANUARY); break;
            case 2: temporaryCalendar.set(Calendar.MONTH, Calendar.FEBRUARY); break;
            case 3: temporaryCalendar.set(Calendar.MONTH, Calendar.MARCH); break;
            case 4: temporaryCalendar.set(Calendar.MONTH, Calendar.APRIL); break;
            case 5: temporaryCalendar.set(Calendar.MONTH, Calendar.MAY); break;
            case 6: temporaryCalendar.set(Calendar.MONTH, Calendar.JUNE); break;
            case 7: temporaryCalendar.set(Calendar.MONTH, Calendar.JULY); break;
            case 8: temporaryCalendar.set(Calendar.MONTH, Calendar.AUGUST); break;
            case 9: temporaryCalendar.set(Calendar.MONTH, Calendar.SEPTEMBER); break;
            case 10: temporaryCalendar.set(Calendar.MONTH, Calendar.OCTOBER); break;
            case 11: temporaryCalendar.set(Calendar.MONTH, Calendar.NOVEMBER); break;
            case 12: temporaryCalendar.set(Calendar.MONTH, Calendar.DECEMBER); break;
        }
        return temporaryCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * This method adds the specified duration in minutes to the current time in the format "yyyy-MM-dd_HH:mm:ss".
     * It updates the current time by incrementing the minutes, and adjusting the hour, day, month, and year accordingly.
     * @param duration The duration in minutes to be added to the current time.
     * @throws IOException If there is an I/O error while updating the current time.
     */
    static void addMinute(int duration) throws IOException {
        String dateData = ControlTime.currentTime;
        int yearData = Integer.parseInt(dateData.substring(0,4));
        int monthData = Integer.parseInt(dateData.substring(5,7));
        int dayData = Integer.parseInt(dateData.substring(8,10));
        int hourData = Integer.parseInt(dateData.substring(11,13));
        int minuteData = Integer.parseInt(dateData.substring(14,16));
        minuteData += duration;
        while(minuteData > 59)
        {
            minuteData -= 60;
            hourData += 1;
            if(hourData > 23)
            {
                hourData -= 24;
                dayData += 1;
                int maxDay = findMaxDay(yearData, monthData + 1);
                if(dayData > maxDay)
                {
                    dayData -= maxDay;
                    monthData += 1;
                    if(monthData > 12)
                    {
                        monthData -= 12;
                        yearData += 1;
                    }
                }
            }
        }
        String minuteOutput;
        String hourOutput;
        String dayOutput;
        String monthOutput;
        String yearOutput;
        if (minuteData < 10) minuteOutput = "0" + String.valueOf(minuteData);
        else minuteOutput = String.valueOf(minuteData);
        if (hourData < 10) hourOutput = "0" + String.valueOf(hourData);
        else hourOutput = String.valueOf(hourData);
        if (dayData < 10) dayOutput = "0" + String.valueOf(dayData);
        else dayOutput = String.valueOf(dayData);
        if (monthData < 10) monthOutput = "0" + String.valueOf(monthData);
        else monthOutput = String.valueOf(monthData);
        yearOutput = String.valueOf(yearData);
        ControlTime.currentTime = String.format("%s-%s-%s_%s:%s:%s", yearOutput, monthOutput, dayOutput, hourOutput, minuteOutput, dateData.substring(17,19));
    }
    /**
     * This method represents a no-operation (nop) function that checks for switch times and performs control switch operations.
     * If there are no switch times available, it writes an error message to the file and returns.
     * Otherwise, it sorts the switch times, updates the current time to the earliest switch time,
     * and invokes the controlSwitchs() method for performing control switch operations.
     * @throws ParseException If there is an error parsing the switch times.
     * @throws IOException If there is an I/O error while writing to the file.
     */
    public static void nop() throws ParseException, IOException {
        if (AllDevices.switchTimes.size() == 0) {Main.fileWriter.append("ERROR: There is nothing to switch!\n"); return;}
        Collections.sort(AllDevices.switchTimes);
        currentTime = AllDevices.switchTimes.get(0);
        controlSwitchs();
    }

    /**
     * This method controls the switch operations for all devices that have switch times set.
     * It iterates through all objects with switch times, compares their switch times with the current time,
     * and if the switch time has passed, it reverses the status of the device, removes the switch time from the switch times list,
     * removes the object from the objectsWithSwitchTimes list, and sets the switch time of the object to null.
     * @throws ParseException If there is an error parsing the switch times.
     * @throws IOException If there is an I/O error while writing to the file.
     */
    public static void controlSwitchs() throws ParseException, IOException {
        ArrayList<AllDevices> willDeleted = new ArrayList<>();
        for (AllDevices device: AllDevices.objectsWithSwitchTimes)
        {
            if (device.getSwitchTime() != null && device.getSwitchTime().compareTo(currentTime) <= 0)
            {
                willDeleted.add(device);
            }
        }
        for (AllDevices object : willDeleted)
        {
            if (object instanceof SmartPlug) ((SmartPlug) object).reverseStatus();
            else if (object instanceof SmartCamera) ((SmartCamera) object).reverseStatus();
            else object.reverseStatus();
            AllDevices.switchTimes.remove(AllDevices.switchTimes.indexOf(object.getSwitchTime()));
            AllDevices.objectsWithSwitchTimes.remove(AllDevices.objectsWithSwitchTimes.indexOf(object));
            object.setSwitchTime(null);
        }
    }
}
