package MultiThreading;

public class SharedResource {

    boolean isItemPresent = false;

    public synchronized void produceItem() {
        isItemPresent = true;
        System.out.println("producer thread calling notify method");
        notifyAll();
    }

    public synchronized void consumeItem() {
        System.out.println("consume thread inside consumeItem() method");
        if(!isItemPresent){
            try{
                System.out.println("consumer thread waiting for item");
                wait();
                System.out.println("consumer thread woke up");
            }catch (Exception e){
                // System.out.println("Consumer thread waiting for item");
            }
        }
        isItemPresent = false;
    }
}
