package org.insa.algo.shortestpath;

import org.insa.graph.Arc;
import org.insa.graph.*;

public class LabelStar extends Label {
	
	private Node nodeCourant;
	
	
	
	public LabelStar(int sc, boolean m, double c, Node n) {
		// TODO Auto-generated constructor stub
		super(sc,m,c);
		this.nodeCourant = n ;
	}
	
	public LabelStar(int sc, boolean m, double c, Arc Popa, Node n) {
		super(sc,m,c,Popa);
		this.nodeCourant = n ;
	}
	
	public double getTotalCost() {
		return this.cout + idDest.getPoint().distanceTo(this.nodeCourant.getPoint());
	}
	
	@Override
	public int compareTo(Label lab) {
		int result = 0 ;
		if((this.getTotalCost()-lab.getTotalCost())<0.0) {
			result = -1 ;
		}else if((this.getTotalCost()-lab.getTotalCost())>0.0){
			result = 1 ;
		}
		return result;
	}
}
