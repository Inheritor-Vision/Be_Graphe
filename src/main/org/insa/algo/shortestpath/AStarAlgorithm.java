package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Path;
import org.insa.graph.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    @Override
    public Label createLabel(int a, boolean b, double c, Node d) {
    	return new LabelStar(a,b,c,d);
    }
    @Override
    public Label createLabel(int a, boolean b, double c, Arc d, Node e) {
    	return new LabelStar(a,b,c,d,e);
    }
    


}
