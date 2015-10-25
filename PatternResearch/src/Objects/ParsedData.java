package Objects;

import java.io.Serializable;
import java.util.*;

public class ParsedData implements Serializable {
	private String title;
	private String recordDuration;
	private int sampleRate;
	private String subject;
	private ArrayList<double[]> points = new ArrayList<>();
	
	//TODO: Convert recordDuration to a more useful variable in this constructor
	public ParsedData(String title, String recordDuration, int sampleRate, String subject ){
		this.title = title;
		this.recordDuration = recordDuration;
		this.sampleRate = sampleRate;
		this.subject = subject;
	}
	public ArrayList<double[]> getDataPoints(){
		return this.points;
	}
	public void addDataPoint(double[] point){
		this.points.add(point);
	}
	
	public double[] getDataPoint(int index){
		return points.get(index);
	}
	
	public String getTitle(){
		return this.title;
	}
	public String getRecordingDuration(){
		return this.recordDuration;
	}
	public int getSampleRate(){
		return this.sampleRate;
	}
	public String getSubject(){
		return this.subject;
	}
	
}

