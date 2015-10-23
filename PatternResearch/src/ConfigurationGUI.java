import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConfigurationGUI extends JPanel implements ActionListener {
	
	/*TODO:
	 * Save the entire parsedDataSet collection to a file
	 * Save a specific parsedData to a file
	 * Delete a specific parsedData
	 * Delete the current parsedDataSet
	 * Load a parsedDataSet from a file
	 * Load a particular parsedData from a file
	 */
	
	public void startWindow(){
		JFrame f = new JFrame("Display Window");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(300, 300);
		
		JPanel whole = new JPanel(new GridLayout(5,0));
		JPanel buttontop = new JPanel();
		JLabel label = new JLabel("Configuration Menu");
		label.setFont(new Font("TimesRoman", Font.ITALIC, 25));
		buttontop.add(label);
		
		JPanel buttonup = new JPanel(new GridLayout(4,1));
		JPanel buttonlow = new JPanel();
		BoxLayout boxLayout = new BoxLayout(buttonlow, BoxLayout.LINE_AXIS);
		buttonlow.setLayout(boxLayout);
		
		
		//Buttons defined below
		
		//Load ParsedData set file.
		JButton loadParsedDataButton = new JButton("Load ParsedData Set");
		loadParsedDataButton.setPreferredSize(new Dimension(40,40));
		loadParsedDataButton.setHorizontalTextPosition(AbstractButton.CENTER);
		loadParsedDataButton.setVerticalTextPosition(AbstractButton.TOP);
		loadParsedDataButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
					//TODO: Call function that will load data and replace the current data set
			}
			
		});
		
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//Auto-generated method stub
	}
}
