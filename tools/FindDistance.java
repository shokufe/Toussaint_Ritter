package tools;

import java.awt.Point;

public class FindDistance {
	public static double dist(Point p, Point q) 
	 { 
	     // Calculating distance 
	     return Math.sqrt(Math.pow(q.getX() - p.getX(), 2) +  
	                 Math.pow(q.getY() - p.getY(), 2) * 1.0); 
	 } 
}
