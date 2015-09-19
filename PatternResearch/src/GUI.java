
import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

public class GUI extends JPanel implements ActionListener{
	public GUI(){
		JFrame f = new JFrame("Display Window");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(400,500);

		JPanel whole = new JPanel(new GridLayout(3,0));
		JPanel buttontop = new JPanel();
		JLabel label = new JLabel("Main Menu");
		label.setFont(new Font("TimesRoman", Font.ITALIC, 25));
		buttontop.add(label);
		
		JPanel buttonup = new JPanel(new GridLayout(3,1));
		JPanel buttonlow = new JPanel();
		BoxLayout boxLayout = new BoxLayout(buttonlow, BoxLayout.LINE_AXIS);
		buttonlow.setLayout(boxLayout);
		
		JButton reportEvent = new JButton("Report Event");
	    reportEvent.setVerticalTextPosition(AbstractButton.CENTER);
	    //reportEvent.setHorizontalTextPosition(AbstractButton.LEFT);
	    reportEvent.setPreferredSize(new Dimension(40,40));
	    reportEvent.setMnemonic(KeyEvent.VK_D);
	  
		JButton beginRecording = new JButton("Begin Reporting");
		beginRecording.setVerticalTextPosition(AbstractButton.CENTER);
		beginRecording.setPreferredSize(new Dimension(40,40));
		//beginRecording.setHorizontalTextPosition(AbstractButton.RIGHT); 
		beginRecording.setMnemonic(KeyEvent.VK_D);
	    
		JButton dataEvent = new JButton("Review Data");
		dataEvent.setPreferredSize(new Dimension(40,40));
		dataEvent.setHorizontalTextPosition(AbstractButton.CENTER);
		dataEvent.setVerticalTextPosition(AbstractButton.BOTTOM);
		
		JButton exitEvent = new JButton("Exit");
		
		exitEvent.setHorizontalTextPosition(AbstractButton.CENTER);
		exitEvent.setVerticalTextPosition(AbstractButton.BOTTOM);
			
		buttonup.add(reportEvent);
		buttonup.add(beginRecording);
		
		buttonup.add(dataEvent);
		buttonlow.add(exitEvent);
		whole.add(buttontop);
		whole.add(buttonup);
		whole.add(buttonlow);

		f.getContentPane().add(whole);

		f.setVisible(true);
		
		
	}

	public static void main(String args[]){
		 GUI g = new GUI();

	}

	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
