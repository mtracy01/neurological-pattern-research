import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import LearningProcess.ParsedDataLearningCore;
import Objects.Data;
import Objects.NormalizeData;
import helperClasses.FileHelper;

public class ConfigurationGUI extends JPanel implements ActionListener {
	
	private JTextField filename = new JTextField(), dir = new JTextField();
	
	public void startWindow(){
		final JFrame f = new JFrame("Display Window");
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
				JFileChooser c = new JFileChooser();
				int rVal= c.showOpenDialog(ConfigurationGUI.this);
				if(rVal==JFileChooser.APPROVE_OPTION){
					FileHelper.loadDataSet(c.getSelectedFile().getAbsolutePath());
					ParsedDataLearningCore.createMLPerceptron();
				}
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
				JFileChooser c = new JFileChooser();
			      int rVal = c.showSaveDialog(ConfigurationGUI.this);
			      if (rVal == JFileChooser.APPROVE_OPTION)
			    	  FileHelper.saveDataSet(c.getCurrentDirectory().toString() +  "/" + c.getSelectedFile().getName());
			      if (rVal == JFileChooser.CANCEL_OPTION) {
			        filename.setText("You pressed cancel");
			        dir.setText("");
			      }
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
					Data.clear();
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
				JFileChooser c = new JFileChooser();
				int rVal= c.showOpenDialog(ConfigurationGUI.this);
				if(rVal==JFileChooser.APPROVE_OPTION)
					FileHelper.loadPoint(c.getSelectedFile().getAbsolutePath());
			}
			
		});
		
		
		JButton savePointButton = new JButton("Save ParsedData");
		savePointButton.setPreferredSize(new Dimension(40,40));
		savePointButton.setHorizontalTextPosition(AbstractButton.CENTER);
		savePointButton.setVerticalTextPosition(AbstractButton.TOP);
		savePointButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] recNames = Data.getRecordingNames();
				int response = JOptionPane.showOptionDialog(f,"Select Recording","Select a Recording",0,0,null, recNames,recNames);
				
				JFileChooser c = new JFileChooser();
			      int rVal = c.showSaveDialog(ConfigurationGUI.this);
			      if (rVal == JFileChooser.APPROVE_OPTION) 
			    	  FileHelper.savePoint(c.getCurrentDirectory().toString() + "/" + c.getSelectedFile().getName(), response);
			      if (rVal == JFileChooser.CANCEL_OPTION) {
			        filename.setText("You pressed cancel");
			        dir.setText("");
			      }
			}
			
		});
		
		
		JButton deletePointButton = new JButton("Remove ParsedData");
		deletePointButton.setPreferredSize(new Dimension(40,40));
		deletePointButton.setHorizontalTextPosition(AbstractButton.CENTER);
		deletePointButton.setVerticalTextPosition(AbstractButton.TOP);
		deletePointButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] recNames = Data.getRecordingNames();
				int response = JOptionPane.showOptionDialog(f,"Select Recording","Select a Recording",0,0,null, recNames,recNames);
				FileHelper.removePoint(response);
			}
			
		});
		
		//Process directory of CSV data
		final JFileChooser dc = new JFileChooser();
		JButton selectCSVDirButton = new JButton("Preprocess CSV Directory");
		selectCSVDirButton.setPreferredSize(new Dimension(40,40));
		selectCSVDirButton.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int ret = dc.showOpenDialog(ConfigurationGUI.this);
				if(ret==JFileChooser.APPROVE_OPTION){
					File file = dc.getSelectedFile();
					
					if(!file.isDirectory())
						JOptionPane.showMessageDialog(f,"Not a directory!","Bad Filetype",JOptionPane.ERROR_MESSAGE);
					else{
						Object[] options = {"Yes","No"};
						int n = JOptionPane.showOptionDialog(f,"Would you like to also load this data into the program?",
								"Loading Data",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE,
								null,
								options,
								"Cancel");
						if(n<2)
							NormalizeData.normalizePDDirectory(file,n);						
					}
				}
			}
		});
		
		JButton selectLearnTypeButton = new JButton("Select Learning Algorithm");
		selectLearnTypeButton.setPreferredSize(new Dimension(40,40));
		selectLearnTypeButton.setHorizontalTextPosition(AbstractButton.CENTER);
		selectLearnTypeButton.setVerticalTextPosition(AbstractButton.TOP);
		selectLearnTypeButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] options = {"MLPerceptron","SVM"};
				int n = JOptionPane.showOptionDialog(f, "Select an algorithm:", "Select Algorithm", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, "Cancel");
				if(n==0)
					GUI.learningType=0;
				if(n==1)
					GUI.learningType=1;
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
		buttonup.add(selectCSVDirButton);
		buttonup.add(selectLearnTypeButton);
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
