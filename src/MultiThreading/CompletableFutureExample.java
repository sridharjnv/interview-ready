package MultiThreading;

import java.util.concurrent.*;

public class CompletableFutureExample {

    public static void main(String[] args) {
        try{
            ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 1, 1,
                    TimeUnit.HOURS, new ArrayBlockingQueue<>(10),
                    Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

            //supplyAsync method
            CompletableFuture<String> asyncTask1 = CompletableFuture.supplyAsync(() ->{
                // this is the task that needs to be performed asynchronously
                return "task completed";
            }, poolExecutor);

            //thenApply method
            CompletableFuture<String> asyncTask2 = CompletableFuture.supplyAsync(() ->{
                return "Concepts and";
            }, poolExecutor).thenApply((String val) -> {
                return val + " Coding";
            });

            //thenCompose method
            CompletableFuture<String> asyncTask3 = CompletableFuture
                    .supplyAsync(() -> {
                        System.out.println("Thread name which runs 'supplyAsync' method" +
                                Thread.currentThread().getName());
                        return "Java";
                    },poolExecutor)
                    .thenCompose((String val) -> {
                        return CompletableFuture.supplyAsync(() -> {
                                    System.out.println("Thread name which runs thenCompose: " + Thread.currentThread().getName());
                                    return val + "Programming";
                                },poolExecutor)
                                .thenApply((String val2) -> {
                                    return val2 + "Language";
                                });
                    });

            //then accept method
            CompletableFuture<String> asyncTask4 = CompletableFuture.supplyAsync(() -> {
                return "Hello";
            },poolExecutor);

            asyncTask4.thenAccept((String val) -> {
                System.out.println("Printing value");
            });

            //thenCombine method with BiFunction
            CompletableFuture<Integer> asyncTask5 = CompletableFuture.supplyAsync(() ->{
                return 10;
            },poolExecutor);
            CompletableFuture<String> asyncTask6 = CompletableFuture.supplyAsync(() -> {
                return "k";
            });

            CompletableFuture<String> combinedTask = asyncTask5
                    .thenCombine(asyncTask6,(Integer val1, String val2)-> val1 + val2);

            System.out.println(asyncTask1.get());
            System.out.println(asyncTask2.get());
            System.out.println(asyncTask3.get());
            System.out.println(asyncTask4.get());
            System.out.println(combinedTask.get());
        } catch (Exception e){
            //
        }
    }

}
