package hw7;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomArrayList<A> implements CustomList<A>, Iterable<A> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final double GROWTH_FACTOR = 1.5;
    private Object[] elements;
    private int size;

    public CustomArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public CustomArrayList(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Размер может быть только положительным");
        }
        this.elements = new Object[n];
        this.size = 0;
    }

    @Override
    public void add(A element){
        if(element == null) {
            throw new IllegalArgumentException("Null isn't acceptable");
        }
        if (this.size == this.elements.length) {
            upgradeCapacity(this.size + 1);
        }
        this.elements[this.size] = element;
        this.size++;
    }

    @Override
    public A get(int index) {
        checkIndex(index);
        return (A) this.elements[index];
    }

    @Override
    public A remove(int index){
        checkIndex(index);
        A removedElement = (A) this.elements[index];

        for (int i = index; i < this.size - 1; i++) {
            this.elements[i] = this.elements[i + 1];
        }

        this.elements[this.size - 1] = null;
        this.size--;

        return removedElement;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Iterator<A> iterator() {
        return new CustomArrayListIterator();
    }

    private class CustomArrayListIterator implements Iterator<A> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public A next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }
            return (A) elements[currentIndex++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove not supported");
        }
    }

    private void upgradeCapacity(int capacity) {
        int newCapacity = (int) (elements.length * GROWTH_FACTOR);
        if (newCapacity < capacity) {
            newCapacity = capacity;
        }
        Object[] newElements = new Object[newCapacity];
        System.arraycopy(this.elements, 0, newElements, 0, this.size);
        this.elements = newElements;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Index " + index + "isn't acceptable");
        }
    }
}

