package emotivcloud.example;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import emotivcloud.example.Edk;
import emotivcloud.example.EdkErrorCode;

/*
 * example demonstrates how to get report online
 */

public class MainTest {

	public static void main(String[] args) {
		Pointer eEvent = Edk.INSTANCE.EE_EmoEngineEventCreate();
		IntByReference nUserID = new IntByReference(0);
		int state = 0;		
		boolean connected = false;

		if (Edk.INSTANCE.EE_EngineConnect("Emotiv Systems-5") != EdkErrorCode.EDK_OK
				.ToInt()) {
			System.out.println("Emotiv Engine start up failed.");
			connected = false;
			return;
		} 
		else {
			Reporter reporter = new Reporter();
			while (true) {
				state = Edk.INSTANCE.EE_EngineGetNextEvent(eEvent);
				if (state == EdkErrorCode.EDK_OK.ToInt()) {
					int eventType = Edk.INSTANCE
							.EE_EmoEngineEventGetType(eEvent);
					Edk.INSTANCE.EE_EmoEngineEventGetUserId(eEvent, nUserID);

					if (eventType == Edk.EE_Event_t.EE_UserAdded.ToInt())
						if (nUserID != null) {
							System.out.println("\nEngine User added\n");
							connected = true;
						}					
				}
				
				if( connected ) {
					reporter.getReportOnline(nUserID.getValue());
					break;
				}
				else
				{
					System.out.println("\nPlease connect to the headset...\n");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
					
			} // end while
			
			Edk.INSTANCE.EE_EngineDisconnect();
		}
	}

}
