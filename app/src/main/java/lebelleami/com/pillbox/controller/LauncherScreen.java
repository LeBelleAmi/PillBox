package lebelleami.com.pillbox.controller;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.os.Handler;

import lebelleami.com.pillbox.R;

public class LauncherScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.lancherscreen);

        /*
         * Declare name of font used for pillbox
         */

        AppCompatTextView textView = findViewById(R.id.pillbox_text);
        AppCompatImageView imageView = findViewById(R.id.pillbox_icon);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transitions);
        textView.startAnimation(animation);
        imageView.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent startActivity = new Intent(LauncherScreen.this, IntroScreen.class);
                startActivity(startActivity);
                finish();
            }
        }, 5000);
    }
}
