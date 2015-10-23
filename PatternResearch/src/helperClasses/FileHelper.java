package helperClasses;

import java.io.FileReader;

import com.opencsv.CSVReader;

import Objects.Data;
import Objects.ParsedData;

public class FileHelper {
	//TODO: Implement all functions having to do with data storage and loading here.
	
	
	public static void loadCSVData(String filename){
		CSVReader reader = null;
		try{
			reader = new CSVReader(new FileReader(filename));
			//In first column, since Emotiv isn't smart, we need to do special parsing of data
			String[] entries = reader.readNext();
			String recordingName = entries[0];
			String recordingDuration = entries[1];
			int samplingRate = Integer.parseInt(entries[2]);
			String subject = entries[3];
			ParsedData pd = new ParsedData(recordingName, recordingDuration, samplingRate,subject);
			Data.parsedData.add(pd);
			
			//Take all of the sampled data and put it in the object
			while((entries=reader.readNext())!=null){
				float[] data = new float[14];
				for(int i=2;i<16;i++)
					data[i-2]=Float.parseFloat(entries[i]);
				pd.addDataPoint(data.clone());
			}
			
		
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
