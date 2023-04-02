import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/* Сервер, с которым комуницируем в потоку 1. Отправляем запрос на сервер и получаем ответ от него.
Второй поток проверяет некоторые условия и если они выполняются, то он должен выключить сервер, предварительно
остановив комуникацию с сервером в первом потоке.
 */
public class Task2 {
    private static final String MESSAGE_REQUEST_WAS_SENT = "\nRequest was sent.";
    private static final int DURATION_IN_SECONDS_DELIVERING_RESPONSE = 1;
    private static final String MESSAGE_RESPONSE_WAS_RECEIVED = "Response was received.";
    private static final String MESSAGE_SERVER_WAS_STOPPED = "Server was stopped.";
    private static final int TIME_WAITING_IN_SECONDS_TO_START_STOPPING_THREAD = 5;
    private static final String MESSAGE_THREAD_WAS_INTERRUPTED = "Thread was interrupted";

    public static void main(final String[] args)
            throws InterruptedException {
        final Thread communicatingThread = new Thread(() -> {
          try {
              while (!Thread.currentThread().isInterrupted()) { //поток в бесконечном цикле имитирует подключение к серверу
                  doRequest(); //пока текущий поток не был прерва, мы общаемся с сервером
              } // как только поток прерывается, заканчивается работа в методе run и поток переходит в состояние terminated
          } catch (final InterruptedException interruptedException){
              System.out.println(MESSAGE_THREAD_WAS_INTERRUPTED);
          }
        });
        communicatingThread.start();

        final Thread stoppingThread = new Thread(()-> { //проверяет условие должен ли быть сервер выключен
          if(isServerShouldBeOffed()){ // если условие выполняется, то вызывается метод interrupt
              communicatingThread.interrupt();
              stopServer(); //имитируем остановку сервера
          }
        });
        SECONDS.sleep(TIME_WAITING_IN_SECONDS_TO_START_STOPPING_THREAD); // засыпает на 5 секунд
        stoppingThread.start();
    }

    private static void doRequest() throws InterruptedException {
    System.out.println(MESSAGE_REQUEST_WAS_SENT); // выводит, что запрос был отправлен
    SECONDS.sleep(DURATION_IN_SECONDS_DELIVERING_RESPONSE); // засыпает на одну секунду
    System.out.println(MESSAGE_RESPONSE_WAS_RECEIVED); //после того как просыпается, печает, что был получен ответ от сервера
    }
    private static boolean isServerShouldBeOffed(){ //метод определяющий должен ли быть сервер выключен
       return true;
    }

    private static void stopServer(){ //метод имитирующий остановку сервера
        System.out.println(MESSAGE_SERVER_WAS_STOPPED);
    }
}
