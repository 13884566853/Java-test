package juc;

import java.util.concurrent.CountDownLatch;

/*
 * @title: ${NAME}
 * @description: TODO
 * @author wwt
 * @date ${DATE} ${TIME}
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(4);

        for (int i = 1; i <= 4 ; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"出门");
                countDownLatch.countDown();//计数-1
            }).start();
        }
        countDownLatch.await();//计数器归零，向下执行
        System.out.println("关门");
    }
}
