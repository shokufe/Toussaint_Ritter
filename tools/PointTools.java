package tools;
import java.awt.Point;
import java.util.ArrayList;

public class PointTools {

	public static Point getEasternMostPoint(ArrayList<Point> points) {
		if(points.size() < 1){
			return null;
		}
		Point maxPoint = points.get(0);
		int pointsSize = points.size();
		for(int i = 1 ; i < pointsSize ; i++){
			Point tempPoint = points.get(i);
			if(tempPoint.getX() > maxPoint.getX()){
				maxPoint = tempPoint;
			}
		}
		return maxPoint;
	}
	
	public static Point getWesternMostPoint(ArrayList<Point> points) {
		if(points.size() < 1){
			return null;
		}
		Point maxPoint = points.get(0);
		int pointsSize = points.size();
		for(int i = 1 ; i < pointsSize ; i++){
			Point tempPoint = points.get(i);
			if(tempPoint.getX() > maxPoint.getX()){
				maxPoint = tempPoint;
			}
		}
		return maxPoint;
	}
	
	public static Point getSouthernMostPoint(ArrayList<Point> arrayList){
		if(arrayList.size() == 1){
			return arrayList.get(0);
		}
	
		Point minPoint = arrayList.get(0);
		for(Point p1 : arrayList){
			if((int)p1.getY() < (int)minPoint.getY()){
				minPoint = p1;
			}
		}
		return minPoint;
	}
	
	public static Point getNorthernMostPoint(ArrayList<Point> arrayList){
		Point maxPoint = arrayList.get(0);
		for(Point p1 : arrayList){
			if((int)p1.getY() >= (int)maxPoint.getY()){
				maxPoint = p1;
			}
		}
		return maxPoint;
	}
	
}