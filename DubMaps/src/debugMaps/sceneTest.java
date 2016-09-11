package debugMaps;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
public class sceneTest extends JFrame implements ActionListener {
JFXPanel fxpanel,fxpanel1;
static String filepath;
JTable table;
public sceneTest()
{
  setLayout(null);
  Button b1= new Button("OK");
  JButton b2= new JButton("OKK");
  add(b1);
  add(b2);
  b1.setBounds(20,50,50,30);
  b2.setBounds(70,50,50,30);
 b1.addActionListener(this);
 b2.addActionListener(this);

 fxpanel= new JFXPanel();
 add(fxpanel);
initFx(fxpanel);

b1.getS
setMinimumSize(new Dimension(600,600));
}

 public void actionPerformed(ActionEvent ae)
 {
 if(ae.getActionCommand().equals("OK"))
  {
    

   }
   if(ae.getActionCommand().equals("OKK"))
   {

  }
   Platform.runLater(new Runnable() {
   public void run() {
      initFx(fxpanel);
    }}
      );
   }



	private void initFx(final JFXPanel fxpanel) {
	  Group group= new Group();
	  Scene scene= new Scene(group);
	  fxpanel.setScene(scene);    
	
	  String resourcePath = "/debugMaps/sheet.css";
	  URL location = getClass().getResource(resourcePath);
	  scene.getStylesheets().add(location.toString());
	   
	  }

	public static void main(final String args[]) {
	       sceneTest frm=new sceneTest();
	       frm.setVisible(true);
	  }


}