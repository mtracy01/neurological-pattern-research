import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import javax.xml.datatype.Duration;

import LearningProcess.SVMLearningCore;
import LearningProcess.ParsedDataLearningCore;
import Objects.Data;
import Objects.ParsedData;
import Objects.Tuple;
import helperClasses.FileHelper;


public class MainWithJavafx extends Application {
	
	private Stage primaryStage;
    private BorderPane rootLayout;
    private int round = 2; // Testing purpose; need to be removed
    public static int learningType = -1; // MLPerceptron =1; SVM = 2;
    static File folder = null;
    static String path = null;
    static ParsedData parsedData;
    @FXML
    private Button setdir;
    @FXML
    private RadioButton svm;
    @FXML 
    private RadioButton mlperceptron;
    @FXML 
    private RadioButton oldsetting;
    @FXML 
    private RadioButton defaultset;
    @FXML
	private TextArea outputTextArea;
    @FXML 
    private RadioButton add_new_data;
    @FXML 
    private RadioButton keep_data;
    @FXML 
    private RadioButton remove_data;
   
    /*
     * Select MLPerceptron for prediction
     * Cannot select both SVM at the same time
     */
    @FXML
    private void handleRadioButton1Action(ActionEvent event) {
        // Button was clicked, do something...
    	outputTextArea.clear();
    	if(svm.isSelected()) svm.setSelected(false);
    	learningType = 1;
        outputTextArea.appendText("You select Multilayer Perceptron algorithm\n");
        
    }
    
    /*
     * Select SVM for prediction
     */
    @FXML
    private void handleRadioButton2Action(ActionEvent event) {
        // Button was clicked, do something...
    	outputTextArea.clear();
    	if(mlperceptron.isSelected()) mlperceptron.setSelected(false);
    	learningType = 2;
        outputTextArea.appendText("You select SVM learning algorithm\n");
    }
    
    /*
     * Select your working directory, where you can
     * preprocess all recording data 
     */
    @FXML
    private void handleDirChooserAction(ActionEvent event) {
    	File file = null;
    	while(file == null){
	    	FileChooser fileChooser = new FileChooser();
	    	fileChooser.setTitle("Open Resource Directory");
	    	file = fileChooser.showOpenDialog(null);
    	}
    	if(file != null ){
    		folder = file.getParentFile();
    		String dirPath = folder.getAbsolutePath();
    		outputTextArea.appendText(dirPath);
    	}
    }
    
    /*
     * Choose saving directory 
     * Only use it when you want to parsed data somewhere
     */
    @FXML
    private void handleDirSavingAction(ActionEvent event) {
    	File selectedDirectory = null;
    	while(selectedDirectory == null){
    		DirectoryChooser chooser = new DirectoryChooser();
	    	chooser.setTitle("Select Directory");
	    	selectedDirectory = chooser.showDialog(primaryStage);
    	}
    	path = selectedDirectory.getAbsolutePath();
    	outputTextArea.appendText(path);
    }
    
    /* 
     * All Data comes from the directory which has been 
     * set earlier(File -> Set working directory)
     */
    @FXML
    private void handleDefaultAction(ActionEvent event) {
    	if(oldsetting.isSelected()) oldsetting.setSelected(false);
    	outputTextArea.clear();
    	outputTextArea.appendText("\n All csv files under the working directory"
    			+ " will be converted to the correct format. \n");
    	if(folder != null){
    		//TODO: Matthew's function(read all csv files under dirPath)
    		//Testing Purpose START
			File[] allfiles = folder.listFiles();
			for(int i = 0; i < round; i++){
				for(int j = 0; j < 4; j++){
					File fileb = allfiles[i+j*10];
	    			FileHelper.loadCSVData(fileb,0);
				}
				SVMLearningCore.createSVM();
			}
			//Testing Purpose. END
    	}
    	else{
    		outputTextArea.clear();
    		outputTextArea.appendText("Invalid Working Directory\n"
    				+ "No worry! Follow this instruction to find a valid directory: \n"
    				+ "File -> set working directory\n"
    				+ "Here we go! Isn't it handy?");
    	}
    }
    
    /* 
     * All Data comes from the directory which has been 
     * set earlier(File -> Set working directory)
     */
    @FXML
    private void handleOldAction(ActionEvent event) {
    	if(defaultset.isSelected()) defaultset.setSelected(false);
    	outputTextArea.clear();
    	outputTextArea.appendText("\n Find your previous dataset file \n");
    	File file = null;
    	while(file == null ){
	    	FileChooser chooser = new FileChooser();
	    	chooser.setTitle("Open Resource Directory");
	    	file = chooser.showOpenDialog(null);
    	}
		if(file != null ){
			String filePath = folder.getAbsolutePath();
			outputTextArea.appendText(filePath);
			boolean loading = FileHelper.loadDataSet(filePath);
			if(!loading){
				outputTextArea.clear();
				outputTextArea.appendText("\n Error when loading files!\n");
			}
		}
    }
    
    @FXML
    private void handleKeepDataAction(ActionEvent event) {
    	if(remove_data.isSelected() || add_new_data.isSelected()){ 
    		remove_data.setSelected(false);
    		add_new_data.setSelected(false);
    	}
        outputTextArea.appendText("Keep your data setting\n");
        
        
    }
    
    @FXML
    private void handleRemoveDataAction(ActionEvent event) {
    	if(keep_data.isSelected() || add_new_data.isSelected()){
    		keep_data.setSelected(false);
    		add_new_data.setSelected(false);
    	}
        outputTextArea.appendText("Button Action\n");
    }
    
    
    @FXML
    private void handleAddDataAction(ActionEvent event) {
    	if(remove_data.isSelected() || keep_data.isSelected()) {
    		keep_data.setSelected(false);
    		remove_data.setSelected(false);
    	}
        outputTextArea.appendText("Button Action\n");
    }
    
    
    /* 
     * Use learningType variable to decide which algorithm 
     * will be used. The output should be updated to table content
     * on the right.
     */
    @FXML
    private void handlePredictionAction(ActionEvent event) {
    	File file = null;  		// File for color prediction 
    	while(file == null ){
	    	FileChooser chooser = new FileChooser();
	    	chooser.setTitle("Open Resource Directory");
	    	file = chooser.showOpenDialog(null);
    	}
		if(file != null ){
			String filePath = folder.getAbsolutePath();
			outputTextArea.appendText(filePath);
			parsedData = FileHelper.loadCSVData(file,0);
		}
		if(parsedData != null){
	    	if(learningType == 1){			//MLPerceptron 
	    		Tuple<Double, String> tuple = ParsedDataLearningCore.testNeuralNetwork(parsedData);
	    	}else if(learningType == 2){ 	//SVM
	    		String result = SVMLearningCore.classifyData(parsedData);
	    		outputTextArea.appendText("SVM: "+result);
	    	}else{							//Error! No Algorithm is specified
	    		outputTextArea.clear();
	    		outputTextArea.appendText("Oops! Something goes wrong :/ \n"
	    				+ "Which algorithm do you wanna try?\n");
	    	}
		}
		else outputTextArea.appendText("\n File has invalid form :(\n");
        
    }
    
    @FXML
    private void handleSavingDataAction(ActionEvent event) {
        // Button was clicked, do something...
        outputTextArea.appendText("Button Action\n");
    }
    
    @FXML
    private void handleExportAction(ActionEvent event) {
        // Button was clicked, do something...
        outputTextArea.appendText("Button Action\n");
    }
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("ColorRecognition");
        initRootLayout();
		
	}
	@FXML
	private void initialize() {
		// Handle Button event.
		//setdir.setOnAction(this::handleButtonAction);
	}
	/**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(getClass().getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add("menusheets.css");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showOverview();
    }
	
    /**
     * Shows the person overview inside the root layout.
     */
    public void showOverview() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Overview.fxml"));
            AnchorPane overview = (AnchorPane) loader.load();
            rootLayout.setCenter(overview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    	System.out.println("java version: "+System.getProperty("java.version"));
    	System.out.println("javafx.version: " + System.getProperty("javafx.version"));
    }

}