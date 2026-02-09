package MultiThreading;

public class SharedResource1 {

    boolean isAvailable = false;

    public synchronized void produce() {
        System.out.println("Lock Acquired");
        isAvailable = true;
        try{
            Thread.sleep(8000);
        } catch(Exception e) {
            //
        }
        System.out.println("Lock Released");
    }
}
