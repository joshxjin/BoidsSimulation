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
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {
	
	final private Duration DURATION = Duration.millis(33); //~30FPS
	
	private Pane centerPanel = new Pane();
	private HBox topPanel = new HBox();
	private Timeline timeline = new Timeline();
	private KeyFrame kf;
	
	private HashMap<Obstacle, Circle> obstacleList = new HashMap<Obstacle, Circle>();
	private HashMap<Boid, MovingBoid> boidList = new HashMap<Boid, MovingBoid>();
	private ArrayList<Obstacle> vertBorderObstacleList = new ArrayList<Obstacle>();
	private ArrayList<Obstacle> horBorderObstacleList = new ArrayList<Obstacle>();
	
	private Boolean vertBorderObstacle = false;
	private Boolean horBorderObstacle = true;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		root.setCenter(centerPanel);
		root.setTop(topPanel);
		
		setupTopPanel(); //top panel of buttons
		setupCenterPanel(); //canvas for animation
		
		kf = new KeyFrame(DURATION, new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				for (Map.Entry<Boid, MovingBoid> boids: boidList.entrySet()) { //move every boid
					Boid boid = boids.getKey();
					MovingBoid movingBoid = boids.getValue();
					
					boid.move(boidList.keySet(), obstacleList.keySet());
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
				
				verticalBorderObstacles();
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
				
				horizontalBorderObstacles();
			}
		});
		
		ToggleButton boidsFollowBtn = new ToggleButton();
		boidsFollowBtn.setSelected(true);
		boidsFollowBtn.setText("Boids Follow");
		boidsFollowBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (Constants.BOIDS_FOLLOW)
					Constants.BOIDS_FOLLOW = false;
				else
					Constants.BOIDS_FOLLOW = true;
			}
		});
		
		ToggleButton randomNoiseBtn = new ToggleButton();
		randomNoiseBtn.setSelected(true);
		randomNoiseBtn.setText("Movement Noise");
		randomNoiseBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (Constants.RANDOM_NOISE)
					Constants.RANDOM_NOISE = false;
				else
					Constants.RANDOM_NOISE = true;
			}
		});
		
		ToggleButton personalSpaceBtn = new ToggleButton();
		personalSpaceBtn.setSelected(true);
		personalSpaceBtn.setText("Personal Space");
		personalSpaceBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (Constants.PERSONAL_SPACE)
					Constants.PERSONAL_SPACE = false;
				else
					Constants.PERSONAL_SPACE = true;
			}
		});
		
		topPanel.setMinHeight(Constants.TOP_PANEL_HEIGHT);
		topPanel.getChildren().add(resetBtn);
		topPanel.getChildren().add(new Separator(Orientation.VERTICAL));
		topPanel.getChildren().add(verticalBorderObstacleBtn);
		topPanel.getChildren().add(horizontalBorderObstaclesBtn);
		topPanel.getChildren().add(boidsFollowBtn);
		topPanel.getChildren().add(randomNoiseBtn);
		topPanel.getChildren().add(personalSpaceBtn);
	}
	
	private void setupCenterPanel() {
		centerPanel.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				switch(event.getButton()) {
				case PRIMARY: //left click to add a single boid
					Boid boid = new Boid(event.getX(), event.getY());
					MovingBoid movingBoid = new MovingBoid(centerPanel, boid.getX(), boid.getY());
					
					boidList.put(boid, movingBoid);
					break;
				case SECONDARY: //right click to add a single obstacle
					Obstacle obstacle = new Obstacle(event.getX(), event.getY());
					
					Circle circle = new Circle(obstacle.getX(), obstacle.getY(), Constants.OBSTACLE_SIZE);
					circle.setFill(Color.BLACK);
					centerPanel.getChildren().add(circle);
					
					obstacleList.put(obstacle, circle);
					break;
				}
			}
		});
		
		centerPanel.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) { //left click and drag to continuously add boids
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
	
	private void setupBorderObstacles() { //setup borders at start up/reset
		verticalBorderObstacles(); //vertical border
		horizontalBorderObstacles(); //horizontal border
	}
	
	private void verticalBorderObstacles() { //
		if (vertBorderObstacle) { //add vertical obstacle border
			int numOfVerticalObstacles = Constants.CANVAS_HEIGHT / (Constants.OBSTACLE_SIZE * 2);
			
			for (int i = 0; i < numOfVerticalObstacles; i++) {//left and right obstacles
				double tempY = Constants.OBSTACLE_SIZE * (i + 1) + Constants.OBSTACLE_SIZE * i;
				for (int j = 0; j <= Constants.CANVAS_WIDTH; j += Constants.CANVAS_WIDTH) {
					double tempX = Math.abs(Constants.OBSTACLE_SIZE - j);
					Obstacle obstacle = new Obstacle(tempX, tempY);
					Circle circle = new Circle(tempX, tempY, Constants.OBSTACLE_SIZE);
					circle.setFill(Color.BLACK);
					centerPanel.getChildren().add(circle);
					obstacleList.put(obstacle, circle);
					vertBorderObstacleList.add(obstacle);
				}
			}
		} else if (!vertBorderObstacleList.isEmpty()) { //remove vertical obstacle border
			for (Obstacle borderObstacle: vertBorderObstacleList) {
				centerPanel.getChildren().remove(obstacleList.remove(borderObstacle));
			}
			vertBorderObstacleList.clear();
		}
	}
	
	private void horizontalBorderObstacles() {
		if (horBorderObstacle) { //add horizontal obstacle border
			int numOfHorizontalObstacles = Constants.CANVAS_WIDTH / (Constants.OBSTACLE_SIZE * 2);
			
			for (int i = 0; i < numOfHorizontalObstacles; i++) { //top and bottom obstacles
				double tempX = Constants.OBSTACLE_SIZE * (i + 1) + Constants.OBSTACLE_SIZE * i;
				for (int j = 0; j <= Constants.CANVAS_HEIGHT; j += Constants.CANVAS_HEIGHT) {
					double tempY = Math.abs(Constants.OBSTACLE_SIZE - j);
					Obstacle obstacle = new Obstacle(tempX, tempY);
					Circle circle = new Circle(tempX, tempY, Constants.OBSTACLE_SIZE);
					circle.setFill(Color.BLACK);
					centerPanel.getChildren().add(circle);
					obstacleList.put(obstacle, circle);
					horBorderObstacleList.add(obstacle);
				}
			}
		} else if (!horBorderObstacleList.isEmpty()) { //remove horizontal obstacle border
			for (Obstacle borderObstacle: horBorderObstacleList) {
				centerPanel.getChildren().remove(obstacleList.remove(borderObstacle));
			}
			horBorderObstacleList.clear();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
