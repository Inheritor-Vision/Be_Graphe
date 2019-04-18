package org.insa.algo.shortestpath;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.* ;
import java.util.* ;
import org.insa.graph.* ;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        boolean trouve = false;
        Label occi;
        Graph graph = data.getGraph() ;
        float ini = 0;
        List<Arc> fils ;
        Arc arc;
        BinaryHeap<Label> tas = new BinaryHeap<Label>() ;
        Label auxi = new Label(data.getOrigin().getId(),false,ini);
        tas.insert(auxi);
        ArrayList<Label> Dijkstra = new ArrayList<Label>(data.getGraph().size());
        for(int i = 0; i < data.getGraph().size(); i++) {
        	Dijkstra.add(null);
        }
        Dijkstra.set(data.getOrigin().getId(), auxi);
        
        while(!trouve && !tas.isEmpty()) {
        	occi = tas.deleteMin();
        	Dijkstra.get(occi.getSommet()).setMarque(true);
        	fils = graph.get(occi.getSommet()).getSuccessors();
        	Iterator<Arc> iter = fils.iterator() ;
        	while(iter.hasNext()) {
        		arc = iter.next();
	        	ini = Dijkstra.get(arc.getOrigin().getId()).getCost() + arc.getLength();
	        	if(Dijkstra.get(arc.getDestination().getId()) == null) {
	        		auxi = new Label (arc.getDestination().getId(), false, ini, arc);
	        		Dijkstra.set(arc.getDestination().getId(), auxi) ;
	        		tas.insert(auxi);
	        	}else {
	        		if(Dijkstra.get(arc.getDestination().getId()).estMarque() == false) {
	        			if(Dijkstra.get(arc.getDestination().getId()).getCost() > ini) {
	        				tas.remove(Dijkstra.get(arc.getDestination().getId()));
	        				auxi = new Label(arc.getDestination().getId(),false, ini, arc);
	        				Dijkstra.set(arc.getDestination().getId(), auxi);
	        				tas.insert(auxi);
	        				
	        			}
	        		}
        		}
        	}
        	if(Dijkstra.get(data.getDestination().getId()) != null) {
        		if(Dijkstra.get(data.getDestination().getId()).estMarque() != true) {
        			trouve = true;
        		}
        	}
        }
        if(tas.isEmpty() == true && !trouve) {
        	solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }else {
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
        return solution;
    }

}
