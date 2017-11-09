import java.util.*;
import java.io.*;
import javax.swing.*;


public class Contacts
{
  private Person person; //Person object
  private LinkedList<Person> list; //Linkedlist to store person objects
  private EmailCompare emailClass; //comparator class
  private TelephoneCompare tel; //comparator class
  
  
  public Contacts() 
  {
    list = new LinkedList<Person>();  //initilize
    JFrame errorFrame = new JFrame("Error");
    try{
      readContactsFile();
    } catch (FileNotFoundException e){
      JOptionPane.showMessageDialog(errorFrame,"File not found!");
    }
  }
  
  /*
   * Finds person using first and last name
   * @param firstname
   * @param lastname
   * @return reference to the person object if found
   * */
  public Person findPerson(String firstName, String lastName)
  {
    Person personOne = new Person(firstName,lastName);
    for (Person element: list)
    {
      if (element.compareTo(personOne) == 0)
        personOne = element;
    }
    return personOne;
  }
  
  /*
   * Finds person using telephone number
   * @param telephone
   * @return reference to the person if found
   * */
  public Person findPersonTel(String telephone)
  {
    Person personTwo = new Person();
    personTwo.setTel(telephone);
    for (Person element : list)
    {
      if (element.compareToTel(telephone) == 0)
        personTwo = element;
    }
    return personTwo;
  }
  
   /*
   * Finds person using email address
   * @param email
   * @return reference to the person if found
   * */
  public Person findPersonEmail(String email)
  {
    Person personThree = new Person();
    personThree.setEmail(email);
    for (Person element : list)
    {
      if (element.compareToEmail(email) == 0)
        personThree = element;
    }
    return personThree;
    
  }
  
  /*
   * Reads contacts.txt and creates a person and stores it in the linkedlist
   * @throws FileNotFoundException
   * @throws IllegalArguementException
   * */
  public void readContactsFile() throws FileNotFoundException, IllegalArgumentException
  {
    JFrame errorFrame = new JFrame("Error");
    Scanner lineCounter = new Scanner(new File("contacts.txt"));
    int size = 0;
    int lineNumber = 0;
    String name = null;
    String lastName = null;
    String address = null;
    String email = null;
    String telephone = null;

    //Count number of lines
    while(lineCounter.hasNextLine())
    {
      lineNumber++;
      lineCounter.nextLine();
    }
    
    Scanner scanner = new Scanner(new File("contacts.txt"));
    
    //Make sure first line is an integer
    try{
      if(scanner.hasNextLine())
        size = Integer.parseInt(scanner.nextLine());
      else
        throw new NumberFormatException();
    } catch (NumberFormatException e){
      JOptionPane.showMessageDialog(errorFrame,"Please make sure contacts.txt's first line is an integer and try again");
    }
    
    //Make sure # of lines divisible by 5
    try{
      if((lineNumber - 1) % 5 != 0) //remove one line which is the size of the list
        throw new IllegalArgumentException();
    }catch (IllegalArgumentException e){
      JOptionPane.showMessageDialog(errorFrame,"Please make sure your lines are a multiple of 5 (excluding the first line)");
    }
    
    //adds the elements to a person object then adds the object to the list
    try{
      for (int x = 0; x < lineNumber/5; x++)
      {
        lastName = scanner.nextLine();
        name = scanner.nextLine();
        address = scanner.nextLine();
        telephone = scanner.nextLine();
        email = scanner.nextLine();
        if (name.equals("") || lastName.equals("") || address.equals("") || telephone.equals("") || email.equals(""))
          throw new IllegalArgumentException();
        else
        {
          Person person = new Person(lastName, name, telephone, address, email);
          list.add(person);
        }
      }
    } catch (IllegalArgumentException e){
      JOptionPane.showMessageDialog(errorFrame,"Please make sure each contact has no missing information");
    }
  }
}
