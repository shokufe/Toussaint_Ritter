package algorithms;

import java.awt.Point;
import java.util.ArrayList;

import supportGUI.Circle;
import supportGUI.Line;

public class Tools {
	
	public static double CircleArea(double r) {
		double circlearea = .0;
		circlearea = Math.PI * r * r;
		return circlearea;
	}

	public static double areaRect(Point a, Point b, Point c) {
		double da = dist(a, b);
		double db = dist(a, c);

		return da * db;
	}
	
	public static double AreadPolygon(ArrayList<Point> enveloppe) {
		double polygonarea = 0.0;
		int enveloppeSize = enveloppe.size();

		int j = enveloppeSize - 1;
		for (int i = 0; i < (enveloppeSize - 1); i++) {
			polygonarea += (enveloppe.get(j).getX() + enveloppe.get(i).getX())
					* (enveloppe.get(j).getY() - enveloppe.get(i).getY());
			j = i;
		}
		return Math.abs(polygonarea / 2.0);
	}
	

	public static double crossProduct(Point p, Point q, Point s, Point t) {
		return ((q.x - p.x) * (t.y - s.y) - (q.y - p.y) * (t.x - s.x));
	}
	

	public static double dist(Point p, Point q) {
		return Math.sqrt(Math.pow((p.getX() - q.getX()), 2) + Math.pow((p.getY() - q.getY()), 2));
	}
	public static double dist(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
	}


	private static double dotProduct(Point p, Point q, Point s, Point t) {
		return ((q.x - p.x) * (t.x - s.x) + (q.y - p.y) * (t.y - s.y));
	}

	public static void rotatePoint(Point a, Point b, double angle) {
		double nx, ny;
		nx = a.x + ((b.x - a.x) * Math.cos((angle)) - (b.y - a.y) * Math.sin((angle)));
		ny = a.y + ((b.y - a.y) * Math.cos((angle)) + (b.x - a.x) * Math.sin((angle)));
		move(nx, ny, b);
	}

	public static double getAngle2(Point a, Point b, Point c, Point d) {
		if (a.equals(b) || c.equals(d))
			return Double.MAX_VALUE;
		double cosTheta = dotProduct(a, b, c, d) / (double) (a.distance(b) * c.distance(d));
		double res = Math.acos(cosTheta);
		return Double.isNaN(res) ? 0.0 : res;
	}

	public static void move(double x, double y, Point p) {
		p.setLocation(x, y);
	}



	public static double qualiteC(ArrayList<Point> env, double r) {
		double areaC = CircleArea(r);
		double areaP = AreadPolygon(env);
		return (areaC / areaP) - 1;
	}
	public static Point intersection(Point a, Point b, Point c, Point d) {
		double a1 = b.y - a.y;
		double b1 = a.x - b.x;
		double c1 = a1 * (a.x) + b1 * (b.y);

		double a2 = d.y - c.y;
		double b2 = c.x - d.x;
		double c2 = a2 * (c.x) + b2 * (c.y);

		double determinant = a1 * b2 - a2 * b1;

		if (determinant == 0) {
			System.err.println("droite parallèle");
			return null;
		} else {
			int x = (int) ((b2 * c1 - b1 * c2) / determinant);
			int y = (int) ((a1 * c2 - a2 * c1) / determinant);
			return new Point(x, y);
		}
	}


}
