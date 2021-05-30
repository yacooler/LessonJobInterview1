package ru.vyazankin.lists;

public class MyArrayList<T> implements BaseList<T>{

    protected int capacity;
    protected Object list[];
    protected int count;
    protected int START_CAPACITY = 4;
    protected int GAIN_PERCENT = 25;
    protected int GAIN_CAPACITY = 75;

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public T get(int index) {
        if (index > count - 1){
            throw new IndexOutOfBoundsException("Incorrect index " + index);
        }
        return (T) list[index];
    }

    @Override
    public int add(T element) {
        recap();
        list[count] = element;
        count++;
        return count - 1;
    }

    @Override
    public void set(int index, T element) {
        if (index > count - 1){
            throw new IndexOutOfBoundsException("Incorrect index " + index);
        }
        list[index] = element;
    }

    @Override
    public void insert(int index, T element) {
        if (index > count){
            throw new IndexOutOfBoundsException("Incorrect index " + index);
        }
        recap();
        if (count - index >= 0) System.arraycopy(list, index, list, index + 1, count - index);
        list[index] = element;
        count++;
    }

    @Override
    public T remove(int index) {
        if (index > count - 1){
            throw new IndexOutOfBoundsException("Incorrect index " + index);
        }

        T val = (T) list[index];
        if (count - index >= 0) System.arraycopy(list, index + 1, list, index, count - index);
        list[count - 1] = null;
        count--;
        return val;
    }

    @Override
    public void clear() {
        for (int i = 0; i < count; i++) {
            list[i] = null;
        }
        list = null;
        count = 0;
        capacity = START_CAPACITY;
    }

    protected void recap(){
        if (list == null) {
            list = new Object[START_CAPACITY];
            capacity = START_CAPACITY;
            return;
        }

        //Списку надо расти
        if (count >= (float) capacity * ((float) GAIN_CAPACITY / 100F)){
            capacity = (int) (capacity + (float) capacity * ((float) GAIN_PERCENT / 100F));
            Object newList[] = new Object[capacity];
            System.arraycopy(list,0, newList, 0, count);
            list = newList;
        }

    }
}
