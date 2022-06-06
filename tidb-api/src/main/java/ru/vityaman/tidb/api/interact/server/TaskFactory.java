package ru.vityaman.tidb.api.interact.server;

import java.io.ObjectInputStream;

public interface TaskFactory {
    Runnable createFrom(ObjectInputStream stream);
}
