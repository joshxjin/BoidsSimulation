package backend;

import java.util.ArrayList;
import java.util.Random;

public class Boid {
	
	private double x, y, dx, dy;
	private Random r = new Random();
	
	public Boid(double x, double y) {
		this.x = x;
		this.y = y;
		
		dx = r.nextDouble() * 2 + 1;
		dy = r.nextDouble() * 2 + 1;
		
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
	
	public void move(ArrayList<Obstacle> obstacles) {
		x += dx;
		y += dy;
		
		wrapCanvas(true);
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
