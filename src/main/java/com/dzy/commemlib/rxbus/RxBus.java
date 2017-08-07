package com.dzy.commemlib.rxbus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxBus {
    private static volatile RxBus defaultInstance;

    private final Subject<Object> bus;


    public RxBus() {
      bus = PublishSubject.create();
    }
    // 单例RxBus
    public static RxBus getDefault() {
        if (defaultInstance == null) {
            synchronized (RxBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new RxBus();
                }
            }
        }
        return defaultInstance ;
    }
    // 发送一个新的事件
    public void post (Object o) {
        bus.onNext(o);

    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable<T> toObservable (Class<T> eventType) {
        return bus.ofType(eventType);
    }
}