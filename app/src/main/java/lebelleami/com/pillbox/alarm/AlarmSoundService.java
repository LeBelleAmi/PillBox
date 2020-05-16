package lebelleami.com.pillbox.alarm;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import androidx.annotation.Nullable;

import lebelleami.com.pillbox.R;

public class AlarmSoundService extends Service {
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

       //Start media player
        mediaPlayer = MediaPlayer.create(this, R.raw.beyond_doubt_2);
        mediaPlayer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //On destroy stop and release the media player
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
        }
    }
}
