package Objects;

import java.util.ArrayList;

//Purpose: Stores some long term variables for global access across the project
public class Data {
	public static ArrayList<ParsedData> parsedData = new ArrayList<>();
	
	public static void clear(){
		parsedData.clear();
	}
	
	public static String[] getRecordingNames(){
		ArrayList<String> names = new ArrayList<>();
		for(ParsedData parsedRecording: parsedData)
			names.add(parsedRecording.getTitle());
		String[] result = new String[names.size()];
		result = names.toArray(result);
		return result;
	}
}
