package pers.cjh;

public class ThreadTest extends Thread {
    public ThreadTest(Runnable r){
        super(r);
    }

    public ThreadTest() {
        super();
    }

    @Override
    public void run() {
        super.run();
        int count = 0;
        System.out.println("ThreadTest");
        while(count++ < 10) {
            System.out.println(count);
            try {
                Thread.sleep(501);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
