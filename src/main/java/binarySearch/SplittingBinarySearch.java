package binarySearch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Deprecated
public class SplittingBinarySearch<T extends Comparable<T>> {

    private final List<T> array;
    private final T target;
    private final ExecutorService service;
    private final int threadPoolSize;

    public SplittingBinarySearch(List<T> array, T target, int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
        if (target == null) {
            throw new IllegalArgumentException("Target cannot be null.");
        }
        this.array = array;
        this.target = target;
        this.service = Executors.newFixedThreadPool(threadPoolSize);

    }

    public int search() throws InterruptedException, ExecutionException {
        int arraySize = array.size();
        int chunkSize = arraySize / this.threadPoolSize;

        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < this.threadPoolSize; i++) {
            int start = i * chunkSize;
            int end = Math.min((i + 1) * chunkSize, arraySize);
            futures.add(service.submit(() -> binarySearch(start, end)));
        }

        int result = -1;
        for (Future<Integer> future : futures) {
            try {
                result = future.get();
                if (result != -1) {
                    break;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        service.shutdown();
        try {
            if (!service.awaitTermination(5, TimeUnit.SECONDS)) {
                service.shutdownNow();
            }
        } catch (InterruptedException e) {
            service.shutdownNow();
            Thread.currentThread().interrupt();
        }

        return result;
    }

    private int binarySearch(int startIndex, int endIndex) throws InterruptedException, ExecutionException {
        if (startIndex <= endIndex) {
            int mid = startIndex + (endIndex - startIndex) / 2; // int overflow protection

            if (array.get(mid).equals(target)) {
                return mid;
            } else if (array.get(mid).compareTo(target) < 0) {
                return binarySearch(mid + 1, endIndex);
            } else {
                return binarySearch(startIndex, mid - 1);
            }
        }

        return -1;
    }

}
