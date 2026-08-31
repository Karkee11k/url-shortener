package projects.urlshortener.encoder;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Base62EncoderTest {

    private final Base62Encoder encoder = new Base62Encoder();

    @Test
    void shouldEncodeZero() {
        assertThat(encoder.encode(0)).isEqualTo("0");
    }

    @Test
    void shouldEncodeOne() {
        assertThat(encoder.encode(1)).isEqualTo("1");
    }

    @Test
    void shouldEncodeTen() {
        assertThat(encoder.encode(10)).isEqualTo("a");
    }

    @Test
    void shouldEncodeSixtyTwo() {
        assertThat(encoder.encode(62)).isEqualTo("10");
    }

    @Test
    void shouldEncodeSixtyThree() {
        assertThat(encoder.encode(63)).isEqualTo("11");
    }
}
