package MultiThreading;

import java.util.concurrent.*;

public class ThreadpoolExecutor2 {

    public static void main(String[] args) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1,
                TimeUnit.HOURS, new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        Future<?> futureObj = executor.submit(() -> {

            try {
                Thread.sleep(1000);
                System.out.println("this is the task, thread will execute");
            } catch (Exception e) {
                //
            }
        });

        System.out.println("isDone" + futureObj.isDone());


        try{
            futureObj.get(2, TimeUnit.SECONDS);
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        try{
            futureObj.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        System.out.println("isDone" + futureObj.isDone());
        System.out.println("isCancelled" + futureObj.isCancelled());
    }

}
