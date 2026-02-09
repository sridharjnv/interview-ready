package MultiThreading;

public class SharedResource1Main {
    public static void main(String[] args) {
        SharedResource1 resource = new SharedResource1();
        System.out.println("Main Thread started");

        Thread t1 = new Thread(() -> {
            System.out.println("Thread 1 started");
            resource.produce();
        });

        Thread t2 = new Thread(() -> {
            try{
                Thread.sleep(1000);
            } catch (Exception e){
                //
            }
            System.out.println("Thread 2 started");
            resource.produce();
        });

        t1.start();
        t2.start();

        try{
            Thread.sleep(3000);
        } catch (Exception e){
            //
        }
        System.out.println("Thread 1 is suspended");
//        t1.suspend();

        try{
            Thread.sleep(3000);
        } catch (Exception e){
            //
        }

//        t1.resume();
        System.out.println("main thread is finishing");
    }
}
