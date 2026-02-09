package MultiThreading;

public class ProducerConsumerLearning {

    public static void main(String[] args) {

        Buffer buffer = new Buffer(3);

        Thread producerThread = new Thread(() -> {

            try {
                for (int i = 0; i < 10; i++) {
                    buffer.produce(i);
                }
            } catch (Exception e) {
                //
            }
        });

        Thread consumerThread = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    buffer.consume();
                }
            } catch (Exception e) {
                //
            }
        });
    producerThread.start();
    consumerThread.start();
    }

}
