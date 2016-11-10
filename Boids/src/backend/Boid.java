package backend;

import java.util.ArrayList;
import java.util.Random;

public class Boid {
	
	private double x, y, dx, dy;
	private Random r = new Random();
	
	public Boid(double x, double y) {
		this.x = x;
		this.y = y;
		
		switch(r.nextInt(2)) {
		case 1: 
			dx = 1;
			break;
		default:
			dx = -1;
			break;
		}
		
		switch(r.nextInt(2)) {
		case 1: 
			dy = 1;
			break;
		default:
			dy = -1;
			break;
		}
	}
	
	public void move(ArrayList<Obstacle> obstacles) {
		x += dx;
		y += dy;
		
		for (Obstacle obstacle: obstacles) {
			double xDist = x - obstacle.getX();
			double yDist = y - obstacle.getY();
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
