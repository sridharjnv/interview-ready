package MultiThreading;

import java.util.concurrent.*;

public class ThreadPoolExecutorMain {

    public static void main(String[] args) {
         ThreadPoolExecutor executor = new ThreadPoolExecutor(2,4,
                 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2),
                 new CustomThreadFactory(),new CustomRejectHandler());

        for(int i =0;i<7;i++){
            executor.submit(() ->{
                try{
                    Thread.sleep(5000);
                } catch (Exception e){
                    //
                }
                System.out.println("Task Produced by : " + Thread.currentThread().getName());
            });
        }


        //fixed threadpool executor
        ExecutorService fixedExecutor = Executors.newFixedThreadPool(4);
        fixedExecutor.submit(() ->{});

        //cached threadpool executor
        ExecutorService cachedExecutor = Executors.newCachedThreadPool();
        cachedExecutor.submit(()->{});

        //single thread pool executor
        ExecutorService singleExecutor = Executors.newSingleThreadExecutor();
        singleExecutor.submit(()->{});
    }
}

class CustomThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r){
        Thread th = new Thread(r);
        th.setPriority(Thread.NORM_PRIORITY);
        th.setDaemon(false);

        return th;
    }

}

class CustomRejectHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor){
        System.out.println("Rejected execution: " + r.toString());
    }
}
