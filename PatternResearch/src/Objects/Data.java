package Objects;

import java.util.ArrayList;

import org.neuroph.nnet.MultiLayerPerceptron;
import libsvm.*;
import net.sf.javaml.classification.Classifier;
//Purpose: Stores some long term variables for global access across the project
public class Data {
	public static ArrayList<ParsedData> parsedData = new ArrayList<>();
	public static ArrayList<Tuple<String,MultiLayerPerceptron>> perceptrons = new ArrayList<>();
	public static Classifier svm;
	public static void clear(){
		parsedData.clear();
		perceptrons.clear();
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
