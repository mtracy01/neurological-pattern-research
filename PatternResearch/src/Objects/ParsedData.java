package Objects;

import java.util.*;

public class ParsedData {
	private String title;
	private String recordDuration;
	private int sampleRate;
	private String subject;
	private ArrayList<float[]> points = new ArrayList<>();
	
	//TODO: Convert recordDuration to a more useful variable in this constructor
	public ParsedData(String title, String recordDuration, int sampleRate, String subject ){
		this.title = title;
		this.recordDuration = recordDuration;
		this.sampleRate = sampleRate;
		this.subject = subject;
	}
	public ArrayList<float[]> getDataPoints(){
		return this.points;
	}
	public void addDataPoint(float[] point){
		this.points.add(point);
	}
	
	public float[] getDataPoint(int index){
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

