
import java.awt.*;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

import java.awt.event.*;
import java.util.*;
import java.util.Timer;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
public class GUI extends JPanel implements ActionListener{
	static String modelName;
	static int numRecordings;
	static double recordingDuration;
	static String getName;
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
		
		JButton reportEvent = new JButton("Record Event");
	    reportEvent.setVerticalTextPosition(AbstractButton.CENTER);
	   
	    reportEvent.setPreferredSize(new Dimension(40,40));
	    reportEvent.setMnemonic(KeyEvent.VK_D);
	    reportEvent.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
            	createFrame();
                
            }
        });
	  
		JButton learnData = new JButton("Learn From Data");
		learnData.setVerticalTextPosition(AbstractButton.CENTER);
		learnData.setPreferredSize(new Dimension(40,40)); 
		learnData.setMnemonic(KeyEvent.VK_D);
	    learnData.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadFiles();
				
			}
	    	
	    });
		JButton dataEvent = new JButton("Recognizing");
		dataEvent.setPreferredSize(new Dimension(40,40));
		dataEvent.setHorizontalTextPosition(AbstractButton.CENTER);
		dataEvent.setVerticalTextPosition(AbstractButton.BOTTOM);
		dataEvent.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				recognizeData();
				
			}
			
		});
		JButton exitEvent = new JButton("Exit");
		
		exitEvent.setHorizontalTextPosition(AbstractButton.CENTER);
		exitEvent.setVerticalTextPosition(AbstractButton.BOTTOM);
			
		buttonup.add(reportEvent);
		buttonup.add(learnData);
		
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
	public static void createFrame()
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {	

                JFrame frame = new JFrame("User Information");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                try 
                {
                   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                   e.printStackTrace();
                }
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setOpaque(true);
                JLabel title1 = new JLabel("                Record Event");
                title1.setFont(new Font("TimesRoman", Font.ITALIC, 25));
                
                JTextField duration = new JTextField(15);
                JTextField number = new JTextField(15);
                duration.setFont(Font.getFont(Font.SANS_SERIF));
                JLabel name = new JLabel("name");
                JLabel lduration = new JLabel("duration");
                JLabel lnumber = new JLabel("counts");
                
                JPanel inputpanel = new JPanel();
                inputpanel.setLayout(new BoxLayout(inputpanel,BoxLayout.Y_AXIS));
                JTextField input = new JTextField(15);
                JButton button = new JButton(new AbstractAction("Start Recording"){

					@Override
					public void actionPerformed(ActionEvent e) {
						modelName = input.getText();
						try{
							recordingDuration = Double.parseDouble(duration.getText());
							numRecordings = Integer.parseInt(number.getText());
							
							//TODO: Connect the UI and the backend....
							
							Main.logData(modelName, numRecordings, recordingDuration);
							//TODO: Ji, Connect the rest of the UI to the backend...
							
						}catch(Exception e1){
							JOptionPane.showMessageDialog(null, "Not an integer, please re-enter it :p","Error",JOptionPane.ERROR_MESSAGE);
						};	
						System.out.println("name is "+modelName+" duration is"+recordingDuration+"and number of recordings is "+ numRecordings );
						recordEvent();
						//recordProcess(30.0);
					}
                	
                });
                inputpanel.add(title1);
                inputpanel.add(name);
                inputpanel.add(input);
           
                inputpanel.add(lduration);
                inputpanel.add(duration);
               
                inputpanel.add(lnumber);
                inputpanel.add(number);
                
                inputpanel.add(button);
                panel.add(inputpanel);
                frame.getContentPane().add(BorderLayout.CENTER, panel);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
                frame.setResizable(false);
                input.requestFocus();
               
            }
        });
    }
	
	
	public static void recordEvent(){
		JFrame f1 = new JFrame("Recording");
		f1.setSize(300,300);
		JPanel panel= new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(true);
        JLabel title = new JLabel("                Recording ..");
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int i = Integer.parseInt("6");
            public void run() {
                System.out.println(i--);
                title.setText("Remaining time: " + Integer.toString(i));
                title.setFont(new Font("TimesRoman", Font.ITALIC, 30));
                if (i< 1){
                    timer.cancel();
                	f1.setVisible(false);
                }
            }
        }, 0, 1000);
        panel.add(title);
        f1.add(panel);
        f1.setVisible(true);
		
	}
	public static void recordProcess(final double durationTime){
		JFrame f1 = new JFrame("Recording");
		f1.setSize(400,300);
		f1.setBackground(Color.WHITE);
		JPanel panel= new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        JLabel mname = new JLabel("Mode Name: "+modelName);
        mname.setFont(new Font("TimesRoman", Font.ITALIC, 30));
        JLabel mdu = new JLabel("Duration time:  "+recordingDuration);
        mdu.setFont(new Font("TimesRoman", Font.ITALIC, 30));
        JLabel rdu = new JLabel("Remaining Time: ");
        rdu.setFont(new Font("TimesRoman", Font.ITALIC, 30));
        JLabel mnum = new JLabel("Number :" +numRecordings); 
        mnum.setFont(new Font("TimesRoman", Font.ITALIC, 30));
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
        	double t = durationTime;
            public void run() {
                System.out.println(t);
                mdu.setText("Remaining time: " + Double.toString(t));
               // title.setFont(new Font("TimesRoman", Font.ITALIC, 30));
                if ((t--)< 1.0){
                    timer.cancel();
                	f1.setVisible(false);
                }
            }
        }, 0, 1000);
        panel.add(mname);
        panel.add(mdu);
        panel.add(rdu);
        panel.add(mnum);
        f1.add(panel);
        f1.setVisible(true);
		
	}
	public static void loadFiles(){
		JFrame loadFrame = new JFrame();
		loadFrame.setSize(new Dimension(500,600));
		String[] allName = new String[Main.rawName.size()];
		Iterator i = Main.rawName.iterator();
		int count = 0;
		while(i.hasNext()){
			allName[count++] = (i.next()).toString();
		}
		//String[] allName = {"one","two","three","four","five"};
		JList list = new JList(allName);
		ListSelectionModel lsm = list.getSelectionModel();
		lsm.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				if( !listSelectionEvent.getValueIsAdjusting() && !lsm.isSelectionEmpty()){
					JOptionPane.showMessageDialog(null, "Selection Confirmed","Confirm",JOptionPane.INFORMATION_MESSAGE);
					getName = allName[lsm.getLeadSelectionIndex()];	
				}
			}
		});
		JScrollPane listPane = new JScrollPane(list);
		listPane.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 20));
		JPanel jp = new JPanel();
		jp.setPreferredSize(new Dimension(480,500));
		jp.add(new JLabel("Select Raw Data"));
		jp.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 20));
        JPanel listContainer = new JPanel(new GridLayout(1,1));
        listContainer.setPreferredSize(new Dimension(480,500));
        listContainer.setBorder(BorderFactory.createTitledBorder("List"));
        listContainer.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 20));
        listContainer.add(listPane);
		jp.add(listContainer);
		loadFrame.add(jp);
		loadFrame.setVisible(true);
	}
	
	public static void recognizeData(){
		return;
	}
	
	
}
