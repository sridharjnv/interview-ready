package MultiThreading;

import java.util.concurrent.locks.ReentrantLock;

public class LockMain {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        ResourceLocks resource1 = new ResourceLocks();
        Thread t1 = new Thread(() -> {
            resource1.producer(lock);
        });

        ResourceLocks resource2 = new ResourceLocks();
        Thread t2 = new Thread(() -> {
            resource2.producer(lock);
        });

        t1.start();
        t2.start();
    }

}
