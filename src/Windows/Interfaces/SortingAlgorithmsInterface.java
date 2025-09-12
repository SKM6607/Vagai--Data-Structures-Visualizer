package Windows.Interfaces;

public interface SortingAlgorithmsInterface extends DefaultWindowsInterface{
    String DEFAULT = "LoadingPage";
    String LINKED_LIST = "Linked List";
    String INSERTION_SORTING = "Insertion Sort";
    String SELECTION_SORTING = "Selection Sort";
    String BUBBLE_SORTING = "Bubble Sort";
    String QUICK_SORTING = "Quick Sort";
    String[] IDENTIFIER_ARRAY = {SELECTION_SORTING, INSERTION_SORTING, BUBBLE_SORTING, QUICK_SORTING};
}
