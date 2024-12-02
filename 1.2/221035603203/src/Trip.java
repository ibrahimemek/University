import java.util.Calendar;

public class Trip
{
    public String tripName;
    public Calendar departureTime;
    public Calendar arrivalTime;
    public int duration;
    public String state;

    public Calendar getDepartureTime()
    {
        return departureTime;
    }
    public Calendar getArrivalTime()
    {
        return arrivalTime;
    }

    public String getState()
    {
        return state;
    }
    public void setState(String currentState)
    {
        state = currentState;
    }
    public void calculateArrival()
    {
        int currentHour = departureTime.get(Calendar.HOUR_OF_DAY);
        int currentMinute = departureTime.get(Calendar.MINUTE);
        currentMinute += duration;
        while(currentMinute > 59)
        {
            currentMinute -= 60;
            currentHour += 1;
            if (currentHour > 23)
                currentHour -= 24;
        }
        Calendar newCal = new Calendar() {
            @Override
            protected void computeTime() {

            }

            @Override
            protected void computeFields() {

            }

            @Override
            public void add(int field, int amount) {

            }

            @Override
            public void roll(int field, boolean up) {

            }

            @Override
            public int getMinimum(int field) {
                return 0;
            }

            @Override
            public int getMaximum(int field) {
                return 0;
            }

            @Override
            public int getGreatestMinimum(int field) {
                return 0;
            }

            @Override
            public int getLeastMaximum(int field) {
                return 0;
            }
        };
        newCal.set(Calendar.HOUR_OF_DAY, currentHour);
        newCal.set(Calendar.MINUTE, currentMinute);
        arrivalTime = newCal;
    }
}
