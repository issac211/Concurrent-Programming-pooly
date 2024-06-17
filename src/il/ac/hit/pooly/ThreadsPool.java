package il.ac.hit.pooly;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Manages a pool of threads to execute tasks based on priority.
 */
public class ThreadsPool {
    final ExecutorService executor;
    private final PriorityBlockingQueue<Task> taskQueue;

    /**
     * Constructs a ThreadsPool with a specified number of threads.
     * @param numberOfThreads the number of threads in the pool
     */
    public ThreadsPool(int numberOfThreads) {
        this.executor = Executors.newFixedThreadPool(numberOfThreads);
        this.taskQueue = new PriorityBlockingQueue<>
        (11, (task1, task2) -> task2.getPriority() - task1.getPriority());
        start();
    }

    /**
     * Submits a task to the pool.
     * @param task the task to be executed
     */
    public void submit(Task task) {
        taskQueue.put(task);
    }

    private void start() {
        for (int i = 0; i < ((ThreadPoolExecutor) executor).getCorePoolSize(); i++) {
            executor.submit(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        Task task = taskQueue.take();
                        task.perform();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    /**
     * Shuts down the thread pool.
     */
    public void shutdown() {
        executor.shutdownNow();
    }
}
