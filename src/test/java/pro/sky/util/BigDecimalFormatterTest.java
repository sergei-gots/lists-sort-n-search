package pro.sky.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BigDecimalFormatterTest {

    public static Stream<Arguments> test_format_params() {
        return Stream.of(
                Arguments.of(0, "0"),
                Arguments.of(10, "10"),
                Arguments.of(123, "123"),
                Arguments.of(1_234, "1 234"),
                Arguments.of(12_345, "12 345"),
                Arguments.of(123_456, "123 456"),
                Arguments.of(1_234_567, "1 234 567"),
                Arguments.of(12_345_678, "12 345 678"),
                Arguments.of(123_456_789, "123 456 789"),
                Arguments.of(1_234_567_890, "1 234 567 890")
        );
    }

    @ParameterizedTest
    @MethodSource("test_format_params")
    void test_format(long number, String expected) {
        assertThat(BigDecimalFormatter.format(number)).isEqualTo(expected);
    }
}