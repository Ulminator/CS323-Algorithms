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
public class BinaryHeap<T extends Comparable<T>> extends AbstractPriorityQueue<T>
{
    private List<T> keys;
    private int size;
    
    public BinaryHeap()
    {
        this(Comparator.naturalOrder());
    }
    
    public BinaryHeap(Comparator<T> comparator)
    {
        super(comparator);
        keys = new ArrayList<>();
        keys.add(null);    // initialize the first item as null
        size = 0;
    }

    public List<T> getKeys()
    {
        return keys;
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public void add(T key)
    {
        keys.add(key);
        swim(++size);
    }

    @Override
    protected T removeAux()
    {
        Collections.swap(keys, 1, size);
        T max = keys.remove(size--);
        sink(1);
        return max;
    }
    
    private void swim(int k)
    {
        while (1 < k && comparator.compare(keys.get(k/2), keys.get(k)) < 0)
        {
            Collections.swap(keys, k/2, k);
            k /= 2;
        }
    }

    private void sink(int k)
    {
        for (int i=k*2; i<=size; k=i,i*=2)
        {
            if (i < size && comparator.compare(keys.get(i), keys.get(i+1)) < 0) i++;
            if (comparator.compare(keys.get(k), keys.get(i)) >= 0) break;
            Collections.swap(keys, k, i);
        }
    }
}
