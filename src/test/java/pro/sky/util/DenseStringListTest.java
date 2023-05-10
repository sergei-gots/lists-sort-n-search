package pro.sky.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pro.sky.util.exception.*;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

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

    public static Stream<Arguments> removeItemParams() {
        return itemParams();
    }

    public static Stream<Arguments> removeByIndexParams() {
        return indexAndItemParams();
    }

    public static Stream<Arguments> indexAndItemParams() {
        return Stream.of(
                Arguments.of(0, "1. first"),
                Arguments.of(1, "2. second"),
                Arguments.of(2, "3. third"),
                Arguments.of(3, "4. fourth"),
                Arguments.of(4, "5. fifth")
        );
    }

    public static Stream<Arguments> itemParams() {
        return Stream.of(
                Arguments.of("1. first"),
                Arguments.of("2. second"),
                Arguments.of("3. third"),
                Arguments.of("4. fourth"),
                Arguments.of("5. fifth")
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

    @ParameterizedTest
    @MethodSource("removeItemParams")
    void remove(String item) {
        //GIVEN
        final int count = denseStringList.size();
        //WHEN
        assertThat(denseStringList.remove(item)).isEqualTo(item);
        //THEN
        assertThat(denseStringList.size()).isEqualTo(count-1);
        assertThat(denseStringList.contains(item)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("removeByIndexParams")
    void remove(int index, String expectedItem) {
        //GIVEN
        final int count = denseStringList.size();
        //WHEN
        assertThat(denseStringList.remove(index)).isEqualTo(expectedItem);
        //THEN
        assertThat(denseStringList.size()).isEqualTo(count-1);
        assertThat(denseStringList.contains(expectedItem)).isFalse();
    }


    @ParameterizedTest
    @MethodSource("itemParams")
    void contains(String item) {
        assertThat(denseStringList.contains(item)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("indexAndItemParams")
    void indexOf(int expectedIndex, String item) {
        assertThat(denseStringList.indexOf(item)).isEqualTo(expectedIndex);
    }

    @Test
    void lastIndexOf() {
        denseStringList.add(4, "1. first");
        denseStringList.add(6, "2. second");
        denseStringList.add(0,"3. third");
        //GIVEN:     "3" "1" "2" "3" "4" "1" "5" "2"
        // indices:   0   1   2   3   4   5   6   7
        assertThat(denseStringList.size()).isEqualTo(8);

        assertThat(denseStringList.lastIndexOf("1. first")).isEqualTo(5);
        assertThat(denseStringList.lastIndexOf("2. second")).isEqualTo(7);
        assertThat(denseStringList.lastIndexOf("3. third")).isEqualTo(3);
        assertThat(denseStringList.lastIndexOf("4. fourth")).isEqualTo(4);
        assertThat(denseStringList.lastIndexOf("5. fifth")).isEqualTo(6);

        String itemEleventh = "11. eleventh";
        assertThat(denseStringList.contains(itemEleventh)).isFalse();
        assertThat(denseStringList.lastIndexOf(itemEleventh)).isEqualTo(-1);

    }

    @ParameterizedTest
    @MethodSource("indexAndItemParams")
    void get(int index, String expectedItem) {
        assertThat(denseStringList.get(index)).isEqualTo(expectedItem);
    }

    @Test
    void testEquals() {
        DenseStringList stringListToCompare = new DenseStringList(denseStringList);
        assertThat(denseStringList.equals(stringListToCompare)).isTrue();
        stringListToCompare.add("1. first");
        assertThat(denseStringList.equals(stringListToCompare)).isFalse();
        stringListToCompare.remove(stringListToCompare.lastIndexOf("1. first"));
        assertThat(denseStringList.equals(stringListToCompare)).isTrue();
        stringListToCompare.remove(stringListToCompare.lastIndexOf("1. first"));
        assertThat(denseStringList.equals(stringListToCompare)).isFalse();
        stringListToCompare.add(0, ("1. first"));
        assertThat(denseStringList.equals(stringListToCompare)).isTrue();
        stringListToCompare.set(0, ("0. Not first:)"));
        assertThat(denseStringList.equals(stringListToCompare)).isFalse();
        stringListToCompare.set(0, ("1. first"));
        assertThat(denseStringList.equals(stringListToCompare)).isTrue();
    }

    @Test
    void isEmpty() {
        for (int i =denseStringList.size(); i > 0; i--) {
            assertThat(denseStringList.isEmpty()).isFalse();
            denseStringList.remove(0);
            assertThat(denseStringList.size()).isEqualTo(i-1);
        }
        assertThat(denseStringList.isEmpty()).isTrue();
        denseStringList.add("1. first");
        assertThat(denseStringList.size()).isEqualTo(1);
        assertThat(denseStringList.isEmpty()).isFalse();
    }

    @Test
    void clear() {
        //GIVEN
        assertThat(denseStringList.isEmpty()).isFalse();
        assertThat(denseStringList.size()).isEqualTo(5);
        //WHEN
        denseStringList.clear();
        //THEN
        assertThat(denseStringList.isEmpty()).isTrue();
        assertThat(denseStringList.size()).isEqualTo(0);
    }

    @Test
    void toArray() {
        assertThat(List.of(denseStringList.toArray()))
                .usingRecursiveComparison()
                .asList()
                .containsExactlyElementsOf(List.of(
                        "1. first",
                        "2. second",
                        "3. third",
                        "4. fourth",
                        "5. fifth")
                );
    }

    @Test
    void should_throwIllegalArgumentExceptionWithMessage_when_capacityIsLessThan0 () {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()->new DenseStringList(-1))
                .withMessage("The capacity should be a natural number");
    }

    @Test
    void should_add_when_capacityIs0_and_expandableIsTrue() {
        DenseStringList dsList = new DenseStringList(0, true);
        assertThat(dsList.size()).isEqualTo(0);
        assertThat(dsList.isEmpty()).isTrue();
        assertThat(dsList.add("1. first")).isEqualTo("1. first");
        assertThat(dsList.size()).isEqualTo(1);
        assertThat(dsList.isEmpty()).isFalse();
    }

    @Test
    void should_throwStringListIsFullException_when_capacityIs0_and_expandableIsFalse() {
        DenseStringList dsList = new DenseStringList(0, false);
        assertThat(dsList.size()).isEqualTo(0);
        assertThat(dsList.isEmpty()).isTrue();
        assertThatExceptionOfType(StringListIsFullException.class)
                .isThrownBy(()->dsList.add("1. first"));

    }

    @Test
    void should_throwStringListIsFullException_when_capacityIs0_and_expandableIsNoPassedToConstructor() {
        DenseStringList dsList = new DenseStringList(0);
        assertThat(dsList.size()).isEqualTo(0);
        assertThat(dsList.isEmpty()).isTrue();
        assertThatExceptionOfType(StringListIsFullException.class)
                .isThrownBy(()->dsList.add("1. first"));

    }

    @Test
    void disableExpandable() {
        assertThat(denseStringList.isExpandable()).isFalse();
        denseStringList.makeExpandable();
        assertThat(denseStringList.isExpandable()).isTrue();
        denseStringList.disableExpandable();
        assertThat(denseStringList.isExpandable()).isFalse();

    }

    @Test
    void should_increaseCapacityAndAddItem_when_expandableIsTrue_and_sizeIsEqualToCapacity() {
        final int initialCapacity = denseStringList.getCapacity();
        assertThat(denseStringList.isExpandable()).isFalse();
        denseStringList.makeExpandable();
        assertThat(denseStringList.isExpandable()).isTrue();
        for (int i = denseStringList.size(); i < 100 ; i++) {
            assertThat(denseStringList.size()).isEqualTo(i);
            assertThat(denseStringList.add("Item " + i)).isEqualTo("Item " + i);
            assertThat(denseStringList.contains("Item " + i)).isTrue();
            assertThat(denseStringList.indexOf("Item " + i)).isEqualTo(i);
            assertThat(denseStringList.size()).isEqualTo(i+1);
        }
        assertThat(denseStringList.getCapacity() > initialCapacity).isTrue();
    }

    @Test
    void should_increaseCapacityAndSetItem_when_expandableIsTrue_and_setIndexIsEqualToCountAndCapacity() {
        final int initialCapacity = denseStringList.getCapacity();
        assertThat(denseStringList.isExpandable()).isFalse();
        denseStringList.makeExpandable();
        assertThat(denseStringList.isExpandable()).isTrue();
        int currentSize = denseStringList.size();
        do {
            assertThat(denseStringList.set(currentSize, "Item " + currentSize))
                    .isEqualTo("Item " + currentSize);
            assertThat(denseStringList.size()).isEqualTo(currentSize+1);
            assertThat(denseStringList.getCapacity()).isEqualTo(initialCapacity);
            ++currentSize;
        } while(currentSize<initialCapacity);
        assertThat(denseStringList.set(currentSize, "Item " + currentSize))
                .isEqualTo("Item " + currentSize);
        assertThat(denseStringList.size()).isEqualTo(currentSize+1);
        assertThat(denseStringList.getCapacity()).isGreaterThan(initialCapacity);
    }

    @Test
    void should_throwStringListIsFullException_when_add_and_expandableIsFalse_and_sizeIsEqualToCapacity() {
        assertThat(denseStringList.isExpandable()).isFalse();
        for (int i = denseStringList.size(); i < 10 ; i++) {
            assertThat(denseStringList.size()).isEqualTo(i);
            assertThat(denseStringList.add("Item " + i)).isEqualTo("Item " + i);
            assertThat(denseStringList.contains("Item " + i)).isTrue();
            assertThat(denseStringList.indexOf("Item " + i)).isEqualTo(i);
            assertThat(denseStringList.size()).isEqualTo(i+1);
        }
        assertThatExceptionOfType(StringListIsFullException.class)
                .isThrownBy(()->denseStringList.add("some item"));

    }

    @Test
    void should_throwStringListIndexOutOfBoundsExceptionStringListIsFullException_when_addIndexIsNotANaturalNumber() {
        assertThatExceptionOfType(StringListIndexOutOfBoundsException.class)
                .isThrownBy(()->denseStringList.add(-1, "some item"));
    }

    @Test
    void should_throwStringListIndexOutOfBoundsExceptionStringListIsFullException_when_addIndexIsGreaterThanSize() {
        final int size = denseStringList.size();
        assertThatExceptionOfType(StringListIndexOutOfBoundsException.class)
                .isThrownBy(()->denseStringList.add(size+1, "some item"));
    }

    @Test
    void should_throwStringListIndexOutOfBoundsExceptionStringListIsFullException_when_setIndexIsNotANaturalNumber() {
        assertThatExceptionOfType(StringListIndexOutOfBoundsException.class)
                .isThrownBy(()->denseStringList.set(-1, "some item"));
    }

    @Test
    void should_throwStringListIndexOutOfBoundsExceptionStringListIsFullException_when_setIndexIsGreaterThanSize() {
        final int size = denseStringList.size();
        assertThatExceptionOfType(StringListIndexOutOfBoundsException.class)
                .isThrownBy(()->denseStringList.set(size+1, "some item"));
    }

    @Test
    void should_throwStringListOutOfBoundsException_when_getIndexIsNotNaturalNumber() {
        assertThatExceptionOfType(StringListIndexOutOfBoundsException.class)
                .isThrownBy(()->denseStringList.get(-1));
    }

    @Test
    void should_throwStringListOutOfBoundsException_when_getIndexIsGreaterOrEqualTOCapacity() {
        final int capacity = denseStringList.getCapacity();
        assertThatExceptionOfType(StringListIndexOutOfBoundsException.class)
                .isThrownBy(()->denseStringList.get(capacity));
        assertThatExceptionOfType(StringListIndexOutOfBoundsException.class)
                .isThrownBy(()->denseStringList.get(capacity+1));
    }

    @Test
    void should_throwStringListOutOfBounds_when_getIndexIsGreaterThanActualSize() {
        final int size = denseStringList.size();
        assertThatExceptionOfType(StringListIndexOutOfBoundsException.class)
                .isThrownBy(()->denseStringList.get(size));
    }

    @Test
    void should_throwStringListNoSuchElementException_when_removeItemThatIsNotListed() {
        final int size = denseStringList.size();
        assertThatExceptionOfType(StringListNoSuchElementException.class)
                .isThrownBy(()->denseStringList.remove("-1000. Unlisted Item"));
        assertThat(denseStringList.size()).isEqualTo(size);
    }

    @Test
    void should_throwStringListOutOfBoundsException_when_removeIndexNotNaturalNumber_or_GreaterOrEqualToCapacity() {
        final int size = denseStringList.size();
        final int capacity = denseStringList.getCapacity();
        assertThatExceptionOfType(StringListIndexOutOfBoundsException.class)
                .isThrownBy(() -> denseStringList.remove(-1));
        assertThatExceptionOfType(StringListIndexOutOfBoundsException.class)
                .isThrownBy(() -> denseStringList.remove(-2));
        assertThatExceptionOfType(StringListIndexOutOfBoundsException.class)
                .isThrownBy(() -> denseStringList.remove(capacity));
        assertThatExceptionOfType(StringListIndexOutOfBoundsException.class)
                .isThrownBy(() -> denseStringList.remove(capacity + 1));
        assertThat(denseStringList.size()).isEqualTo(size);
    }

    @Test
    void should_throwStringListIndexOutOfBoundsException_when_removeIndexGreaterOrEqualToActualSize() {
        final int size = denseStringList.size();
        assertThatExceptionOfType(StringListIndexOutOfBoundsException.class)
                .isThrownBy(() -> denseStringList.remove(size));
        assertThat(denseStringList.size()).isEqualTo(size);
        denseStringList.remove(size-1);
        assertThat(denseStringList.size()).isEqualTo(size-1);
    }

    @Test
    void should_throwStringListNullPointerException_when_equalsToOtherListWhichIsNull() {
        assertThatExceptionOfType(StringListNullPointerException.class)
                .isThrownBy(() -> denseStringList.equals(null));
    }

    @Test
    void should_throwStringListIndexOutOfBoundsException_whenToArrayIndexIsGreaterThanActualSize() {
        final int size = denseStringList.size();
        assertThatExceptionOfType(StringListIndexOutOfBoundsException.class)
                .isThrownBy(() -> denseStringList.toArray(size+1));
        assertThat(denseStringList.toArray(size).length).isEqualTo(0);
        assertThat(denseStringList.toArray(size-1).length).isEqualTo(1);
    }

    @Test
    void should_throwStringListItemIsNullException_whenAdd_whenItemIsNull() {
        final int size = denseStringList.size();
        assertThatExceptionOfType(StringListItemIsNullException.class)
                .isThrownBy(() -> denseStringList.add(null));
        assertThat(denseStringList.size()).isEqualTo(size);
    }

    @Test
    void should_throwStringListItemIsNullException_whenAddWithIndex_whenItemIsNull() {
        final int size = denseStringList.size();
        assertThatExceptionOfType(StringListItemIsNullException.class)
                .isThrownBy(() -> denseStringList.add(0, null));
        assertThat(denseStringList.size()).isEqualTo(size);
    }

    @Test
    void should_throwStringListItemIsNullException_whenSet_whenItemIsNull() {
        final int size = denseStringList.size();
        assertThatExceptionOfType(StringListItemIsNullException.class)
                .isThrownBy(() -> denseStringList.set(0, null));
        assertThat(denseStringList.size()).isEqualTo(size);
    }

}