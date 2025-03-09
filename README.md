# Concurrent Programming - Thread Pool with Priority

## Overview

This project was developed as part of the **Concurrent Programming** course.

This project implements a **Thread Pool with Priority**, designed to efficiently manage and execute tasks in a **multi-threaded environment**. Tasks can be submitted with different priority levels, ensuring that more important tasks are executed before lower-priority ones.

## Features

- **Priority-based task execution** – tasks with higher priority are executed first.
- **Thread pool management** – a fixed number of threads handle the task execution.
- **Safe concurrent execution** – utilizes Java's `PriorityBlockingQueue` for thread-safe task management.
- **Graceful shutdown** – allows stopping the thread pool safely.
- **Unit tests** – verifies functionality and correctness.

## Technologies Used

- **Java** – main programming language.
- **Java Concurrency API** – including `ExecutorService`, `ThreadPoolExecutor`, and `PriorityBlockingQueue`.
- **JUnit** – for unit testing.
- **AtomicBoolean** – for thread-safe task execution verification.

## Example Usage

```java
ThreadsPool pool = new ThreadsPool(3);

SimpleTask task1 = new SimpleTask(1000);
task1.setPriority(5);

SimpleTask task2 = new SimpleTask(500);
task2.setPriority(10);

pool.submit(task1);
pool.submit(task2);

pool.shutdown();
```

In this example, `task2` will execute before `task1` because it has a **higher priority**.
