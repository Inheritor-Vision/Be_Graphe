package org.insa.graph;

import org.insa.algo.*;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.shortestpath.DijkstraAlgorithm;
import org.insa.algo.shortestpath.AStarAlgorithm;
import org.insa.algo.shortestpath.BellmanFordAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.shortestpath.ShortestPathSolution;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
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
    private static Graph graph, graph2, graph3;
    
    private static DijkstraAlgorithm DA1, DA2, DA3,DA4 , DA5, DA6, DA7;
    
    private static BellmanFordAlgorithm BFA1,BFA2,BFA3, BFA4 , BFA5;
    
    private static AStarAlgorithm ASA1,ASA2, ASA3, ASA4, ASA5;
    
    @BeforeClass
    public static void initAll() throws IOException {
    	
    	
    	String mapName = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/french-polynesia.mapgr" ;
    	GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
    	graph = reader.read();
    	
    	ShortestPathData spd1 = new ShortestPathData(DijkstraTest.graph,DijkstraTest.graph.get(8141), DijkstraTest.graph.get(8601), ArcInspectorFactory.getAllFilters().get(0));
    	DA1 = new DijkstraAlgorithm(spd1);
    	
    	ShortestPathData spd2 = new ShortestPathData(DijkstraTest.graph,DijkstraTest.graph.get(8141), DijkstraTest.graph.get(8141), ArcInspectorFactory.getAllFilters().get(0));
    	DA2 = new DijkstraAlgorithm(spd2);
    	
    	String mapName2 = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/toulouse.mapgr" ;
    	GraphReader reader2 = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName2))));
    	graph2 = reader2.read();
    	
    	//10307 6097
    	ShortestPathData spd3 = new ShortestPathData(DijkstraTest.graph2, DijkstraTest.graph2.get(10307), DijkstraTest.graph2.get(6226), ArcInspectorFactory.getAllFilters().get(0)) ;
    	BFA1 = new BellmanFordAlgorithm(spd3) ;
    	DA3 = new DijkstraAlgorithm(spd3);
    	ASA1 = new AStarAlgorithm(spd3);
    	
    	ShortestPathData spd4 = new ShortestPathData(DijkstraTest.graph2, DijkstraTest.graph2.get(20371), DijkstraTest.graph2.get(10512), ArcInspectorFactory.getAllFilters().get(0)) ;
    	BFA2 = new BellmanFordAlgorithm(spd4) ;
    	DA4 = new DijkstraAlgorithm(spd4);
    	ASA2 = new AStarAlgorithm(spd4);
    	
    	String mapName3 = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/carre-dense.mapgr" ;
    	GraphReader reader3 = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName3))));
    	graph2 = reader3.read();
    	ShortestPathData spd5 = new ShortestPathData(DijkstraTest.graph2, DijkstraTest.graph2.get(157630), DijkstraTest.graph2.get(190456), ArcInspectorFactory.getAllFilters().get(0)) ;
    	BFA3 = new BellmanFordAlgorithm(spd5) ;
    	DA5 = new DijkstraAlgorithm(spd5);
    	ASA3 = new AStarAlgorithm(spd5);
    	
    	mapName3 = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/belgium.mapgr" ;
    	reader3 = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName3))));
    	graph2 = reader3.read();
    	spd5 = new ShortestPathData(DijkstraTest.graph2, DijkstraTest.graph2.get(863405), DijkstraTest.graph2.get(922661), ArcInspectorFactory.getAllFilters().get(0)) ;
    	BFA4 = new BellmanFordAlgorithm(spd5) ;
    	DA6 = new DijkstraAlgorithm(spd5);
    	ASA4 = new AStarAlgorithm(spd5);
    	
    	spd5 = new ShortestPathData(DijkstraTest.graph2, DijkstraTest.graph2.get(863405), DijkstraTest.graph2.get(922661), ArcInspectorFactory.getAllFilters().get(2)) ;
    	BFA5 = new BellmanFordAlgorithm(spd5) ;
    	DA7 = new DijkstraAlgorithm(spd5);
    	ASA5 = new AStarAlgorithm(spd5);
    }
    @Test
    public void testFeasibility() {
    	
    	assertEquals(Status.INFEASIBLE, DA1.run().getStatus());
    }
    
    @Test
    public void testNullCost() {
    	assertEquals(Status.OPTIMAL, DA2.run().getStatus());
    }
    
    @Test
    public void testOpti() throws IOException{
    	File fil = new File("Belgium-Test-LENGTH.txt");
    	if(fil.exists()) {
    		fil.delete();
    		try {
    			fil.createNewFile();
    		}catch(IOException e) {
    			e.printStackTrace();
    		}
    	}
    	
    	FileWriter writer = new FileWriter(fil,true);
    	ShortestPathSolution sphDA = DA3.run();
    	ShortestPathSolution sphASA = ASA1.run();
    	writer.write("Test Optimalite: \n\n\tMap: Toulouse \n");
    	writer.write("\tDisjkstra: " + sphDA+"\n");
    	writer.write("\tA*: " + sphASA+"\n\n");

    
    	sphDA = DA4.run();
    	sphASA = ASA2.run();
    	writer.write("\tMap: Toulouse \n");
    	writer.write("\tDisjkstra: " + sphDA+"\n");
    	writer.write("\tA*: " + sphASA+"\n\n");

    

    	sphDA = DA7.run();
    	sphASA = ASA5.run();
    	writer.write("\tMap: Belgium \n");
    	writer.write("\tDisjkstra: " + sphDA+"\n");
    	writer.write("\tA*: " + sphASA+"\n\n");
    	
    	String mapNameF = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/belgium.mapgr" ;
    	GraphReader readerF = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapNameF))));
    	Graph graphF = readerF.read();
    	
    	
    	DijkstraAlgorithm DAF;
    	AStarAlgorithm ASAF;
    	ShortestPathData spdf ;
    	int start = 384731;
    	int end = 681851;

    	for (int x = 0; x < 38;  x+=1) {  		
    		spdf = new ShortestPathData(graphF, graphF.get(start +x), graphF.get(end + x), ArcInspectorFactory.getAllFilters().get(0)) ;
    		DAF = new DijkstraAlgorithm(spdf);
    		ASAF = new AStarAlgorithm(spdf);
    		sphDA = DAF.run();
    		sphASA = ASAF.run();
    		writer.write("\tMap: Belgium \n");
        	writer.write("\tDisjkstra: " + sphDA + "\n");
        	writer.write("Nodes explorés: " + DAF.explores + "\n");
        	writer.write("Nodes marqués: " + DAF.marques + "\n");
        	writer.write("\tA*: " + sphASA+"\n");
        	writer.write("Nodes explorés: " + ASAF.explores + "\n");
        	writer.write("Nodes marqués: " + ASAF.marques + "\n\n");
        	
    		
    	}
    	writer.close();
    	
    	fil = new File("Belgium-Test-TIME.txt");
    	if(fil.exists()) {
    		fil.delete();
    		try {
    			fil.createNewFile();
    		}catch(IOException e) {
    			e.printStackTrace();
    		}
    	}
    	
    	writer = new FileWriter(fil,true);
    	for (int x = 0; x < 38;  x+=1) {  		
    		spdf = new ShortestPathData(graphF, graphF.get(start +x), graphF.get(end + x), ArcInspectorFactory.getAllFilters().get(2)) ;
    		DAF = new DijkstraAlgorithm(spdf);
    		ASAF = new AStarAlgorithm(spdf);
    		sphDA = DAF.run();
    		sphASA = ASAF.run();
    		writer.write("\tMap: Belgium \n");
        	writer.write("\tDisjkstra: " + sphDA + "\n");
        	writer.write("Nodes explorés: " + DAF.explores + "\n");
        	writer.write("Nodes marqués: " + DAF.marques + "\n");
        	writer.write("\tA*: " + sphASA+"\n");
        	writer.write("Nodes explorés: " + ASAF.explores + "\n");
        	writer.write("Nodes marqués: " + ASAF.marques + "\n\n");
        	
    		
    	}
    	writer.close();

    	mapNameF = "/home/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/midi-pyrenees.mapgr" ;
    	readerF = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapNameF))));
    	graphF = readerF.read();
    	
    	start = 261818;
    	end = 418999;
    	fil = new File("Midi-Pyrenee-Test-LENGTH.txt");
    	if(fil.exists()) {
    		fil.delete();
    		try {
    			fil.createNewFile();
    		}catch(IOException e) {
    			e.printStackTrace();
    		}
    	}
    	
    	writer = new FileWriter(fil,true);
    			;
    	for (int x = 0; x < 38;  x+=1) {  
    		spdf = new ShortestPathData(graphF, graphF.get(start +x), graphF.get(end + x), ArcInspectorFactory.getAllFilters().get(0)) ;
    		DAF = new DijkstraAlgorithm(spdf);
    		ASAF = new AStarAlgorithm(spdf);
    		sphDA = DAF.run();
    		sphASA = ASAF.run();
    		writer.write("\tMap: Midi-Pyrénées \n");
        	writer.write("\tDisjkstra: " + sphDA + "\n");
        	writer.write("Nodes explorés: " + DAF.explores + "\n");
        	writer.write("Nodes marqués: " + DAF.marques + "\n");
        	writer.write("\tA*: " + sphASA+"\n");
        	writer.write("Nodes explorés: " + ASAF.explores + "\n");
        	writer.write("Nodes marqués: " + ASAF.marques + "\n\n");
    	}
    	
    	writer.close();
    	
    	fil = new File("Midi-Pyrenee-Test-TIME.txt");
    	if(fil.exists()) {
    		fil.delete();
    		try {
    			fil.createNewFile();
    		}catch(IOException e) {
    			e.printStackTrace();
    		}
    	}
    	
    	writer = new FileWriter(fil,true);
    	for (int x = 0; x < 38;  x+=1) {  
    		spdf = new ShortestPathData(graphF, graphF.get(start +x), graphF.get(end + x), ArcInspectorFactory.getAllFilters().get(2)) ;
    		DAF = new DijkstraAlgorithm(spdf);
    		ASAF = new AStarAlgorithm(spdf);
    		sphDA = DAF.run();
    		sphASA = ASAF.run();
    		writer.write("\tMap: Midi-Pyrénées \n");
        	writer.write("\tDisjkstra: " + sphDA + "\n");
        	writer.write("Nodes explorés: " + DAF.explores + "\n");
        	writer.write("Nodes marqués: " + DAF.marques + "\n");
        	writer.write("\tA*: " + sphASA+"\n");
        	writer.write("Nodes explorés: " + ASAF.explores + "\n");
        	writer.write("Nodes marqués: " + ASAF.marques + "\n\n");
    	}
    	
    	writer.close();
    	
    	
    	
    
    }
    

}
