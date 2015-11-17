package Objects;

import java.io.File;
import java.util.ArrayList;

import helperClasses.FileHelper;

public class NormalizeData {
	public static Tuple<Double[], Double[]> findMaxMin(ArrayList<double[]> parsedData){
		Tuple<Double[],Double[]> maxMin = new Tuple(new Double[14], new Double[14]);
		/*x stores all max values, y stores all min values*/
		for(int i = 0 ; i< 14; i++){
			maxMin.x[i] = Double.MIN_VALUE;
			maxMin.y[i] = Double.MAX_VALUE;
		}
		int maxLen = 14;
		if(parsedData!= null ){
			int i = 0;
			int length = parsedData.size();
			while(i < length){
				int j = 0;
				while( j < maxLen){

					double[] arr = parsedData.get(i);
					if(maxMin.x[j] < arr[j])
						maxMin.x[j] = arr[j];
					if(maxMin.y[j] > arr[j])
						maxMin.y[j] = arr[j];
					j++;
				}
				i++;
			}
		}
		return maxMin;
	}
	
	public static ParsedData normalizeParsedData(ParsedData pd){
		Tuple<Double[],Double[]> maxMin = findMaxMin(pd.getDataPoints());
		ParsedData pdnew = new ParsedData(pd.getTitle(),
					pd.getRecordingDuration(),pd. getSampleRate(),pd.getSubject());
		ArrayList<double[]> temp = pd.getDataPoints();
		for(double[] arr: temp){
			/* Normalize by newVal = (currVal-min)/(max-min)*/
			int k = 0;
			while(k < 14){
				arr[k] = (arr[k]- maxMin.y[k])/(maxMin.x[k] - maxMin.y[k]);;
				k++;
			}
			pdnew.addDataPoint(arr);
		}
		return pdnew;
	}
	
	public static void normalizePDDirectory(File dir, int option){
		String dPath = dir.getAbsolutePath();
		while(dPath.charAt(dPath.length()-1)!='/')
			dPath = dPath.substring(0,dPath.length()-1);
		
		dPath +="parsedData";
		try{
			File nDir = new File(dPath);
			nDir.mkdir();
		} catch(SecurityException e){
			e.printStackTrace();
			return;
		}
		dPath += "/";
		
		File[] files = dir.listFiles();
		
		for(File file: files){
			String fname = file.getName();
			ParsedData parsedData = FileHelper.loadCSVData(file, option);
			File nFile = new File(dPath + fname);
			boolean check = FileHelper.savePoint(nFile, parsedData);
			if(!check){
				//TODO: Create error dialog
			}
		}
	}
}
