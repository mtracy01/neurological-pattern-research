package emotivcloud.profile;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.*;

public interface ElsClient extends Library {
	ElsClient INSTANCE = (ElsClient) Native.loadLibrary("edk",
			ElsClient.class);

	enum profileFileType {
		TRAINING, EMOKEY
	};

	// ! Initializes the connection to ElsCloud server
	/*
	 * ! \return boolean - true if connect successfully
	 */
	boolean ELS_Connect();

	// ! Login on ElsCloud with exist account
	/*
	 * ! \param email - email of account \param password - password of account
	 * \param userID - receives number of user \return boolean - true if login
	 * successfully and return id of user
	 */
	boolean ELS_Login(String email, String password, IntByReference userID);

	// ! Sign up new account on ElsCloud
	/*
	 * ! \param username - user name of account \param firstname - first name of
	 * account \param lastname - last name of account \param email - email of
	 * account using for login \param password - password of account using for
	 * login \param userID - receives number of user \param gender - gender of
	 * user "M" or "F" \param date_of_birth- datetime string of user's birthday.
	 * Format: YYYY[-MM[-DD]] \return boolean - true if sign up successfully and
	 * return id of user
	 */
	boolean ELS_Signup(String username, String firstname, String lastname,
			String email, String password, String gender, String date_of_birth,
			String best_describe);

	// ! Create new infomation of headset on ElsCloud
	/*
	 * ! \param userID \return boolean - true if create headset successfully
	 */
	boolean ELS_CreateHeadset(int userID);

	// ! Create new protocol on ElsCloud
	/*
	 * ! \param name - name of protocol \param protocolID - receives number of
	 * protocol \return boolean - true if create protocol successfully and
	 * return id of protocol
	 */
	boolean ELS_CreateProtocol(String name, IntByReference protocolID);

	// ! Create subject in a protocol on ElsCloud
	/*
	 * ! \param userID - id of user \param protocolID - id of protocol \return
	 * boolean - true if add subject successfully
	 */
	boolean ELS_AddSubjectToProtocol(int userID, int protocolID);

	// ! Set protocol on ElsCloud
	/*
	 * ! \param protocolID - protocolID of protocol \return boolean - true if
	 * set protocol successfully
	 */
	boolean ELS_SetProtocol(int protocolID);

	// ! Set experiment on ElsCloud
	/*
	 * ! \param experimentID - experimentID of experiment \return boolean - true
	 * if set protocol successfully
	 */
	boolean ELS_SetExperiment(int experimentID);

	// ! Create new experiment on ElsCloud
	/*
	 * ! \param name - name of experiment \param description - description of
	 * experiment \param experimentID - receives number of experiment
	 * 
	 * \return boolean - true if create experiment successfully and return id of
	 * experiment
	 */
	boolean ELS_CreateExperiment(String name, String description,
			IntByReference experimentID);

	// ! Create new session on ElsCloud
	/*
	 * ! \param sessionID - receives id of session \return cont - true if create
	 * session successfully and return id of session
	 */
	String ELS_CreateRecordingSession();

	// ! Start record data from headset and upload to ElsCloud
	/*
	 * ! \return char - return null if create sessionID successfully
	 */
	boolean ELS_StartRecordData();

	// ! Start record data from headset and upload to ElsCloud
	/*
	 * ! \param oceiFilePath \param timeLimit (h) \param overtime \return char -
	 * return null if create sessionID successfully
	 */
	boolean ELS_StartRecordDataWithOCEFile(String oceiFilePath,
			IntByReference overtime, int timeLimit);

	// ! Stop record data from headset
	/*
	 * ! \return boolean - true if end successfully
	 */
	boolean ELS_StopRecordData();

	// ! Request get report on ElsCloud
	/*
	 * ! \return void \param IntByReference
	 */
	void ELS_GetReportOnline(IntByReference engagementScore,
			IntByReference excitementScore, IntByReference stressScore,
			IntByReference relaxScore, IntByReference interestScore);

	// ! Request get offline report on ElsCloud
	/*
	 * ! \param boolean - true if report is generated on ElsCloud successfully
	 */
	void ELS_GetOfflineReport(IntByReference engagementScore,
			IntByReference excitementScore, IntByReference affinityScore);

	// ! Start marker of "eyes open" state and upload on ElsCloud
	/*
	 * ! \return boolean - true if marker successfully
	 */
	boolean ELS_Marker_EyeOpenStart();

	// ! End marker of "eyes open" state and upload on ElsCloud
	/*
	 * ! \return boolean - true if marker successfully
	 */
	boolean ELS_Marker_EyeOpenEnd();

	// ! Start marker of "eyes closed" state and upload on ElsCloud
	/*
	 * ! \return boolean - true if marker successfully
	 */
	boolean ELS_Marker_EyeClosedStart();

	// ! End marker of "eyes closed" state and upload on ElsCloud
	/*
	 * ! \return boolean - true if marker successfully
	 */
	boolean ELS_Marker_EyeClosedEnd();

	// ! Start marker of "recording" state and upload on ElsCloud
	/*
	 * ! \return boolean - true if marker successfully
	 */
	boolean ELS_Marker_RecordingStart();

	// ! Update note for session
	/*
	 * ! \param note - note of session
	 * 
	 * \return boolean - true if update successfully
	 */
	boolean ELS_UpdateNote(String note);

	// ! Update tag for session
	/*
	 * ! \param tag - tag of session \return boolean - true if update
	 * successfully
	 */
	boolean ELS_UpdateTag(String tag[]);

	// ! Update photo for session
	/*
	 * ! \param filePath - path of image file which want upload
	 * 
	 * \return boolean - true if upload successfully
	 */
	boolean ELS_UploadPhoto(String filePath);

	// ! Terminate the connection to ElsCloud server and EmoEngine
	/*
	 * !
	 */
	void ELS_Disconnect();

	// ! Update new EmoState from Engine
	/*
	 * ! \param eState - new emoStateHandle
	 */
	String ELS_UploadEdfFile(String emostateFilePath, String edfFilePath);

	// ! Upload file profile of user
	/*
	 * ! \param profileName - \param filePath \param ptype
	 * 
	 * \return boolean - 1 if succes, 0 if failed
	 */
	boolean ELS_UploadProfileFile(String profileName, String filePath,
			int ptype);

	// ! Update exist profile of user
	/*
	 * ! \param profileId - \param filePath
	 * 
	 * \return boolean - 1 if succes, 0 if failed
	 */
	boolean ELS_UpdateProfileFile(int profileId, String filePath);

	// ! Delete exist profile of user
	/*
	 * ! \param profileId -
	 * 
	 * \return boolean - 1 if succes, 0 if failed
	 */
	boolean ELS_DeleteProfileFile(int profileId);

	// ! Get info Profile of user
	/*
	 * ! \param profile name \ return int - profile id of profile name - 0 if
	 * request failed, -1 if not existe profile name
	 */
	int ELS_GetProfileId(String profileName);

	// ! Donwload file Profile
	/*
	 * ! \param profile id \param destPath \return boolean - 1 if succes, 0 if
	 * failed
	 */
	boolean ELS_DownloadFileProfile(int profileId, String destPath);

	// ! Get All Profile Name
	/*
	 * ! \return int - Number of Profile Name
	 */
	int ELS_GetAllProfileName();

	// ! Get ProfileIDNameAtIndex
	/*
	 * ! \param index \return String - Profile Name at index
	 */

	int ELS_ProfileIDAtIndex(int index);

	// ! Get ProfileNameAtIndex
	/*
	 * ! \param index \return String - Profile Name at index
	 */

	String ELS_ProfileNameAtIndex(int index);

	// ! Get last modified time of Profile
	/*
	 * ! \param index \return String - last modified time of Profile at index
	 */

	String ELS_ProfileLastModifiedAtIndex(int index);

	// ! Get type of Profile
	/*
	 * ! \param index \return String - type of Profile at index
	 */

	profileFileType ELS_ProfileTypeAtIndex(int index);

	// ! Save Open Close Eye Info to file
	/*
	 * ! \param fileName \return boolean - 1 if succes, 0 if failed
	 */
	boolean ELS_SaveOpenCloseEyeInfo(String fileName);

	// ! Reset pasword
	/*
	 * ! \param userName \return boolean - 1 if succes, 0 if failed
	 */
	boolean ELS_ResetPassword(String userName);

	// ! Logout
	/*
	 * \return boolean - 1 if succes, 0 if failed
	 */
	boolean ELS_Logout();

	// ! Reupload offline data
	/*
	 * \param filename - name of file which contains offline info \return
	 * boolean - 1 if succes, 0 if failed
	 */
	boolean ELS_ReuploadOfflineSession(String filename);

	// ! save offline session info to file
	/*
	 * \param filename - the name of the file used to store offline session
	 * information for later upload \return boolean - 1 if succes, 0 if failed
	 */
	void ELS_SaveOfflineSessionInfo(String fileName);
	
	 //! set client id and client secret
	 /*
	  \param clientID  - clientID
	  \param clientSecret - clientSecret
	 */
	 void ELS_SetClientSecret(String clientID, String clientSecret);	
}
