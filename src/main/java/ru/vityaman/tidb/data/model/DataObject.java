package ru.vityaman.tidb.data.model;

import java.util.Map;

public interface DataObject {
    /**
     * @return json representation of the object
     */
    Map<String, Object> json();
    /**
     * @return string representation of the object
     */
    String repr();
}
