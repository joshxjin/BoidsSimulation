package backend;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class Boid {
	
	private double x, y, dx, dy;
	private Random r = new Random();
	
	public Boid(double x, double y) {
		this.x = x;
		this.y = y;
		
		dx = randomNegative(r.nextDouble() * 3);
		dy = randomNegative(Math.sqrt(Math.pow(Constants.BOID_SPEED, 2) - Math.pow(dx, 2)));
		
	}
	
	public void move(Set<Boid> boidList, Set<Obstacle> obstacles) {
		followNeighbour(boidList);
		makePersonalSpace(boidList);
		
		randomMoveNoise();

		wrapCanvas(Constants.WRAP_CANVAS);
		
		for (Obstacle obstacle: obstacles) {
			double distX = x - obstacle.getX();
			double distY = y - obstacle.getY();
			
			if (Math.hypot(distX, distY) <= Constants.AVOID_OBSTACLE_DISTANCE) {
				if (distX < 0) { //to the left of obstacle
					dx -= Constants.BOID_OBSTACLE_AVOID_SPEED;
				} else {
					dx += Constants.BOID_OBSTACLE_AVOID_SPEED;
				}
					
				if (distY < 0) { //on top of obstacle
					dy -= Constants.BOID_OBSTACLE_AVOID_SPEED;
				} else {
					dy += Constants.BOID_OBSTACLE_AVOID_SPEED;
				}
				
				//normaliseSpeed();
			}
		}
		normaliseSpeed();
		x += dx;
		y += dy;
	}
	
	private void followNeighbour(Set<Boid> boidList) {
		if (Constants.BOIDS_FOLLOW) {
			ArrayList<Boid> neighbourBoids = new ArrayList<Boid>();
			for (Boid boid: boidList) {
				double dist = Math.hypot(x - boid.getX(), y - boid.getY());
				if (boid != this && dist < Constants.NEIGHBOUR_RADIUS) {
					neighbourBoids.add(boid);
				}
			}
			
			if (!neighbourBoids.isEmpty()) {
				for (Boid neighbourBoid: neighbourBoids) {
					dx += neighbourBoid.getDx();
					dy += neighbourBoid.getDy();
				}
				dx = dx / (neighbourBoids.size() + 1);
				dy = dy / (neighbourBoids.size() + 1);
				
				//normaliseSpeed();
			}
		} 
	}
	
	private void normaliseSpeed() {
		double speed = Math.hypot(dx, dy);
		dx = dx * Constants.BOID_SPEED / speed;
		dy = dy * Constants.BOID_SPEED / speed;
	}
	
	private void makePersonalSpace(Set<Boid> boidList) {
		
		if (Constants.PERSONAL_SPACE) {
			ArrayList<Boid> neighbourBoids = new ArrayList<Boid>();
			for (Boid boid: boidList) {
				double dist = Math.hypot(x - boid.getX(), y - boid.getY());
				if (boid != this && dist < Constants.AVOID_NEIGHBOUR_DISTANCE) {
					neighbourBoids.add(boid);
				}
			}
			
			if (!neighbourBoids.isEmpty()) {
				Boid closestBoid = neighbourBoids.get(0);
				double distX = x - closestBoid.getX();
				double distY = y - closestBoid.getY();
				double dist = Math.hypot(distX, distY);
				for (int i = 1; i < neighbourBoids.size(); i++) {
					distX = x - neighbourBoids.get(i).getX();
					distY = y - neighbourBoids.get(i).getY();
					if (Math.hypot(distX, distY) < dist) {
						dist = Math.hypot(distX, distY);
						closestBoid = neighbourBoids.get(i);
					}
				}
				distX = x - closestBoid.getX();
				distY = y - closestBoid.getY();
				
				if (distX < 0) { //to the left of neighbour
					dx -= Constants.BOID_NEIGHBOUR_AVOID_SPEED;
				} else {
					dx += Constants.BOID_NEIGHBOUR_AVOID_SPEED;
				}
					
				if (distY < 0) { //on top of neighbour
					dy -= Constants.BOID_NEIGHBOUR_AVOID_SPEED;
				} else {
					dy += Constants.BOID_NEIGHBOUR_AVOID_SPEED;
				}
				
				//normaliseSpeed();
			}
		}
	}
	
	private void randomMoveNoise() {
		if (Constants.RANDOM_NOISE){
			double noiseX = randomNegative(r.nextDouble() * 0.25);
			double noiseY = randomNegative(r.nextDouble() * 0.25);
			
			dx += noiseX;
			dy += noiseY;
			
			//normaliseSpeed();
		}
	}
	
	private void wrapCanvas(Boolean wrap) {
		if (wrap) {
			if (x < 0) {
				x = Constants.CANVAS_WIDTH;
			} else if (x > Constants.CANVAS_WIDTH) {
				x = 0;
			}
			
			if (y < 0) {
				y = Constants.CANVAS_HEIGHT;
			} else if (y > Constants.CANVAS_HEIGHT) {
				y = 0;
			}
		}
	}
	
	private double randomNegative(double number) {
		switch(r.nextInt(2)) {
		case 1:
			number = -number;
			break;
		}
		
		return number;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

}
