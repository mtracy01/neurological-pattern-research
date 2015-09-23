import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JPanel{
	
	public static void main(String[] args){
		GUI gui = new GUI();
		
		
	}
	
	//Record a certain number of raw inputs and store them into some sort of database
	public static void logData(String dataName, int numRecordings, double recordingDuration){
		Pointer eEvent = Edk.INSTANCE.EE_EmoEngineEventCreate();
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

		
		//Begin creating and processing data
		Pointer hData = Edk.INSTANCE.EE_DataCreate();
		Edk.INSTANCE.EE_DataSetBufferSizeInSec(secs);
		System.out.print("Buffer size in secs: ");
		System.out.println(secs);

		System.out.println("Start receiving EEG Data!");
		for(int j=0;j< numRecordings;j++){
			//TODO: Add 5 second delay before collection start and at UI elements to compliment this
			
			while (true) { //TODO: Change this to a counting down event, store data into whatever we use to store data...
				state = Edk.INSTANCE.EE_EngineGetNextEvent(eEvent);
	
				// New event needs to be handled
				if (state == EdkErrorCode.EDK_OK.ToInt()) {
					int eventType = Edk.INSTANCE.EE_EmoEngineEventGetType(eEvent);
					Edk.INSTANCE.EE_EmoEngineEventGetUserId(eEvent, userID);
	
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
					break;
				}
	
				if (readytocollect) {
					Edk.INSTANCE.EE_DataUpdateHandle(0, hData);
	
					Edk.INSTANCE.EE_DataGetNumberOfSample(hData, nSamplesTaken);
	
					if (nSamplesTaken != null) {
						if (nSamplesTaken.getValue() != 0) {
	
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
								System.out.println();
							}
						}
					}
				}
			}
		}

		Edk.INSTANCE.EE_EngineDisconnect();
		Edk.INSTANCE.EE_EmoStateFree(eState);
		Edk.INSTANCE.EE_EmoEngineEventFree(eEvent);
		System.out.println("Disconnected!");
	}
}
