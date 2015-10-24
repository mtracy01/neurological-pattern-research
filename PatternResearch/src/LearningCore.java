import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;


import com.opencsv.CSVReader;

import Objects.Data;
import Objects.ParsedData;
import Objects.RawNativeData;

public class LearningCore {
	public static ArrayList<RawNativeData> rawData = new ArrayList<>();
	public static MultiLayerPerceptron mlPerceptron = null;
	public static Hashtable<Double,String> nameMap = new Hashtable<>();
	
	public static void createMLPerceptron(){
		
		DataSet trainingSet = new DataSet(17,1);
		nameMap.clear();
		double i=0;
		int size = 0;
		
		for(RawNativeData r: rawData){										  //For each RawData that we are training
			nameMap.put(i,r.getName());
			for(double j=0;j<r.getCount();j++){							//For each raw pattern in that data
				ArrayList<double[]> dataPoints = r.getRawPattern((int)j);	
				for(double[] arr: dataPoints){						 //For each data point in a raw data
					trainingSet.addRow(arr,new double[]{i});		//arr is the row input, j is what would be the predicted result
					size++;
				}
			}
			i++;
		}
		mlPerceptron = new MultiLayerPerceptron(TransferFunctionType.TANH,17,size,1);
		mlPerceptron.learn(trainingSet);
	}
	

	
	
	public static void testNeuralNetwork(DataSet testSet) {
		double average = 0;
		double i=0;
		for(DataSetRow dataRow : testSet.getRows()) {
			mlPerceptron.setInput(dataRow.getInput());
			mlPerceptron.calculate();
			double[ ] networkOutput = mlPerceptron.getOutput();
			//System.out.print("Input: " + Arrays.toString(dataRow.getInput()) );
			//System.out.println(" Output: " + Arrays.toString(networkOutput) );
			average+=networkOutput[0];
			i++;
			//TODO: Average the outputs and round maybe?
		}
		int result = Integer.parseInt(Double.toString(average/i));
		String prediction = nameMap.get(i);
		System.out.println("Prediction is: " + prediction);

	}

}


