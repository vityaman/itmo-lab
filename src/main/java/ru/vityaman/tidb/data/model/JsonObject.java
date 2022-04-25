package ru.vityaman.tidb.data.model;

import java.util.Map;

public interface JsonObject {
    /**
     * @return json representation of the object
     */
    Map<String, Object> json();
}
