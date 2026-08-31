package projects.urlshortener.encoder;

public class Base62Encoder {
    private static final String ALPHABET =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = ALPHABET.length(); // 62

    public String encode(long value) {
        if (value == 0) {
            return "0";
        }

        var builder = new StringBuilder();
        while (value > 0) {
            var reminder = (int) (value % 62);
            builder.append(ALPHABET.charAt(reminder));
            value /= BASE;
        }

        return builder.reverse().toString();
    }
}
