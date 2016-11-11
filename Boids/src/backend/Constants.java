package backend;

public class Constants {
	
	public final static int CANVAS_WIDTH = 1000;
	public final static int CANVAS_HEIGHT = 600;
	public final static int TOP_PANEL_HEIGHT = 30;
	
	public final static Boolean WRAP_CANVAS = true;
	public static Boolean BOIDS_FOLLOW = true;
	public static Boolean RANDOM_NOISE = true;
	public static Boolean PERSONAL_SPACE = true;
	
	public final static double BOID_SPEED = 4;
	public final static double BOID_OBSTACLE_AVOID_SPEED = 2.5;
	public final static double BOID_NEIGHBOUR_AVOID_SPEED = 0.5;
	
	public final static int BOID_SIZE = 5;
	public final static int OBSTACLE_SIZE = 10;
	
	public final static double NEIGHBOUR_RADIUS = BOID_SIZE * 8;
	public final static double AVOID_NEIGHBOUR_DISTANCE = BOID_SIZE * 4;
	public final static double AVOID_OBSTACLE_DISTANCE = (BOID_SIZE + OBSTACLE_SIZE) * 2;
}
