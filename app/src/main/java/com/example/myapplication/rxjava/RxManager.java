package com.example.myapplication.rxjava;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.Subject;

/**
 * @Description
 * @author ZhuYihang
 * @date 2020.9
 */
public class RxManager {

    private RxBus rxBus = RxBus.getInstance();

    private Map<String, Observable<?>> observableMap = new HashMap<>();

    public void on(String eventName, RxAction action) {
        Observable<?> mObservable = rxBus.register(eventName);
        observableMap.put(eventName, mObservable);
        mObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(action);
    }


    public void clear() {
        for (Map.Entry<String, Observable<?>> entry : observableMap.entrySet()) {
            rxBus.unRegister(entry.getKey(), (Subject) entry.getValue());
        }
        observableMap.clear();
    }

    public void post(Object tag, Object content) {
        rxBus.post(tag, content);
    }


}
