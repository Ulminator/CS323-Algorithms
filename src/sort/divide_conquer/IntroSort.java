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
 */
package sort.divide_conquer;

import sort.AbstractSort;

import java.util.Comparator;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class IntroSort<T extends Comparable<T>> extends QuickSort<T>
{
    private AbstractSort<T> engine;
    
    public IntroSort(AbstractSort<T> engine)
    {
        this(engine, Comparator.naturalOrder());
    }
    
    public IntroSort(AbstractSort<T> engine, Comparator<T> comparator)
    {
        super(comparator);
        this.engine = engine;
    }
    
    @Override
    public void sort(T[] array, int beginIndex, int endIndex)
    {
        final int maxdepth = getMaxDepth(beginIndex, endIndex);
        intro(array, beginIndex, endIndex, maxdepth);
    }
    
    protected void intro(T[] array, int beginIndex, int endIndex, int maxdepth)
    {
        if (beginIndex >= endIndex) return;
        
        if (maxdepth == 0)
            engine.sort(array, beginIndex, endIndex);
        else
        {
            int pivotIndex = partition(array, beginIndex, endIndex);
            intro(array, beginIndex, pivotIndex, maxdepth-1);
            intro(array, pivotIndex+1, endIndex, maxdepth-1);
        }
    }
    
    private int getMaxDepth(int beginIndex, int endIndex)
    {
        return 2 * (int)log2(endIndex - beginIndex);
    }
    
    private double log2(int i)
    {
        return Math.log(i) / Math.log(2);
    }
}
