package com.chuxin.wechat.fake.sound;

import android.media.AudioManager;
import android.media.SoundPool;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.app.MyApp;

import java.util.Hashtable;

/**
 * 音效
 * Created by chao on 2018/3/26.
 */

public class SoundWrapper {
    private SoundPool pool;
    private Hashtable<String, Integer> soundMap = new Hashtable<>();

    public static final String SOUND_SUCCESS = "sound_success";
    public static final String SOUND_ALERT = "sound_alert";

    private static class SoundHolder {
        public static final SoundWrapper INSTANCE = new SoundWrapper();
    }

    public static SoundWrapper get() {
        return SoundHolder.INSTANCE;
    }

    public void init() {
        pool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        soundMap.put(SOUND_SUCCESS, pool.load(MyApp.get(), R.raw.sound_success, 1));
        soundMap.put(SOUND_ALERT, pool.load(MyApp.get(), R.raw.sound_alert, 1));
    }

    public void playSuccess () {
        pool.play(soundMap.get(SOUND_SUCCESS), 1, 1, 0, 0, 1);
    }

    public void playAlert () {
        pool.play(soundMap.get(SOUND_ALERT), 1, 1, 0, 0, 1);
    }
}
