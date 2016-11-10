package frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {
	
	final private static int  CANVAS_WIDTH = 800;
	final private static int  CANVAS_HEIGHT = 800;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		
		Scene scene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT);
		
		primaryStage.setTitle("Boids Simulation");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
