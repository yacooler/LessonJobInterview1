package ru.vyazankin.lists;

interface BaseList<T>{
    int getCount();
    T get(int index);
    int add(T element);
    void set(int index, T element);
    void insert(int index, T element);
    T remove(int index);
    void clear();
}