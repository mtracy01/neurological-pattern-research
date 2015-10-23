import java.util.*;

public class PreprocessData {
	private String title;
	private String record;
	private String sample;
	private String subject;
	private ArrayList<DataPoint> points = new ArrayList<>();
	public PreprocessData(String title, String record, String sample, String subject ){
		this.title = title;
		this.record = record;
		this.sample = sample;
		this.subject = subject;
	}
	public ArrayList<DataPoint> getDataPoints(){
		return this.points;
	}
	public void addDataPoint(DataPoint point){
		this.points.add(point);
	}
	
	public String getTitle(){
		return this.title;
	}
	public String getRecord(){
		return this.record;
	}
	public String getSample(){
		return this.sample;
	}
	public String getSubject(){
		return this.subject;
	}
	
}
