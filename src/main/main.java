package main;
import control.robotGUIControl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Matrix;
import model.Robot;
import view.robotGUI;

public class main extends Application{

	public static void main(String[] args) {
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
        
		GridPane root = new robotGUI(new Robot());
		robotGUIControl robControl = new robotGUIControl((robotGUI)root);
		
		Stage stage = primaryStage;
		
		stage.setScene(new Scene(root,1600,400));
		stage.show();
		
	}

}
