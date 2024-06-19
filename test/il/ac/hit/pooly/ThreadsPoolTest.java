package il.ac.hit.pooly;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit tests for the ThreadsPool class.
 */
public class ThreadsPoolTest {

    private final int taskDelayTime = 200;
    private final int delayTime = taskDelayTime / 2;

    /**
     * Tests the submission of a single task.
     * @throws InterruptedException if the thread is interrupted while sleeping
     */
    @Test
    public void testSubmitTask() throws InterruptedException {
        ThreadsPool pool = new ThreadsPool(2);

        SimpleTask task1 = new SimpleTask(taskDelayTime);
        task1.setPriority(1);
        pool.submit(task1);

        // Allow some time for the task to be executed
        Thread.sleep(taskDelayTime + delayTime);

        assertTrue(task1.isExecuted());

        pool.shutdown();
    }

    /**
     * Tests the submission and execution of multiple tasks.
     * @throws InterruptedException if the thread is interrupted while sleeping
     */
    @Test
    public void testMultipleTasks() throws InterruptedException {
        ThreadsPool pool = new ThreadsPool(3);

        SimpleTask task1 = new SimpleTask(taskDelayTime);
        task1.setPriority(1);

        SimpleTask task2 = new SimpleTask(taskDelayTime);
        task2.setPriority(5);

        SimpleTask task3 = new SimpleTask(taskDelayTime);
        task3.setPriority(10);

        pool.submit(task1);
        pool.submit(task2);
        pool.submit(task3);

        // Allow some time for all tasks to be executed
        Thread.sleep(taskDelayTime + delayTime);

        assertTrue(task1.isExecuted());
        assertTrue(task2.isExecuted());
        assertTrue(task3.isExecuted());

        pool.shutdown();
    }

    /**
     * Tests the execution order of tasks based on priority with a single thread.
     * @throws InterruptedException if the thread is interrupted while sleeping
     */
    @Test
    public void testPriorityExecutionOrder() throws InterruptedException {
        ThreadsPool pool = new ThreadsPool(1); // Single thread to ensure execution order

        SimpleTask task1 = new SimpleTask(taskDelayTime);
        task1.setPriority(1);

        SimpleTask task2 = new SimpleTask(taskDelayTime);
        task2.setPriority(5);

        SimpleTask task3 = new SimpleTask(taskDelayTime);
        task3.setPriority(10);

        pool.submit(task1);
        // Allow some time for tasks1 to be submitted
        Thread.sleep(delayTime / 5);

        pool.submit(task2);
        // Allow some time for tasks2 to be submitted
        Thread.sleep(delayTime / 5);

        pool.submit(task3);
        // Allow some time for tasks3 to be submitted
        Thread.sleep(delayTime / 5);

        // Allow some time for tasks1 to be executed
        Thread.sleep(taskDelayTime);
        // First task that was submitted should be executed first
        // Do to occupation of the Thread
        assertTrue(task1.isExecuted());

        // Allow some time for tasks3 (Highest priority) to be executed
        Thread.sleep(taskDelayTime);
        // Highest priority should be executed Second
        // Do to release of the Thread occupation
        assertTrue(task3.isExecuted());

        // Allow some time for tasks2 (Second-highest priority) to be executed
        Thread.sleep(taskDelayTime);
        // Last task to execute
        assertTrue(task2.isExecuted());

        pool.shutdown();
    }

    /**
     * Tests the submission of tasks in a loop.
     * @throws InterruptedException if the thread is interrupted while sleeping
     */
    @Test
    public void testSubmitTasksInLoop() throws InterruptedException {
        ThreadsPool pool = new ThreadsPool(5);

        for (int i = 0; i < 100; i++) {
            SimpleTask task = new SimpleTask(taskDelayTime);
            task.setPriority(i);
            pool.submit(task);
        }

        // Allow some time for tasks to be executed
        Thread.sleep(taskDelayTime + delayTime);

        pool.shutdown();
    }

    /**
     * Tests the edge case where no tasks are submitted.
     */
    @Test
    public void testEdgeCaseNoTasksSubmitted() {
        ThreadsPool pool = new ThreadsPool(2);

        // No tasks submitted, just shutting down
        pool.shutdown();

        assertTrue(pool.isShutdown());
    }

    /**
     * Tests the submission and execution of a task with negative priority.
     * @throws InterruptedException if the thread is interrupted while sleeping
     */
    @Test
    public void testTaskWithNegativePriority() throws InterruptedException {
        ThreadsPool pool = new ThreadsPool(2);

        SimpleTask task = new SimpleTask(taskDelayTime);
        task.setPriority(-1); // Negative priority
        pool.submit(task);

        // Allow some time for the task to be executed
        Thread.sleep(taskDelayTime + delayTime);

        assertTrue(task.isExecuted());

        pool.shutdown();
    }

    /**
     * Tests the handling of exceptions within a task.
     * @throws InterruptedException if the thread is interrupted while sleeping
     */
    @Test
    public void testExceptionHandlingInTask() throws InterruptedException {
        ThreadsPool pool = new ThreadsPool(2);

        Task exceptionTask = new Task() {
            @Override
            public void perform() {
                throw new RuntimeException("Test Exception");
            }

            @Override
            public void setPriority(int level) {
                // No-op
            }

            @Override
            public int getPriority() {
                return 1;
            }
        };

        pool.submit(exceptionTask);

        // Allow some time for the task to be executed
        Thread.sleep(taskDelayTime + delayTime);

        // Since exceptions in tasks should not crash the thread pool,
        // we can check if the pool is still running
        assertFalse(pool.isShutdown());

        pool.shutdown();
    }
}
