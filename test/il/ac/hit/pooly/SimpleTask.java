package il.ac.hit.pooly;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A simple implementation of Task for testing purposes.
 */
public class SimpleTask implements Task {
    private int priority;
    private final int delayTime;
    private final AtomicBoolean executed;

    public SimpleTask(int delayTime) {
        this.executed = new AtomicBoolean(false);
        this.delayTime = delayTime;
    }

    @Override
    public void perform() {
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        executed.set(true);
    }

    @Override
    public void setPriority(int level) {
        this.priority = level;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    public boolean isExecuted() {
        return executed.get();
    }
}
