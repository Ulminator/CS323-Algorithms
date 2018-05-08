package sort.comparison;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ShellSortPratt<T extends Comparable<T>> extends ShellSort<T>
{

    public ShellSortPratt()
    {
        this(Comparator.naturalOrder());
    }

    public ShellSortPratt(Comparator<T> comparator)
    {
        this(comparator, 1000);
    }

    public ShellSortPratt(Comparator<T> comparator, int n)
    {
        super(comparator, n);
    }

    @Override
    protected void populateSequence(int n)
    {
        if (sequence.isEmpty()){
            int last2ind = 0;
            int last3ind = 0;
            sequence.add(1);

            for (int i=0; ; i++)
            {
                if (sequence.get(i)>n) // Checks if most recently added number is larger than the max size of list
                {
                    sequence.remove(i);
                    break;
                }

                if (sequence.get(last2ind)*2 < sequence.get(last3ind)*3)
                {
                    sequence.add(sequence.get(last2ind)*2);
                    last2ind+=1;
                }
                else if (sequence.get(last2ind)*2 > sequence.get(last3ind)*3)
                {
                    sequence.add(sequence.get(last3ind)*3);
                    last3ind+=1;
                }
                else
                {
                    sequence.add(sequence.get(last2ind)*2);
                    last2ind+=1;
                    last3ind+=1;
                }
            }
            //System.out.println(sequence.size());
        }
        else {
            sequence = new ArrayList<>();
            populateSequence(n);
        }
    }

    @Override
    protected int getSequenceStartIndex(int n)
    {
        int index = 12;
//        int index = Collections.binarySearch(sequence, n/3);
//        if (index < 0) index = -(index+1);
//        if (index == sequence.size()) index--;
        return index;
    }

}
