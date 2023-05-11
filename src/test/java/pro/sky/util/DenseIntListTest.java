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

class DenseIntListTest {

    private DenseIntegerList denseIntegerList;

    public static Stream<Arguments> addParams() {
        return Stream.of(
                Arguments.of(6),
                Arguments.of(7),
                Arguments.of(8),
                Arguments.of(9),
                Arguments.of(10)
        );
    }

    public static Stream<Arguments> addWithIndexParams() {
        return Stream.of(
                Arguments.of(5, 50),
                Arguments.of(4, 40),
                Arguments.of(3, 30),
                Arguments.of(2, 20),
                Arguments.of(0, 10)
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
                // index-item pairs:
                Arguments.of(0, 10),
                Arguments.of(1, 20),
                Arguments.of(2, 30),
                Arguments.of(3, 40),
                Arguments.of(4, 50)
        );
    }

    public static Stream<Arguments> itemParams() {
        return Stream.of(
                Arguments.of(10),
                Arguments.of(20),
                Arguments.of(30),
                Arguments.of(40),
                Arguments.of(50)
        );
    }

    @BeforeEach
    public void beforeEach() {
        denseIntegerList = new DenseIntegerList();
        denseIntegerList.add(10);
        denseIntegerList.add(20);
        denseIntegerList.add(30);
        denseIntegerList.add(40);
        denseIntegerList.add(50);
    }

    @Test
    void makeExpandable() {
        assertThat(denseIntegerList.isExpandable()).isEqualTo(false);
        denseIntegerList.makeExpandable();
        assertThat(denseIntegerList.isExpandable()).isEqualTo(true);
    }

    @ParameterizedTest
    @MethodSource("addParams")
    void add(Integer item) {
        int count = denseIntegerList.size();
        assertThat(denseIntegerList.add(item)).isEqualTo(item);
        assertThat(denseIntegerList.size()).isEqualTo(count+1);
        assertThat(denseIntegerList.get(count)).isEqualTo(item);
        assertThat(denseIntegerList.contains(item)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("addWithIndexParams")
    void add(int index, Integer item) {
        //GIVEN
        int count = denseIntegerList.size();
        List<Integer> toTheRight = denseIntegerList.toList(index);
        //WHEN
        assertThat(denseIntegerList.add(index, item)).isEqualTo(item);
        //THEN
        assertThat(denseIntegerList.size()).isEqualTo(count+1);
        assertThat(denseIntegerList.get(index)).isEqualTo(item);
        assertThat(denseIntegerList.contains(item)).isTrue();
        assertThat(denseIntegerList.toList(index+1))
                .usingRecursiveComparison()
                .asList()
                .containsExactlyElementsOf(toTheRight);

    }

    @ParameterizedTest
    @MethodSource("addWithIndexParams")
    void set(int index, Integer item) {
            //GIVEN
            final int count = denseIntegerList.size();
            final int countExpected =  (index == count)? count+1 : count;
            //WHEN
            assertThat(denseIntegerList.set(index, item)).isEqualTo(item);
            //THEN
            assertThat(denseIntegerList.size()).isEqualTo(countExpected);
            assertThat(denseIntegerList.get(index)).isEqualTo(item);
            assertThat(denseIntegerList.contains(item)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("removeItemParams")
    void remove(Integer item) {
        //GIVEN
        final int count = denseIntegerList.size();
        //WHEN
        assertThat(denseIntegerList.remove(item)).isEqualTo(item);
        //THEN
        assertThat(denseIntegerList.size()).isEqualTo(count-1);
        assertThat(denseIntegerList.contains(item)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("removeByIndexParams")
    void remove(int index, Integer expectedItem) {
        //GIVEN
        final int count = denseIntegerList.size();
        //WHEN
        assertThat(denseIntegerList.removeByIndex(index)).isEqualTo(expectedItem);
        //THEN
        assertThat(denseIntegerList.size()).isEqualTo(count-1);
        assertThat(denseIntegerList.contains(expectedItem)).isFalse();
    }


    @ParameterizedTest
    @MethodSource("itemParams")
    void contains(Integer item) {
        assertThat(denseIntegerList.contains(item)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("indexAndItemParams")
    void indexOf(int expectedIndex, Integer item) {
        assertThat(denseIntegerList.indexOf(item)).isEqualTo(expectedIndex);
    }

    @Test
    void lastIndexOf() {
        denseIntegerList.add(4, 10);
        denseIntegerList.add(6, 20);
        denseIntegerList.add(0,30);
        //GIVEN:     "30 "10" "20" "30" "40" "10" "50" "20"
        // indices:   0    1    2    3    4    5    6    7
        assertThat(denseIntegerList.size()).isEqualTo(8);

        assertThat(denseIntegerList.lastIndexOf(10)).isEqualTo(5);
        assertThat(denseIntegerList.lastIndexOf(20)).isEqualTo(7);
        assertThat(denseIntegerList.lastIndexOf(30)).isEqualTo(3);
        assertThat(denseIntegerList.lastIndexOf(40)).isEqualTo(4);
        assertThat(denseIntegerList.lastIndexOf(50)).isEqualTo(6);

        int itemEleventh = 110;
        assertThat(denseIntegerList.contains(itemEleventh)).isFalse();
        assertThat(denseIntegerList.lastIndexOf(itemEleventh)).isEqualTo(-1);

    }

    @ParameterizedTest
    @MethodSource("indexAndItemParams")
    void get(int index, Integer expectedItem) {
        assertThat(denseIntegerList.get(index)).isEqualTo(expectedItem);
    }

    @Test
    void testEquals() {
        DenseIntegerList stringListToCompare = new DenseIntegerList(denseIntegerList);
        assertThat(denseIntegerList.equals(stringListToCompare)).isTrue();
        stringListToCompare.add(10);
        assertThat(denseIntegerList.equals(stringListToCompare)).isFalse();
        stringListToCompare.removeByIndex(stringListToCompare.lastIndexOf(10));
        assertThat(denseIntegerList.equals(stringListToCompare)).isTrue();
        stringListToCompare.removeByIndex(stringListToCompare.lastIndexOf(10));
        assertThat(denseIntegerList.equals(stringListToCompare)).isFalse();
        stringListToCompare.add(0, (10));
        assertThat(denseIntegerList.equals(stringListToCompare)).isTrue();
        stringListToCompare.set(0, (-10));
        assertThat(denseIntegerList.equals(stringListToCompare)).isFalse();
        stringListToCompare.set(0, (10));
        assertThat(denseIntegerList.equals(stringListToCompare)).isTrue();
    }

    @Test
    void isEmpty() {
        for (int i =denseIntegerList.size(); i > 0; i--) {
            assertThat(denseIntegerList.isEmpty()).isFalse();
            denseIntegerList.removeByIndex(0);
            assertThat(denseIntegerList.size()).isEqualTo(i-1);
        }
        assertThat(denseIntegerList.isEmpty()).isTrue();
        denseIntegerList.add(10);
        assertThat(denseIntegerList.size()).isEqualTo(1);
        assertThat(denseIntegerList.isEmpty()).isFalse();
    }

    @Test
    void clear() {
        //GIVEN
        assertThat(denseIntegerList.isEmpty()).isFalse();
        assertThat(denseIntegerList.size()).isEqualTo(5);
        //WHEN
        denseIntegerList.clear();
        //THEN
        assertThat(denseIntegerList.isEmpty()).isTrue();
        assertThat(denseIntegerList.size()).isEqualTo(0);
    }

    @Test
    void toList() {
        assertThat(denseIntegerList.toList())
                .usingRecursiveComparison()
                .asList()
                .containsExactlyElementsOf(List.of(
                        10,
                        20,
                        30,
                        40,
                        50)
                );
    }

    @Test
    void toArray() {
        int [] actual = denseIntegerList.toArray();
        assertThat(actual.length).isEqualTo(5);
        for (int i = 0; i < 5; i++) {
            assertThat(actual[i]).isEqualTo((i + 1)* 10);
        }
    }

    @Test
    void should_throwIllegalArgumentExceptionWithMessage_when_capacityIsLessThan0 () {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()->new DenseIntegerList(-1))
                .withMessage("The capacity should be a natural number");
    }

    @Test
    void should_add_when_capacityIs0_and_expandableIsTrue() {
        DenseIntegerList dsList = new DenseIntegerList(0, true);
        assertThat(dsList.size()).isEqualTo(0);
        assertThat(dsList.isEmpty()).isTrue();
        assertThat(dsList.add(10)).isEqualTo(10);
        assertThat(dsList.size()).isEqualTo(1);
        assertThat(dsList.isEmpty()).isFalse();
    }

    @Test
    void should_throwListIsFullException_when_capacityIs0_and_expandableIsFalse() {
        DenseIntegerList dsList = new DenseIntegerList(0, false);
        assertThat(dsList.size()).isEqualTo(0);
        assertThat(dsList.isEmpty()).isTrue();
        assertThatExceptionOfType(ListIsFullException.class)
                .isThrownBy(()->dsList.add(10));

    }

    @Test
    void should_throwListIsFullException_when_capacityIs0_and_expandableIsNoPassedToConstructor() {
        DenseIntegerList dsList = new DenseIntegerList(0);
        assertThat(dsList.size()).isEqualTo(0);
        assertThat(dsList.isEmpty()).isTrue();
        assertThatExceptionOfType(ListIsFullException.class)
                .isThrownBy(()->dsList.add(10));

    }

    @Test
    void disableExpandable() {
        assertThat(denseIntegerList.isExpandable()).isFalse();
        denseIntegerList.makeExpandable();
        assertThat(denseIntegerList.isExpandable()).isTrue();
        denseIntegerList.disableExpandable();
        assertThat(denseIntegerList.isExpandable()).isFalse();

    }

    @Test
    void should_increaseCapacityAndAddItem_when_expandableIsTrue_and_sizeIsEqualToCapacity() {
        final int initialCapacity = denseIntegerList.getCapacity();
        assertThat(denseIntegerList.isExpandable()).isFalse();
        denseIntegerList.makeExpandable();
        assertThat(denseIntegerList.isExpandable()).isTrue();
        for (int i = denseIntegerList.size(); i < 100 ; i++) {
            assertThat(denseIntegerList.size()).isEqualTo(i);
            Integer itemToAdd = (i + 1) * 10;
            assertThat(denseIntegerList.add(itemToAdd)).isEqualTo(itemToAdd);
            assertThat(denseIntegerList.contains(itemToAdd)).isTrue();
            assertThat(denseIntegerList.indexOf(itemToAdd)).isEqualTo(i);
            assertThat(denseIntegerList.size()).isEqualTo(i+1);
        }
        assertThat(denseIntegerList.getCapacity() > initialCapacity).isTrue();
    }

    @Test
    void should_increaseCapacityAndSetItem_when_expandableIsTrue_and_setIndexIsEqualToCountAndCapacity() {
        final int initialCapacity = denseIntegerList.getCapacity();
        assertThat(denseIntegerList.isExpandable()).isFalse();
        denseIntegerList.makeExpandable();
        assertThat(denseIntegerList.isExpandable()).isTrue();
        int currentSize = denseIntegerList.size();
        do {
            assertThat(denseIntegerList.set(currentSize, currentSize * 10))
                    .isEqualTo(currentSize * 10);
            assertThat(denseIntegerList.size()).isEqualTo(currentSize+1);
            assertThat(denseIntegerList.getCapacity()).isEqualTo(initialCapacity);
            ++currentSize;
        } while(currentSize<initialCapacity);
        assertThat(denseIntegerList.set(currentSize, currentSize * 10))
                .isEqualTo(currentSize * 10);
        assertThat(denseIntegerList.size()).isEqualTo(currentSize+1);
        assertThat(denseIntegerList.getCapacity()).isGreaterThan(initialCapacity);
    }

    @Test
    void should_throwListIsFullException_when_add_and_expandableIsFalse_and_sizeIsEqualToCapacity() {
        assertThat(denseIntegerList.isExpandable()).isFalse();
        for (int i = denseIntegerList.size(); i < 10 ; i++) {
            Integer itemToAdd = (i + 1) * 10;
            assertThat(denseIntegerList.size()).isEqualTo(i);
            assertThat(denseIntegerList.add(itemToAdd)).isEqualTo(itemToAdd);
            assertThat(denseIntegerList.contains(itemToAdd)).isTrue();
            assertThat(denseIntegerList.indexOf(itemToAdd)).isEqualTo(i);
            assertThat(denseIntegerList.size()).isEqualTo(i+1);
        }
        assertThatExceptionOfType(ListIsFullException.class)
                .isThrownBy(()->denseIntegerList.add(1_000_000));

    }

    @Test
    void should_throwListIndexOutOfBoundsExceptionIntegerListIsFullException_when_addIndexIsNotANaturalNumber() {
        assertThatExceptionOfType(ListIndexOutOfBoundsException.class)
                .isThrownBy(()->denseIntegerList.add(-1, 1_000_000));
    }

    @Test
    void should_throwListIndexOutOfBoundsExceptionIntegerListIsFullException_when_addIndexIsGreaterThanSize() {
        final int size = denseIntegerList.size();
        assertThatExceptionOfType(ListIndexOutOfBoundsException.class)
                .isThrownBy(()->denseIntegerList.add(size+1, 1_000_000));
    }

    @Test
    void should_throwListIndexOutOfBoundsExceptionIntegerListIsFullException_when_setIndexIsNotANaturalNumber() {
        assertThatExceptionOfType(ListIndexOutOfBoundsException.class)
                .isThrownBy(()->denseIntegerList.set(-1, 1_000_000));
    }

    @Test
    void should_throwListIndexOutOfBoundsExceptionIntegerListIsFullException_when_setIndexIsGreaterThanSize() {
        final int size = denseIntegerList.size();
        assertThatExceptionOfType(ListIndexOutOfBoundsException.class)
                .isThrownBy(()->denseIntegerList.set(size+1, 1_000_000));
    }

    @Test
    void should_throwListOutOfBoundsException_when_getIndexIsNotNaturalNumber() {
        assertThatExceptionOfType(ListIndexOutOfBoundsException.class)
                .isThrownBy(()->denseIntegerList.get(-1));
    }

    @Test
    void should_throwListOutOfBoundsException_when_getIndexIsGreaterOrEqualTOCapacity() {
        final int capacity = denseIntegerList.getCapacity();
        assertThatExceptionOfType(ListIndexOutOfBoundsException.class)
                .isThrownBy(()->denseIntegerList.get(capacity));
        assertThatExceptionOfType(ListIndexOutOfBoundsException.class)
                .isThrownBy(()->denseIntegerList.get(capacity+1));
    }

    @Test
    void should_throwListOutOfBounds_when_getIndexIsGreaterThanActualSize() {
        final int size = denseIntegerList.size();
        assertThatExceptionOfType(ListIndexOutOfBoundsException.class)
                .isThrownBy(()->denseIntegerList.get(size));
    }

    @Test
    void should_throwListNoSuchElementException_when_removeItemByValue_whenValueIsNotListed() {
        final int size = denseIntegerList.size();
        assertThatExceptionOfType(ListNoSuchElementException.class)
                .isThrownBy(()->denseIntegerList.remove(-1));
        assertThat(denseIntegerList.size()).isEqualTo(size);
    }

    @Test
    void should_throwListIndexOutOfBoundsException_when_removeByIndex_whenIndexIsIllegal() {
        final int size = denseIntegerList.size();
        assertThatExceptionOfType(ListIndexOutOfBoundsException.class)
                .isThrownBy(()->denseIntegerList.removeByIndex(-1000));
        assertThat(denseIntegerList.size()).isEqualTo(size);
    }

    @Test
    void should_throwListOutOfBoundsException_when_removeIndexNotNaturalNumber_or_GreaterOrEqualToCapacity() {
        final int size = denseIntegerList.size();
        final int capacity = denseIntegerList.getCapacity();
        assertThatExceptionOfType(ListIndexOutOfBoundsException.class)
                .isThrownBy(() -> denseIntegerList.removeByIndex(-1));
        assertThatExceptionOfType(ListIndexOutOfBoundsException.class)
                .isThrownBy(() -> denseIntegerList.removeByIndex(-2));
        assertThatExceptionOfType(ListIndexOutOfBoundsException.class)
                .isThrownBy(() -> denseIntegerList.removeByIndex(capacity));
        assertThatExceptionOfType(ListIndexOutOfBoundsException.class)
                .isThrownBy(() -> denseIntegerList.removeByIndex(capacity + 1));
        assertThat(denseIntegerList.size()).isEqualTo(size);
    }

    @Test
    void should_throwListIndexOutOfBoundsException_when_removeIndexGreaterOrEqualToActualSize() {
        final int size = denseIntegerList.size();
        assertThatExceptionOfType(ListIndexOutOfBoundsException.class)
                .isThrownBy(() -> denseIntegerList.removeByIndex(size));
        assertThat(denseIntegerList.size()).isEqualTo(size);
        denseIntegerList.removeByIndex(size-1);
        assertThat(denseIntegerList.size()).isEqualTo(size-1);
    }

    @Test
    void should_throwListNullPointerException_when_equalsToOtherListWhichIsNull() {
        assertThatExceptionOfType(ListNullPointerException.class)
                .isThrownBy(() -> denseIntegerList.equals(null));
    }

    @Test
    void should_throwListIndexOutOfBoundsException_whenToArrayIndexIsGreaterThanActualSize() {
        final int size = denseIntegerList.size();
        assertThatExceptionOfType(ListIndexOutOfBoundsException.class)
                .isThrownBy(() -> denseIntegerList.toArray(size+1));
        assertThat(denseIntegerList.toArray(size).length).isEqualTo(0);
        assertThat(denseIntegerList.toArray(size-1).length).isEqualTo(1);
    }

}