import java.io.File;
import helperClasses.FileHelper;
import LearningProcess.SVMLearningCore;
import Objects.Data;
import Objects.NormalizeData;
import Objects.ParsedData;
public class AutoGenerator {
	String path = "/Users/Ji/Desktop/neurological-pattern-research/TestData/";
	int round  =3;
	public void listFilesForFolder(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            System.out.println(fileEntry.getName());
	        }
	    }
	}
	public void autoGenerator(){
		final File folder = new File("/Users/Ji/Desktop/neurological-pattern-research/TestData/csv");
		//listFilesForFolder(folder);
		File[] allfiles = folder.listFiles();
		for(int i = 0; i < round; i++){
			File fileb = allfiles[i];
			FileHelper.loadCSVData(fileb,0);
			SVMLearningCore.createSVM();
			File fileg = allfiles[i+10];
			FileHelper.loadCSVData(fileg,0);
			SVMLearningCore.createSVM();
			File filer = allfiles[i+20];
			FileHelper.loadCSVData(filer,0);
			SVMLearningCore.createSVM();
			File filey = allfiles[i+30];
			FileHelper.loadCSVData(filey,0);
			SVMLearningCore.createSVM();
		}
		String[] recNames = Data.getRecordingNames();
		String color[] = {"Blue", "Green", "Red", "Yellow"};
		for(int i = 1; i<round; i++){
			FileHelper.savePoint(path+ color[0]+i, i*4);
			FileHelper.loadPoint(path+ color[0]+i);
			FileHelper.savePoint(path+ color[1]+i, i*4+1);
			FileHelper.loadPoint(path+ color[1]+i);
			FileHelper.savePoint(path+ color[2]+i, i*4+2);
			FileHelper.loadPoint(path+ color[2]+i);
			FileHelper.savePoint(path+ color[3]+i, i*4+3);
			FileHelper.loadPoint(path+ color[3]+i);
		}
		for(int i = 1; i<round; i++){
			FileHelper.removePoint(i*4);
			FileHelper.removePoint(i*4+1);
			FileHelper.removePoint(i*4+2);
			FileHelper.removePoint(i*4+3);
		}
		int valid = 0;
		
		for(int i = 1; i<round; i++){
			for(int j = 0; j < 4; j++){
				ParsedData parsedData = FileHelper.getParsedData(path+ color[j]+i);
				if(parsedData!=null){
					parsedData = NormalizeData.normalizeParsedData(parsedData);
					String result = SVMLearningCore.classifyData(parsedData);
					System.out.println(result + color[j]);
					if(result.contains(color[j])) valid++;
				}
				else System.out.println("Empty!");
			}
		}
		double accuracy = (1.0*valid)/((round-1)*4.0);
		System.out.println("There are" +valid+ " valid result. The accuracy is " + accuracy);
	}

}
