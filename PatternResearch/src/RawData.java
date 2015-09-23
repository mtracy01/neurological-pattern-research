import java.util.*;

public class RawData {
	public ArrayList<ArrayList<double[]>> data = new ArrayList<>();
	public String dataName;
	public int count;
	public double durations;
	
	public RawData(String dataName, int count, double durations){
		this.dataName = dataName;
		this.count = count;
		this.durations = durations;
	}
	
	public void addRecordedPattern(ArrayList<double[]>pattern){
		data.add(pattern);
	}
	
	public ArrayList<double[]> getRawPattern(int index){
		if(index<count)
			return data.get(index);
		return null;
	}
}
