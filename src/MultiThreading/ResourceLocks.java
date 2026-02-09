package MultiThreading;

import java.util.concurrent.locks.ReentrantLock;

public class ResourceLocks {
    boolean isAvailable = false;

    public void producer( ReentrantLock lock) {

        try{
            lock.lock();
            System.out.println("Lock acquired by producer");
            isAvailable = true;
            Thread.sleep(1000);
        } catch (Exception e){
            //
        }
        finally {
            lock.unlock();
            System.out.println("Lock released by producer");
        }
    }
}
