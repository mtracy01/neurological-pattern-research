import com.sun.jna.Pointer;

import com.sun.jna.ptr.IntByReference;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

import java.util.*;
import java.util.Timer;

public class Main extends JPanel{
	
	public static ArrayList<String> rawName = new ArrayList<>();
	public static Hashtable<String,RawData> rawPatterns = new Hashtable<>();
	public static ArrayList<String> rawDataNames = new ArrayList<String>();
	
	public static void main(String[] args){
		GUI gui = new GUI();
		gui.startWindow();
	}
	
	//Record a certain number of raw inputs and store them into some sort of database
	public static void logData(String dataName, int numRecordings, double recordingDuration){
		
		short composerPort = 1726;
		int option = 1;
		
		RawData rawData = new RawData(dataName, numRecordings, recordingDuration);
		
		//Confirm connection to device
		switch (option) {
		case 1: {
			
			break;
		}
		case 2: {
			System.out.println("Target IP of EmoComposer: [127.0.0.1] ");

			if (Edk.INSTANCE.EE_EngineRemoteConnect("127.0.0.1", composerPort,
					"Emotiv Systems-5") != EdkErrorCode.EDK_OK.ToInt()) {
				System.out
						.println("Cannot connect to EmoComposer on [127.0.0.1]");
				return;
			}
			System.out.println("Connected to EmoComposer on [127.0.0.1]");
			break;
		}
		default:
			System.out.println("Invalid option...");
			return;
		}

		
		//Begin creating and processing data
		
		System.out.println("Start receiving EEG Data!");
		//TODO: Add 5 second delay before collection start and at UI elements to compliment this
		/*Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask(){
			public void run(){
				GUI.recordEvent();
			}
			
		}, 1,5000);*/
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int j=0;j< numRecordings;j++){
			System.out.println("Run " + j);
			
			if (Edk.INSTANCE.EE_EngineConnect("Emotiv Systems-5") != EdkErrorCode.EDK_OK
					.ToInt()) {
				System.out.println("Emotiv Engine start up failed.");
				return;
			}
			ArrayList<double[]> data2 = new ArrayList<>();
			//Timer timer = new Timer();
			
			Pointer eEvent = Edk.INSTANCE.EE_EmoEngineEventCreate();
			Pointer eState = Edk.INSTANCE.EE_EmoStateCreate();
		
			IntByReference userID = new IntByReference(0);
			IntByReference nSamplesTaken = new IntByReference(0);
			
			int state = 0;
			float secs = 1;
			boolean readytocollect = false;
			Pointer hData = Edk.INSTANCE.EE_DataCreate();
			Edk.INSTANCE.EE_DataSetBufferSizeInSec(secs);
			System.out.print("Buffer size in secs: ");
			System.out.println(secs);
			
			long startTime = System.currentTimeMillis();
			long endTime = startTime + (long)(recordingDuration*1000);
			
			while(System.currentTimeMillis() < endTime){
				
				//System.out.println("Before getting next event");
				state = Edk.INSTANCE.EE_EngineGetNextEvent(eEvent);
				//System.out.println("Got here");
	
				// New event needs to be handled
				if (state == EdkErrorCode.EDK_OK.ToInt()) {
					//System.out.println("Got past OK");
					int eventType = Edk.INSTANCE.EE_EmoEngineEventGetType(eEvent);
					Edk.INSTANCE.EE_EmoEngineEventGetUserId(eEvent, userID);
	
					// Log the EmoState if it has been updated
					if (eventType == Edk.EE_Event_t.EE_UserAdded.ToInt())
						if (userID != null) {
							System.out.println("User added");
							Edk.INSTANCE.EE_DataAcquisitionEnable(
									userID.getValue(), true);
							//System.out.println("Got here");
							readytocollect = true;
						}
				} else if (state != EdkErrorCode.EDK_NO_EVENT.ToInt()) {
					System.out.println("Internal error in Emotiv Engine!");
				}
	
				if (readytocollect) {
					//System.out.println("Got to readyToCollect");
					Edk.INSTANCE.EE_DataUpdateHandle(0, hData);
	
					Edk.INSTANCE.EE_DataGetNumberOfSample(hData, nSamplesTaken);
					//System.out.println("CLoser....");
					if (nSamplesTaken != null) {
						//System.out.println("nSamplesTaken!=NULL");
						if (nSamplesTaken.getValue() != 0) {
							//System.out.println("There we go! :D :D :D");
							System.out.print("Updated: ");
							System.out.println(nSamplesTaken.getValue());
	
							double[] data = new double[nSamplesTaken.getValue()];
							for (int sampleIdx = 0; sampleIdx < nSamplesTaken
									.getValue(); ++sampleIdx) {
								for (int i = 0; i < 17; i++) {
	
									Edk.INSTANCE.EE_DataGet(hData, i, data,
											nSamplesTaken.getValue());
									System.out.print(data[sampleIdx]);
									System.out.print(",");
								}
								data2.add(data);
								System.out.println();
							}
						}
					}
				}
			}
			
			rawData.addRecordedPattern((ArrayList<double[]>)data2.clone());
			rawName.add(dataName);
			rawDataNames.add(dataName);
			JOptionPane.showMessageDialog(null, "Done!","Finish Recording",JOptionPane.PLAIN_MESSAGE);
			data2.clear();
			Edk.INSTANCE.EE_EngineDisconnect();
			Edk.INSTANCE.EE_EmoStateFree(eState);
			Edk.INSTANCE.EE_EmoEngineEventFree(eEvent);
			System.out.println("Disconnected!");
		}
		rawPatterns.put(dataName,rawData);
		
	}
	
	
	
	
	public static boolean recognizing= false;
	//TODO: Implement Recognition task here
	//Note: THIS MUST BE CALLED ASYNCHRONOUSLY OR IT WILL MESS THINGS UP!!!!!
	public static void startRecognition(){
		startEdk();
		recognizing=true;
		while(recognizing){
			Pointer hData = Edk.INSTANCE.EE_DataCreate();
			Edk.INSTANCE.EE_DataSetBufferSizeInSec(1);
			IntByReference userID = null;
			IntByReference nSamplesTaken = null;
			userID = new IntByReference(0);
			nSamplesTaken = new IntByReference(0);
			int option = 1;
			int state = 0;
			float secs = 1;
			boolean readytocollect = false;
			state = Edk.INSTANCE.EE_EngineGetNextEvent(eEvent2);

			// New event needs to be handled
			if (state == EdkErrorCode.EDK_OK.ToInt()) {
				int eventType = Edk.INSTANCE.EE_EmoEngineEventGetType(eEvent2);
				Edk.INSTANCE.EE_EmoEngineEventGetUserId(eEvent2, userID);

				// Log the EmoState if it has been updated
				if (eventType == Edk.EE_Event_t.EE_UserAdded.ToInt())
					if (userID != null) {
						System.out.println("User added");
						Edk.INSTANCE.EE_DataAcquisitionEnable(
								userID.getValue(), true);
						readytocollect = true;
					}
			} else if (state != EdkErrorCode.EDK_NO_EVENT.ToInt()) {
				System.out.println("Internal error in Emotiv Engine!");
			}

			if (readytocollect) {
				Edk.INSTANCE.EE_DataUpdateHandle(0, hData);

				Edk.INSTANCE.EE_DataGetNumberOfSample(hData, nSamplesTaken);

				if (nSamplesTaken != null) {
					if (nSamplesTaken.getValue() != 0) {

						System.out.print("Updated: ");
						System.out.println(nSamplesTaken.getValue());

						double[] data = new double[nSamplesTaken.getValue()];
						System.out.println("Samples Taken: " + nSamplesTaken);
						for (int sampleIdx = 0; sampleIdx < nSamplesTaken
								.getValue(); ++sampleIdx) {
							for (int i = 0; i < 17; i++) {

								Edk.INSTANCE.EE_DataGet(hData, i, data,
										nSamplesTaken.getValue());
								System.out.print(data[sampleIdx]);
								System.out.print(",");
							}
							
							
							//TODO: Set up testing function LearningCore.testNeuralNetwork(data);
							DataSet dataSet = new DataSet(17,1);
							for(int i=0; i<17;i++){
								dataSet.addRow(new DataSetRow(data[i]));
							}
							LearningCore.testNeuralNetwork(dataSet);
							System.out.println();
							//TODO: Keep track of the results of testNeuralNetowork
						}
					}
				}
			}
		}
	}
	public static void stopRecognition(){ recognizing = false; }
	
	
	public static Pointer eEvent2;
	
	//Start the sdk
	public static void startEdk(){
		eEvent2 = Edk.INSTANCE.EE_EmoEngineEventCreate();
		Pointer eState = Edk.INSTANCE.EE_EmoStateCreate();
		IntByReference userID = null;
		IntByReference nSamplesTaken = null;
		userID = new IntByReference(0);
		nSamplesTaken = new IntByReference(0);
		short composerPort = 1726;
		int option = 1;
		int state = 0;
		float secs = 1;
		boolean readytocollect = false;
		
		//Confirm connection to device
		switch (option) {
		case 1: {
			if (Edk.INSTANCE.EE_EngineConnect("Emotiv Systems-5") != EdkErrorCode.EDK_OK
					.ToInt()) {
				System.out.println("Emotiv Engine start up failed.");
				return;
			}
			break;
		}
		case 2: {
			System.out.println("Target IP of EmoComposer: [127.0.0.1] ");

			if (Edk.INSTANCE.EE_EngineRemoteConnect("127.0.0.1", composerPort,
					"Emotiv Systems-5") != EdkErrorCode.EDK_OK.ToInt()) {
				System.out
						.println("Cannot connect to EmoComposer on [127.0.0.1]");
				return;
			}
			System.out.println("Connected to EmoComposer on [127.0.0.1]");
			break;
		}
		default:
			System.out.println("Invalid option...");
			return;
		}
	}
}
