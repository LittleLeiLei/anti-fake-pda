package com.chuxin.wechat.fake.scanner;

import java.util.ArrayList;
import java.util.List;

/**
 * 扫描枪
 * Created by chao on 2018/3/15.
 */

public class ScannerWrapper {

    private List<ScannerObserver> observers = new ArrayList<>();

    private ScannerWrapper() {}

    private static class ScannerHolder {
        private static ScannerWrapper INSTANCE = new ScannerWrapper();
    }

    public static ScannerWrapper instance () {
        return ScannerHolder.INSTANCE;
    }

    public void subscribe(ScannerObserver observer) {
        observers.add(observer);
    }

    public void unsubscribe(ScannerObserver observer) {
        observers.remove(observer);
    }

    public void publish(String data) {
        for (ScannerObserver observer : observers) {
            observer.onScanned(data);
        }
    }

    public interface ScannerObserver {
        void onScanned(String data);
    }
}
