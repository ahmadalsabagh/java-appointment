//Ahmad Alsabagh
//500763910
//Required Packages
import java.util.*;
import java.text.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.io.*;


/*
 * @author Ahmad Alsabagh
 * @date March 16th 2017
 * @description Creates the GUI of the Calendar and sets up the ActionListeners for create,show,cancel,front button
 * @description back button and creates appointments with descriptions in a sorted manner
 * */
public class AppointmentFrame extends JFrame
{
  //Frame's width and length
  private static final int SIZE_WIDTH = 1200;
  private static final int SIZE_LENGTH = 715;
  
  //Instance variables
  private JTextField lastNameField; //textfield for last name
  private JTextField firstNameField; // textfield for first name
  private JTextField telephoneField; //textfield for telephone number
  private JTextField emailField; //textfield for email address
  private JTextArea addressField; //textarea for address
  private JButton findButton; // find button
  private JButton clearButton; // clear button
  private JButton backButton; //Decrements the current day in the calendar
  private JButton recallButton; //recall button
  private JButton frontButton;//Increments the current day in the calendar
  private JTextField yearField;//Input for calendar year
  private JTextField monthField;//Input for calendar month
  private JTextField dayField;//Input for calendar day
  private JTextField hourOfAppointment;//Input for calendar hour
  private JTextField minuteOfAppointment;//Input for calendar minute
  private JButton createButton; //Create appointment button
  private JButton cancelButton;//Cancel a specified appointment
  private ArrayList<Appointment> appointmentList; //ArrayList to store and sort appointments
  private JTextArea appointmentOutput; //Outputs created appointments
  private JTextArea descriptionOfAppointment; //Input field for the description of the calendar
  private JScrollPane scrollPane; //Allows for the description box to be scrollable
  private JLabel currentDate; //Label at the top of the GUI to show the current date
  private Appointment appointment; //Appointment object to allow for methods in that class to be used
  private SimpleDateFormat dateFormat; //Formats the date
  private Person person; //instance variable of the person class
  private Contacts contacts; //instance variable of the contacts class
  private Stack<Appointment> stack; //stack of appointments
  private static int counter = 0; //static counter
  private JPanel buttonsPanel; //helper panel
  private JLabel monthLabel; //month label
  private Calendar calDate; //calendar object
  public JButton buttonArray[]; //button array
  public JButton coloredDay; // highlighted color in the calendar
  
  
  
  /**
   * Default Constructor creates the frame with set size
   * */
  public AppointmentFrame() //throws FileNotFoundException, Exception
  {
    appointmentList = new ArrayList<Appointment>();
    setSize(SIZE_WIDTH,SIZE_LENGTH);
    createControlPanel();
    contacts = new Contacts();
    stack = new Stack<Appointment>();
  }
  /*
   * The main method which when contains every other method inside of it
   * Only method required to be invoked to allow the program to run correctly
   * */
  public void createControlPanel()
  {
    //Initialize objects
    appointment = new Appointment(); //Create an Appointment object
    currentDate = new JLabel(appointment.stringDate()); //Creates a JLabel with current date
    JPanel mainPanel = new JPanel(); //main panel contains north,center,south panels
    JPanel outputPanel = new JPanel(); //Contains the date and output
    JPanel datePanel = datePanel(); //subpanel
    JPanel actionPanel = actionPanel();//subpanel
    JPanel descriptionPanel = descriptionPanel();//subpanel  
    JPanel contactPanel = contactPanel(); //subpanel
    JPanel calendarPanel = calendarPanel();
    appointmentOutput = new JTextArea(17,22); //Text are outputting the appointments
    appointmentOutput.setEditable(false); //No user input allowed in the output textbox
    scrollPane = new JScrollPane(appointmentOutput); //scroll bar added to appointmentOutput
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //Enable vertical scrollbar
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); //Enable horizontal scrollbar
    
    //Set Layout 
    mainPanel.setLayout(new GridLayout(3,2));
    outputPanel.setLayout(new BorderLayout());
    
    //Add subPanels to main panels
    outputPanel.add(currentDate, BorderLayout.NORTH);
    outputPanel.add(scrollPane, BorderLayout.CENTER);
    mainPanel.add(outputPanel);
    mainPanel.add(calendarPanel);
    mainPanel.add(datePanel);
    mainPanel.add(contactPanel);
    mainPanel.add(actionPanel);
    mainPanel.add(descriptionPanel);
    
    //add mainPanel to JFrame
    this.add(mainPanel);    
  }
  /*
   * Creates the JPanel with the date section including back & front buttons & year,month,day input boxes
   * @return panel
   * */
  public JPanel datePanel()
  {
    //Create the components used with specific names & sizes
    JPanel panel = new JPanel();
    JPanel backFrontPanel = new JPanel();
    JPanel fieldsPanel = new JPanel();
    JPanel showBtnPanel = new JPanel();
    JButton showButton = new JButton("Show");
    JLabel dayLabel = new JLabel("Day");
    JLabel monthLabel = new JLabel("Month");
    JLabel yearLabel = new JLabel("Year");
    yearField = new JTextField(4); 
    monthField = new JTextField(2); 
    dayField = new JTextField(2); 
    backButton = new JButton("<");
    frontButton = new JButton(">");
    
    //Layouts
    panel.setLayout(new BorderLayout());
    backFrontPanel.setLayout(new GridLayout(1,2));
    
    /*
     * Embedded class for the back button's action Listener
     * */
    class BackButtonListener implements ActionListener
    {     
      /*
       * When the back button is clicked perform this method
       * @param e the actionListener
       * */
      public void actionPerformed(ActionEvent e)
      {
        
        dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy"); //Format the date
        appointment.dayChanger(-1); //Decrement current day
        currentDate.setText(appointment.stringDate()); //Gets time
        printAppointments(); //call printAppointments method
        descriptionOfAppointment.setText(""); //reset description box
        calDate = appointment.getDate();
        //Gets current day of month number
        int currentDay = calDate.get(Calendar.DAY_OF_MONTH) - 1;
        
        createButtons(currentDay);
        updateMonthLabel();
        
      }
    }
    ActionListener backListener = new BackButtonListener(); //Create actionlistener object
    backButton.addActionListener(backListener); //pass the actionlistener object
    
    /*
     * Embedded class for the front button's action listener
     * */
    class FrontButtonListener implements ActionListener
    {
      /*
       * When the front button is clicked perform this method
       * @param e the actionListener
       * */
      public void actionPerformed(ActionEvent e)
      {
        dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy"); //Format the date
        appointment.dayChanger(1); //Increment the current day
        currentDate.setText(appointment.stringDate()); //Gets time
        printAppointments(); //call printAppointments method
        descriptionOfAppointment.setText(""); //reset description
        calDate = appointment.getDate();
        //Gets current day of month number
        int currentDay = calDate.get(Calendar.DAY_OF_MONTH) - 1;
        createButtons(currentDay); //create calendar buttons
        updateMonthLabel(); // update labels
        
      }
    }
    ActionListener frontListener = new FrontButtonListener(); //Create action listener object
    frontButton.addActionListener(frontListener); //pass the action listener to the front button
    
    /*
     * Embedded class for the show button
     * */
    class ShowButtonListener implements ActionListener
    { 
      /*
       * Method activated when the show button is clicked
       * @param e the actionListener
       * */
      public void actionPerformed(ActionEvent e)
      {
        findAppointments(); //call findAppointments method
        printAppointments(); //call the printAppointments method
        descriptionOfAppointment.setText(""); //reset the description box
      }
    }
    ActionListener showListener = new ShowButtonListener(); //create actionlistener
    showButton.addActionListener(showListener); //pass the actionlistener to the showbutton
    
    //Add components to the specified panel
    //1st row
    backFrontPanel.add(backButton);
    backFrontPanel.add(frontButton);
    
    //2nd row
    fieldsPanel.add(dayLabel);
    fieldsPanel.add(dayField);
    fieldsPanel.add(monthLabel);
    fieldsPanel.add(monthField);
    fieldsPanel.add(yearLabel);
    fieldsPanel.add(yearField);
    
    //3rd row
    showBtnPanel.add(showButton);
    
    //Border
    panel.setBorder(new TitledBorder(new EtchedBorder(), "Date"));
    panel.add(backFrontPanel, BorderLayout.NORTH);
    panel.add(fieldsPanel, BorderLayout.CENTER);
    panel.add(showBtnPanel, BorderLayout.SOUTH);
    
    //return statement
    return panel;
    
  }
  
  /*
   * Contact panel to input user info or find user
   * @return JPanel that will be displayed in the GUI 
   * */
  public JPanel contactPanel()
  {
    JPanel panel = new JPanel(); //panel being returned
    JPanel subPanel = new JPanel(); //helper panel
    JPanel subPanel2 = new JPanel(); //helper panel
    JPanel subPanel3 = new JPanel(); //helper panel
    JLabel firstName = new JLabel("First Name"); //label
    JLabel lastName = new JLabel("Last Name"); //label
    JLabel email = new JLabel("E-mail"); //label
    JLabel telephone = new JLabel("telephone"); //label
    JLabel address = new JLabel("Address"); //label
    findButton = new JButton("Find"); //jbutton
    clearButton = new JButton("Clear"); //jbutton
    firstNameField = new JTextField(10);
    lastNameField = new JTextField(10);
    emailField = new JTextField(10);
    telephoneField = new JTextField(10);
    addressField = new JTextArea(2,50);
    panel.setBorder(new TitledBorder(new EtchedBorder(), "Contact"));
    
    //set layout
    panel.setLayout(new BorderLayout()); 
    subPanel.setLayout(new GridLayout(5,2,6,6));
    
    //organize the panels
    subPanel.add(lastName);
    subPanel.add(firstName);
    subPanel.add(lastNameField);
    subPanel.add(firstNameField);
    subPanel.add(telephone);
    subPanel.add(email);
    subPanel.add(telephoneField);
    subPanel.add(emailField);
    subPanel.add(address);
    subPanel2.add(addressField);
    subPanel3.add(findButton);
    subPanel3.add(clearButton);
    
    panel.add(subPanel, BorderLayout.NORTH);
    panel.add(subPanel2, BorderLayout.CENTER);
    panel.add(subPanel3, BorderLayout.SOUTH);
    
    
    
    class FindButtonListener implements ActionListener
    {     
      /*
       * When the find button is clicked perform this method
       * @param e the actionListener
       * */
      public void actionPerformed(ActionEvent e)
      {
        Person temp;
        JFrame errorFrame = new JFrame("Error");
        //if searched linkedlist using email field
        if (!emailField.getText().isEmpty())
        {
          temp = contacts.findPersonEmail(emailField.getText());
          firstNameField.setText(temp.getFirstName());
          lastNameField.setText(temp.getLastName());
          addressField.setText(temp.getAddress());
          telephoneField.setText(temp.getTel());
        }
        //if searched linkedlist using telephone field
        else if (!telephoneField.getText().isEmpty())
        {
          temp = contacts.findPersonTel(telephoneField.getText());
          firstNameField.setText(temp.getFirstName());
          lastNameField.setText(temp.getLastName());
          addressField.setText(temp.getAddress());
          emailField.setText(temp.getEmail());
          
        }
        //if searched linkedlist using first & last name fields
        else if(!firstNameField.getText().isEmpty() && !lastNameField.getText().isEmpty())
        {
          temp = contacts.findPerson(firstNameField.getText(),lastNameField.getText());
          emailField.setText(temp.getEmail());
          addressField.setText(temp.getAddress());
          telephoneField.setText(temp.getTel());
        }
        //error
        else
          JOptionPane.showMessageDialog(errorFrame,"Please enter information in the correct fields");
        
        
        
      }
    }
    ActionListener findListener = new FindButtonListener(); //Create actionlistener object
    findButton.addActionListener(findListener); //pass the actionlistener object
    
    
    
    class ClearButtonListener implements ActionListener
    {     
      /*
       * When the back button is clicked perform this method
       * @param e the actionListener
       * */
      public void actionPerformed(ActionEvent e)
      {
        firstNameField.setText("");
        lastNameField.setText("");
        addressField.setText("");
        telephoneField.setText("");
        emailField.setText("");
        
        
      }
    }
    ActionListener clearListener = new ClearButtonListener(); //Create actionlistener object
    clearButton.addActionListener(clearListener); //pass the actionlistener object
    
    
    return panel;    
  }
  
 /*
  * Creates the calendar buttons panel
  * @return JPanel to appear in the GUI
  * */
  public JPanel calendarPanel()
  {
    calDate = appointment.getDate(); // Gets current date
    JLabel sunday = new JLabel("Sun");
    JLabel monday = new JLabel("Mon");
    JLabel tuesday = new JLabel("Tue");
    JLabel wednesday = new JLabel("Wed");
    JLabel thursday = new JLabel("Thur");
    JLabel friday = new JLabel("Fri");
    JLabel saturday = new JLabel("Sat");
    monthLabel = new JLabel(appointment.stringMonthDate());
    JPanel panel = new JPanel();
    JPanel subPanel = new JPanel();
    JPanel dayOfWeek = new JPanel();
    buttonsPanel = new JPanel();
    int currentDay = calDate.get(Calendar.DAY_OF_MONTH) - 1;
    
    createButtons(currentDay);
    
    //setLayout
    subPanel.setLayout(new BorderLayout());
    panel.setLayout(new BorderLayout());
    dayOfWeek.setLayout(new GridLayout(0,7));
    buttonsPanel.setLayout(new GridLayout(0,7));
    
    //organize the components
    dayOfWeek.add(sunday);
    dayOfWeek.add(monday);
    dayOfWeek.add(tuesday);
    dayOfWeek.add(wednesday);
    dayOfWeek.add(thursday);
    dayOfWeek.add(friday);
    dayOfWeek.add(saturday);
    
    subPanel.add(dayOfWeek,BorderLayout.NORTH);
    subPanel.add(buttonsPanel,BorderLayout.CENTER);
    panel.add(monthLabel,BorderLayout.NORTH);
    panel.add(subPanel,BorderLayout.CENTER);
    
    
    
    
    return panel;
  }
  
  /*
   * The action Panel in the GUI
   * @return the JPanel itself
   * */
  public JPanel actionPanel()
  {
    //Instantiating objects
    JPanel panel = new JPanel();
    JPanel fieldsPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();
    JPanel emptyPanel = new JPanel();
    JLabel hourLabel = new JLabel("Hour");
    JLabel minuteLabel = new JLabel("Minute");
    hourOfAppointment = new JTextField(4);
    minuteOfAppointment = new JTextField(4);
    createButton = new JButton("CREATE");
    cancelButton = new JButton("CANCEL");
    recallButton = new JButton("RECALL");
    //layout
    panel.setLayout(new BorderLayout());
    
    /*
     * Helper class for the createButton
     * */
    class CreateButtonListener implements ActionListener
    {
      /*
       * method excuted when the button is clicked
       * @param e the actionlistener
       * */
      public void actionPerformed(ActionEvent e)
      {
        createAppointment(); //calls the createAppointment method
      }
    }
    
    ActionListener createBtnListener = new CreateButtonListener(); //create an actionlistener object
    createButton.addActionListener(createBtnListener); //pass the actionlistener to the createButton
    
    /*
     * Helper class for the cancelButton
     * */
    class CancelButtonListener implements ActionListener
    {
      /*
       * Method only excuted when the button is clicked
       * @param e the actionListener
       * */
      public void actionPerformed(ActionEvent e)
      {
        cancelAppointment(); //call the cancelAppointment method
      }
    }
    ActionListener cancelBtnListener = new CancelButtonListener(); //create an actionlistener object
    cancelButton.addActionListener(cancelBtnListener); //pass the actionlistener to the createButton
    
    
    /*
     * Helper class for the recallButton
     * */
    class RecallButtonListener implements ActionListener
    {
      /*
       * method excuted when the button is clicked
       * @param e the actionlistener
       * */
      public void actionPerformed(ActionEvent e)
      {
        recallAppointment();//calls the recallAppointment method
      }
    }
    
    ActionListener recallBtnListener = new RecallButtonListener(); //create an actionlistener object
    recallButton.addActionListener(recallBtnListener); //pass the actionlistener to the createButton
    
    //Place the buttons and textfields in the correct place in the current panel
    //1st row
    fieldsPanel.add(hourLabel);
    fieldsPanel.add(hourOfAppointment);
    fieldsPanel.add(minuteLabel);
    fieldsPanel.add(minuteOfAppointment);
    
    //2nd row
    buttonsPanel.add(createButton);
    buttonsPanel.add(cancelButton);
    buttonsPanel.add(recallButton);
    
    //Border
    panel.setBorder(new TitledBorder(new EtchedBorder(), "Appointment"));
    
    //add subPanels to main panel
    panel.add(emptyPanel, BorderLayout.CENTER);
    panel.add(fieldsPanel, BorderLayout.NORTH);
    panel.add(buttonsPanel, BorderLayout.SOUTH);
    
    //return statement
    return panel;
  }
  
  /*
   * Creates a JPanel for the description box
   * @return panel the JPanel which contains the description box
   * */
  public JPanel descriptionPanel()
  {
    JPanel panel = new JPanel(); //Initialize the panel
    descriptionOfAppointment = new JTextArea(6,35); //set dimensions
    panel.add(descriptionOfAppointment); //add the description box to the panel
    //Border
    panel.setBorder(new TitledBorder(new EtchedBorder(), "Description"));
    //return statement
    return panel;
  }
  
  /*
   * Prints the appointments from the appointmentList
   * */
  public void printAppointments()
  {
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    Collections.sort(appointmentList); //Sorts the appointmentList everytime the appointments are printed
    int year,month,day;
    appointmentOutput.setText(""); //reset
    year = appointment.getDate().get(Calendar.YEAR);
    month = appointment.getDate().get(Calendar.MONTH);
    day = appointment.getDate().get(Calendar.DAY_OF_MONTH);
    
    if (firstNameField.getText().equals("") && lastNameField.getText().equals("") && addressField.getText().equals("") && emailField.getText().equals("") && telephoneField.getText().equals(""))
    {
      for (int x = 0; x < appointmentList.size(); x++)
      {
        int years = appointmentList.get(x).getDate().get(Calendar.YEAR);
        int months = appointmentList.get(x).getDate().get(Calendar.MONTH);
        int days = appointmentList.get(x).getDate().get(Calendar.DAY_OF_MONTH);
        
        if(year == years && month == months && day == days)
        {
          
          appointmentOutput.append(appointmentList.get(x).appointmentTime() + "\n\n"); //call appointmentTime from Appointment class
          
        }
      } 
    }
    
    else
    {
      
      Person humanBeing = new Person(lastNameField.getText(),firstNameField.getText(),telephoneField.getText(), addressField.getText(), emailField.getText());
      for (int x = 0; x < appointmentList.size(); x++)
      {
        int years = appointmentList.get(x).getDate().get(Calendar.YEAR);
        int months = appointmentList.get(x).getDate().get(Calendar.MONTH);
        int days = appointmentList.get(x).getDate().get(Calendar.DAY_OF_MONTH);
        
        if(year == years && month == months && day == days)
        {
          
          appointmentOutput.append(appointmentList.get(x).appointmentTime() + " " + humanBeing.toString() + "\n\n"); //call appointmentTime from Appointment class
          
        }
      }
    }
  }
  /*
   * Creates an appointment and adds it to the appointmentList
   * Catches any exception so the program does not crash
   * */
  public void createAppointment()
  {
    boolean bool = false;
    int hours,minutes,years,months,days;
    String description;
    final JFrame errorFrame = new JFrame("Error"); //JFrame to be displayed incase an exception occurs
    //begin try-catch
    try
    {
      hours = Integer.parseInt(hourOfAppointment.getText()); 
      minutes = Integer.parseInt(minuteOfAppointment.getText());
      years = appointment.getDate().get(Calendar.YEAR);
      months = appointment.getDate().get(Calendar.MONTH);
      days = appointment.getDate().get(Calendar.DAY_OF_MONTH);
      description = descriptionOfAppointment.getText();
      
      //reset text fields
      descriptionOfAppointment.setText("");
      hourOfAppointment.setText("");
      minuteOfAppointment.setText("");
      
      //Makes sure real logical numbers are entered
      if (hours < 0 || minutes < 0 || hours > 24 || minutes > 60)
      {
        JOptionPane.showMessageDialog(errorFrame,"Please enter a real number");
        hourOfAppointment.setText(""); //reset text field
        minuteOfAppointment.setText(""); //reset text field
      }
      
      else if (appointmentList.size() == 0)
      {
        appointmentList.add(new Appointment(years,months,days,hours,minutes,description));
        stack.push(appointmentList.get(0));
        printAppointments();
      }
      
      else
      {   
        for (int x = 0; x < appointmentList.size(); x++)
        {
          int appointmentYear = appointmentList.get(x).getDate().get(Calendar.YEAR);
          int appointmentMonth = appointmentList.get(x).getDate().get(Calendar.MONTH);
          int appointmentDay = appointmentList.get(x).getDate().get(Calendar.DAY_OF_MONTH);
          int appointmentHour = appointmentList.get(x).getDate().get(Calendar.HOUR_OF_DAY);
          int appointmentMinute = appointmentList.get(x).getDate().get(Calendar.MINUTE);
          
          //If an appointment with the same date & times exists
          if (years == appointmentYear && months == appointmentMonth && days == appointmentDay && hours == appointmentHour && minutes == appointmentMinute)
          {
            descriptionOfAppointment.setText("CONFLICT!");
            bool = true;
            break;
          }        
        }
        if (bool == false)
        {
          appointmentList.add(new Appointment(years,months,days,hours,minutes,description));
          counter = counter + 1;
          stack.push(appointmentList.get(counter));
          printAppointments();
          
        }
        
      }
    } catch (NumberFormatException event) {
      hourOfAppointment.setText("");
      minuteOfAppointment.setText("");
      JOptionPane.showMessageDialog(errorFrame,"Please enter a real number");
    } catch (IndexOutOfBoundsException e){
      System.out.println("Hello");
    }
    //end try-catch
  }
  
  /*
   * Searching for appointments at a different date
   * */
  public void findAppointments()
  {
    int year,month,day;
    int maxMonth,maxDay;
    int minMonth,minDay;
    final JFrame errorFrame = new JFrame("Error"); //parent frame for the error message
    
    //begin try-catch
    try
    {
      year = Integer.parseInt(yearField.getText());
      month = Integer.parseInt(monthField.getText()) - 1;
      day = Integer.parseInt(dayField.getText());
      
      maxMonth = new GregorianCalendar(year,month,day).getActualMaximum(Calendar.MONTH);
      maxDay = new GregorianCalendar(year,month,day).getActualMaximum(Calendar.DAY_OF_MONTH);
      
      minMonth = new GregorianCalendar(year,month,day).getActualMinimum(Calendar.MONTH);
      minDay = new GregorianCalendar(year,month,day).getActualMinimum(Calendar.DAY_OF_MONTH);
      
      
      //Makes sure real numbers are entered
      if (year <= 0 || month < minMonth || month > maxMonth || day < minDay || day > maxDay)
        JOptionPane.showMessageDialog(errorFrame,"Date does not exist, please enter a valid date"); //error
      else
      {
        appointment.setDate(year,month,day);
        currentDate.setText(appointment.stringDate());
      }
    }
    catch (NumberFormatException event)
    {
      JOptionPane.showMessageDialog(errorFrame,"Please enter a real number"); //error
    }
    //end try-catch
  }
  
  /*
   * Cancels appointments created at a specific time
   * */
  public void cancelAppointment()
  {
    boolean bool = false;
    int hours,minutes,years,months,days;
    final JFrame errorFrame = new JFrame("Error");
    
    //begin try-catch
    try
    {
      hours = Integer.parseInt(hourOfAppointment.getText()); 
      minutes = Integer.parseInt(minuteOfAppointment.getText());
      years = appointment.getDate().get(Calendar.YEAR);
      months = appointment.getDate().get(Calendar.MONTH);
      days = appointment.getDate().get(Calendar.DAY_OF_MONTH);
      
      //reset text fields
      descriptionOfAppointment.setText("");
      hourOfAppointment.setText("");
      minuteOfAppointment.setText("");
      
      //Makes sure real logical numbers are entered
      if (hours < 0 || minutes < 0 || hours > 24 || minutes > 60)
      {
        JOptionPane.showMessageDialog(errorFrame,"Please enter a real number");
        hourOfAppointment.setText(""); //reset text field
        minuteOfAppointment.setText(""); //reset text field
      }
      
      else
      {   
        for (int x = 0; x < appointmentList.size(); x++)
        {
          int appointmentYear = appointmentList.get(x).getDate().get(Calendar.YEAR);
          int appointmentMonth = appointmentList.get(x).getDate().get(Calendar.MONTH);
          int appointmentDay = appointmentList.get(x).getDate().get(Calendar.DAY_OF_MONTH);
          int appointmentHour = appointmentList.get(x).getDate().get(Calendar.HOUR_OF_DAY);
          int appointmentMinute = appointmentList.get(x).getDate().get(Calendar.MINUTE);
          
          //if the date & time of a specificed appointment exists
          if (years == appointmentYear && months == appointmentMonth && days == appointmentDay && hours == appointmentHour && minutes == appointmentMinute)
          {
            if (stack.search(appointmentList.get(x)) != -1)
              stack.remove(x);
            appointmentList.remove(x); //Removes the appointment from the list
            appointmentOutput.setText(""); //Reset the output box
            bool = true;
            break;
          }        
        }
        if (bool == false)
        {
          JOptionPane.showMessageDialog(errorFrame,"There is nothing going on at " + hours + ":" + minutes);
        }
        printAppointments();
        
      }
    } catch (NumberFormatException event) {
      hourOfAppointment.setText("");
      minuteOfAppointment.setText("");
      JOptionPane.showMessageDialog(errorFrame,"Please enter a real number");
    }
    //end try-catch
    
  }
  
  
  /*
   * Method used in the recall button's action listener
   * */
  public void recallAppointment()
  {
    
    JFrame errorFrame = new JFrame("Error");
    
    try{
      
      Appointment undoAppointment = stack.peek(); //peek and store in an Appointment
      Calendar cal = undoAppointment.getDate(); //get the date of the appointment
      Calendar tempCal = new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)); //create a temporary calendar using cal
      hourOfAppointment.setText(cal.get(Calendar.HOUR) + ""); //set the text to the appointment's hour
      minuteOfAppointment.setText(cal.get(Calendar.MINUTE) + ""); //set the text to the appointment's minute
      dateFormat = new SimpleDateFormat("EEE, MMM, dd, yyyy"); //format it properly
      appointment.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
      currentDate.setText(dateFormat.format(tempCal.getTime())); 
      printAppointments();
    } catch (EmptyStackException e){
      JOptionPane.showMessageDialog(errorFrame,"Stack is empty!");
    }
  }
  
  /*
   * updates the instance variable monthLabel
   * */
  public void updateMonthLabel()
  {
    monthLabel.setText(appointment.stringMonthDate());
  }
  
  /*
   * Creates button given the current day
   * @param current day 
   * */
  public void createButtons(int currentDay)
  {
    calDate = appointment.getDate(); 
    buttonArray = new JButton[calDate.getActualMaximum(Calendar.DAY_OF_MONTH)];
    buttonsPanel.removeAll();
    /*
     * Embedded class for the ButtonListener button's action listener
     * */
    class ButtonListener implements ActionListener
    {
      /*
       * When the front button is clicked perform this method
       * @param e the actionListener
       * */
      public void actionPerformed(ActionEvent e)
      {
        String temp = e.getActionCommand();
        int day = Integer.parseInt(temp);
        coloredDay.setBackground(new JButton().getBackground());
        coloredDay = buttonArray[day - 1];
        buttonArray[day - 1].setBackground(Color.red);
        appointment.setDate(calDate.get(Calendar.YEAR), calDate.get(Calendar.MONTH), day);
        currentDate.setText(appointment.stringDate());//Creates a JLabel with current date
        printAppointments();
      }
    }
    ActionListener buttonListener = new ButtonListener(); //Create action listener object
    
    
    //Creating the buttons
    for (int x = 0; x < calDate.getActualMaximum(Calendar.DAY_OF_MONTH); x++)
    { 
      if (x == currentDay)
      {
        coloredDay = new JButton(x + 1 + "");
        coloredDay.setBackground(Color.red);
        buttonsPanel.add(coloredDay);
      }
      else
      {
        buttonArray[x] = new JButton(x + 1 + "");
        buttonArray[x].addActionListener(buttonListener);
        buttonsPanel.add(buttonArray[x]);
      }
    }  
  }
}