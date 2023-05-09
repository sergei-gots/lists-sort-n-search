package pro.sky.java.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DenseStringListTest {

    private DenseStringList denseStringList;

    public static Stream<Arguments> addParams() {
        return Stream.of(
                Arguments.of("6. sixth"),
                Arguments.of("7. seventh"),
                Arguments.of("8. eighth"),
                Arguments.of("9. ninth"),
                Arguments.of("10. tenth")
        );
    }

    public static Stream<Arguments> addWithIndexParams() {
        return Stream.of(
                Arguments.of(5, "5. sixth"),
                Arguments.of(4, "4. seventh"),
                Arguments.of(3, "3. eighth"),
                Arguments.of(2, "2. ninth"),
                Arguments.of(0, "1. tenth")
        );

    }

    @BeforeEach
    public void beforeEach() {
        denseStringList = new DenseStringList();
        denseStringList.add("1. first");
        denseStringList.add("2. second");
        denseStringList.add("3. third");
        denseStringList.add("4. fourth");
        denseStringList.add("5. fifth");
    }

    @Test
    void makeExpandable() {
        assertThat(denseStringList.isExpandable()).isEqualTo(false);
        denseStringList.makeExpandable();
        assertThat(denseStringList.isExpandable()).isEqualTo(true);
    }

    @ParameterizedTest
    @MethodSource("addParams")
    void add(String item) {
        int count = denseStringList.size();
        assertThat(denseStringList.add(item)).isEqualTo(item);
        assertThat(denseStringList.size()).isEqualTo(count+1);
        assertThat(denseStringList.get(count)).isEqualTo(item);
        assertThat(denseStringList.contains(item)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("addWithIndexParams")
    void add(int index, String item) {
        //GIVEN
        int count = denseStringList.size();
        List<String> toTheRight = List.of(denseStringList.toArray(index));
        //WHEN
        assertThat(denseStringList.add(index, item)).isEqualTo(item);
        //THEN
        assertThat(denseStringList.size()).isEqualTo(count+1);
        assertThat(denseStringList.get(index)).isEqualTo(item);
        assertThat(denseStringList.contains(item)).isTrue();
        assertThat(List.of(denseStringList.toArray(index+1)))
                .usingRecursiveComparison()
                .asList()
                .containsExactlyElementsOf(toTheRight);

    }

    @ParameterizedTest
    @MethodSource("addWithIndexParams")
    void set(int index, String item) {
            //GIVEN
            final int count = denseStringList.size();
            final int countExpected =  (index == count)? count+1 : count;
            //WHEN
            assertThat(denseStringList.set(index, item)).isEqualTo(item);
            //THEN
            assertThat(denseStringList.size()).isEqualTo(countExpected);
            assertThat(denseStringList.get(index)).isEqualTo(item);
            assertThat(denseStringList.contains(item)).isTrue();
    }

    @Test
    void remove() {
    }

    @Test
    void testRemove() {
    }

    @Test
    void contains() {
    }

    @Test
    void indexOf() {
    }

    @Test
    void lastIndexOf() {
    }

    @Test
    void get() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void isEmpty() {
    }

    @Test
    void clear() {
    }

    @Test
    void toArray() {
    }
}