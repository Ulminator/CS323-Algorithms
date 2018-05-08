/**
 * Copyright 2015, Emory University
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package graph.path;

import graph.Graph;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;


/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class SPWikiUlmer
{
    List<String> titles;
    int[][] links;
    
    public SPWikiUlmer(InputStream inTitles, InputStream inLinks) throws Exception
    {
        titles = getTitles(inTitles);
        links  = getLinks(inLinks, titles.size());
        
        Graph g = new Graph(titles.size());
        Dijkstra d = new Dijkstra();

        Random generator = new Random();
//        int source = titles.indexOf("John_Travolta");
//        int target = titles.indexOf("Michael_Jackson");
        int source = generator.nextInt(titles.size());
        int target = generator.nextInt(titles.size());

        System.out.println("SOURCE: " +titles.get(source));
        System.out.println("TARGET: " +titles.get(target)+"\n");

        // TODO:

        //Populate the graph
        for(int i=0; i<links.length; i++){
            for(int j=0; j<links[i].length; j++){
                g.setDirectedEdge(i, links[i][j], 1);
            }
        }

        Integer[] path = d.getShortestPath(g, source, target);

        //Print the path from source to target if it exists
        if(path[source]!=null){
            while(source!=target){
                System.out.println(titles.get(source)+" ("+source+"->"+path[source]+") "+titles.get(path[source]));
                source = path[source];
            }
        }
        else{
            System.out.println("NO PATH EXISTS");
        }
    }
    
    public List<String> getTitles(InputStream in) throws Exception
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        List<String> list = new ArrayList<>();
        String line;
        
        while ((line = reader.readLine()) != null)
            list.add(line.trim());
        
        reader.close();
        return list;
    }
    
    public int[][] getLinks(InputStream in, int size) throws Exception
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        Pattern p = Pattern.compile(" ");
        int[][] array = new int[size][];
        int[] links;
        String line;
        String[] t;
        int i, j;
        
        for (i=0; (line = reader.readLine()) != null; i++)
        {
            line = line.trim();
            
            if (line.isEmpty())
                array[i] = new int[0];
            else
            {
                t = p.split(line);
                links = new int[t.length];
                array[i] = links;

                for (j=0; j<links.length; j++)
                    links[j] = Integer.parseInt(t[j]);    
            }
        }
        
        return array;
    }
    
    static public void main(String[] args) throws Exception
    {
        final String TITLE_FILE = "C:/Users/Matthew/IdeaProjects/CS323_Course_Material/src/graph/path/wiki-titles-small.txt";
        final String LINK_FILE = "C:/Users/Matthew/IdeaProjects/CS323_Course_Material/src/graph/path/wiki-links-small.txt";

        new SPWikiUlmer(new FileInputStream(TITLE_FILE), new FileInputStream(LINK_FILE));
    }
}
