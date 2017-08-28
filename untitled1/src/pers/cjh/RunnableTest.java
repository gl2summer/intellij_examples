package pers.cjh;

public class RunnableTest implements Runnable {
    @Override
    public void run() {
        int count = 0;
        System.out.println("RunnableTest");
        while(count++ < 10) {
            System.out.println(count);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
