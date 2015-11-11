package Objects;

import java.util.ArrayList;

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
	
	
	

}
