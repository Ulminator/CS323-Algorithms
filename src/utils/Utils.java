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
package utils;

import java.util.*;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class Utils
{
    /**
     * @param beginIndex the beginning index (inclusive).
     * @param endIndex the ending index (inclusive).
     * @return the middle index between the beginning and ending indices.
     */
    static public int getMiddleIndex(int beginIndex, int endIndex)
    {
        return beginIndex + (endIndex - beginIndex) / 2;
    }

    static public void main(String[] args)
    {
        System.out.println(getMiddleIndex(0, 10));
    }

    static public int[] getRandomIntArray(Random rand, int size)
    {
        int[] array = new int[size];

        for (int i=0; i<size; i++)
            array[i] = rand.nextInt();

        return array;
    }

    static public Integer[] getRandomIntegerArray(Random rand, int size)
    {
        return getRandomIntegerArray(rand, size, Integer.MAX_VALUE);
    }

    static public Integer[] getRandomIntegerArray(Random rand, int size, int range)
    {
        Integer[] array = new Integer[size];

        for (int i=0; i<size; i++)
            array[i] = rand.nextInt(range);

        return array;
    }

    static public Integer[] getIntegerArray(Random rand, int size, int range, Comparator<Integer> comp, double ratio){

        Integer[] array = new Integer[size];

        int shuffle = (int)(size*ratio);

        for (int i=0; i<size; i++){ array[i] = rand.nextInt(); }
        if (comp!=null){ Arrays.sort(array, comp); }
        for (int i = 0; i<shuffle; i++){ swap(array, rand.nextInt(array.length), rand.nextInt(array.length)); }

        return array;
    }

    static void swap(Integer[] array, int i, int j)
    {
        int t = array[i]; // Dummy variable
        array[i] = array[j];
        array[j] = t;
    }

    static public Integer[] getOrderedIntegerArray(Random rand, int size, int range)
    {
        Integer[] array = new Integer[size];

        for (int i=0; i<size; i++){
            array[i] = rand.nextInt(range);
        }
        Arrays.sort(array, Comparator.naturalOrder());

        return array;
    }


    static public List<Integer> getRandomIntegerList(Random rand, int size)
    {
        return getRandomIntegerList(rand, size, Integer.MAX_VALUE);
    }

    static public List<Integer> getRandomIntegerList(Random rand, int size, int range)
    {
        List<Integer> list = new ArrayList<>(size);

        for (int i=0; i<size; i++)
            list.add(rand.nextInt(range));

        return list;
    }

    static public <T>Deque<?>[] createEmptyDequeArray(int size)
    {
        Deque<?>[] deque = new ArrayDeque<?>[size];

        for (int i=0; i<size; i++)
            deque[i] = new ArrayDeque<T>();

        return deque;
    }

    static public <T>List<?>[] createEmptyListArray(int size)
    {
        List<?>[] array = new ArrayList<?>[size];

        for (int i=0; i<size; i++)
            array[i] = new ArrayList<T>();

        return array;
    }

    static public String join(long[] array, String delim)
    {
        StringBuilder build = new StringBuilder();

        for (long item : array)
        {
            build.append(delim);
            build.append(item);
        }

        return build.substring(delim.length());
    }

    static public <T>String join(List<T> list, String delim)
    {
        StringBuilder build = new StringBuilder();

        for (T item : list)
        {
            build.append(delim);
            build.append(item);
        }

        return build.length() > delim.length() ? build.substring(delim.length()) : "";
    }
}
