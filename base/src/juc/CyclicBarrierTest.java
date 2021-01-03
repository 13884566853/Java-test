package juc;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/*
 * @Author wwt
 * @Date 23:54 2021/1/3
 * @Description //TODO 
 **/
public class CyclicBarrierTest {
    
    /*
     * @Author wwt
     * @Date 23:54 2021/1/3
     * @Description //TODO 
     **/
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{
            System.out.println("召唤神龙成功");
        });

        for (int i = 1; i <= 7 ; i++) {
            final  int temp = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "获取了第" + temp +"颗龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }

            ).start();
        }
    }
}
