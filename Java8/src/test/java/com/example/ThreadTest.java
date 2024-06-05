package com.example;

import java.util.concurrent.*;

/**
 * @author WangYunwei [2024-05-31]
 */
public class ThreadTest {

    class TestA implements Runnable{

        @Override
        public void run() {
            System.out.println("Runnable: "+Thread.currentThread().getId());
        }
    }

    class TestB implements Callable{

        @Override
        public Object call() throws Exception {

            long id = Thread.currentThread().getId();
            System.out.println("Callable: "+id);
            return id;
        }
    }

    public static void main(String[] args){

        System.out.println("main: "+Thread.currentThread().getId());
        ThreadTest threadTest = new ThreadTest();

        TestA testA = threadTest.new TestA();
        new Thread(testA).start();

        TestB testB = threadTest.new TestB();
        FutureTask futureTask = new FutureTask<>(testB);
        new Thread(futureTask,"有线程返回").start();
        try {
            System.out.println("Future: "+ futureTask.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        // Lambda 写法
        new Thread(() -> System.out.println("new Thread:"+Thread.currentThread().getId())).start();
        // 这是匿名内部类写法
        new Thread() {
            public void run(){
                System.out.println("new Thread:"+ this.getId());
            }
        }.start();
    }
}
