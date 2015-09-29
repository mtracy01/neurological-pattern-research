import java.awt.Color;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RunnableGUI implements Runnable{

	@Override
	public void run() {
		//GUI.recordEvent();
		recordProcess(GUI.recordingDuration);
		
	}
	public static void recordProcess(final double durationTime){
		System.out.println("hello");
		JFrame f1 = new JFrame("Recording");
		f1.setSize(400,300);
		f1.setBackground(Color.WHITE);
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
