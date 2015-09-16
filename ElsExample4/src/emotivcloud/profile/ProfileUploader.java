package emotivcloud.profile;

import com.sun.jna.ptr.IntByReference;

/*
 * ProfileUploader.java
 * Example lists all profiles of the user, 
 * upload an user's profile to the server 
 * download profile file from server
 * delete profile file in server
 */
public class ProfileUploader {

	public static void printProfileDetails() {
		int nNumOfProfiles = 0;

		nNumOfProfiles = ElsClient.INSTANCE.ELS_GetAllProfileName();
		System.out.printf("\n Number of profiles : %d \r\n", nNumOfProfiles);
		if (nNumOfProfiles > 0) {
			for (int i = 0; i < nNumOfProfiles; i++) {
				System.out.printf("\n %d , ",
						ElsClient.INSTANCE.ELS_ProfileIDAtIndex(i));
				System.out.printf(" %s , ",
						ElsClient.INSTANCE.ELS_ProfileNameAtIndex(i));
				if (ElsClient.INSTANCE.ELS_ProfileTypeAtIndex(i) == ElsClient.profileFileType.TRAINING)
					System.out.printf(" TRAINING , ");
				else {
					System.out.printf(" EMOKEY , ");
				}

				System.out.printf(" %s ",
						ElsClient.INSTANCE.ELS_ProfileLastModifiedAtIndex(i));
			}
		}

	}

	public static void main(String[] args) {
		IntByReference nUserID = new IntByReference(0);
		String szEmail = "jqk", szPassword = "jqk";

		String clientID = "";
		String clientSecret = "";
		if (ElsClient.INSTANCE.ELS_Connect() == false) {
			System.out.println(" Error : Cannot connect to cloud server! ");
			System.exit(1);
		} else {
			System.out.println(" Connect successfully!");
			System.out.println(" set client secret...");
			ElsClient.INSTANCE.ELS_SetClientSecret(clientID, clientSecret);
			System.out.printf(" Login... ");
			ElsClient.INSTANCE.ELS_Login(szEmail, szPassword, nUserID);
			printProfileDetails();
			if (ElsClient.INSTANCE.ELS_UploadProfileFile("profile 1",
					"profile1.emu", 0) == false)
				System.out.printf("\n Fail to upload profile file ! ");
			else {
				System.out.printf("\n Profile file was uploaded successfully!");
				printProfileDetails();

				int nProfileID = ElsClient.INSTANCE
						.ELS_GetProfileId("profile 1");
				if (nProfileID >= 0) {
					ElsClient.INSTANCE.ELS_DownloadFileProfile(nProfileID,
							"profile1.emu");
					System.out.printf("\n Delete profile ...");
					ElsClient.INSTANCE.ELS_DeleteProfileFile(nProfileID);
				}

				printProfileDetails();
			}
		}
	}

}
