package frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import backend.Boid;
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
	
	final static int  CANVAS_WIDTH = 800;
	final static int  CANVAS_HEIGHT = 800;
	final static int CIRCLE_SIZE = 10;
	final private Duration DURATION = Duration.millis(33); //~30FPS
	
	private Pane centerPanel = new Pane();;
	private Timeline timeline = new Timeline();
	private KeyFrame kf;
	
	private ArrayList<Obstacle> obstacleList = new ArrayList<Obstacle>();
	private HashMap<Boid, Circle> boidList = new HashMap<Boid, Circle>();
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		root.setCenter(centerPanel);
		
		setupCenterPanel();
		
		kf = new KeyFrame(DURATION, new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				for (Map.Entry<Boid, Circle> boids: boidList.entrySet()) {
					Boid boid = boids.getKey();
					Circle circle = boids.getValue();
					
					boid.move(obstacleList);
					
					circle.setCenterX(boid.getX());
					circle.setCenterY(boid.getY());
				}
			}
		});
		timeline.setCycleCount(timeline.INDEFINITE);
		timeline.getKeyFrames().add(kf);
		timeline.play();
		
		Scene scene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT);
		
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
				Circle circle = new Circle(boid.getX(), boid.getY(), CIRCLE_SIZE);
				circle.setFill(Color.ORANGE);
				centerPanel.getChildren().add(circle);
				boidList.put(boid, circle);
			}
		});
		centerPanel.minWidth(CANVAS_WIDTH);
		centerPanel.minHeight(CANVAS_HEIGHT);
		setupBorderObstacles();
	}
	
	private void setupBorderObstacles() {
		int numOfHorizontalObstacles = CANVAS_WIDTH / (CIRCLE_SIZE * 2);
		int numOfVerticalObstacles = CANVAS_HEIGHT / (CIRCLE_SIZE * 2);
		
		for (int i = 0; i < numOfHorizontalObstacles; i++) {
			double tempX = CIRCLE_SIZE * (i + 1) + CIRCLE_SIZE * i;
			for (int j = 0; j <= CANVAS_HEIGHT; j += CANVAS_HEIGHT) {
				double tempY = Math.abs(CIRCLE_SIZE - j);
				Obstacle obstacle = new Obstacle(tempX, tempY);
				obstacleList.add(obstacle);
				Circle circle = new Circle(tempX, tempY, CIRCLE_SIZE);
				circle.setFill(Color.BLACK);
				centerPanel.getChildren().add(circle);
			}
		}
		
		for (int i = 1; i < numOfVerticalObstacles - 1; i++) {
			double tempY = CIRCLE_SIZE * (i + 1) + CIRCLE_SIZE * i;
			for (int j = 0; j <= CANVAS_WIDTH; j += CANVAS_WIDTH) {
				double tempX = Math.abs(CIRCLE_SIZE - j);
				Obstacle obstacle = new Obstacle(tempX, tempY);
				obstacleList.add(obstacle);
				Circle circle = new Circle(tempX, tempY, CIRCLE_SIZE);
				circle.setFill(Color.BLACK);
				centerPanel.getChildren().add(circle);
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
