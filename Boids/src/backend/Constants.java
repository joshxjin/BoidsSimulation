package backend;

public class Constants {
	
	public final static int CANVAS_WIDTH = 1000;
	public final static int CANVAS_HEIGHT = 800;
	
	public final static Boolean WRAP_CANVAS = true;
	public final static Boolean BOIDS_FOLLOW = true;
	public final static Boolean RANDOM_NOISE = true;
	public final static Boolean PERSONAL_SPACE = true;
	
	public final static double BOID_SPEED = 4;
	public final static double BOID_OBSTACLE_AVOID_SPEED = 1.5;
	public final static double BOID_NEIGHBOUR_AVOID_SPEED = 0.5;
	
	public final static int CIRCLE_SIZE = 5;
	
	public final static double NEIGHBOUR_RADIUS = CIRCLE_SIZE * 8;
	public final static double AVOID_NEIGHBOUR_DISTANCE = CIRCLE_SIZE * 4;
	public final static double AVOID_OBSTACLE_DISTANCE = CIRCLE_SIZE * 10;
}
