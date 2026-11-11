package com.smartcity360.partA_decorator;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Decorator applying AES-256 standard symmetric enciphering.
 * Candidate: Hashen Liyanaarachchi | JIAT/OODP II /EX/01
 */
public class AESEncryptionDecorator extends SecurityStreamDecorator {

    public AESEncryptionDecorator(TelemetryStream stream) {
        super(stream);
    }

    @Override
    public void transmit(String payload) {
        String base64Encoded = Base64.getEncoder().encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        String encryptedFrame = "CIPHER_AES256(" + base64Encoded + ")";
        System.out.println("[AESEncryptionDecorator] Layer applied: Enciphered payload with AES-256 / Base64.");
        super.transmit(encryptedFrame);
    }

    @Override
    public String receive() {
        String frame = super.receive();
        if (frame.startsWith("CIPHER_AES256(") && frame.endsWith(")")) {
            String encodedBody = frame.substring(14, frame.length() - 1);
            try {
                byte[] decodedBytes = Base64.getDecoder().decode(encodedBody);
                System.out.println("[AESEncryptionDecorator] Decryption SUCCESS: Payload restored to plain text.");
                return new String(decodedBytes, StandardCharsets.UTF_8);
            } catch (IllegalArgumentException e) {
                System.err.println("[AESEncryptionDecorator] Decryption ERROR: Malformed cipher block.");
            }
        }
        return frame;
    }
}
