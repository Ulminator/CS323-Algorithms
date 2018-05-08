package sort.hybrid;

import sort.AbstractSort;
import sort.comparison.ShellSortKnuth;
import sort.divide_conquer.QuickSort;
import java.lang.reflect.Array;

/**
 * @author Matt D. Ulmer ({@code mulmer@emory.edu})
 */
public class HybridSortUlmer<T extends Comparable<T>> implements HybridSort<T> {

    private AbstractSort<T> engine1;
    private AbstractSort<T> engine2;

    public HybridSortUlmer()
    {
        engine1 = new ShellSortKnuth<>(); //Descending
        engine2 = new QuickSort<T>(); //Mostly Ascending, Mostly Descending, Random
    }

    @Override
    @SuppressWarnings("unchecked")
    /**
     * @param input the 2D array to be sorted by ascending order into a flat 1D array.
     */
    public T[] sort(T[][] input)
    {
        for (T[] t : input) {
            int type = determineArrayType(t);
            if (type == 1) { engine1.sort(t); }
            else if (type == 2) { engine2.sort(t); }
        }
        MergeKSortedArrays engine3 = new MergeKSortedArrays(input.length, input[0].length);
        return engine3.merge(input);
    }

    /**
     * @param array the 1D array to be labeled as sorted (0), reverse ordered (1), or other (3)
     */
    public int determineArrayType(T[] array){
        int type;
        int length = array.length;
        int ascending_count = 0;
        int descending_count = 0;

        for (int i=0; i<length-1; i++){
            if (array[i].compareTo(array[i+1]) <= 0) { ascending_count+=1; }
            if (array[i].compareTo(array[i+1]) >= 0) { descending_count+=1; }
        }

        if (ascending_count==length-1){ type = 0; }
        else if (descending_count==length-1){ type = 1; }
        else { type = 2; }

        return type;
    }

    public class MergeKSortedArrays {

        int rows; int cols; int position;
        HeapNode[] Heap;
        T[] result;

        /**
         * Uses a min heap to sort a 2D array with already sorted rows.
         * @param K the number of rows.
         * @param N the number of columns.
         */
        MergeKSortedArrays(int K, int N){
            rows = K; cols = N; position = 0;
            Heap = getArray(HeapNode.class, K+1);
            Heap[position] = new HeapNode(null, -1);
        }

        <T> T[] getArray(Class<T> cls, int size){
            @SuppressWarnings("unchecked")
            T[] array = (T[]) Array.newInstance(cls, size);
            return array;
        }

        /**
         * @param input the 2D array to be sorted.
         * @return the sorted 1D array of all elements from the input.
         */
        T[] merge(T[][] input){
            int total = rows * cols;
            result = (T[]) Array.newInstance(input[0][0].getClass(), total);

            int count = 0;
            int[] pointers = new int[rows];

            for (int i=0; i<pointers.length; i++){
                pointers[i] = 0; //Pointers looking at start of each row
            }

            for (int i=0; i<rows; i++){
                if (pointers[i] < cols){
                    insert(input[i][pointers[i]], i, true);
                }
            }

            while (count < total){
                HeapNode min = remove();  //Extract node at root off the heap
                result[count] = min.value;  //Insert min value into solution array
                pointers[min.row]++;  //Move pointer across row where removed item came from.

//                if (count+1 == total){ count += 1; }//GET OUT!!!!

                if (pointers[min.row] < cols){
                    insert(input[min.row][pointers[min.row]], min.row, true);
                } else{
                    insert(input[0][0], -1, false);
                } count += 1;
            }

            return result;
        }

        HeapNode remove(){
            int finishedRows = 0;
            for (HeapNode node: Heap){
                if (node.value == null){
                    continue;
                }
                if (!node.finite){
                    finishedRows += 1;
                }
            }
            HeapNode min = Heap[1];
            Heap[1] = Heap[position-finishedRows-1];
            Heap[position -1] = null;
            position--;
            sink(1);
            return min;
        }

        void sink(int k){
            int smallest = k;
            if (2 * k < position && Heap[2 * k].value.compareTo(Heap[smallest].value) < 0){// && Heap[2 * k].finite){ //Check left child
                smallest = 2 * k;
            }
            if (2 * k + 1 < position && Heap[2 * k + 1].value.compareTo(Heap[smallest].value) < 0){// && Heap[2 * k + 1].finite) { //Check right child
                smallest = 2 * k + 1;
            }
            if (smallest != k){
                swap(k, smallest);
                sink(smallest);
            }
        }

        void swap(int k, int smallest){
            HeapNode temp = Heap[k];
            Heap[k] = Heap[smallest];
            Heap[smallest] = temp;
        }

        void insert(T value, int row, boolean finite) {
            if (position == 0){
                Heap[position + 1] = new HeapNode(value, row);
                position = 2;
            }
            else{
                if (finite){
                    Heap[position++] = new HeapNode(value, row);
                    swim();
                }
                else {
                    Heap[position] = new HeapNode();
                }
            }
        }

        void swim(){
            int pos = position-1;
            if (pos == 1){
                return;
            }
            while (pos > 1 && Heap[pos].value.compareTo(Heap[pos/2].value) < 0 ){
                HeapNode temp = Heap[pos];
                Heap[pos] = Heap[pos/2];
                Heap[pos/2] = temp;
                pos = pos/2;
            }
        }
    }

    public class HeapNode{
        T value;
        int row;
        boolean finite;

        /**
         * @param value a value in the 2D array
         * @param row the row the value came from
         */
        HeapNode(T value, int row){
            this.value = value;
            this.row = row;
            this.finite = true;
        }

        /**
         * Placeholder nodes. When a row is finished it takes it substitutes for that row
         * after the last non placeholder node.
         */
        HeapNode(){
            this.finite = false;
        }
    }
}