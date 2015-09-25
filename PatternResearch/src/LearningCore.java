import java.util.*;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.util.TransferFunctionType;

public class LearningCore {
	public static ArrayList<RawData> rawData = new ArrayList<>();
	public static MultiLayerPerceptron mlPerceptron = null;
	public static Hashtable<Double,String> nameMap = new Hashtable<>();
	
	public static void createMLPerceptron(){
		//TODO: U
		DataSet trainingSet = new DataSet(17,1);
		nameMap.clear();
		double i=0;
		int size = 0;
		for(RawData r: rawData){
			nameMap.put(i,r.getName());
			for(double j=0;j<r.getCount();j++){
				ArrayList<double[]> dataPoints = r.getRawPattern((int)j);
				
				for(double[] arr: dataPoints){
					trainingSet.addRow(arr,new double[]{j});
					size++;
				}
			}
			i++;
		}
		
		mlPerceptron = new MultiLayerPerceptron(TransferFunctionType.TANH,17,size,1);
		mlPerceptron.learn(trainingSet);
	}
	
	public static void testNeuralNetwork(DataSet testSet) {

		for(DataSetRow dataRow : testSet.getRows()) {
		mlPerceptron.setInput(dataRow.getInput());
		mlPerceptron.calculate();
		double[ ] networkOutput = mlPerceptron.getOutput();
		System.out.print("Input: " + Arrays.toString(dataRow.getInput()) );
		System.out.println(" Output: " + Arrays.toString(networkOutput) );
		}

	}

	public static void saveModel(String filename){
		//TODO: Implement serialization for saving models
	}
	
	public static void loadModel(String filename){
		//TODO: implement serialization for loading models
	}
}


