package ru.vyazankin.treads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MyCounter {

    public static void main(String[] args) throws InterruptedException {
        MyCounter myCounter = new MyCounter();
        myCounter.test();
    }

    /*
    В случае, если счетчик потоконебезопасен, общий счетчик для 2х потоков даст число, отличное от 4000,
    т.к. в него одновременно пишут 3 потока
     */
    private void test() throws InterruptedException {
        Counter counter1 = new Counter();
        Counter counter2 = new Counter();
        Counter counterCommon = new Counter();

        Thread thread1 = new Thread(new IncThread(counter1, counterCommon, 1000));
        Thread thread2 = new Thread(new IncThread(counter2, counterCommon, 1000));
        Thread thread3= new Thread(new IncThread(counterCommon, counterCommon, 1000));

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();


        System.out.println(counter1.get());
        System.out.println(counter2.get());
        System.out.println(counterCommon.get());

    }

    private class IncThread implements Runnable{

        private Counter counter1;
        private Counter counter2;
        private int iterations;

        public IncThread(Counter counter1, Counter counter2, int iterations) {
            this.counter1 = counter1;
            this.counter2 = counter2;
            this.iterations = iterations;
        }

        @Override
        public void run() {
            for (int i = 0; i < iterations; i++) {
                counter1.incAndGet();
                counter2.incAndGet();
            }
        }
    }

    private class Counter {
        long value;

        Lock lock = new ReentrantLock();

        public long get() {
            return value;
        }

        public long incAndGet() {
            return incAndGet(1L);
        }

        public long incAndGet(long inc) {
            lock.lock();
            this.value += inc;
            lock.unlock();
            return value;
        }

        public long decAndGet() {
            return incAndGet(-1L);
        }

        public long decAndGet(long dec) {
            return incAndGet(-dec);
        }
    }
}