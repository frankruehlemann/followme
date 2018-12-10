package main;
import view.robotGUI;
import control.robotGUIControl;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Robot;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class main extends Application{

	public static void main(String[] args) {
		
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
        
		GridPane root = new robotGUI(new Robot());
		robotGUIControl robControl = new robotGUIControl((robotGUI)root);
		
		Stage stage = primaryStage;
		
		stage.setScene(new Scene(root,1200,400));
		stage.show();
		
	}

}
