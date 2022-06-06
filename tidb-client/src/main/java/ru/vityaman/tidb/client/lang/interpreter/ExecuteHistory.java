package ru.vityaman.tidb.client.lang.interpreter;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Queue that contains no more than given number
 * of elements. If you add element to full queue
 * then the oldest element will be deleted.
 */
public final class ExecuteHistory {
    private final int limit;
    private final Queue<Instruction> queue;

    public ExecuteHistory(int size) {
        limit = size;
        this.queue = new LinkedList<>();
    }

    public void add(Instruction e) {
        if (queue.size() >= limit) {
            queue.poll();
        }
        queue.add(e);
    }

    public Instruction[] lastNInstructions() {
        return queue.toArray(new Instruction[0]);
    }
}
