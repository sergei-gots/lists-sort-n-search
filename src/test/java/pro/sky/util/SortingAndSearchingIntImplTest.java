package pro.sky.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SortingAndSearchingIntImplTest {

    @Test
    public void testThat_sortAndSearchProductsAreEqual() {
        BenchmarkDenseIntList testList = new BenchmarkDenseIntList();
        SortingAndSearchingInt sortAndSearchEngine1 = new SortingAndSearchingIntImpl(testList);
        SortingAndSearchingInt sortAndSearchEngine2 = new SortingAndSearchingIntImpl(testList);
        SortingAndSearchingInt sortAndSearchEngine3 = new SortingAndSearchingIntImpl(testList);
        SortingAndSearchingInt sortAndSearchEngine4 = new SortingAndSearchingIntImpl(testList);

        //GIVEN
        assertThat(sortAndSearchEngine1).usingRecursiveComparison().isEqualTo(sortAndSearchEngine2);
        assertThat(sortAndSearchEngine2).usingRecursiveComparison().isEqualTo(sortAndSearchEngine3);
        assertThat(sortAndSearchEngine3).usingRecursiveComparison().isEqualTo(sortAndSearchEngine4);
        int value = testList.get(0);
        //WHEN
        sortAndSearchEngine1.sortInsertion();
        sortAndSearchEngine2.sortBubble();
        sortAndSearchEngine3.sortSelection();
        sortAndSearchEngine4.sortRecursiveQuickSort();
        int indexLinear1 = sortAndSearchEngine1.linearIndexOf(value);
        int indexLinear2 = sortAndSearchEngine2.linearIndexOf(value);
        int indexLinear3 = sortAndSearchEngine3.linearIndexOf(value);
        int indexLinear4 = sortAndSearchEngine4.linearIndexOf(value);
        int indexBinary1 = sortAndSearchEngine1.binaryIndexOf(value);
        int indexBinary2 = sortAndSearchEngine2.binaryIndexOf(value);
        int indexBinary3 = sortAndSearchEngine3.binaryIndexOf(value);
        int indexBinary4 = sortAndSearchEngine4.binaryIndexOf(value);

        //THEN
        assertThat(sortAndSearchEngine1).usingRecursiveComparison().isEqualTo(sortAndSearchEngine2);
        assertThat(sortAndSearchEngine2).usingRecursiveComparison().isEqualTo(sortAndSearchEngine3);
        assertThat(sortAndSearchEngine3).usingRecursiveComparison().isEqualTo(sortAndSearchEngine4);

        assertThat(sortAndSearchEngine1.containsLinear(value)).isTrue();
        assertThat(indexLinear1).isEqualTo(indexBinary1);
        assertThat(indexLinear1).isEqualTo(indexBinary2);
        assertThat(indexLinear1).isEqualTo(indexBinary3);
        assertThat(indexLinear2).isEqualTo(indexBinary2);
        assertThat(indexLinear3).isEqualTo(indexBinary3);
        assertThat(indexLinear3).isEqualTo(indexBinary4);
        assertThat(indexLinear4).isEqualTo(indexBinary4);

    }

}