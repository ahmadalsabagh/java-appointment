//Ahmad Alsabagh
//500763910
import java.util.*;
import java.text.*;

/*
 * @author Ahmad Alsabagh
 * @date March 16th 2017
 * @descriptionAppointment class constructs calendars and allows for certain modifications of already made calendars
 * @descriptionImplements the Comparable interface
 * */
public class Appointment implements Comparable<Appointment>
{
  private Calendar date;
  private String description;
  private SimpleDateFormat dateFormat;
  private Person person;
  
  /*
   * Default constructor, constructs a calendar with the current time and no description
   * */
  public Appointment()
  {
    this.date = Calendar.getInstance();
    description = "";
  }
  
  /*
   * Custom constructor, constructs with a set date and time and description
   * @param year set current year
   * @param month set current month
   * @param day set current day
   * @param hour set current hour
   * @param minute set current minute
   * @param description set custom calendar description
   * */
  public Appointment(int year, int month, int day,int hour,int minute,String description)
  {
    this.date = new GregorianCalendar(year,month,day,hour,minute);
    this.description = description;
  }
  
  /**
   * gets the instance variable description
   * @return description of the appointment
   **/
  public String getDescription()
  {
    return description;
  }
  
  /**
   * Sets the description of the specified appointment
   * @param description sets new description of calendar
   * */
  public void setDescription(String description)
  {
    this.description = description;
  }
  
  /**
   * returns the current date
   * @return Calendar date instance variable
   * */
  public Calendar getDate()
  {
    return date;
  }
  

  
  /*
   * returns string representation of the calendar date
   * @return formatted date in string representation
   * */
  public String stringDate()
  {
    dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy");
    return dateFormat.format(date.getTime());
  }
  
   public String stringMonthDate()
  {
    dateFormat = new SimpleDateFormat("MMM");
    return dateFormat.format(date.getTime());
  }
  
  
  /*
   * gets the date of calendar and changes current day to a specificed day
   * @param day what day you would like your calendar to be changed to
   * */
  public void dayChanger(int day)
  {
    getDate();
    date.add(Calendar.DAY_OF_MONTH, day); 
  }
  
  /*
   * configures the current date, excluding the time
   * @param year what year to set your calendar to
   * @param month what month to set your calendar to
   * @param day what month to set your calendar to
   * */
  public void setDate(int year,int month,int day)
  {
    date = new GregorianCalendar(year,month,day);
  }
  
  /*
   * returns current time with the description
   * @return calendar's current time an description
   * */
  public String appointmentTime()
  {
    return date.get(Calendar.HOUR_OF_DAY) + ":" + date.get(Calendar.MINUTE) + " " + description;
  }
  
  /*
   * Overriding the compareTo method in the Comparable interface
   * @param calendarObject another calendar you would like to compare your current calendar with
   * @return 1 if current date is after calendarObject's date
   * @return -1 if current date is before calendarObject's date
   * @return 0 if both dates are equal
   * */
  public int compareTo(Appointment calendarObject)
  {
    if (date.after(calendarObject.getDate()))
      return 1;
    else if (date.before(calendarObject.getDate()))
      return -1;
    else
      return 0;
  }
  
  /*
   * Checks if current calendar occurs on the  same day as another calendar
   * @param year other calendar's year
   * @param month other calendar's month
   * @param day other calendar's day
   * @param hour other calendar's hour
   * @param minute other calendar's minute
   * @return true if they are equal in date & time
   * @return false if they are not equal
   * */
  public boolean occursOn(int year,int month,int day,int hour,int minute)
  {
    Calendar otherDate = new GregorianCalendar(year,month,day,hour,minute);
    
    if (this.getDate() == otherDate)
      return true;
    else
      return false;
  }
}