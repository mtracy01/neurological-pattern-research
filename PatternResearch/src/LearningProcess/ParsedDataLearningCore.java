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
import Objects.Tuple;
import helperClasses.FileHelper;

public class ParsedDataLearningCore {
	
	public static void createMLPerceptron(){
		Data.perceptrons.clear();
		for(ParsedData parsedData: Data.parsedData){
			String subject = parsedData.getSubject();
			DataSet trainingSet = new DataSet(14,1);
			ArrayList<double[]> points = parsedData.getDataPoints();
			for(double[] point: points)
				trainingSet.addRow(new DataSetRow(point,new double[]{1}));
			for(ParsedData parsedDataOther: Data.parsedData){
				if(!parsedDataOther.getSubject().equals(subject)){
					points = parsedDataOther.getDataPoints();
					for(double[] point: points)
						trainingSet.addRow(new DataSetRow(point,new double[]{0}));
					
				}
			}
			MultiLayerPerceptron mlPerceptron = new MultiLayerPerceptron(TransferFunctionType.TANH, 14, 8, 1);
			mlPerceptron.learn(trainingSet);
			Data.perceptrons.add(new Tuple<String,MultiLayerPerceptron>(subject,mlPerceptron));
		}
	}

	public static Tuple<Double,String> testNeuralNetwork(ParsedData data) {
		DataSet testSet = convertToDataSet(data);
		Tuple<Double,String> maxProb =new Tuple<>(0.0,null);
		for(Tuple<String,MultiLayerPerceptron> model: Data.perceptrons){
			MultiLayerPerceptron mlp = model.y;
			double out = 0;
			double i = 0;
			for(DataSetRow dataRow : testSet.getRows()) {
				mlp.setInput(dataRow.getInput());
				mlp.calculate();
				out+=mlp.getOutput()[0];
				i++;
			}
			System.out.println("Out before: " + out);
			out = out/i;
			System.out.println("I= " + i  + " Out for " + model.x + ": " + out);
			if(out>maxProb.x){
				maxProb.x=out;
				maxProb.y=model.x;
			}
		}
		
		return maxProb;
	}
	public static DataSet convertToDataSet(ParsedData parsedData){
		DataSet dataSet = new DataSet(14);
		ArrayList<double[]> points = parsedData.getDataPoints();
		for(double[] p: points){
			dataSet.addRow(p);
		}
		return dataSet;
	}
	
}
