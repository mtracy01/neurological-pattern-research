package LearningProcess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

import Objects.Data;
import Objects.ParsedData;
import Objects.RawNativeData;
import helperClasses.FileHelper;

public class ParsedDataLearningCore {
	//public static ArrayList<ParsedData> parsedData = new ArrayList<>();
	public static MultiLayerPerceptron mlPerceptron = null;
	public static Hashtable<Double,String> nameMap = new Hashtable<>();
	public static Hashtable<String, Double> namemap1 = new Hashtable<>();
	public static double[ ] learningOutput= new double[1];
	public static int valid = 0;
	public static void createMLPerceptron(){
		DataSet trainingSet = new DataSet(14,1);
		nameMap.clear();
		double i=0;
		int size = 0;
		for(ParsedData p: Data.parsedData){
			//TODO: Check hashmap is the reference already exists
			//Store the pattern name with unique index
			if(!namemap1.containsKey(p.getTitle())){
				nameMap.put(i,p.getTitle());
				namemap1.put(p.getTitle(), i);
				
				ArrayList<double[]> points = p.getDataPoints();
				// Add all points from the file to the training set
				for(double[] arr: points){
					trainingSet.addRow(arr,new double[]{i});
					size++;
				}
				i++;
			}
		}
		
		mlPerceptron = new MultiLayerPerceptron(TransferFunctionType.TANH,14,size,1);
		mlPerceptron.learn(trainingSet); // Should learn all samples 
		
	}
	
	/*Find the matching pattern*/
	/*public static void matchNeuralNetwork(DataSet testSet){
		String str;
		Set<String> set = namemap1.keySet();
		Iterator s = set.iterator();
	    while (s.hasNext()) {
	    	str = (String)s.next();
			ArrayList<Double> output = namemap1.get(str);
			if(output!= null ){
				int i = 0;
				for(DataSetRow dataRow : testSet.getRows()) {
					mlPerceptron.setInput(dataRow.getInput());
					mlPerceptron.calculate();
					double[ ] networkOutput = mlPerceptron.getOutput();
					System.out.println(" Output: " + Arrays.toString(networkOutput) );
					if(closeResult(networkOutput[0], output.get(i))) valid ++;
					else valid--;	
				}
				if(valid > 0){
					System.out.println("Pattern matches: "+ str +"\n");
					break;
				}
				else valid=0;
			}
		}
	}*/

	public static String testNeuralNetwork(DataSet testSet) {
		double average = 0;
		double i=0;

		for(DataSetRow dataRow : testSet.getRows()) {
			//Get one row of the data, which is a point in ParsedData or NativeData
			mlPerceptron.setInput(dataRow.getInput());
			mlPerceptron.calculate();
			double[ ] networkOutput = mlPerceptron.getOutput();
			//System.out.print("Input: " + Arrays.toString(dataRow.getInput()) );
			System.out.println(" Output: " + Arrays.toString(networkOutput) );
			average+=networkOutput[0];
			i++;
			
		}
		int result = Integer.parseInt(Double.toString(average/i));
		String prediction = nameMap.get(i);
		System.out.println("Prediction is: " + prediction);
		return prediction;
	}
	public static DataSet convertToDataSet(ParsedData parsedData){
		DataSet dataSet = new DataSet(14,1);
		ArrayList<double[]> points = parsedData.getDataPoints();
		for(double[] p: points){
			dataSet.addRow(p);
		}
		return dataSet;
	}
	
}
