package main.interfaces;

public interface MacroInterface extends DefaultWindowsInterface {
    String LINKED_LIST = "Linked List";
    String CYCLE_DETECTION = "Cycle Detection";
    String DEFAULT = "Loading Page";
    String STACK = "Stack";
    String QUEUE = "Queue";
    String SIMPLE_QUEUE = "Simple Queue";
    String CIRCULAR_QUEUE = "Circular Queue";
    String PRIORITY_QUEUE = "Priority Queue";
    String SORTING_ALGORITHMS = "Sorting Algorithms";
    String INSERTION_SORTING = "Insertion Sort";
    String SELECTION_SORTING = "Selection Sort";
    String BUBBLE_SORTING = "Bubble Sort";
    String QUICK_SORTING = "Quick Sort";
    String[] SORTING_ARRAY = {SELECTION_SORTING, INSERTION_SORTING, BUBBLE_SORTING, QUICK_SORTING};
    String[] QUEUE_ARRAY = {SIMPLE_QUEUE, CIRCULAR_QUEUE, PRIORITY_QUEUE};
    String[] LINKED_LIST_ARRAY = {LINKED_LIST, CYCLE_DETECTION};
    String[] STACK_ARRAY = {STACK};
    String SEARCHING_ALGORITHMS = "Searching Algorithms";
    String A_STAR_ALGORITHM = "A* Algorithm";
    String BFS_ALGORITHM = "Breadth First Search";
    String DFS_ALGORITHM = "Depth First Search";
    String[] SEARCHING_ARRAY = {BFS_ALGORITHM, DFS_ALGORITHM, A_STAR_ALGORITHM};
}
