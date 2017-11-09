import java.util.*;

public class TelephoneCompare extends Person implements Comparator<Person>
{
  /*
   * compare method which must be used because of the comparator class
   * @param first person object
   * @param second person object
   * @return 0, 1 , -1 depending on which one comes first
   * */
  public int compare(Person one, Person two)
  {
    return one.getTel().compareTo(two.getTel());
  }
}