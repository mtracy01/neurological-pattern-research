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
		f.setSize(350, 350);
		
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
		JButton loadDataButton = new JButton("Load Data Set");
		loadDataButton.setPreferredSize(new Dimension(40,40));
		loadDataButton.setHorizontalTextPosition(AbstractButton.CENTER);
		loadDataButton.setVerticalTextPosition(AbstractButton.TOP);
		loadDataButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
					//TODO: Call function that will load data and replace the current data set
			}
			
		});
		
		//Save data to a file
		JButton saveDataButton = new JButton("Save Data Set");
		saveDataButton.setPreferredSize(new Dimension(40,40));
		saveDataButton.setHorizontalTextPosition(AbstractButton.CENTER);
		saveDataButton.setVerticalTextPosition(AbstractButton.TOP);
		saveDataButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
					//TODO: Call function that will save the current data set to a file
			}
			
		});
		
		//Clear Data Set Button
		JButton clearDataButton = new JButton("Clear Data Set");
		clearDataButton.setPreferredSize(new Dimension(40,40));
		clearDataButton.setHorizontalTextPosition(AbstractButton.CENTER);
		clearDataButton.setVerticalTextPosition(AbstractButton.TOP);
		clearDataButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
					//TODO: Call function that will clear the current data set
			}
			
		});
		
		//Add point from file button
		JButton loadPointButton = new JButton("Add ParsedData");
		loadPointButton.setPreferredSize(new Dimension(40,40));
		loadPointButton.setHorizontalTextPosition(AbstractButton.CENTER);
		loadPointButton.setVerticalTextPosition(AbstractButton.TOP);
		loadPointButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
					//TODO: Call function that will clear the current data set
			}
			
		});
		
		
		JButton savePointButton = new JButton("Save ParsedData");
		savePointButton.setPreferredSize(new Dimension(40,40));
		savePointButton.setHorizontalTextPosition(AbstractButton.CENTER);
		savePointButton.setVerticalTextPosition(AbstractButton.TOP);
		savePointButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
					//TODO: Call function that will clear the current data set
			}
			
		});
		
		
		JButton deletePointButton = new JButton("Remove ParsedData");
		deletePointButton.setPreferredSize(new Dimension(40,40));
		deletePointButton.setHorizontalTextPosition(AbstractButton.CENTER);
		deletePointButton.setVerticalTextPosition(AbstractButton.TOP);
		deletePointButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
					//TODO: Call function that will clear the current data set
			}
			
		});
		
		//Exit Button
		JButton exitEvent = new JButton("Exit");
		exitEvent.setHorizontalTextPosition(AbstractButton.CENTER);
		exitEvent.setVerticalTextPosition(AbstractButton.BOTTOM);
		
		buttonup.add(loadDataButton);
		buttonup.add(loadPointButton);
		buttonup.add(saveDataButton);
		buttonup.add(savePointButton);
		buttonup.add(clearDataButton);
		buttonup.add(deletePointButton);
		
		buttonlow.add(exitEvent);
		
		whole.add(buttontop);
		whole.add(buttonup);
		whole.add(buttonlow);

		f.getContentPane().add(whole);
		f.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//Auto-generated method stub
	}
}
