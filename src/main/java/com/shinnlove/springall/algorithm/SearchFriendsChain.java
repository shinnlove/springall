/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.shinnlove.springall.algorithm;

import java.util.*;

/**
 * Boys and girls know some of them respectively, count know chain equals four people.
 * 
 * @author Tony Zhao
 * @version $Id: SearchFriendsChain.java, v 0.1 2021-01-06 10:51 AM Tony Zhao Exp $$
 */
public class SearchFriendsChain {

    public static void main(String[] args) {

        Map<String, List<String>> relations = new HashMap<>();

        // Tony -> Nancy -> Eric -> Lily
        // Tony -> Nancy -> Eric -> Kate -> Tony
        // Tony -> Linda -> James -> Kate -> Tony
        // Tony -> Wendy -> Linda -> James -> Kate -> Tony
        List<String> tonyFriends = new ArrayList<>();
        tonyFriends.add("Nancy");
        tonyFriends.add("Wendy");
        tonyFriends.add("Linda");
        relations.put("Tony", tonyFriends);

        List<String> nancyFriends = new ArrayList<>();
        nancyFriends.add("Eric");
        nancyFriends.add("James");
        relations.put("Nancy", nancyFriends);

        List<String> ericFriends = new ArrayList<>();
        ericFriends.add("Lily");
        ericFriends.add("Kate");
        relations.put("Eric", ericFriends);

        List<String> lilyFriends = new ArrayList<>();
        relations.put("Lily", lilyFriends);

        List<String> kateFriends = new ArrayList<>();
        ericFriends.add("Tony");
        relations.put("Kate", kateFriends);

        List<String> jamesFriends = new ArrayList<>();
        ericFriends.add("Kate");
        relations.put("James", jamesFriends);

        List<String> lindaFriends = new ArrayList<>();
        ericFriends.add("James");
        relations.put("Linda", lindaFriends);

        List<String> wendyFriends = new ArrayList<>();
        ericFriends.add("Linda");
        relations.put("Wendy", wendyFriends);

        SearchFriendsChain sfc = new SearchFriendsChain();
        int result = sfc.searchFriendsChain(relations);

        System.out.println("Total recognize chain count is " + result);
    }

    class Vertex {
        String  name;
        boolean visited;

        public Vertex(String name) {
            this.name = name;
            this.visited = false;
        }

        @Override
        public String toString() {
            return "Vertex{" + "name='" + name + ", visited=" + visited + '}';
        }
    }

    public int searchFriendsChain(Map<String, List<String>> relations) {
        // Step1: initialize vertexes
        int numbers = relations.size();
        Map<String, Vertex> vertexMap = new HashMap<>();
        Iterator<Map.Entry<String, List<String>>> iterator = relations.entrySet().iterator();
        while (iterator.hasNext()) {
            String name = iterator.next().getKey();
            vertexMap.put(name, new Vertex(name));
        }

        // System.out.println(vertices);

        // Step2: initialize visited stack
        int chainsCount = 0;
        Vertex[] visitedStack = new Vertex[numbers];
        int stackPointer = -1;

        // Step3: Depth First Traversal and mark visited
        for (Map.Entry<String, Vertex> entry : vertexMap.entrySet()) {
            Vertex v = entry.getValue();
            if (!v.visited) {
                v.visited = true;
                visitedStack[++stackPointer] = v;

                // stack not empty
                while (stackPointer >= 0) {
                    // fetch top
                    Vertex topVertex = visitedStack[stackPointer];

                    boolean found = false;
                    List<String> friends = relations.get(topVertex.name);
                    for (String n : friends) {
                        if (vertexMap.containsKey(n)) {
                            Vertex rv = vertexMap.get(n);
                            if (!rv.visited) {
                                found = true;
                                rv.visited = true;
                                visitedStack[++stackPointer] = rv;
                                if (stackPointer >= 3) {
                                    chainsCount++;
                                }
                                break;
                            } else {
                                // if visited, see stack size
                                // i.e. 5 people in stack now, and duplicated again
                                // A -> B -> C -> D -> E | -> A
                                // A -> B -> C -> D is counted
                                // B -> C -> D -> E is also counted
                                // C -> D -> E -> A is not
                                // D -> E -> A -> B is not
                                // E -> A -> B -> C is not
                                if (stackPointer > 3) {
                                    // circle plus edge length's count
                                    chainsCount += 3;
                                }
                            }
                        } // if has relation 
                    }
                    if (!found) {
                        stackPointer--;
                    }
                }
            }
        }

        return chainsCount;
    }

}