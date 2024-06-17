package il.ac.hit.pooly;

/**
 * Represents a task to be executed by the ThreadsPool.
 */
public interface Task {
    /**
     * Executes the task.
     */
    public abstract void perform();

    /**
     * Sets the priority of the task.
     * @param level the priority level
     */
    public abstract void setPriority(int level);

    /**
     * Gets the priority of the task.
     * @return the priority level
     */
    public abstract int getPriority();
}
