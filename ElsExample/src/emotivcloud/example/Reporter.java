/*
Example demonstrates how to get report online from the cloud server
 */

package emotivcloud.example;

import com.sun.jna.ptr.IntByReference;

import emotivcloud.example.ElsClient;

public class Reporter {

	public void getReportOnline(int engineUserID) {
		IntByReference userID = new IntByReference(0);
		IntByReference experimentID = new IntByReference(0);
		IntByReference protocolID = new IntByReference(0);
		String sessionID = null;

		IntByReference engageScore = new IntByReference(0);
		IntByReference excitementScore = new IntByReference(0);
		IntByReference stressScore = new IntByReference(0);
		IntByReference relaxScore = new IntByReference(0);
		IntByReference interestScore = new IntByReference(0);
		boolean hasReport = true;

		String clientID = "";
		String clientSecret = "";
		if (ElsClient.INSTANCE.ELS_Connect() == false) {
			System.out.println(" Error : Cannot connect to cloud server! ");
			System.exit(1);
		} else {
			System.out.println(" Connect successfully!");
			System.out.println(" set client secret...");
			ElsClient.INSTANCE.ELS_SetClientSecret(clientID, clientSecret);

			System.out.println(" Sign in ...");
			String email = "jqk", password = "jqk", protocol = "example", name = "test", description = "descript";

			if (ElsClient.INSTANCE.ELS_Login(email, password, userID) == false)
				System.out.printf(" FAIL!");
			else
				System.out.printf("\n userID :  " + userID.getValue());

			System.out.println("\n Create Protocol ...");
			ElsClient.INSTANCE.ELS_CreateProtocol(protocol, protocolID);
			System.out.printf("\n protocolID :  " + protocolID.getValue());

			System.out.println(" Create subject ...");
			ElsClient.INSTANCE.ELS_AddSubjectToProtocol(1,
					protocolID.getValue());

			System.out.println(" Create Experiment ...");
			ElsClient.INSTANCE.ELS_CreateExperiment(name, description,
					experimentID);
			System.out.printf("\n ExperimentID :  " + experimentID.getValue());

			System.out.println(" Create Headset ...");
			ElsClient.INSTANCE.ELS_CreateHeadset(engineUserID);

			System.out.println(" Create Session ...");
			sessionID = ElsClient.INSTANCE.ELS_CreateRecordingSession();
			System.out.printf(" SessionID : " + sessionID);

			System.out.println(" Create Record ...");
			ElsClient.INSTANCE.ELS_StartRecordData();

			System.out.println(" Create Marker ...");
			ElsClient.INSTANCE.ELS_Marker_EyeOpenStart();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println(" Create Marker ...");
			ElsClient.INSTANCE.ELS_Marker_EyeOpenEnd();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println(" Create Marker ...");
			ElsClient.INSTANCE.ELS_Marker_EyeClosedStart();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println(" Create Marker ...");
			ElsClient.INSTANCE.ELS_Marker_EyeClosedEnd();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println(" Create Marker ...");
			ElsClient.INSTANCE.ELS_Marker_RecordingStart();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println(" Stop Recording...");

			if (ElsClient.INSTANCE.ELS_StopRecordData() == true) {
				System.out.println(" Success... ");

				while (hasReport) {
					ElsClient.INSTANCE.ELS_GetReportOnline(engageScore,
							excitementScore, stressScore, relaxScore,
							interestScore);
					if (engageScore.getValue() != 0) {
						System.out.printf("\n Report is : %d %d %d %d %d ",
								engageScore.getValue(),
								excitementScore.getValue(),
								stressScore.getValue(), relaxScore.getValue(),
								interestScore.getValue());
						hasReport = false;
					} else {
						System.out.printf("\n Waiting for report...\r");
					}

					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else {
				System.out.println(" Fail! ");
			}

			ElsClient.INSTANCE.ELS_Disconnect();
		} // end else
	}
}
