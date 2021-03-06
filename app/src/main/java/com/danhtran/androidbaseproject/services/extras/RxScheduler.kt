package com.danhtran.androidbaseproject.services.extras

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by DanhTran on 5/31/2019.
 */
object RxScheduler {

    /**
     * Apply scheduler:
     * observable
     * .subscribeOn(Schedulers.computation())
     * .observeOn(AndroidSchedulers.mainThread());
     */
    fun <T> applyLogicSchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * Apply scheduler io for call api
     */
    fun <T> applyIoSchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(3)       //retry 3 times

            /*.retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) {
                                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                                    int retryCount = 0;
                                    int delay = 5;
                                    int maxRetries = 2; //2 times

                                    @Override
                                    public ObservableSource<?> apply(Throwable throwable) {
                                        if (retryCount < maxRetries && throwable instanceof SocketTimeoutException) {
                                            retryCount++;
                                            return Observable.timer(delay, TimeUnit.MILLISECONDS);
                                        } else {
                                            return Observable.error(throwable);
                                        }
                                    }
                                });
                            }
                        })*/
        }
    }

    /**
     * Apply scheduler with newThread:
     * observable
     * .subscribeOn(Schedulers.newThread())
     * .observeOn(AndroidSchedulers.mainThread());
     */
    fun <T> applyNewThreadSchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * onStop with subscription
     */
    fun onStop(disposable: Disposable?) {
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
        }
    }


    /*
     */
    /**
     * onNext with subscriber and item
     *//*
    public static <T> void onNext(Subscriber<T> subscriber, T item) {
        if (!subscriber.isUnsubscribed()) {
            subscriber.onNext(item);
        }
    }

    */
    /**
     * onError with subscriber and exception
     *//*
    public static void onError(Subscriber<?> subscriber, Exception e) {
        if (!subscriber.isUnsubscribed()) {
            subscriber.onError(e);
        }
    }

    */
    /**
     * onCompleted with subscriber
     *//*
    public static void onCompleted(Subscriber<?> subscriber) {
        if (!subscriber.isUnsubscribed()) {
            subscriber.onCompleted();
        }
    }

    */
    /**
     * onStop with subscription
     *//*
    public static void onStop(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }


    */
    /**
     * Run method in aSync mode
     *
     * @return Subscription
     *//*
    public static <T> Subscription aSyncTask(Observable.OnSubscribe<T> onSubscribe) {
        return aSyncTask(onSubscribe, null);
    }

    */
    /**
     * Run method in aSync mode
     *
     * @return Subscription
     *//*
    public static <T> Subscription aSyncTask(Observable.OnSubscribe<T> onSubscribe,
                                             rx.functions.Action1<? super T> onNext) {
        return aSyncTask(onSubscribe, onNext, null);
    }

    */
    /**
     * Run method in aSync mode
     *//*
    public static <T> Subscription aSyncTask(Observable.OnSubscribe<T> onSubscribe,
                                             rx.functions.Action1<? super T> onNext,
                                             rx.functions.Action1<Throwable> onError) {
        return aSyncTask(onSubscribe, onNext, onError, null);
    }

    */
    /**
     * Run method in aSync mode
     *//*
    public static <T> Subscription aSyncTask(Observable.OnSubscribe<T> onSubscribe,
                                             rx.functions.Action1<? super T> onNext,
                                             rx.functions.Action1<Throwable> onError,
                                             rx.functions.Action0 onCompleted) {
        return safeSubscribe(Observable.create(onSubscribe).compose(RxScheduler.applyLogicSchedulers()),
                onNext, onError, onCompleted);
    }

    */
    /**
     * Run method in new thread with async mode
     *//*
    public static <T> Subscription aSyncTaskNewThread(Observable.OnSubscribe<T> onSubscribe) {
        return aSyncTaskNewThread(onSubscribe, null);
    }

    */
    /**
     * Run method in new thread with async mode
     *//*
    public static <T> Subscription aSyncTaskNewThread(Observable.OnSubscribe<T> onSubscribe,
                                                      rx.functions.Action1<? super T> onNext) {
        return aSyncTaskNewThread(onSubscribe, onNext, null, null);
    }

    */
    /**
     * Run method in new thread with async mode
     *//*
    public static <T> Subscription aSyncTaskNewThread(Observable.OnSubscribe<T> onSubscribe,
                                                      rx.functions.Action1<? super T> onNext,
                                                      rx.functions.Action1<Throwable> onError) {
        return aSyncTaskNewThread(onSubscribe, onNext, onError, null);
    }

    */
    /**
     * Run method in new thread with async mode
     *//*
    public static <T> Subscription aSyncTaskNewThread(Observable.OnSubscribe<T> onSubscribe,
                                                      rx.functions.Action1<? super T> onNext,
                                                      rx.functions.Action1<Throwable> onError,
                                                      rx.functions.Action0 onCompleted) {
        return safeSubscribe(Observable.create(onSubscribe).compose(RxScheduler.applyNewThreadSchedulers()),
                onNext, onError, onCompleted);
    }

    */
    /**
     * Run method on Ui
     *
     * @return Subscription
     *//*
    public static Subscription runOnUi(rx.functions.Action1<? super Object> onNext) {
        return safeSubscribe(Observable
                        .create(subscriber -> subscriber.onNext(true))
                        .compose(RxScheduler.applyLogicSchedulers()),
                onNext);
    }

    */
    /**
     * Run method with sync mode
     *
     * @return Subscription
     *//*
    public static <T> Subscription syncTask(Observable.OnSubscribe<T> onSubscribe) {
        return safeSubscribe(Observable.create(onSubscribe));
    }

    */
    /**
     * Safe Subscribe
     *//*
    public static <T> Subscription safeSubscribe(Observable<T> observable) {
        return safeSubscribe(observable, null);
    }

    */
    /**
     * Safe Subscribe
     *//*
    public static <T> Subscription safeSubscribe(Observable<T> observable,
                                                 rx.functions.Action1<? super T> onNext) {
        return safeSubscribe(observable, onNext, null);
    }

    */
    /**
     * Safe Subscribe
     *//*
    public static <T> Subscription safeSubscribe(Observable<T> observable,
                                                 rx.functions.Action1<? super T> onNext,
                                                 rx.functions.Action1<Throwable> onError) {
        return safeSubscribe(observable, onNext, onError, null);
    }

    */
    /**
     * Safe Subscribe
     *//*
    public static <T> Subscription safeSubscribe(Observable<T> observable,
                                                 rx.functions.Action1<? super T> onNext,
                                                 rx.functions.Action1<Throwable> onError,
                                                 rx.functions.Action0 onCompleted) {
        if (onNext == null) onNext = t -> {
        };
        if (onError == null) onError = throwable -> {
        };
        if (onCompleted == null) onCompleted = () -> {
        };

        return observable.subscribe(onNext, onError, onCompleted);
    }
*/
}
