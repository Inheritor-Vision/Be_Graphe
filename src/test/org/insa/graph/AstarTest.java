package org.insa.graph;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.shortestpath.BellmanFordAlgorithm;
import org.insa.algo.shortestpath.DijkstraAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;
import org.insa.algo.shortestpath.*;
public class AstarTest {

    // Small graph use for tests
    private static Graph graph, graph2;
    
    private static AStarAlgorithm algo1, algo2, algo4;
    
    private static BellmanFordAlgorithm algo3 ;
    
    @BeforeClass
    public static void initAll() throws IOException {
    	String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/french-polynesia.mapgr" ;
    	GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
    	graph = reader.read();
    	
    	ShortestPathData spd1 = new ShortestPathData(AstarTest.graph,AstarTest.graph.get(8141), AstarTest.graph.get(8601), ArcInspectorFactory.getAllFilters().get(0));
    	algo1 = new AStarAlgorithm(spd1);
    	
    	ShortestPathData spd2 = new ShortestPathData(AstarTest.graph,AstarTest.graph.get(8141), AstarTest.graph.get(8141), ArcInspectorFactory.getAllFilters().get(0));
    	algo2 = new AStarAlgorithm(spd2);
    	
    	String mapName2 = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/toulouse.mapgr" ;
    	GraphReader reader2 = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName2))));
    	graph2 = reader2.read();
    	
    	//10307 6097
    	ShortestPathData spd3 = new ShortestPathData(AstarTest.graph2, AstarTest.graph2.get(10307), AstarTest.graph2.get(6226), ArcInspectorFactory.getAllFilters().get(0)) ;
    	algo3 = new BellmanFordAlgorithm(spd3) ;
    	
    	algo4 = new AStarAlgorithm(spd3);
    	
    }
    @Test
    public void testFeasibility() {
    	assertEquals(Status.INFEASIBLE, algo1.run().getStatus());
    }
    
    @Test
    public void testNullCost() {
    	assertEquals(Status.OPTIMAL, algo2.run().getStatus());
    }
    
    @Test
    public void testOpti() {
    	assertEquals(algo3.run().getPath().getLength(), algo4.run().getPath().getLength(), 1) ;
    }

}
