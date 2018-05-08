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
package sort.hybrid;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class HybridSortTest
{
    private Random rand = new Random();
    
    @Test
    void testAccuracy()
    {
        HybridSort<Integer> choi = new HybridSortChoi<>();
        HybridSort<Integer> mine = new HybridSortUlmer<>();
        
        Integer[][] input = {{0,1,2,3},{7,6,5,4},{0,3,1,2},{4,7,6,5},{9,8,11,10}};
        testAccuracy(input, choi, mine); // Standard accuracy check

        for (int row=10; row<=20; row++)
            for (int col=10; col<=20; col++)
                for (int i=0; i<100; i++)
                    testAccuracy(randomInput(row, col, 0.25), choi, mine);
    }

    private void testAccuracy(Integer[][] input, HybridSort<Integer> choi, HybridSort<Integer> mine)
    {
        Integer[] gold = choi.sort(copyOf(input));
        Integer[] auto = mine.sort(input);
        assertArrayEquals(gold, auto);
    }
    
    @Test
    @SuppressWarnings("unchecked")
    void testSpeed()
    {
        int row = 100, col = 100;
        double ratio = 0.25;
        
        HybridSort<Integer> choi = new HybridSortChoi<>();
        HybridSort<Integer> mine = new HybridSortUlmer<>();


        for (col=100; col<=3000; col+=100)
//        for (row=100; row<=3000; row+=100)
        {
            long[] time = testSpeed(row, col, ratio, choi, mine);
            StringJoiner join = new StringJoiner("\t");
            join.add(String.format("Row: %d, Col: %d, ratio: %4.2f", row, col, ratio));
            for (long t : time) join.add(Long.toString(t));
            System.out.println(join.toString());
        }
    }
    
    /**
     * @param row the row size of the input.
     * @param col the column size of the input.
     * @param ratio the ratio of the input to be shuffled (for the 3rd and 4th cases).
     */
    @SuppressWarnings("unchecked")
    private long[] testSpeed(int row, int col, double ratio, HybridSort<Integer>... engine)
    {
        long[] time = new long[engine.length];
        final int warm = 10, iter = 100;
        Integer[][] input, t;
        long st, et;
        
        for (int i=0; i<warm; i++)
        {
            input = randomInput(row, col, ratio);

            for (HybridSort<Integer> anEngine : engine)
                anEngine.sort(copyOf(input));
        }
        
        for (int i=0; i<iter; i++)
        {
            input = randomInput(row, col, ratio);
            
            for (int j=0; j<engine.length; j++)
            {
                t = copyOf(input);
                st = System.currentTimeMillis();
                engine[j].sort(t);
                et = System.currentTimeMillis();
                time[j] += et - st;    
            }
        }
        
        return time;
    }

    private Integer[][] copyOf(Integer[][] input)
    {
        Integer[][] copy = new Integer[input.length][];
        
        for (int i=0; i<input.length; i++)
            copy[i] = Arrays.copyOf(input[i], input[i].length);
        
        return copy;
    }

    private Integer[][] randomInput(int row, int col, double ratio)
    {
        Integer[][] input = new Integer[row][];
        
        for (int i=0; i<row; i++)
            input[i] = randomArray(col, ratio); // Input[i] is a row of the subarray
        
        return input; // Returns a 2D subarray to add to the main array
    }

    //Generates a random number between 0-4 and handles them as different cases. These cases represent the types
    // of ordering in the rows of the 2D array.
    private Integer[] randomArray(int size, double ratio)
    {
        switch (rand.nextInt(5))
        {
        case 0: return randomArray(size, 0, Comparator.naturalOrder());
        case 1: return randomArray(size, 0, Comparator.reverseOrder());
        case 2: return randomArray(size, ratio, Comparator.naturalOrder());
        case 3: return randomArray(size, ratio, Comparator.reverseOrder());
        case 4: return randomArray(size, 0, null);
        default: throw new IllegalArgumentException();
        }
    }

    private Integer[] randomArray(int size, double ratio, Comparator<Integer> comparator)
    {
        Integer[] array = new Integer[size];
        int shuffle = (int)(size*ratio);
        
        for (int i=0; i<size; i++) array[i] = rand.nextInt(size); //Generate random integers for the number of columns
        if (comparator != null) Arrays.sort(array, comparator); //Sort according to the comparator in question
        
        for (int i=0; i<shuffle; i++) // If shuffle != 0, then swap that percent of items in the sorted array
            swap(array, rand.nextInt(array.length), rand.nextInt(array.length));
        
        return array;
    }

    private void swap(Integer[] array, int i, int j)
    {
        int t = array[i]; // Dummy variable
        array[i] = array[j];
        array[j] = t;
    }
}
