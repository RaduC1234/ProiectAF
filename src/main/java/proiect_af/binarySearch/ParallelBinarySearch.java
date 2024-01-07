package proiect_af.binarySearch;

import java.util.List;
import java.util.concurrent.*;

public class ParallelBinarySearch<T extends Comparable<T>> {

    private final List<T> array;
    private final T target;
    private final ExecutorService service;

    public ParallelBinarySearch(List<T> array, T target) {
        if (target == null) {
            throw new IllegalArgumentException("Target cannot be null.");
        }
        this.array = array;
        this.target = target;
        this.service = Executors.newCachedThreadPool();
    }

    public int search() throws InterruptedException, ExecutionException {
        try {
            Future<Integer> result = service.submit(() -> binarySearch(0, array.size() - 1));
            return result.get();
        } finally {
            service.shutdown();
            try {
                if (!service.awaitTermination(5, TimeUnit.SECONDS)) {
                    service.shutdownNow();
                }
            } catch (InterruptedException e) {
                service.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    private int binarySearch(int startIndex, int endIndex) throws InterruptedException, ExecutionException {
        if (startIndex <= endIndex) {
            int mid = startIndex + (endIndex - startIndex) / 2; // int overflow protection

            if (array.get(mid).equals(target)) {
                return mid;
            } else {
                Future<Integer> leftFuture = service.submit(() -> binarySearch(startIndex, mid - 1));
                Future<Integer> rightFuture = service.submit(() -> binarySearch(mid + 1, endIndex));

                int leftResult = leftFuture.get();
                int rightResult = rightFuture.get();

                if (leftResult != -1) {
                    return leftResult;
                } else return rightResult;
            }
        }

        return -1;
    }
}
