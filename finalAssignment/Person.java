//Ahmad Alsabagh
//500763910
//Required Packages
public class Person implements Comparable<Person>
{
  
  private String lastName, firstName, telephone, address, email; //instance variables
  
  //Default constructor
  public Person()
  {
    this.lastName = "";
    this.firstName = "";
    this.telephone = "";
    this.address = "";
    this.email = "";
  }
  /*
   * Constructor sets only firstname and lastname
   * @param firstName 
   * @param lastName
   * */
  public Person(String firstName, String lastName)
  {
    this.lastName = lastName;
    this.firstName = firstName;
    this.telephone = "";
    this.address = "";
    this.email = "";
  }
  
  /*
   * Constructor sets everything
   * @param lastName
   * @param firstName
   * @param telephone
   * @param address
   * @param email
   * */
  public Person(String lastName, String firstName, String telephone, String address, String email)
  {
    this.lastName = lastName;
    this.firstName = firstName;
    this.telephone = telephone;
    this.address = address;
    this.email = email;
    
  }
  
  /*
   * gets first name
   * @return firstname
   * */
  public String getFirstName()
  {
    return firstName;
  }
  
  /*
   * gets last name
   * @return lastname
   * */
  public String getLastName()
  {
    return lastName;
  }
  
  /*
   * gets email
   * @return email
   * */
  public String getEmail()
  {
    return email;
  }
  
  /*
   * gets telephone 
   * @return telephone
   * */
  public String getTel()
  {
    return telephone;
  }
  
  /*
   * gets address
   * @return address
   * */
  public String getAddress()
  {
    return address;
  }
  
  /*
   * sets firstname
   * @param firstname
   * */
  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }
  
  /*
   * sets lastname
   * @param lastname
   * */
  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }
  
  /*
   * sets email
   * @param email
   * */
  public void setEmail(String email)
  {
    this.email = email;
  }
  
  /*
   * sets telephone 
   * @param telephone
   * */
  public void setTel(String telephone)
  {
    this.telephone = telephone;
  }
  
  /*
   * Compare emails
   * @param email
   * @return 0, -1, 1 according to which one comes first
   * */
  public int compareToEmail(String email)
  {
    return this.email.compareTo(email);
  }
  
  /*
   * Compare telephone numbers
   * @param telephone
   * @return 0, -1, 1 according to which one comes first
   * */
  public int compareToTel(String telephone)
  {
    return this.telephone.compareTo(telephone);
  }
  
  /*
   * Compare first & last names
   * @param Person object
   * @return 0, -1, 1 according to which one comes first
   * */
  public int compareTo(Person other)
  {
    if (lastName.equals(other.getLastName())) {
      return firstName.compareTo(other.getFirstName()) ;
    }
    else
      return lastName.compareTo(other.getLastName()) ; 
  }
  
  /*
   * String representation of a person
   * @return String representation
   * */
  public String toString()
  {
    return firstName + " " + lastName + " " + telephone + " " + email + " " + address; 
  }
}