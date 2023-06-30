package algorithms.huffman;

public class ArrayList<T> {
    private Object[] arr;
    private int size = 0;

    private static final int DEFAULT_INITIAL_SIZE = 10;

    private void extendArray() {
        int newCapacity = arr.length * 2;

        if (newCapacity < 0) {
            throw new ArithmeticException("Capacity overflow when attempting to increase ArrayList capacity.");
        }

        if (arr.length == 0) {
            arr = new Object[DEFAULT_INITIAL_SIZE];
            return;
        }

        Object[] newArr = new Object[newCapacity];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = arr[i];
        }
        arr = newArr;
    }

    public void ensureCapacity(int capacity) {
        if (arr.length < capacity)
            extendArray();
    }

    public ArrayList(int initialCapacity) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal capacity: " + initialCapacity);

        arr = new Object[initialCapacity];
    }

    public boolean add(T value) {
        try {
            ensureCapacity(size + 1);
            arr[size] = value;
            size++;
            return true;
        } catch (Exception exception) {
            System.out.println("Failed to add new element to ArrayList.");
            return false;
        }
    }

    public void add(int index, T value) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException();

        ensureCapacity(size + 1);
        // shift all elements after index to the right
        for (int i = size; i > index; i--) {
            arr[i] = arr[i - 1];
        }

        arr[index] = value;
        size++;
    }

    public T set(int index, T value) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException();

        T previousValue = (T) arr[index];
        arr[index] = value;
        return previousValue;
    }

    public T get(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException();

        return (T) arr[index];
    }

    public T remove(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException();

        T removedValue = (T) arr[index];
        while (index < size - 1) {
            arr[index] = arr[index + 1];
            index++;
        }
        arr[index] = null;
        size--;

        return removedValue;
    }

    public int size() {
        return size;
    }

    public int indexOf(T value) {
        for (int i = 0; i < size; i++) {
            if (arr[i].equals(value))
                return i;
        }
        return -1;
    }

    public boolean contains(T value) {
        return indexOf(value) > -1;
    }
}
