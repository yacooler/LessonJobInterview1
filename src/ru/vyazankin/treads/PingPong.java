package ru.vyazankin.treads;


public class PingPong {

    /*
     * Основная мысль алгоритма следующая
     * 1 - Текущий поток отпечатывает сообщение
     * 2 - Текущий поток будит другой поток
     * 3 - Текущий поток засыпает
     * т.к. всё это делается в рамках синхронизированной функции, а сами потоки все время в бесконечном цикле,
     * когда засыпает текущий поток к функции получает доступ другой поток, и так до бесконечности.
     */

    public static void main(String[] args) {
        PingPong pingPong = new PingPong();
        pingPong.test();
    }


    private void test(){
        Thread ping = new Thread(new MessageTransmitter("ping"));
        Thread pong = new Thread(new MessageTransmitter("pong"));
        pong.start();
        ping.start();
    }


    protected synchronized void printMessage(String message) throws InterruptedException {
        System.out.println(Thread.currentThread().toString() + " " + message);
        notify();
        wait();
    }

    protected class MessageTransmitter implements Runnable{

        private String message;
        public MessageTransmitter(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            while (true){
                try {
                    printMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
