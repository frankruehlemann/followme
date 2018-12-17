package main;
import control.TrackSysGUIControl;
import control.robotGUIControl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Robot;
import model.TrackingSystem;
import view.MainGUI;
import view.TrackSysGUI;
import view.robotGUI;

public class main extends Application{

	public static void main(String[] args) {
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
        
		robotGUI robgui = new robotGUI(new Robot());
		robotGUIControl robControl = new robotGUIControl(robgui);
		
		TrackSysGUI tsgui = new TrackSysGUI(new TrackingSystem());
		TrackSysGUIControl tsc = new TrackSysGUIControl(tsgui);
		
		MainGUI root = new MainGUI(tsgui,robgui);
		
		Stage stage = primaryStage;
		
		stage.setScene(new Scene(root,1650,800));
		stage.show();
		
	}

}
