package helperClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.regex.Pattern;

import com.opencsv.CSVReader;

import Objects.Data;
import Objects.NormalizeData;
import Objects.ParsedData;

public class FileHelper {
	//TODO: Implement all functions having to do with data storage and loading here.
	public static ParsedData pd;
	
	public static ParsedData loadCSVData(File file,int option){
		CSVReader reader = null;
		try{
			reader = new CSVReader(new FileReader(file.getAbsolutePath()));
			//In first column, since Emotiv isn't smart, we need to do special parsing of data
			String[] entries = reader.readNext();
			String recordingName = getAfterColon(entries[0]);
			String recordingDuration = getAfterColon(entries[1]);
			int samplingRate = Integer.parseInt(entries[2].split(Pattern.quote(":"))[1]);
			String subject = getAfterColon(entries[3]);
			pd = new ParsedData(recordingName, recordingDuration, samplingRate,subject);
			
			//Take all of the sampled data and put it in the object
			while((entries=reader.readNext())!=null){
				double[] data = new double[14];
				for(int i=2;i<16;i++)
					data[i-2]=Double.parseDouble(entries[i]);
				pd.addDataPoint(data.clone());
			}
			
			if(option==0)
				Data.parsedData.add(NormalizeData.normalizeParsedData(pd));
			reader.close();
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return pd;
	}
	
	public static String getAfterColon(String in){
		return in.split(Pattern.quote(":"))[1];
	}
	
	public static boolean loadDataSet(String fileName){
		try{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
			Data.parsedData.clear();
			Object object = in.readObject();
			while(object!=null){
				Data.parsedData.add((ParsedData)object);
				object=in.readObject();
			}
			in.close();
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean saveDataSet(String fileName){
		try{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
			for(ParsedData parsedData: Data.parsedData)
				out.writeObject(parsedData);
			out.close();
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean loadPoint(String fileName){
		try{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
			//TODO: Later, try to add error checking to make sure two points
			//with the same title are not in the ArrayList
			Data.parsedData.add((ParsedData)in.readObject());
			in.close();
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean savePoint(String fileName, int recordingIndex){
		ParsedData dataPoint = null;
		dataPoint = Data.parsedData.get(recordingIndex);
		try{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
			out.writeObject(dataPoint);
			out.close();
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean savePoint(File file, ParsedData parsedData){
		try{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.getAbsolutePath()));
			out.writeObject(parsedData);
			out.close();
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean removePoint(int recordingIndex){
		if(recordingIndex> Data.parsedData.size())
			return false;
		Data.parsedData.remove(recordingIndex);
		return true;
	}
	
	public static ParsedData getParsedData(String fileName){
		try{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));	
			ParsedData ret = (ParsedData)in.readObject();
			in.close();
			return ret;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
