package com.example.myapplication.rxjava;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * @Description
 * @author ZhuYihang
 * @date 2020.9
 */
public class RxBus {

    private static volatile RxBus instance;
    private ConcurrentHashMap<Object, List<Subject>> subjectMap = new ConcurrentHashMap<Object, List<Subject>>();

    private String TAG = getClass().getSimpleName();

    private RxBus() {
    }

    public static RxBus getInstance(){
        if (instance == null) {
            synchronized (RxBus.class){
                if (instance == null){
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    public <T> Observable<T> register(Object tag){
        List<Subject> subjectList = subjectMap.get(tag);
        if (subjectList == null) {
            subjectList = new ArrayList<>();
            subjectMap.put(tag, subjectList);
        }
        Subject<T> subject = PublishSubject.create();
        subjectList.add(subject);
        Log.d(TAG, "register: " + tag + "  subject size: " + subjectList.size());
        return subject;
    }

    public void unRegister(Object tag){
        List<Subject> subjectList = subjectMap.get(tag);
        if (subjectList != null) {
            subjectMap.remove(tag);
        }
    }

    public void unRegister(Object tag, Subject subject){
        List<Subject> subjectList = subjectMap.get(tag);
        if (subjectList != null) {
            subjectList.remove(subject);
            Log.d(TAG, "unRegister:  " + tag);
        }
    }

    public void post(Object tag, Object content){
        List<Subject> subjectList = subjectMap.get(tag);
        if (subjectList != null) {
            for (Subject subject : subjectList) {
                subject.onNext(content);
            }
        }
    }



}

