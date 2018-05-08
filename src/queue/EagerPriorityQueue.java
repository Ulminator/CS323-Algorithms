/*
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
 */
package queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class EagerPriorityQueue<T extends Comparable<T>> extends AbstractPriorityQueue<T>
{
    private List<T> keys;
    
    public EagerPriorityQueue()
    {
        this(Comparator.naturalOrder());
    }
    
    public EagerPriorityQueue(Comparator<T> comparator)
    {
        super(comparator);
        keys = new ArrayList<>();
    }

    @Override
    public int size()
    {
        return keys.size();
    }

    @Override
    public void add(T key)
    {
        // binary search the element in the list (if not found, index < 0)
        int index = Collections.binarySearch(keys, key, comparator);
        
        // if element not found, the appropriate insert index = -(index +1)
        if (index < 0) index = -(index + 1); //No elements present
        
        keys.add(index, key);
    }

    @Override
    protected T removeAux()
    {
        return keys.remove(keys.size()-1);
    }
}
