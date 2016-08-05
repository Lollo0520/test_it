package com.example;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class SingleClass {

    public static void main(String[] args) {
        SingleClass instance = new SingleClass();
        instance.singleZip();
    }

    private void singleJust() {
        Single.just(3)
                .compose(integerSingle -> integerSingle.map(integer -> String.valueOf(integer + 2)))
                .subscribe(System.out::println);
    }

    private void singleConcat(){
        Single.concat(Single.just(1), Single.error(new Throwable("concat_err")))
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("concat_completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println(integer);
                    }
                });
    }

    private void singleCreate() {
        Single.create(new Single.OnSubscribe<Integer>() {
            @Override
            public void call(SingleSubscriber<? super Integer> singleSubscriber) {
                singleSubscriber.onSuccess(7);
//                singleSubscriber.onError(new Throwable("7Error"));
            }
        }).subscribeOn(Schedulers.computation())
                .subscribe(new SingleSubscriber<Integer>() {
            @Override
            public void onSuccess(Integer value) {
                System.out.println(value);
            }

            @Override
            public void onError(Throwable error) {
                System.out.println(error.getMessage());
            }
        });
    }

    private void singleFlatMap(){
        Single.just(11).flatMap(integer -> Single.just(String.valueOf(integer+1)))
                .subscribe(System.out::println);

        Single.just(11).map(integer -> Single.just(String.valueOf(integer + "map")))
                .subscribe(System.out::println);
    }

    private static Observable<List<String>> query(){
        List<String> s = Arrays.asList("Java", "Swift", "Javascript", "PHP");
        return Observable.just(s);
    }

    private void obsFlatMap(){
        query().flatMap(Observable::from)
                .subscribe(s -> System.out.println("_flatmap:"+ s));
    }

    private void obsFlatMapObs(){
        Single.just(1).flatMapObservable(integer -> Observable.just(String.valueOf(integer), "obs"))
                .subscribe(System.out::println);
    }

    private void singleOnErrorReturn(){
        Single.create(new Single.OnSubscribe<Integer>() {
            @Override
            public void call(SingleSubscriber<? super Integer> singleSubscriber) {
                singleSubscriber.onError(new Throwable("x"));
            }
        }).onErrorReturn(throwable -> 2).subscribe(System.out::println);
    }

    private void obsTooSingle(){
        Observable.just(123).toSingle().subscribe(System.out::println);
    }

    private void singleZip(){
        Single.zip(Single.just(12), Single.just("hello"), (integer, s) -> integer+s).subscribe(System.out::println);
    }
}
