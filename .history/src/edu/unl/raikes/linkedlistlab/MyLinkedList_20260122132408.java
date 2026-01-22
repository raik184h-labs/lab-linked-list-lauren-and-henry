package edu.unl.raikes.linkedlistlab;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**.
 * @author downey
 * @param <E>
 *
 */
public class MyLinkedList<E> implements List<E> {
    
    /**
     * Node is identical to ListNode from the example, but parameterized with T.
     * 
     * @author downey
     *
     */
    private class Node {
        public E cargo;
        public Node next;
        
        public Node() {
            this.cargo = null;
            this.next = null;
        }

        public Node(E cargo) {
            this.cargo = cargo;
            this.next = null;
        }

        public Node(E cargo, Node next) {
            this.cargo = cargo;
            this.next = next;
        }

        public String toString() {
            return "Node(" + this.cargo.toString() + ")";
        }
    }
    
    private int size;            // keeps track of the number of elements
    private Node head;           // reference to the first node
    
    /**
     * 
     */
    public MyLinkedList() {
        head = null;
        size = 0;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // run a few simple tests
        List<Integer> mll = new MyLinkedList<Integer>();
        mll.add(1);
        mll.add(2);
        mll.add(3);
        System.out.println(Arrays.toString(mll.toArray()) + " size = " + mll.size());
        
        mll.remove(new Integer(2));
        System.out.println(Arrays.toString(mll.toArray()) + " size = " + mll.size());
    }

    @Override
    public boolean add(E element) {
        if (head == null) {
            head = new Node(element);
        } else {
            Node node = head;
            // loop until the last node
            for ( ; node.next != null; node = node.next) {}
            node.next = new Node(element);
        }
        this.size++;
        return true;
    }

    @Override
    public void add(int index, E element) {
        this.size++;

        Node nodeToAppend = this.getNode(index);

        if (index == 0) {
            this.head = new Node(element, nodeToAppend);
        } else {
            Node targetNode = this.getNode(index - 1);

            targetNode.next = new Node(element, nodeToAppend);
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        boolean flag = true;
        for (E element: collection) {
            flag &= add(element);
        }
        return flag;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public boolean contains(Object obj) {
        return this.indexOf(obj) != -1;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object obj : collection) {
            if (!this.contains(obj)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public E get(int index) {
        Node node = this.getNode(index);
        return node.cargo;
    }

    /** Returns the node at the given index.
     * @param index
     * @return
     */
    private Node getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node node = head;
        for (int i=0; i<index; i++) {
            node = node.next;
        }
        return node;
    }

    @Override
    public int indexOf(Object target) {
        for (int i = 0; i < this.size; i++) {
            Node currentNode = this.getNode(i);
            if (currentNode == null) {
                return -1;
            } else if (currentNode.cargo.equals(target)) {
                return i;
            }
        }
        
        return -1;
    }

    /** Checks whether an element of the array is the target.
     * 
     * Handles the special case that the target is null.
     * 
     * @param target
     * @param object
     */
    private boolean equals(Object target, Object element) {
        if (target == null) {
            return element == null;
        } else {
            return target.equals(element);
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        E[] array = (E[]) toArray();
        return Arrays.asList(array).iterator();
    }

    @Override
    public int lastIndexOf(Object target) {
        Node node = head;
        int index = -1;
        for (int i=0; i<size; i++) {
            if (equals(target, node.cargo)) {
                index = i;
            }
            node = node.next;
        }
        return index;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public boolean remove(Object obj) {
        int targetIndex = this.indexOf(obj);

        if (targetIndex == -1) {
            return false;
        } else if (targetIndex == 0) {
			Node nodeToAppend = null;
            
            if (targetIndex < this.size - 1) {
                nodeToAppend = this.getNode(targetIndex + 1);
            }

            this.head = nodeToAppend;
            this.size--;
            return true;
        } else {
            Node previousNode = this.getNode(targetIndex - 1);
            Node nodeToAppend = null;
            
            if (targetIndex < this.size - 1) {
                nodeToAppend = this.getNode(targetIndex + 1);
            }

            previousNode.next = nodeToAppend;
            this.size--;
            return true;
        }
    }

    @Override
    public E remove(int index) {
        // TODO: fill this in
        return null;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean flag = true;
        for (Object obj: collection) {
            flag &= remove(obj);
        }
        return flag;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E set(int index, E element) {
        Node node = getNode(index);
        E old = node.cargo;
        node.cargo = element;
        return old;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        // TODO: classify this and improve it.
        int i = 0;
        MyLinkedList<E> list = new MyLinkedList<E>();
        for (Node node=head; node != null; node = node.next) {
            if (i >= fromIndex && i <= toIndex) {
                list.add(node.cargo);
            }
            i++;
        }
        return list;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for (Node node=head; node != null; node = node.next) {
            // System.out.println(node);
            array[i] = node.cargo;
            i++;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();        
    }
}
