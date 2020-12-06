/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<String>();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    @Test
    public void testAddRemove() {
        
        Graph<String> checkGraph = emptyInstance();
        
        assertEquals("expected new graph to have no such vertex",
                false, checkGraph.remove("twelve"));
        
        assertEquals("expected vertex to be length of list",
                0, checkGraph.vertices().size());
        
        assertEquals("expected new graph to add a new vertex",
                true, checkGraph.add("twelve"));
        
        assertEquals("expected new graph to add a new vertex",
                true, checkGraph.add("yuck"));
        
        assertEquals("expected vertex to be length of list",
                2, checkGraph.vertices().size());
        
        assertEquals("expected new graph to remove vertex",
                true, checkGraph.remove("twelve"));
        
        assertEquals("expected vertex to be length of list",
                1, checkGraph.vertices().size());
        
    }
    
    
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    // make sure it returns a null string for an empty graph
    // make sure it returns the list of vertices for a non-empty graph
    @Test
    public void testToString() {
        
        Graph<String> checkGraph = emptyInstance();
        
        assertEquals("expected new graph to have blank string representation",
                "[]", checkGraph.toString());
        
        checkGraph.add("yuck");
        checkGraph.add("yikes");
        
        assertEquals("expected new graph to add a new vertex",
                "[yikes, yuck]", checkGraph.toString());
        
    }
    
    // Testing strategy for Edge
    // returns weights even if zero
    // returns correct methods answers
    
    @Test
    public void testEdge() {
        
        Edge<String> checkEdge = new Edge<String>("so", "sleepy", 42);
        
        assertEquals("expected new graph to add a new vertex",
                "so", checkEdge.source());
        
        assertEquals("expected new graph to add a new vertex",
                "sleepy", checkEdge.target());
        
        assertEquals("expected new graph to add a new vertex",
                42, checkEdge.weight());
        
       
    }
    
}
