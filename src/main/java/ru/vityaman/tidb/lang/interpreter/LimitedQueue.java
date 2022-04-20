package ru.vityaman.tidb.lang.interpreter;

import java.util.LinkedList;
import java.util.Queue;

public final class LimitedQueue<T> {
    private final int limit;
    private final Queue<T> queue;

    public LimitedQueue(int size) {
        limit = size;
        this.queue = new LinkedList<>();
    }

    public void add(T e) {
        if (queue.size() >= limit) {
            queue.poll();
        }
        queue.add(e);
    }

    public Instruction[] elements() {
        return queue.toArray(new Instruction[0]);
    }
}
