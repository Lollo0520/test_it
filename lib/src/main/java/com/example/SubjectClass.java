package com.example;

import rx.Subscriber;
import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

/**
 * Created by Jet Wang on 2016/8/5.
 */
public class SubjectClass {

    public static void main(String[] args) {
        replaySub();
    }

    private static void asyncSub(){
        AsyncSubject<Integer> as = AsyncSubject.create();
        as.onNext(1);
        as.onNext(2);
        as.subscribe(System.out::println, e-> System.out.println(e.getMessage()));
        as.onNext(3);
        as.onError(new Throwable("err"));
        as.onCompleted();
    }

    private static void behaviorSub(){
        BehaviorSubject<Integer> bs = BehaviorSubject.create(12);
        bs.onNext(1);
        bs.onNext(2);
        bs.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(integer);
            }
        });

        bs.onNext(3);
        bs.onCompleted();
    }

    private static void publishSub(){
        PublishSubject<Integer> ps = PublishSubject.create();
        ps.onNext(1);
        ps.subscribe(System.out::println);
        ps.onNext(2);
        ps.onNext(3);
        ps.onCompleted();
    }

    private static void replaySub(){
        ReplaySubject<Integer> rs = ReplaySubject.create();
        rs.onNext(1);
        rs.onNext(2);
        rs.onNext(3);
        rs.onCompleted();

        rs.subscribe(System.out::println);
    }
}
