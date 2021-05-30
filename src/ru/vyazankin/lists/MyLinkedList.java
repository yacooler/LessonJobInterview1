package ru.vyazankin.lists;



public class MyLinkedList<T> implements BaseList<T>{

    protected Node head;
    protected Node tail;
    protected int count;

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public T get(int index) {
        if (index > count - 1){
            throw new IndexOutOfBoundsException("Incorrect index " + index);
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    @Override
    public int add(T element) {
        if (head == null){
            //Если добавляем самый первый элемент
            tail = new Node();
            head = new Node(null, tail, element);
            tail.previous = head;
        } else {
            //Если добавляем новый элемент вместо последнего
            Node newNode = new Node(tail.previous, tail, element);
            tail.previous.next = newNode;
            tail.previous = newNode;
        }
        count++;
        return count - 1;
    }

    @Override
    public void set(int index, T element) {
        if (index > count - 1){
            throw new IndexOutOfBoundsException("Incorrect index " + index);
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.value = element;
    }

    @Override
    public void insert(int index, T element) {
        if (index > count){
            throw new IndexOutOfBoundsException("Incorrect index " + index);
        }

        //Если добавляем в конец или список пуст и добавляем в начало
        if (index == count){
            add(element);
            return;
        }

        //Если добавляем вместо головы
        if (index == 0){
            Node newNode = new Node(null, head, element);
            head.previous = newNode;
            head = newNode;
            count++;
            return;
        }

        //Находим элемент, на место которого добавляем новый
        Node shifted = head;
        for (int i = 0; i < index; i++) {
            shifted = shifted.next;
        }

        //Создаем новый элемент и добавляем вместо текущего со сдвигом вправо (0й элемент при замене станет 1м)
        Node newNode = new Node(shifted.previous, shifted, element);
        shifted.previous.next = newNode;
        shifted.previous = newNode;
        count++;
    }


    @Override
    public T remove(int index) {
        if (index > count - 1){
            throw new IndexOutOfBoundsException("Incorrect index " + index);
        }

        //Если удаляем единственный элемент
        if (count == 1){
            T val = head.value;
            head = null;
            tail = null;
            count--;
            return val;
        }

        //Если удаляем голову
        if (index == 0){
            T val = head.value;
            head.next.previous = null;
            head = head.next;
            count--;
            return val;
        }


        //Нашли удаляемый элемент
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        if (current.previous != null){
            current.previous.next = current.next;
        }

        current.next.previous = current.previous;
        T val = current.value;
        count--;
        current = null;
        return val;

    }

    @Override
    public void clear() {
        while (count>0) {
            remove(0);
        }
    }

    protected class Node{
        protected Node previous;
        protected Node next;
        protected T value;

        public Node(Node previous, Node next, T value) {
            this.previous = previous;
            this.next = next;
            this.value = value;
        }

        public Node() {
        }
    }



}
