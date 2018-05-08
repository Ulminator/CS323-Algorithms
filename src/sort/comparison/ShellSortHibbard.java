package sort.comparison;

import sort.AbstractSort;

import java.util.Collections;
import java.util.Comparator;

public class ShellSortHibbard<T extends Comparable<T>> extends ShellSort<T>
{

    public ShellSortHibbard()
    {
        this(Comparator.naturalOrder());
    }  // this refers to the next constructor

    public ShellSortHibbard(Comparator<T> comparator)
    {
        this(comparator, 1000);
    }   // refers to next constructor

    public ShellSortHibbard(Comparator<T> comparator, int n)
    {
        super(comparator, n);
    } // refers to super constructor

    @Override
    protected void populateSequence(int n)
    {
        for (int k=0; ; k++)
        {
            int h = (int) Math.pow(2, k) - 1;
            if (h <= n) sequence.add(h);
            else break;
        }
    }

    @Override
    protected int getSequenceStartIndex(int n)
    {
        int index = 7;
        return index;
    }
}
