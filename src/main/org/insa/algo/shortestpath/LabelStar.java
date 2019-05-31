package org.insa.algo.shortestpath;

import org.insa.graph.Arc;
import org.insa.algo.AbstractInputData.Mode;
import org.insa.graph.*;

public class LabelStar extends Label {
	
	private Node nodeCourant;
	private double heuri;
	
	
	
	public LabelStar(int sc, boolean m, double c, Node n) {
		// TODO Auto-generated constructor stub
		super(sc,m,c);
		this.nodeCourant = n ;
		if(LabelStar.mode == Mode.LENGTH) {
			this.heuri = idDest.getPoint().distanceTo(this.nodeCourant.getPoint());
		}else {
			this.heuri = idDest.getPoint().distanceTo(this.nodeCourant.getPoint())*3.6/ms;
		}
	}
	
	public LabelStar(int sc, boolean m, double c, Arc Popa, Node n) {
		super(sc,m,c,Popa);
		this.nodeCourant = n ;
		
		if(LabelStar.mode == Mode.LENGTH) {
			this.heuri = idDest.getPoint().distanceTo(this.nodeCourant.getPoint());
		}else {
			this.heuri = idDest.getPoint().distanceTo(this.nodeCourant.getPoint())*3.6/ms;
		}
		
	}
	
	public double getTotalCost() {
		
		if(LabelStar.mode == Mode.LENGTH) {
			//System.out.println("Node: " + nodeCourant.getId() + " Cout: " + this.cout + " Heuristique: " + (idDest.getPoint().distanceTo(this.nodeCourant.getPoint())));
			return this.cout + this.heuri;
		}else {
			//System.out.println("Node: " + nodeCourant.getId() + " Cout: " + this.cout + " Heuristique: " + (idDest.getPoint().distanceTo(this.nodeCourant.getPoint())*3.6/ms));
			return this.cout + this.heuri;
		}
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
