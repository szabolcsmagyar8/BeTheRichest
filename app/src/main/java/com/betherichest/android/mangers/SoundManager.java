package com.betherichest.android.mangers;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.betherichest.android.App;
import com.betherichest.android.R;

public class SoundManager {
    private static final int MAX_STREAMS = 5;
    private static final int streamType = AudioManager.STREAM_MUSIC;
    public static int soundBuy;
    public static int soundClick;
    public static int soundError;
    public static int soundAchievement;
    public static int soundBottle;
    public static int soundGambling;
    public static int soundPull;

    private static boolean loaded;
    public static SoundPool soundPool;

    public SoundManager(Activity activity) {
        activity.setVolumeControlStream(streamType);
        if (Build.VERSION.SDK_INT >= 21) {
            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);

            this.soundPool = builder.build();
        } else {
            this.soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });
        soundClick = this.soundPool.load(App.getContext(), R.raw.click, 1);
        soundBuy = this.soundPool.load(App.getContext(), R.raw.buy, 1);
        soundAchievement = this.soundPool.load(App.getContext(), R.raw.achievement, 1);
        soundError = this.soundPool.load(App.getContext(), R.raw.error, 1);
        soundBottle = this.soundPool.load(App.getContext(), R.raw.bottle, 1);
        soundGambling = this.soundPool.load(App.getContext(), R.raw.gambling, 1);
        soundPull = this.soundPool.load(App.getContext(), R.raw.pull, 1);
    }

    public static void playSound(int sound) {
        if (Game.soundDisabled) {
            return;
        }
        if (loaded) {
            soundPool.play(sound, 1, 1, 1, 0, 1f);
        }
    }
}
