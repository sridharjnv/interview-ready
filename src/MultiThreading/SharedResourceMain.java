package MultiThreading;

public class SharedResourceMain {

    public static void main(String[] args) {
        SharedResource sharedResourceObj = new SharedResource();

        Thread producerThread = new Thread(() -> {
            try{
                Thread.sleep(2000);
            }catch(Exception e){
                //
            }
            sharedResourceObj.produceItem();
        });

        Thread consumerThread = new Thread(() -> {
            sharedResourceObj.consumeItem();
        });

        producerThread.start();
        consumerThread.start();
    }
}
