package com.smartcity360.partE_interpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * Context encapsulating live municipal environmental readings.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public class UrbanRuleContext {
    private final Map<String, Object> dataStore = new HashMap<>();

    public void setContextParameter(String key, Object value) {
        dataStore.put(key, value);
    }

    public Object getContextParameter(String key) {
        return dataStore.get(key);
    }

    public int getIntValue(String key) {
        Object val = dataStore.get(key);
        return (val instanceof Number) ? ((Number) val).intValue() : 0;
    }

    public double getDoubleValue(String key) {
        Object val = dataStore.get(key);
        return (val instanceof Number) ? ((Number) val).doubleValue() : 0.0;
    }

    public boolean hasKey(String key) {
        return dataStore.containsKey(key);
    }
}
