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
		
		dx = r.nextDouble() * 3;
		dy = Math.sqrt(Math.pow(Constants.BOID_SPEED, 2) - Math.pow(dx, 2));
		
		switch(r.nextInt(2)) {
		case 1:
			dx = -dx;
			break;
		}
		
		switch(r.nextInt(2)) {
		case 1:
			dy = -dy;
			break;
		}
		
	}
	
	public void move(Set<Boid> boidList, ArrayList<Obstacle> obstacles) {
		followNeighbour(Constants.BOIDS_FOLLOW, boidList);
		wrapCanvas(Constants.WRAP_CANVAS);
	}
	
	private void followNeighbour(Boolean follow, Set<Boid> boidList) {
		if (follow) {
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
				
				double speed = Math.hypot(dx, dy);
				dx = dx * Constants.BOID_SPEED / speed;
				dy = dy * Constants.BOID_SPEED / speed;
				
				follow = false;
			}
			
			
			/*
			 * get average direction
			 * move towards average direction
			 */
		} 
		
		x += dx;
		y += dy;
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
