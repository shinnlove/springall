/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.shinnlove.springall.algorithm.util.Vertex;

/**
 * Graph BFS traversal.
 * 
 * Queue: LinkedBlockingQueue.
 * add: when queue is full throw exception,
 * offer: when queue is full just return false,
 * poll: when queue is not empty return element while return false when empty.
 * 
 * @author Tony Zhao
 * @version $Id: GraphBFSTraversal.java, v 0.1 2021-01-18 12:17 PM Tony Zhao Exp $$
 */
public class GraphBFSTraversal {

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

        GraphBFSTraversal gt = new GraphBFSTraversal();
        gt.graphBFSTraversal(graph);
    }

    public void graphBFSTraversal(List<Vertex> graph) {
        // initialize queue
        Queue<Vertex> queue = new LinkedBlockingQueue<>();

        for (Vertex v : graph) {
            if (!v.visited) {

                v.visited = true;
                System.out.println("Visit vertex " + v.value);

                for (Vertex adv : v.adjacentVertexes) {
                    if (!adv.visited) {
                        queue.offer(adv);
                    }
                } // for adjacent

                while (!queue.isEmpty()) {
                    Vertex head = queue.poll();
                    if (!head.visited) {
                        head.visited = true;
                        System.out.println("Visit vertex " + head.value);

                        for (Vertex hv : head.adjacentVertexes) {
                            if (!hv.visited) {
                                queue.offer(hv);
                            }
                        }
                    }
                } // while

            } // if visited

        } // for

    }

}