package ru.vyazankin.lists;

public class Test {
    public static void main(String[] args) {

        System.out.println("=============================MyLinkedList===================================");
        testBaseList(new MyLinkedList<>());

        System.out.println("=============================MyArrayList===================================");
        testBaseList(new MyArrayList<>());
    }


    private static void testBaseList(BaseList<String> list){

        list.add("Первый");
        list.add("Второй");
        list.add("Третий");
        list.add("Четвертый");
        list.add("Четвертый");
        list.add("Шестой");
        System.out.println("Add result");
        for (int i = 0; i < list.getCount(); i++) {
            System.out.println(list.get(i));
        }


        list.set(4, "Пятый");
        System.out.println("\nSet result");
        for (int i = 0; i < list.getCount(); i++) {
            System.out.println(list.get(i));
        }


        list.insert(0, "0");
        list.insert(2, "1");
        list.insert(4, "2");
        list.insert(6, "3");
        list.insert(8, "4");
        list.insert(10, "5");
        list.insert(12, "6");
        System.out.println("\nInsert result");
        for (int i = 0; i < list.getCount(); i++) {
            System.out.println(list.get(i));
        }

        list.remove(1);
        list.remove(10);
        System.out.println("\nRemove #1 result");
        for (int i = 0; i < list.getCount(); i++) {
            System.out.println(list.get(i));
        }

        list.remove(0);
        list.remove(0);
        list.remove(7);
        list.remove(7);
        System.out.println("\nRemove #2 result");
        for (int i = 0; i < list.getCount(); i++) {
            System.out.println(list.get(i));
        }

        list.clear();
        System.out.println("\nClear result");
        for (int i = 0; i < list.getCount(); i++) {
            System.out.println(list.get(i));
        }
    }
}
