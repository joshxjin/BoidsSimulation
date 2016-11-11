package frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import backend.Boid;
import backend.Constants;
import backend.Obstacle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {
	
	final private Duration DURATION = Duration.millis(33); //~30FPS
	
	private Pane centerPanel = new Pane();;
	private Timeline timeline = new Timeline();
	private KeyFrame kf;
	
	private ArrayList<Obstacle> obstacleList = new ArrayList<Obstacle>();
	private HashMap<Boid, MovingBoid> boidList = new HashMap<Boid, MovingBoid>();
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		root.setCenter(centerPanel);
		
		setupCenterPanel();
		
		kf = new KeyFrame(DURATION, new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				for (Map.Entry<Boid, MovingBoid> boids: boidList.entrySet()) {
					Boid boid = boids.getKey();
					MovingBoid movingBoid = boids.getValue();
					
					boid.move(obstacleList);
					movingBoid.move(centerPanel, boid.getX(), boid.getY());
				}
			}
		});
		timeline.setCycleCount(timeline.INDEFINITE);
		timeline.getKeyFrames().add(kf);
		timeline.play();
		
		Scene scene = new Scene(root, Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);
		
		primaryStage.setTitle("Boids Simulation");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void setupCenterPanel() {
		centerPanel.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				Boid boid = new Boid(event.getX(), event.getY());
				MovingBoid movingBoid = new MovingBoid(centerPanel, boid.getX(), boid.getY());
				
				boidList.put(boid, movingBoid);
			}
		});
		centerPanel.minWidth(Constants.CANVAS_WIDTH);
		centerPanel.minHeight(Constants.CANVAS_HEIGHT);
		setupBorderObstacles();
	}
	
	private void setupBorderObstacles() {
		int numOfHorizontalObstacles = Constants.CANVAS_WIDTH / (Constants.CIRCLE_SIZE * 2);
		int numOfVerticalObstacles = Constants.CANVAS_HEIGHT / (Constants.CIRCLE_SIZE * 2);
		
		for (int i = 0; i < numOfHorizontalObstacles; i++) {
			double tempX = Constants.CIRCLE_SIZE * (i + 1) + Constants.CIRCLE_SIZE * i;
			for (int j = 0; j <= Constants.CANVAS_HEIGHT; j += Constants.CANVAS_HEIGHT) {
				double tempY = Math.abs(Constants.CIRCLE_SIZE - j);
				Obstacle obstacle = new Obstacle(tempX, tempY);
				obstacleList.add(obstacle);
				Circle circle = new Circle(tempX, tempY, Constants.CIRCLE_SIZE);
				circle.setFill(Color.BLACK);
				centerPanel.getChildren().add(circle);
			}
		}
		
		for (int i = 1; i < numOfVerticalObstacles - 1; i++) {
			double tempY = Constants.CIRCLE_SIZE * (i + 1) + Constants.CIRCLE_SIZE * i;
			for (int j = 0; j <= Constants.CANVAS_WIDTH; j += Constants.CANVAS_WIDTH) {
				double tempX = Math.abs(Constants.CIRCLE_SIZE - j);
				Obstacle obstacle = new Obstacle(tempX, tempY);
				obstacleList.add(obstacle);
				Circle circle = new Circle(tempX, tempY, Constants.CIRCLE_SIZE);
				circle.setFill(Color.BLACK);
				centerPanel.getChildren().add(circle);
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
