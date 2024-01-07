package proiect_af.binarySearch;

import java.util.List;

public class BinarySearch<T extends Comparable<T>> {

    private final List<T> array;
    private final T target;

    public BinarySearch(List<T> array, T target) {
        if (target == null) {
            throw new IllegalArgumentException("Target cannot be null.");
        }
        this.array = array;
        this.target = target;
    }

    public int search() {
        return binarySearch(0, array.size() - 1);
    }

    private int binarySearch(int startIndex, int endIndex) {
        if (startIndex <= endIndex) {
            int mid = startIndex + (endIndex - startIndex) / 2; // int overflow protection

            if (array.get(mid).equals(target)) {
                return mid;
            } else {

                int leftResult = binarySearch(startIndex, mid - 1);
                int rightResult = binarySearch(mid + 1, endIndex);

                if (leftResult != -1) {
                    return leftResult;
                } else return rightResult;
            }
        }

        return -1;
    }
}
