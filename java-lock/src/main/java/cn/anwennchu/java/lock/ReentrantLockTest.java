package cn.anwennchu.java.lock;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by an_wch on 2018/5/21.
 */
@Slf4j
@Data
public class ReentrantLockTest extends Thread {

    private String name2;

    public static ReentrantLock lock = new ReentrantLock();
    public static int i = 0;

    public ReentrantLockTest(String name) {
        this.name2 = name;
    }

    @Override
    public void run() {
        for (int j = 0; j < 20000000; j++) {
            lock.lock();
            try {
                log.info("{} : {}", this.getName(), i);
                i++;
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
//        ReentrantLockTest test1 = new ReentrantLockTest("thread1");
//        ReentrantLockTest test2 = new ReentrantLockTest("thread2");
//
//        test1.start();
//        test2.start();
//        test1.join();
//        test2.join();
    }




}

