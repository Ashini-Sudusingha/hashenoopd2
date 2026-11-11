package com.smartcity360.partA_decorator;

/**
 * Core stream abstraction for IoT energy telemetry.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public interface TelemetryStream {
    void transmit(String payload);
    String receive();
}
