package MultiThreading;

public class MultiThreadChildClass extends Thread{

    @Override
    public void run() {
        System.out.println("Inside Child Thread: " + Thread.currentThread().getName());
    }
}
