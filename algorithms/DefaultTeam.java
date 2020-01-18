package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import supportGUI.Circle;
import algorithms.*;

public class DefaultTeam {

	
	//Algorithme de Ritter pour le calcul du cercle min
	public Circle calculCercleMin(ArrayList<Point> points) {
		System.out.println(points.size());
		if (points.size() < 1)
			return null;
		ArrayList<Point> res = (ArrayList<Point>) points.clone();
		Point dummy = res.get(0);
		Point p = dummy;
		double cx, cy, cr;
		for (Point s : res) {
			if (Tools.dist(s, dummy) > Tools.dist(p, dummy))
				p = s;
		}
		Point q = p;
		for (Point s : res) {
			if (Tools.dist(p, s) > Tools.dist(p, q))
				q = s;
		}
		cx = .5 * (p.getX() + q.getX());
		cy = .5 * (p.getY() + q.getY());
		cr = .5 * Tools.dist(p, q);

		res.remove(p);
		res.remove(q);

		while (!res.isEmpty()) {
			Point s = res.remove(0);
			double distCS = Tools.dist(cx, cy, s.getX(), s.getY());

			if (distCS <= cr)
				continue;
			double c2r = .5 * (cr + distCS);
			double a = c2r / distCS;

			double b = (distCS - c2r) / distCS;
			double c2x = a * cx + b * s.getX();
			double c2y = a * cy + b * s.getY();
			cr = c2r;
			cx = c2x;
			cy = c2y;
		}

		
		
		ArrayList<Point> po = enveloppeConvexe2(points);
		//calcule de la qualité
		double d  = Tools.qualiteC(po,cr);
		System.out.println("qualite ::"+d);
		return new Circle(new Point((int) cx, (int) cy), (int) cr);
	}

	
	// calcule de l'enveloppe convexe
	public ArrayList<Point> enveloppeConvexe2(ArrayList<Point> points) {

		if (points.size() < 4)
			return points;

		ArrayList<Point> result = tme6exercice2(points);
		for (int i = 1; i < result.size() + 2; i++) {
			Point p = result.get((i - 1) % result.size());
			Point q = result.get(i % result.size());
			Point r = result.get((i + 1) % (result.size()));
			if (Tools.crossProduct(p, q, p, r) > 0) {
				result.remove(i % result.size());
				if (i == 2)
					i = 1;
				if (i > 2)
					i -= 2;
			}
		}
		return result;
	}
	// calcule de l'enveloppe convexe 
	private ArrayList<Point> tme6exercice2(ArrayList<Point> points) {
		if (points.size() < 4)
			return points;
		int maxX = points.get(0).x;
		for (Point p : points)
			if (p.x > maxX)
				maxX = p.x;
		Point[] maxY = new Point[maxX + 1];
		Point[] minY = new Point[maxX + 1];
		for (Point p : points) {
			if (maxY[p.x] == null || p.y > maxY[p.x].y)
				maxY[p.x] = p;
			if (minY[p.x] == null || p.y < minY[p.x].y)
				minY[p.x] = p;
		}
		ArrayList<Point> result = new ArrayList<Point>();
		for (int i = 0; i < maxX + 1; i++)
			if (maxY[i] != null)
				result.add(maxY[i]);
		for (int i = maxX; i >= 0; i--)
			if (minY[i] != null && !result.get(result.size() - 1).equals(minY[i]))
				result.add(minY[i]);

		if (result.get(result.size() - 1).equals(result.get(0)))
			result.remove(result.size() - 1);

		return result;
	}

	
	 public ArrayList<Point> enveloppeConvexe(ArrayList<Point> points){
		 
		 ArrayList<Point> ret = toussaint(points);
		 return ret;
	 }
	
	 
	 // algorithme de Toussaint
	 public ArrayList<Point> toussaint(ArrayList<Point> points) {
		 System.out.println(points.size());
			ArrayList<Point> envconv = enveloppeConvexe2(points);
			Point p;
			int i = 0, j = 0, k = 0, l = 0;
			Point pi = new Point (Integer.MAX_VALUE, 0), pj = new Point (Integer.MIN_VALUE, 0),
				pk = new Point(0, Integer.MAX_VALUE), pl = new Point(0, Integer.MIN_VALUE);

			int s = envconv.size();
			for (int indx = 0; indx < s; indx++) {
				p = envconv.get(indx);
				if (p.x < pi.x) {
					i = indx;
					pi.move(p.x, p.y);
				}
				if (p.x > pj.x) {
					j = indx;
					pj.move(p.x, p.y);
				}
				if (p.y < pk.y) {
					k = indx;
					pk.move(p.x, p.y);
				}
				if (p.y > pl.y) {
					l = indx;
					pl.move(p.x, p.y);
				}
			}

			Point pid = new Point(pi.x, pi.y + 1), pjd = new Point(pj.x, pj.y - 1),
					pkd = new Point(pk.x - 1, pk.y), pld = new Point ((int)pl.x + 1,(int) pl.y);


			Point pin = new Point(0, 0), pjn = new Point(0, 0), pkn = new Point(0, 0),
					pln = new Point(0, 0);
			double ai, aj, ak, al, teta;
			Point A = Tools.intersection(pi, pid, pkd, pk), B = Tools.intersection(pk, pkd, pjd, pj),
					C = Tools.intersection(pj, pjd, pld, pl), D = Tools.intersection(pl, pld, pid, pi);

			double minvol = A.distance(B) * B.distance(C);
			ArrayList<Point> sq = new ArrayList<Point>();

			sq.add(new Point((int) A.x, (int) A.y));
			sq.add(new Point((int) B.x, (int) B.y));
			sq.add(new Point((int) C.x, (int) C.y));
			sq.add(new Point((int) D.x, (int) D.y));

			for (int indx = 1; indx < s; indx++) {
				teta = 0;

				p = envconv.get((i + 1) % s);
				pin.move(p.x, p.y);
				ai = Tools.getAngle2(pi, pid, pi, pin);

				p = envconv.get((j + 1) % s);
				pjn.move(p.x, p.y);
				aj = Tools.getAngle2(pj, pjd, pj, pjn);

				p = envconv.get((k + 1) % s);
				pkn.move(p.x, p.y);
				ak = Tools.getAngle2(pk, pkd, pk, pkn);

				p = envconv.get((l + 1) % s);
				pln.move(p.x, p.y);
				al = Tools.getAngle2(pl, pld, pl, pln);

				teta = Double.min(Double.min(ai, aj), Double.min(ak, al));

				Tools.rotatePoint(pi, pid, -teta);
				Tools.rotatePoint(pj, pjd, -teta);
				Tools.rotatePoint(pk, pkd, -teta);
				Tools.rotatePoint(pl, pld, -teta);

				ai = Tools.getAngle2(pi, pid, pi, pin);
				aj = Tools.getAngle2(pj, pjd, pj, pjn);
				ak = Tools.getAngle2(pk, pkd, pk, pkn);
				al = Tools.getAngle2(pl, pld, pl, pln);
				if (ai == 0.0) {
					i = (i + 1) % s;
					p = envconv.get(i);
					pid.move(p.x + (pid.x - pi.x), p.y + (pid.y - pi.y));
					pi.move(p.x, p.y);
				}
				if (aj == 0.0) {
					j = (j + 1) % s;
					p = envconv.get(j);
					pjd.move(p.x + (pjd.x - pj.x), p.y + (pjd.y - pj.y));
					pj.move(p.x, p.y);
				}
				if (ak == 0.0) {
					k = (k + 1) % s;
					p = envconv.get(k);
					pkd.move(p.x + (pkd.x - pk.x), p.y + (pkd.y - pk.y));
					pk.move(p.x, p.y);
				}
				if (al == 0.0) {
					l = (l + 1) % s;
					p = envconv.get(l);
					pld.move(p.x + (pld.x - pl.x), p.y + (pld.y - pl.y));
					pl.move(p.x, p.y);
				}
	

				A = Tools.intersection(pi, pid, pk, pkd);
				B = Tools.intersection(pk, pkd, pj, pjd);
				C = Tools.intersection(pj, pjd, pl, pld);
				D = Tools.intersection(pl, pld, pi, pid);

				
				if (A.distance(B) * B.distance(C) < minvol) {
					minvol = A.distance(B) * B.distance(C);
					sq.clear();
					sq.add(new Point((int) A.x, (int) A.y));
					sq.add(new Point((int) B.x, (int) B.y));
					sq.add(new Point((int) C.x, (int) C.y));
					sq.add(new Point((int) D.x, (int) D.y));
				}
			}
			
			//Calculer la qualité de l'algo
			double areaP = Tools.AreadPolygon(envconv);
			double areaR = Tools.areaRect(A, B, D);
			
			double q = (areaR / areaP) - 1;
			System.out.println("qualite TOU:: "+Math.abs(q));
			return sq;
		}
	
	
	 

}