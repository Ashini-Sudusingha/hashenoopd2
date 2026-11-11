package com.smartcity360.partA_decorator;

/**
 * Base stream decorator maintaining the wrapped inner stream.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public abstract class SecurityStreamDecorator implements TelemetryStream {
    protected final TelemetryStream innerStream;

    public SecurityStreamDecorator(TelemetryStream stream) {
        this.innerStream = stream;
    }

    @Override
    public void transmit(String payload) {
        innerStream.transmit(payload);
    }

    @Override
    public String receive() {
        return innerStream.receive();
    }
}
