package com.dzy.commemlib.rx;

import android.util.Log;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/** 
 * Created by dengziyue on 2017/8/21.
 */

public class RetryWhenTimeout implements Function<Observable<Throwable>, ObservableSource<?>> {

    private final int max;
    private int count;
    private int retryDelayMillis;
    
    public RetryWhenTimeout(int max, int retryDelayMillis){
        this.max = max;
        this.retryDelayMillis = retryDelayMillis;
    }
    
    public RetryWhenTimeout(int max) {
        this.max = max;
        retryDelayMillis = 500;
    }

    @Override
    public ObservableSource<?> apply(@NonNull final Observable<Throwable> throwableObservable) throws Exception {
        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                if (throwable instanceof SocketTimeoutException){
                    if (count++ <max){
                        Log.d("RetryWhenTimeout", "SocketTimeout , now retry...");
                        return Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS);
                    }
                }
                return Observable.error(throwable);   
                
            }
        });
    }
}