//Ahmad Alsabagh
//500763910
import javax.swing.JFrame;
import java.io.*;

/**
 * @author Ahmad Alsabagh
 * @date March 16th, 2017
 * @description This class allows the user to test out the appointment program.
*/
public class AppointmentViewer
{  
   public static void main(String[] args) //throws FileNotFoundException, Exception
   {  
      JFrame frame = new AppointmentFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setTitle("Appointments");
      frame.setVisible(true);
   }

}