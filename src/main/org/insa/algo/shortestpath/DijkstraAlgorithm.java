package org.insa.algo.shortestpath;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.* ;
import java.util.* ;
import org.insa.graph.* ;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {
	
	public int marques = 0;
	public int explores = 0;
    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    public Label createLabel(int a, boolean b, double c, Node d) {
    	return new Label(a,b,c);
    }
    
    public Label createLabel(int a, boolean b, double c, Arc d, Node e) {
    	return new Label(a,b,c,d);
    }
    
    @Override
    protected ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Graph graph = data.getGraph() ;
        if(data.getDestination() != data.getOrigin()) {
	        boolean trouve = false;
	        Label occi;
	        double ini = 0;
	        List<Arc> fils ;
	        Label.idDest = data.getDestination();
	        Label.mode = data.getMode();
	        if(data.getMaximumSpeed() == GraphStatistics.NO_MAXIMUM_SPEED) {
	        	Label.ms = data.getGraph().getGraphInformation().getMaximumSpeed();
	        }else {
	        	Label.ms = data.getMaximumSpeed();
	        }
	        
	        Arc arc;
	        BinaryHeap<Label> tas = new BinaryHeap<Label>() ;
	        Label auxi = this.createLabel(data.getOrigin().getId(),false,ini, data.getOrigin());
	        tas.insert(auxi);
	        ArrayList<Label> Dijkstra = new ArrayList<Label>(data.getGraph().size());
	        for(int i = 0; i < data.getGraph().size(); i++) {
	        	Dijkstra.add(null);
	        }
	        Dijkstra.set(data.getOrigin().getId(), auxi);
	        
	        while(!trouve && !tas.isEmpty()) {
	        	occi = tas.deleteMin();
	        	this.notifyNodeMarked(graph.get(occi.getSommet())) ;
	        	Dijkstra.get(occi.getSommet()).setMarque(true);
	        	marques ++;
	        	fils = graph.get(occi.getSommet()).getSuccessors();
	        	Iterator<Arc> iter = fils.iterator() ;
	        	if(data.getOrigin() == graph.get(occi.getSommet())) {
	        		this.notifyOriginProcessed(graph.get(occi.getSommet()));
	        	}
	        	while(iter.hasNext()) {
	        		arc = iter.next();
	        		if (!data.isAllowed(arc)) {
                        continue;
                    }
		        	ini = Dijkstra.get(arc.getOrigin().getId()).getCost() + data.getCost(arc) ;
		        	if(Dijkstra.get(arc.getDestination().getId()) == null) {
		        		explores ++;
		        		this.notifyNodeReached(graph.get(arc.getDestination().getId()));
		        		auxi = this.createLabel(arc.getDestination().getId(), false, ini, arc, arc.getDestination());
		        		Dijkstra.set(arc.getDestination().getId(), auxi) ;
		        		tas.insert(auxi);
		        	}else {
		        		if(Dijkstra.get(arc.getDestination().getId()).estMarque() == false) {
		        			if(Dijkstra.get(arc.getDestination().getId()).getCost() > ini) {
		        				tas.remove(Dijkstra.get(arc.getDestination().getId()));
		        				auxi = this.createLabel(arc.getDestination().getId(),false, ini, arc, arc.getDestination());
		        				Dijkstra.set(arc.getDestination().getId(), auxi);
		        				tas.insert(auxi);
		        				
		        			}
		        		}
	        		}
	        	}
	        	if(Dijkstra.get(data.getDestination().getId()) != null) {
	        		if(Dijkstra.get(data.getDestination().getId()).estMarque() == true) {
	        			trouve = true;
	        		}
	        	}
	        }
	       
	        if(tas.isEmpty() == true && !trouve) {
	        	solution = new ShortestPathSolution(data, Status.INFEASIBLE);
	        }else {
	        	this.notifyDestinationReached(data.getDestination());
	        	ArrayList<Arc> Chemin = new ArrayList<Arc>();
	        	boolean fini = false;
	        	Arc ou = Dijkstra.get(data.getDestination().getId()).getArc();
	        	Chemin.add(ou);
	        	while(!fini) {
	        		ou = Dijkstra.get(ou.getOrigin().getId()).getArc();
	        		Chemin.add(ou);
	        		if(ou.getOrigin() == data.getOrigin()) {
	        			fini = true;
	        		}
	        	}
	        	Collections.reverse(Chemin);
	        	solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph,Chemin));
	        	
	        }
        }else {
        	solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph,data.getDestination()));
        }
        return solution;
    }

}
