package com.example.myapplication.rxjava;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @Description
 * @author ZhuYihang
 * @date 2020.9
 */
public abstract class RxAction implements Observer {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Object o) {
        call(o);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    protected abstract void call(Object content);
}
