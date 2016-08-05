package com.example;

import java.util.concurrent.TimeUnit;

import rx.schedulers.Schedulers;

/**
 * Created by Jet Wang on 2016/8/5.
 */
public class SchedulerClass {

    public static void main(String[] args) {
        rxPeriodical();
    }

    private static void javaThread() {
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                System.out.println(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void rxWorker(){
        Schedulers.immediate().createWorker().schedule(() -> System.out.println("schedule"), 500, TimeUnit.MILLISECONDS);
    }

    private static void rxPeriodical(){
        Schedulers.trampoline().createWorker().schedulePeriodically(() -> System.out.println("rxPeriodical"), 500, 1000, TimeUnit.MILLISECONDS);
    }
}
