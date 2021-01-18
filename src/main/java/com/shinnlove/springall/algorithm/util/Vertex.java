/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Vertex structure for graph algorithm.
 * 
 * @author Tony Zhao
 * @version $Id: Vertex.java, v 0.1 2021-01-18 12:18 PM Tony Zhao Exp $$
 */
public class Vertex {

    public String       value;
    public boolean      visited;
    public List<Vertex> adjacentVertexes = new ArrayList<>();

    public Vertex(String value) {
        this.value = value;
        this.visited = false;
    }

    public void addAdjacent(Vertex vertex) {
        adjacentVertexes.add(vertex);
    }

    @Override
    public String toString() {
        return "Vertex{" + "value=" + value + ", visited=" + visited + ", adjacentVertexes="
               + adjacentVertexes + '}';
    }

}