package MultiThreading;

public class Main {
    public static void main(String[] args) {

        //using runnable interface
        System.out.println("Going into main method: " + Thread.currentThread().getName());
        MultiThreadingLearning runnableObj = new MultiThreadingLearning();
        Thread thread = new Thread(runnableObj);
        thread.start();
        System.out.println("Finish main method: " + Thread.currentThread().getName());


        //using thread class
        MultiThreadChildClass myThread = new MultiThreadChildClass();
        myThread.start();
        System.out.println("End of program: " + Thread.currentThread().getName());
    }
}
