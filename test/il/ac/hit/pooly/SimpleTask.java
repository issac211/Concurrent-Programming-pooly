package il.ac.hit.pooly;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A simple implementation of Task for testing purposes.
 */
public class SimpleTask implements Task {
    private int priority;
    private final AtomicBoolean executed;

    public SimpleTask() {
        this.executed = new AtomicBoolean(false);
    }

    @Override
    public void perform() {
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
