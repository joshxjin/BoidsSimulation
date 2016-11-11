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
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {
	
	final private Duration DURATION = Duration.millis(33); //~60FPS
	
	private Pane centerPanel = new Pane();
	private HBox topPanel = new HBox();
	private Timeline timeline = new Timeline();
	private KeyFrame kf;
	
	private ArrayList<Obstacle> obstacleList = new ArrayList<Obstacle>();
	private HashMap<Boid, MovingBoid> boidList = new HashMap<Boid, MovingBoid>();
	
	private Boolean vertBorderObstacle = false;
	private Boolean horBorderObstacle = true;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		root.setCenter(centerPanel);
		root.setTop(topPanel);
		
		setupTopPanel();
		setupCenterPanel();
		
		kf = new KeyFrame(DURATION, new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				for (Map.Entry<Boid, MovingBoid> boids: boidList.entrySet()) {
					Boid boid = boids.getKey();
					MovingBoid movingBoid = boids.getValue();
					
					boid.move(boidList.keySet(), obstacleList);
					movingBoid.move(centerPanel, boid.getX(), boid.getY());
				}
			}
		});
		timeline.setCycleCount(timeline.INDEFINITE);
		timeline.getKeyFrames().add(kf);
		timeline.play();
		
		Scene scene = new Scene(root, Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT  + Constants.TOP_PANEL_HEIGHT);
		
		primaryStage.setTitle("Boids Simulation");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void setupTopPanel() {
		Button resetBtn = new Button();
		resetBtn.setText("Reset");
		resetBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				boidList.clear();
				obstacleList.clear();
				centerPanel.getChildren().clear();
				setupCenterPanel();
			}
		});
		
		ToggleButton verticalBorderObstacleBtn = new ToggleButton();
		verticalBorderObstacleBtn.setSelected(false);
		verticalBorderObstacleBtn.setText("Vertical Border");
		verticalBorderObstacleBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (vertBorderObstacle) 
					vertBorderObstacle = false;
				else
					vertBorderObstacle = true;
			}
		});
		
		ToggleButton horizontalBorderObstaclesBtn = new ToggleButton();
		horizontalBorderObstaclesBtn.setSelected(true);
		horizontalBorderObstaclesBtn.setText("Horizontal Border");
		horizontalBorderObstaclesBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (horBorderObstacle)
					horBorderObstacle = false;
				else
					horBorderObstacle = true;
			}
		});
		
		topPanel.getChildren().add(resetBtn);
		topPanel.getChildren().add(verticalBorderObstacleBtn);
		topPanel.getChildren().add(horizontalBorderObstaclesBtn);
	}
	
	private void setupCenterPanel() {
		centerPanel.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				switch(event.getButton()) {
				case PRIMARY:
					Boid boid = new Boid(event.getX(), event.getY());
					MovingBoid movingBoid = new MovingBoid(centerPanel, boid.getX(), boid.getY());
					
					boidList.put(boid, movingBoid);
					break;
				case SECONDARY:
					Obstacle obstacle = new Obstacle(event.getX(), event.getY());
					obstacleList.add(obstacle);
					
					Circle circle = new Circle(obstacle.getX(), obstacle.getY(), Constants.OBSTACLE_SIZE);
					circle.setFill(Color.BLACK);
					centerPanel.getChildren().add(circle);
					break;
				}
			}
		});
		
		centerPanel.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (event.getButton() == MouseButton.PRIMARY) {
					Boid boid = new Boid(event.getX(), event.getY());
					MovingBoid movingBoid = new MovingBoid(centerPanel, boid.getX(), boid.getY());
					
					boidList.put(boid, movingBoid);
				}
			}
		});
		
		centerPanel.minWidth(Constants.CANVAS_WIDTH);
		centerPanel.minHeight(Constants.CANVAS_HEIGHT);
		setupBorderObstacles();
	}
	
	private void setupBorderObstacles() {
		verticalBorderObstacles();
		horizontalBorderObstacles();
	}
	
	private void verticalBorderObstacles() {
		if (vertBorderObstacle) {
			int numOfVerticalObstacles = Constants.CANVAS_HEIGHT / (Constants.OBSTACLE_SIZE * 2);
			for (int i = 1; i < numOfVerticalObstacles - 1; i++) {//left and right obstacles
				double tempY = Constants.OBSTACLE_SIZE * (i + 1) + Constants.OBSTACLE_SIZE * i;
				for (int j = 0; j <= Constants.CANVAS_WIDTH; j += Constants.CANVAS_WIDTH) {
					double tempX = Math.abs(Constants.OBSTACLE_SIZE - j);
					Obstacle obstacle = new Obstacle(tempX, tempY);
					obstacleList.add(obstacle);
					Circle circle = new Circle(tempX, tempY, Constants.OBSTACLE_SIZE);
					circle.setFill(Color.BLACK);
					centerPanel.getChildren().add(circle);
				}
			}
		}
	}
	
	private void horizontalBorderObstacles() {
		if (horBorderObstacle) {
			int numOfHorizontalObstacles = Constants.CANVAS_WIDTH / (Constants.OBSTACLE_SIZE * 2);
			
			
			for (int i = 0; i < numOfHorizontalObstacles; i++) { //top and bottom obstacles
				double tempX = Constants.OBSTACLE_SIZE * (i + 1) + Constants.OBSTACLE_SIZE * i;
				for (int j = 0; j <= Constants.CANVAS_HEIGHT; j += Constants.CANVAS_HEIGHT) {
					double tempY = Math.abs(Constants.OBSTACLE_SIZE - j);
					Obstacle obstacle = new Obstacle(tempX, tempY);
					obstacleList.add(obstacle);
					Circle circle = new Circle(tempX, tempY, Constants.OBSTACLE_SIZE);
					circle.setFill(Color.BLACK);
					centerPanel.getChildren().add(circle);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
