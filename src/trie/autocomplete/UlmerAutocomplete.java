/**
 * Copyright 2014, Emory University
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
 *
 * THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - Matt Ulmer
 */
package trie.autocomplete;

import trie.Trie;
import trie.TrieNode;

import java.util.*;


/**
 * @author Matt Ulmer ({@code mulmer@emory.edu})
 */
public class UlmerAutocomplete extends Trie<List<String>> implements IAutocomplete<List<String>>
{

    /**
     * @param prefix the prefix of candidate words to return.
     * @return the list of candidate words for the specific prefix.
     */
    @Override
    public List<String> getCandidates(String prefix)
    {
        prefix = prefix.trim();
        TrieNode<List<String>>
                node = this.find(prefix);
        if (node==null){
//            System.out.println("PREFIX NOT IN DICT");
            return new ArrayList<>();
        }
//        System.out.println(node.getValue());/////////////////////////////////////////////////
        List<String> candidates = new ArrayList<>(); //Initial empty list
        if (node.getValue() != null){  //Check if prefix node has values
            candidates.addAll(node.getValue());  //If so, add them all to the list
            if (candidates.size() == 20){  //If there are 20, you're done. Just return those.
                return candidates;
            }
        }
        Queue<TrieNode<List<String>>> nodeQ = new LinkedList<>();
        nodeQ.add(node);  //Add prefix node as first element of queue
//        List<Character> keys = new ArrayList<>(node.getChildrenMap().keySet());
//        keys.sort(Comparator.naturalOrder());
//        for (int i = 0; i < keys.size(); i++) {
//            nodeQ.add(node.getChild(keys.get(i)));
//        }
        //Breadth first search for words
        while (!nodeQ.isEmpty()) {
            TrieNode<List<String>> current = nodeQ.remove();
            List<Character> keys = new ArrayList<>(current.getChildrenMap().keySet());  //Next potential characters for words
            keys.sort(Comparator.naturalOrder());  //Orders the n-th current characters for potential words
            for (int i = 0; i < keys.size(); i++) {
                nodeQ.add(current.getChild(keys.get(i)));  //Add all potential TrieNodes to queue.
            }
            //If it is a word
            if (current.isEndState()) {
                String word = getString(current);  //CALLS getString
                if (!candidates.contains(word)) {  //If candidates does not have the word, add it
                    candidates.add(word);
                    if (candidates.size()==20){ //List is max size
                        return candidates;
                    }
                }
            }
        }
//        System.out.println("NOT ENOUGH POSSIBLE WORDS");
        return candidates;
    }

    /**
     * @param prefix the prefix.
     * @param candidate the selected candidate for the prefix.
     */
    @Override
    public void pickCandidate(String prefix, String candidate) {
        //Remove spaces around prefix & candidate
        prefix = prefix.trim();
        candidate = candidate.trim();

        //If prefix node not in trie - add it
        TrieNode<List<String>> node = find(prefix);
        if (node == null){
            node = add(prefix);
        }
        //Otherwise check if it is recognized as the end of a word - if not make it so
        else{
            if (!node.isEndState()){
                node.setEndState(true);
            }
        }

        //If it does not have a value list, initialize it.
        if (!node.hasValue()) {
            List<String> list = new ArrayList<>();
            node.setValue(list);
        }

        List<String> val_list = node.getValue();

        // If not list does not contain candidate, add candidate to list and then check if it exceeds max length.
        if(!val_list.contains(candidate)){
            val_list.add(0, candidate);
            if(val_list.size()==21){   //Check to make sure that list does not exceed 20. Remove least recent if so.
                val_list.remove(20);
            }
        }
        else{
            //If not already in the first position, move to first and slide rest down
            if(!val_list.get(0).equals(candidate)){
                int index = val_list.indexOf(candidate);
                val_list.remove(index);
                val_list.add(0, candidate);
//                Collections.swap(val_list, 0, val_list.indexOf(candidate));
            }
        }
        //If candidate node not in trie - add it
        node = find(candidate);
        if (node == null)
        {
            add(candidate);
        }
        //Otherwise check if it is recognized as the end of a word - if not make it so
        else{
            if (!node.isEndState()){
                node.setEndState(true);
            }
        }
    }

    /**
     * @param node the end of a relevant word
     * @return the word itself
     */
    private String getString(TrieNode<List<String>> node){
        StringBuilder word = new StringBuilder();
        while(node.getParent()!=null){
            word.append(node.getKey());
            node = node.getParent();
        }
        return word.reverse().toString();
//        String reverse = Character.toString(node.getKey());
//        while (node.getParent()!=null){
//            node = node.getParent();
//            reverse += node.getKey();
//        }
//        return new StringBuilder(reverse).reverse().toString().trim();
    }

    /**
     * @param word that is being added to the trie.
     * @return the node in the trie that represents the last character of the word.
     */
    private TrieNode<List<String>> add(String word){
        TrieNode<List<String>> node = this.getRoot();
        char[] array = word.toCharArray();
        int i, len = word.length();
        for (i=0; i<len; i++){
            if (node.getChild(array[i]) != null){
                node = node.getChild(array[i]);
            }
            else{
                node = node.addChild(array[i]);
//                node = node.addChild(array[i]);
            }
            if (i == len-1){
                node.setEndState(true);
//                System.out.println("STATE SET");
            }
        }
        return node;
    }
}
