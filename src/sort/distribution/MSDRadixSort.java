package sort.distribution;

import java.util.Comparator;
import java.util.List;

/*
Create a class MSDRadixSort under sort.distribution extending BucketSort.

MSDRadixSort must sort the most significant digit first, the 2nd most significant digit next,
and so on. Feel free to use the code in LSDRadixSort.

Run the unit test and make sure your sorting algorithms perform accurately.

Compare the speed between LSDRadixSort and MSDRadixSort using the unit test.
Write a report about the speed comparison and save it as quiz3.pdf.
*/
public class MSDRadixSort extends RadixSort {

    public MSDRadixSort() {this(Comparator.naturalOrder());}

    public MSDRadixSort(Comparator<Integer> comparator){
        super(comparator);
    }

    @Override
    public void sort(Integer[] array, int beginIndex, int endIndex)
    {
        int maxBit = getMaxBit(array, beginIndex, endIndex)-1;
        sort(array, beginIndex, endIndex, maxBit);
    }

    private void sort(Integer[] array, int beginIndex, int endIndex, int bit)
    {
        int div = (int)Math.pow(10, bit), idx;

        for (int i=beginIndex; i<endIndex; i++)
            buckets.get(getBucketIndex(array[i], div)).add(array[i]);

        for (List<Integer> bucket : buckets)
        {
            bucket.sort(this.comparator);
            idx = beginIndex = beginIndex + bucket.size();
            while (bucket.size() > 0) array[--idx] = bucket.remove(bucket.size()-1);
        }
    }
}
