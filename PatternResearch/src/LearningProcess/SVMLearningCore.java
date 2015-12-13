package LearningProcess;

import java.io.File;
import java.util.Hashtable;

import Objects.Data;

import Objects.ParsedData;
import Objects.Tuple;
import helperClasses.FileHelper;
import libsvm.*;
import net.sf.javaml.*;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.data.FileHandler;

public class SVMLearningCore {
	static int nSets = 0;
	public static void createSVM(){
		nSets++;
		if(nSets<2)
			return;
		FileHelper.createSVMDataSet();
		Dataset data = null;
		try{
			data = FileHandler.loadDataset(new File("data.data"),14,",");
		}catch(Exception e){
			e.printStackTrace();
			System.exit(-1);
		}
		System.out.println("Size of Data: " + data.size());
		Data.svm = new LibSVM();
		Data.svm.buildClassifier(data);
	}
	
	public static String classifyData(ParsedData pd){
		FileHelper.createSVMPDSet(pd);
		Dataset data = null;
		Hashtable<Object,Integer> map = new Hashtable<>();
		try{
			data = FileHandler.loadDataset(new File("data.data"),14,",");
		}catch(Exception e){
			e.printStackTrace();
			System.exit(-1);
		}
		int max =0;
		Object prediction=null;
		for(Instance inst: data){
			Object predictedValue = Data.svm.classify(inst);
			int val = 1;
			if(map.containsKey(predictedValue)){
				val = map.get(predictedValue) + 1;
				map.put(predictedValue, val);
			}
			else
				map.put(predictedValue, 1);
			
			//check if new max found
			if(val > max){
				max = val;
				prediction = predictedValue;
			}
			
		}
		if(prediction!=null)
			return prediction.toString();
		return null;
	}
}
