package cn.anwenchu.queue.consumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 消费者线程
 * Created by an_wch on 2018/5/28.
 */
public class Consumer implements Runnable {

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        System.out.println("启动消费者线程！");
        Random r = new Random();
        boolean isRunning = true;
        try {
            while (isRunning) {
                System.out.println("---------正从队列获取数据...----------");
                // 在给定的时间里，从队列中获取值，如果没有取到会抛出异常。
//                String data = queue.poll(2, TimeUnit.SECONDS);

                // 从队列中获取值，如果队列中没有值，线程会一直阻塞，直到队列中有值，并且该方法取得了该值。
                String data = queue.take();
                if (null != data) {
                    System.out.println("拿到数据：" + data);
                    System.out.println("正在消费数据：" + data);
                    Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));
                } else {
                    // 超过2s还没数据，认为所有生产线程都已经退出，自动退出消费线程。
//                    isRunning = false;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("退出消费者线程！");
        }
    }

    private BlockingQueue<String> queue;
    private static final int      DEFAULT_RANGE_FOR_SLEEP = 1000;
}
