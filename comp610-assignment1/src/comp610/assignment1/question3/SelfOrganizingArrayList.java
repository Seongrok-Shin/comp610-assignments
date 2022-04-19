/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp610.assignment1.question3;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 *
 * @author ssr7324
 */
public class SelfOrganizingArrayList<E> implements List<E> {

    protected int numElements;
    protected E[] e;
    private int[] checkElements;
    private final int SET_ARRAY_CAPACITY = 20;

    @Override
    public int size() {
        return numElements;
    }

    public SelfOrganizingArrayList() {
        super();
        numElements = 0;
        e = (E[]) new Object[SET_ARRAY_CAPACITY];
        checkElements = new int[SET_ARRAY_CAPACITY];
    }

    public SelfOrganizingArrayList(Collection<? extends E> c) {
        this();
        for (Iterator<? extends E> it = c.iterator(); it.hasNext();) {
            E element = it.next();
            add(element);
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < numElements; i++) {
            if (o.equals(e[i])) {
                checkElements[i] += 1;
                this.sortedElements(o, i);
                return true;
            }
        }
        return false;
    }

    private void sortedElements(Object o, int index) {

        for (int i = index; i > 0; i--) {
            if (checkElements[i] > checkElements[i - 1]) {
                E elementsAtCurrent = e[i];
                e[i] = e[i - 1];
                e[i - 1] = elementsAtCurrent;

                int currentCheckElement = checkElements[i];
                checkElements[i] = checkElements[i - 1];
                checkElements[i - 1] = currentCheckElement;

            }
            if (checkElements[i] == checkElements[i - 1]) {
                if (e[i].equals(o)) {
                    E elementsAtCurrent = e[i];
                    e[i] = e[i - 1];
                    e[i - 1] = elementsAtCurrent;

                    int currentCheckElement = checkElements[i];
                    checkElements[i] = checkElements[i - 1];
                    checkElements[i - 1] = currentCheckElement;
                }
            }

        }
    }

    @Override
    public Iterator<E> iterator() {
        return new setIterator<E>(e, numElements);
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean add(E e) {
        if (!(contains(e))) {
            if (numElements >= this.e.length) {
                moreCapacity();
            }
            this.e[numElements] = e;
            checkElements[numElements] = 1;
            numElements++;
            return true;
        } else {
            return false;
        }
    }

    private void moreCapacity() {
        E[] increaseE = (E[]) new Object[numElements * 2];
        int[] increaseCheckElements = new int[numElements * 2];
        for (int i = 0; i < numElements; i++) {
            increaseE[i] = e[i];
            increaseCheckElements[i] = checkElements[i];
        }
        e = increaseE;
        checkElements = increaseCheckElements;
    }

    @Override
    public boolean remove(Object o) {
        int i = 0;
        boolean found = false;

        for (int j = 0; j < numElements; j++) {
            if (o.equals(e[j])) {
                i = j;
                found = true;
                break;
            }
        }
        remove(i);
        return found;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean found;
        for (Object o : c) {
            found = contains(o);

            if (!found) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            add(e);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object o : c) {
            remove(o);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        numElements = 0;
        e = (E[]) new Object[SET_ARRAY_CAPACITY];
        checkElements = new int[SET_ARRAY_CAPACITY];
    }

    @Override
    public E get(int index) throws NoSuchElementException {
        if ((index < 0) || (index > numElements)) {
            throw new NoSuchElementException("Index is not founded.");
        }
        return e[index];
    }

    @Override
    public E set(int index, E element) throws NoSuchElementException {
        if ((index < 0) || (index > numElements)) {
            throw new NoSuchElementException("Index is not founded.");
        }
        checkElements[index] = 1;
        return e[index] = element;
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public E remove(int index) throws NoSuchElementException {
        if ((index < 0) || (index > numElements)) {
            throw new NoSuchElementException("Index is not founded.");
        }

        if (index == numElements - 1) {
            Objects.isNull(e[index]);
        } else {
            for (int i = 0; i < numElements - 1; i++) {
                if (i >= index) {
                    e[i] = e[i + 1];
                    checkElements[i] = checkElements[i + 1];
                }
            }
        }
        numElements--;

        return e[index];
    }

    @Override
    public int indexOf(Object o) throws NoSuchElementException {
        for (int i = 0; i < numElements; i++) {
            if (o.equals(e[i])) {
                checkElements[i] += 1;
                sortedElements(o, i);
                break;
            }
        }
        for (int j = 0; j < numElements; j++) {
            if (o.equals(e[j])) {
                return j;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ListIterator<E> listIterator() {
        return (ListIterator<E>) new setIterator<>(e, numElements);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static class setIterator<E> implements Iterator<E> {

        private final int numElements;
        private final E[] e;
        private int next;

        public setIterator(E[] elements, int numElements) {
            this.numElements = numElements;
            this.e = elements;
        }

        public setIterator(E[] e, int numElements, int index) {
            this.e = e;
            this.numElements = numElements;
            this.next = index;
        }

        @Override
        public boolean hasNext() {
            return next < numElements;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return e[next++];
        }
    }

}
