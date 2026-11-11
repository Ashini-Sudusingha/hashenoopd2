package com.smartcity360.partD_flyweight;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Flyweight factory maintaining shared luminaire type instances.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public class LuminaireFactory {
    private static final Map<String, LuminaireType> cachePool = new HashMap<>();

    public static LuminaireType getLuminaire(String modelCode, int wattageWatts, String colorKelvin, String brandVendor) {
        String cacheKey = modelCode + "::" + wattageWatts + "::" + colorKelvin + "::" + brandVendor;
        if (!cachePool.containsKey(cacheKey)) {
            System.out.println("[LuminaireFactory] Allocating unique shared flyweight: " + modelCode + " (" + wattageWatts + "W)");
            cachePool.put(cacheKey, new LuminaireType(modelCode, wattageWatts, colorKelvin, brandVendor));
        }
        return cachePool.get(cacheKey);
    }

    public static int getCacheCount() {
        return cachePool.size();
    }

    public static Map<String, LuminaireType> getPool() {
        return Collections.unmodifiableMap(cachePool);
    }
}
