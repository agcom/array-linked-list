package com.github.agcom.arraylinkedlist;

/**
 * sequence <b>"index"</b> frequently used as a meaning of <b>kinda pointer</b>
 *
 * @param <T> type of elements to be inserted and retrieved
 */
public class ArrayLinkedList<T> {

    private int[] empties;
    private int emptyLastIndex = -1;

    private int[] previouses;
    private T[] elements;
    private int[] nexts;

    private int size = 0, firstIndex = -1;

    /**
     * @param capacity content arrays length
     * @throws IllegalArgumentException if capacity < 0
     */
    public ArrayLinkedList(int capacity) {

        if (capacity <= 0)
            throw new IllegalArgumentException("capacity should be positive and greater than zero; " + "capacity = " + capacity);

        this.previouses = new int[capacity];
        this.elements = (T[]) new Object[capacity];
        this.nexts = new int[capacity];
        this.empties = new int[capacity];

        for (int i = 0; i < capacity; i++) {

            empties[++emptyLastIndex] = i;

        }

    }

    /**
     * adds an element to start of this list
     *
     * @param element
     * @return index of the newly inserted element for further use (e.g. {@link #insertAfter(int, Object)}, {@link #remove(int)})
     */
    public int insertFirst(T element) {

        int insertIndex = getEmptyIndex();

        setEntry(insertIndex, -1, element, firstIndex);

        if (firstIndex >= 0) previouses[firstIndex] = insertIndex;

        firstIndex = insertIndex;

        size++;

        return insertIndex;

    }

    /**
     * adds an element after specified index
     *
     * @param element
     * @return index of the newly inserted element for further use (e.g. {@link #insertAfter(int, Object)}, {@link #remove(int)})
     * @throws ArrayIndexOutOfBoundsException if index is out of capacity bounds (0 ~ capacity-1)
     * @throws IllegalArgumentException       if no element is present at the passed index
     */
    public int insertAfter(int index, T element) {

        if (index < 0 || index >= elements.length) throw new ArrayIndexOutOfBoundsException(index);
        else if (elements[index] == null) throw new IllegalArgumentException("no element at called index = " + index);

        int insertIndex = getEmptyIndex(), next = nexts[index];
        setEntry(insertIndex, index, element, next);
        nexts[index] = insertIndex;
        if (next >= 0) previouses[next] = insertIndex;

        size++;

        return insertIndex;

    }

    /**
     * @param index
     * @return the next element's index (which is linked to the passed index)
     * @throws ArrayIndexOutOfBoundsException if index is out of capacity bounds (0 ~ capacity-1)
     * @throws IllegalArgumentException       if no element is present at the passed index
     */
    public int getNext(int index) {

        if (index < 0 || index >= elements.length) throw new ArrayIndexOutOfBoundsException(index);
        else if (elements[index] == null) throw new IllegalArgumentException("no element at called index = " + index);

        return nexts[index];

    }

    /**
     * @param index
     * @return the previous element's index (which is linked to the passed index)
     * @throws ArrayIndexOutOfBoundsException if index is out of capacity bounds (0 ~ capacity-1)
     * @throws IllegalArgumentException       if no element is present at the passed index
     */
    public int getPrevious(int index) {

        if (index < 0 || index >= elements.length) throw new ArrayIndexOutOfBoundsException(index);
        else if (elements[index] == null) throw new IllegalArgumentException("no element at called index = " + index);

        return previouses[index];

    }

    public int getFirst() {

        if (firstIndex < 0) throw new RuntimeException("no element assigned as first");

        return firstIndex;

    }

    /**
     * @param index
     * @return mapped element
     * @throws ArrayIndexOutOfBoundsException if index is out of capacity bounds (0 ~ capacity-1)
     * @throws IllegalArgumentException       if no element is present at the passed index
     */
    public T get(int index) {

        if (index < 0 || index >= elements.length) throw new ArrayIndexOutOfBoundsException(index);
        else if (elements[index] == null) throw new IllegalArgumentException("no element at called index = " + index);

        return elements[index];

    }

    /**
     * removes the corresponding element at the pointed index
     *
     * @param index
     * @return
     * @throws ArrayIndexOutOfBoundsException if index is out of capacity bounds (0 ~ capacity-1)
     * @throws IllegalArgumentException       if no element is present at the passed index
     */
    public T remove(int index) {

        if (index < 0 || index >= elements.length) throw new ArrayIndexOutOfBoundsException(index);
        else if (elements[index] == null) throw new IllegalArgumentException("no element at called index = " + index);

        int next = nexts[index];
        int previous = previouses[index];

        if (index == firstIndex) firstIndex = next;

        if (previous >= 0) nexts[previous] = next;
        if (next >= 0) previouses[next] = previous;

        addEmptyIndex(index);
        size--;

        T element = elements[index];
        elements[index] = null;

        return element;

    }

    /**
     * @return number of elements present
     */
    public int size() {

        return size;

    }

    /**
     * @return lastIndex == 0
     */
    public boolean isEmpty() {

        return size == 0;

    }

    private void setEntry(int index, int previous, T element, int next) {

        previouses[index] = previous;
        elements[index] = element;
        nexts[index] = next;

    }

    private int getEmptyIndex() {

        if (size == elements.length)
            throw new RuntimeException("list is full; lastIndex = " + size + ", capacity = " + elements.length);

        return empties[emptyLastIndex--];

    }

    private void addEmptyIndex(int index) {

        empties[++emptyLastIndex] = index;

    }

    //for test

    public static void main(String[] args) {

        ArrayLinkedList<Integer> mine = new ArrayLinkedList<>(10);

        System.out.println("Inserting 0, 1, 2, 3, 4, 5, 6:");

        int index0, index1, index2, index3, index4, index5, index6;
        index0 = mine.insertFirst(0);
        index1 = mine.insertAfter(index0, 1);
        index2 = mine.insertAfter(index1, 2);
        index3 = mine.insertAfter(index2, 3);
        index4 = mine.insertAfter(index3, 4);
        index5 = mine.insertAfter(index4, 5);
        index6 = mine.insertAfter(index5, 6);
        printElements(mine);
        System.out.println();

        System.out.println("Removing 2:");
        mine.remove(index2);
        printElements(mine);
        System.out.println();

        System.out.println("Removing 0:");
        mine.remove(index0);
        printElements(mine);
        System.out.println();

        System.out.println("Inserting 9999 after 5:");
        int newEntryIndex = mine.insertAfter(index5, 9999);
        printElements(mine);
        System.out.println();

        System.out.println("Inserting 888888 after 9999:");
        mine.insertAfter(newEntryIndex, 888888);
        printElements(mine);
        System.out.println();

        System.out.println("Removing 9999:");
        mine.remove(newEntryIndex);
        printElements(mine);
        System.out.println();

    }

    private static void printElements(ArrayLinkedList<?> list) {

        if (list == null || list.isEmpty()) return;

        System.out.print("[");

        int currentIndex = list.getFirst();

        for (int i = 0; i < list.size(); i++) {

            System.out.print(list.get(currentIndex));

            currentIndex = list.getNext(currentIndex);

            if (i != list.size() - 1) System.out.print(", ");

        }

        System.out.println("]");

    }

}
