import java.util.stream.IntStream;

/*public class Runner {
    private static final int FIRST_NUMBER =1;
    private static final int LAST_NUMBER = 1000;
    public static void main(final String[] args){
        int sum = 0;
        for(int i = FIRST_NUMBER; i<=LAST_NUMBER; i++){
            sum += i;
        }
        System.out.println(sum);
 */

public class Task1 {
    private static final int FROM_NUMBER_FIRST_THREAD =1; //� ������ ������ ������� ����� ����� �� 1 �� 500
    private static final int TO_NUMBER_FIRST_THREAD = 500;

    private static final int FROM_NUMBER_SECOND_THREAD=501;//�� ������ ��������� ������� ����� ����� �� 501 �� 1000
    private static final int TO_NUMBER_SECOND_THREAD=1000;
    private static final String TEMPLATE_MESSAGE_THREAD_NAME_AND_NUMBER ="%s : %d\n";  //��� �������� ������ � �����


    public static void main(final String[] args) throws InterruptedException {
        final TaskSummingNumbers firstTask = new TaskSummingNumbers(FROM_NUMBER_FIRST_THREAD, TO_NUMBER_FIRST_THREAD);//�������� ��������� ������ ��������� � �����������
        final Thread firstThread = new Thread(firstTask);
        firstThread.start();

        final TaskSummingNumbers secondTask = new TaskSummingNumbers(FROM_NUMBER_SECOND_THREAD, TO_NUMBER_SECOND_THREAD);//�������� ��������� ������ ��������� � �����������
        final Thread secondThread = new Thread(secondTask);
        secondThread.start();

        waitForTaskFinished(firstThread,secondThread);
        final int resultNumber = firstTask.getResultNumber() + secondTask.getResultNumber();//���������� �������� ������ ���������, �������� ��������� ���������� ���� �������
        printThreadNameAndNumber(resultNumber); //������� ��������� �� �������
    }
    private static void printThreadNameAndNumber(final int number){
        System.out.printf(TEMPLATE_MESSAGE_THREAD_NAME_AND_NUMBER,Thread.currentThread().getName(),number);
    }
    private static void waitForTaskFinished(final Thread... threads) throws InterruptedException {
        for(final Thread thread : threads){
            thread.join(); //join ������������� ������ �������� ������ �� ��������� ������ ������ �� ������� �� ��� ������
        }
    }
    private static final class TaskSummingNumbers implements Runnable{
        private static final int INITIAL_VALUE_RESULT_NUMBER=0; //��������, ������� ����� ������ � resultNumber ����� ������ �����������

        private final int fromNumber;  //�������� �� �������� ���������� ������������
        private final int toNumber;    // �� ������ ����� ����� ������������
        private int resultNumber;

        public TaskSummingNumbers(final int fromNumber, final int toNumber) {
            this.fromNumber = fromNumber;
            this.toNumber = toNumber;
            this.resultNumber =INITIAL_VALUE_RESULT_NUMBER;
        }

        public int getResultNumber(){
            return this.resultNumber;
        }

        @Override
        public void run() {
            IntStream.rangeClosed(this.fromNumber, this.toNumber).forEach(i->this.resultNumber+=i);//��� ����� � �������� Stream ������������� � resultNumber � �������� ���������, ������� ��������� � resultNumber
            printThreadNameAndNumber(this.resultNumber);//����� ��������, ��� ����� �������� ���� ������
        }
    }
}
