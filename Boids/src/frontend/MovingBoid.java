package frontend;

import java.util.ArrayList;

import backend.Constants;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MovingBoid {
	
	ArrayList<Circle> circleList = new ArrayList<Circle>();
	
	public MovingBoid(Pane pane, double x, double y) {
		pane.getChildren().add(addCircle(x, y));
	}
	
	public void move(Pane pane, double x, double y) {
		pane.getChildren().add(addCircle(x, y));
		
		if(circleList.size() > 10) {
			pane.getChildren().remove(circleList.remove(0));
		}
		
		double alpha = 1;
		double shrinkSize = Constants.CIRCLE_SIZE;
		for (int i = circleList.size() - 1; i >= 0; i--) {
			circleList.get(i).setFill(Color.rgb(200, 50, 50, alpha));
			circleList.get(i).setRadius(shrinkSize);
			alpha -= 0.1;
			shrinkSize -= 0.4;
		}
	}
	
	private Circle addCircle(double x, double y) {
		Circle circle = new Circle(x, y, Constants.CIRCLE_SIZE);
		circle.setFill(Color.rgb(200, 50, 50, 1));
		circleList.add(circle);
		
		return circle;
	}
}
