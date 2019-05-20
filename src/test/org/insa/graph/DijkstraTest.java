package org.insa.graph;

import org.insa.algo.*;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.shortestpath.DijkstraAlgorithm;
import org.insa.algo.shortestpath.BellmanFordAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.insa.graph.RoadInformation.RoadType;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

public class DijkstraTest {

    // Small graph use for tests
    private static Graph graph, graph2;
    
    private static DijkstraAlgorithm algo1, algo2, algo4;
    
    private static BellmanFordAlgorithm algo3 ;
    
    @BeforeClass
    public static void initAll() throws IOException {
    	String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/french-polynesia.mapgr" ;
    	GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
    	graph = reader.read();
    	
    	ShortestPathData spd1 = new ShortestPathData(DijkstraTest.graph,DijkstraTest.graph.get(8141), DijkstraTest.graph.get(8601), ArcInspectorFactory.getAllFilters().get(0));
    	algo1 = new DijkstraAlgorithm(spd1);
    	
    	ShortestPathData spd2 = new ShortestPathData(DijkstraTest.graph,DijkstraTest.graph.get(8141), DijkstraTest.graph.get(8141), ArcInspectorFactory.getAllFilters().get(0));
    	algo2 = new DijkstraAlgorithm(spd2);
    	
    	String mapName2 = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/toulouse.mapgr" ;
    	GraphReader reader2 = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName2))));
    	graph2 = reader2.read();
    	
    	//10307 6097
    	ShortestPathData spd3 = new ShortestPathData(DijkstraTest.graph2, DijkstraTest.graph2.get(10307), DijkstraTest.graph2.get(6226), ArcInspectorFactory.getAllFilters().get(0)) ;
    	algo3 = new BellmanFordAlgorithm(spd3) ;
    	
    	algo4 = new DijkstraAlgorithm(spd3);
    	
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
