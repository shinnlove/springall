/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.ArrayList;
import java.util.List;

import com.shinnlove.springall.algorithm.util.Vertex;

/**
 * This is algorithm about graph's DFS search.
 * 
 * @author Tony Zhao
 * @version $Id: GraphDFS.java, v 0.1 2021-01-06 12:47 PM Tony Zhao Exp $$
 */
public class GraphDFSTraversal {

    public static void main(String[] args) {
        List<Vertex> graph = new ArrayList<>();

        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");
        Vertex d = new Vertex("D");
        Vertex e = new Vertex("E");
        Vertex f = new Vertex("F");
        Vertex g = new Vertex("G");
        Vertex h = new Vertex("H");
        Vertex s = new Vertex("S");

        a.addAdjacent(b);
        a.addAdjacent(s);

        b.addAdjacent(a);

        c.addAdjacent(d);
        c.addAdjacent(e);
        c.addAdjacent(f);
        c.addAdjacent(s);

        d.addAdjacent(c);

        e.addAdjacent(c);
        e.addAdjacent(h);

        f.addAdjacent(c);
        f.addAdjacent(g);

        g.addAdjacent(s);
        g.addAdjacent(f);
        g.addAdjacent(h);

        h.addAdjacent(e);
        h.addAdjacent(g);

        s.addAdjacent(a);
        s.addAdjacent(c);
        s.addAdjacent(g);

        graph.add(a);
        graph.add(b);
        graph.add(c);
        graph.add(d);
        graph.add(e);
        graph.add(f);
        graph.add(g);
        graph.add(h);
        graph.add(s);

        GraphDFSTraversal gt = new GraphDFSTraversal();
        gt.graphDFSTraversal(graph);
    }

    public void graphDFSTraversal(List<Vertex> graph) {
        int size = graph.size();
        Vertex[] visitedStack = new Vertex[size];
        int stackPointer = -1;

        for (Vertex v : graph) {
            if (!v.visited) {
                v.visited = true;
                System.out.println("Visit vertex " + v.value);
                visitedStack[++stackPointer] = v;

                while (stackPointer >= 0) {
                    Vertex c = visitedStack[stackPointer];
                    boolean found = false;
                    List<Vertex> adjacentVertexes = c.adjacentVertexes;
                    for (Vertex av : adjacentVertexes) {
                        if (!av.visited) {
                            av.visited = true;
                            System.out.println("Visit vertex " + av.value);
                            visitedStack[++stackPointer] = av;
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        stackPointer--;
                    }
                } // while visited stack not empty
            } // if visited
        } // for graph
    }

}