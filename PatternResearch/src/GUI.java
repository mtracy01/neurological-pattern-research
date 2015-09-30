
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
	static int clickTime;
	static boolean reg = true;
	public void startWindow(){
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
                clickTime =1;
                JPanel inputpanel = new JPanel();
                inputpanel.setLayout(new BoxLayout(inputpanel,BoxLayout.Y_AXIS));
                JTextField input = new JTextField(15);
                JButton button = new JButton(new AbstractAction("Start Recording"){
					@Override
					public void actionPerformed(ActionEvent e) {
						modelName = input.getText();
						try{
							recordingDuration = Double.parseDouble(duration.getText());
							if(clickTime == 1){
								numRecordings = Integer.parseInt(number.getText());
								clickTime = 0;
							}
							else numRecordings = numRecordings-1;
							
						}catch(Exception e1){
							JOptionPane.showMessageDialog(null, "Not an integer, please re-enter it :p","Error",JOptionPane.ERROR_MESSAGE);
						};	
						if(numRecordings > 0){
							System.out.println("name is "+modelName+" duration is"+recordingDuration+"and number of recordings is "+ numRecordings );
							frame.setVisible(false);
							Main.logData(modelName, numRecordings, recordingDuration);
						}
					}
                	
                });
                button.setText("Continue");
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
		if(reg){
		Thread t1 = new Thread(new Runnable() {
		     public void run() {
		          Main.startRecognition();
		     }
		});  
		t1.start();
		}
		
		else Main.stopRecognition();
		reg = !reg;
	}
	public static void recordProcess(double durationTime){
		System.out.println("hello");
		JFrame f1 = new JFrame("Recording");
		f1.setSize(400,300);
		JPanel panel= new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        JLabel mname = new JLabel("Mode Name: "+GUI.modelName);
        mname.setFont(new Font("TimesRoman", Font.ITALIC, 30));
        JLabel pdu = new JLabel("Preparing Time: ");
        pdu.setFont(new Font("TimesRoman", Font.ITALIC, 30));
        JLabel rdu = new JLabel("Remaining Time: ");
        rdu.setFont(new Font("TimesRoman", Font.ITALIC, 30));
        JLabel mnum = new JLabel("Number :" +GUI.numRecordings); 
        mnum.setFont(new Font("TimesRoman", Font.ITALIC, 30));
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
        	double t = durationTime+5;
        	double ptime = 5;
            public void run() {
                System.out.println(t+""+ptime);
                if(ptime > 0){
                	ptime--;
                	pdu.setText("Preparing time: " + Double.toString(ptime));
                }
                if(ptime == 0.0){	
                	rdu.setText("Remaining time: " + Double.toString(t));
                }
                if ((t--)< 1.0){
                    timer.cancel();
                    
                	f1.setVisible(false);
                }
            }
        }, 0, 1000);
        panel.add(mname);
        panel.add(pdu);
        panel.add(rdu);
        panel.add(mnum);
        f1.add(panel);
        f1.setVisible(true);
	}

	
}
