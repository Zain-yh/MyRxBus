package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.myapplication.rxjava.RxAction;
import com.example.myapplication.rxjava.RxBus;
import com.example.myapplication.rxjava.RxManager;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private String TAG = "RxBus_Test";
    private RxManager rxManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();

        rxManager = new RxManager();
        rxManager.on("123123", new RxAction() {
            @Override
            protected void call(Object content) {
                Log.d(TAG, "123 call: " + content);
            }
        });

        rxManager.on("321321", new RxAction() {
            @Override
            protected void call(Object content) {
                Log.d(TAG, "321 call: " + content);
            }
        });


    }

    public void postMessage(View view) {
        rxManager.post("123123", "11111");

    }

    public void postMessage2(View view) {
        rxManager.post("321321", "22222");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rxManager.clear();
    }

    public void test() {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Exception {
            }
        })
                .subscribeOn(AndroidSchedulers.mainThread())    //在 subscribeActual 方法中将source
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull Object o) {
                        Log.d(TAG, "onSubscribe: onNext");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onSubscribe: onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onSubscribe: onComplete");
                    }
                });
    }

    public void start(View view) {
        startActivity(new Intent(MainActivity.this, MainActivity2.class));
    }
}
