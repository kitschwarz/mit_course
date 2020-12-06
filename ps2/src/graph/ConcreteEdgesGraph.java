/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Collections;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge<String>> edges = new ArrayList<>();
    
    // Rep invariant:
    //   vertices is a list of Strings
    //   edges is a list of the type Edge
    // all edges are tied to vertices which exist in vertices
    //   does edges.length() have a strict relationship with vertices.length()? 
    //   Not sure about this...
    
    // Abstraction Function:
    //   represents a weighted directional graph with vertices and edges
    // Safety from rep exposure:
    //   All fields are private;
    //   vertices are Strings, and weights are Integers, so are guaranteed immutable;
    
    // constructor
    ConcreteEdgesGraph() {
        
        checkRep();
    }
    
    private void checkRep() {
        
        for (int i = 0; i < edges.size(); i++) {
            
            assert vertices.contains(edges.get(i).source()) == true;
            assert vertices.contains(edges.get(i).target()) == true;
            
        }
        
    }
    
    @Override public boolean add(String vertex) {
        if(vertices.contains(vertex)) {
            return false;
        } else {
            vertices.add(vertex);
            return true;
        }
    }
    
    
    @Override public int set(String source, String target, int weight) {
        
        // exception for illegal weights
        if(weight < 0){
            throw new RuntimeException("weight does not fit spec.");
        // remove if zero weight
        } else if (weight == 0) {
            
            if(vertices.contains(source) && vertices.contains(target)){
                
                
                for (int i = 0; i < edges.size(); i++) {
                    if((edges.get(i).source() == source) && 
                            (edges.get(i).target() == target)) {
                        
                        int oldWeight = edges.get(i).weight();
                        edges.remove(i) ;
                        return oldWeight;
                        
                    } else {
                        continue ;
                    }
                }
                
            throw new RuntimeException("vertices exist but edge not found.");
                
            } else {
                return 0;
            }
        
        // add if non-zero weight
        } else {
            
            // change the existing edge's weight
            if(vertices.contains(source) && vertices.contains(target)){
                
                for (int i = 0; i < edges.size(); i++) {
                    if((edges.get(i).source() == source) && 
                            (edges.get(i).target() == target)) {
                        
                        edges.get(i).alterWeight(weight);
                        return edges.get(i).weight();
                        
                    } else {
                        continue ;
                    }
                }
                
            throw new RuntimeException("vertices exist but edge not found.");
            
            // add new edge
            } else {
                
                Edge<String> newEdge = new Edge<String>("","",0) ;
                newEdge.alterWeight(weight);
                newEdge.alterTarget(target);
                newEdge.alterSource(source);
                edges.add(newEdge);
                
                return 0;
            }
            
        }
            
    }


    @Override public boolean remove(String vertex) {
        
        if(vertices.contains(vertex)) {
            
            // remove vertex
            vertices.remove(vertex);
            
            // remove edge
            for (int i = 0; i < edges.size(); i++) {
                if((edges.get(i).source() == vertex) ||
                        (edges.get(i).target() == vertex)) {
                    
                    edges.remove(i);
                } else {
                    continue;
                }
            }
            return true;
        
        // return false if vertex doesn't exist in Graph
        } else {
            return false;
        }  
    }
    
    @Override public Set<String> vertices() {
        return vertices;
    }
    
    @Override public Map<String, Integer> sources(String target) {
        
        Map<String, Integer> SourceMap = Collections.emptyMap();
        
        for (int i = 0; i < edges.size(); i++) {
            if(edges.get(i).target() == target) {
                String source = edges.get(i).source().toString();
                SourceMap.put(source, edges.get(i).weight());
            } else {
                continue;
            }
                   
        }
        return SourceMap;
        
    }
    
    @Override public Map<String, Integer> targets(String source) {
        
        Map<String, Integer> TargetMap = Collections.emptyMap();
        
        for (int i = 0; i < edges.size(); i++) {
            if(edges.get(i).source() == source) {
                String target = edges.get(i).target().toString();
                TargetMap.put(target, edges.get(i).weight());
            } else {
                continue;
            }
                   
        }
        return TargetMap;
        
    }
    
    @Override public String toString() {
        
        String string = String.join(", ", vertices); 
        
        return string;
    }
    
}

/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
final class Edge<E> {
    
    // fields
    private int weight;
    private E source;
    private E target;
    
    // Rep invariant:
    //   this class has three immutable characteristics, a weight, source, and target.
    // Abstraction Function:
    //   represents the edge of a weighted directional graph, with source and target vertices.
    // Safety from rep exposure:
    //   All fields are private;
    //   vertices are only immutable types, and weights are Integers, so are guaranteed immutable.
    
   
    // TODO constructor 
    public Edge(E s, E t, int w) {
        this.weight = w;
        this.source = s;
        this.target = t;
        checkRep();
    }
    
    // TODO checkRep
    private void checkRep() {
        // I have no idea what to put here... ?
    }
    
    // TODO methods
    public E source() {
        return source;
    }
    
    public E target() {
        return target;
    }
    
    public int weight() {
        return weight;
    }
    
    public boolean alterWeight(int newWeight) {
        weight = newWeight;
        return true;
    }
    
    public boolean alterSource(E newSource) {
        source = newSource;
        return true;
    }
    
    public boolean alterTarget(E newTarget) {
        target = newTarget;
        return true;
    }
    
    public String toString() {
        String string = String.valueOf(weight) + source.toString() + target.toString() ;
        return string;
    }
    
}
