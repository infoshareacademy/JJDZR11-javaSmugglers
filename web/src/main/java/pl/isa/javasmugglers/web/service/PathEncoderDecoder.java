package pl.isa.javasmugglers.web.service;


import java.nio.charset.StandardCharsets;
        import java.util.Base64;

public class PathEncoderDecoder {

    public static String encodePath(Long id) {
        // Zmieniam Long id to bytes bo tego potrzebuje encoder z Base64
        byte[] idBytes = id.toString().getBytes(StandardCharsets.UTF_8);
        // szyfruje idBytes wykorzystując Base64
        String encodedValue = Base64.getUrlEncoder().encodeToString(idBytes);

        return encodedValue;
    }

    public static Long decodePath(String encodedValue) {
        byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedValue);
        String decodedIdString = new String(decodedBytes, StandardCharsets.UTF_8);
        Long decodedIdLong = Long.parseLong(decodedIdString);

        return decodedIdLong;
    }


}
