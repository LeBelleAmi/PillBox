package lebelleami.com.pillbox.controller;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import lebelleami.com.pillbox.R;

public class LoginActivity extends AppCompatActivity {

    AppCompatTextView textView1, textView2, textView3, textView4, textView5, heading;
    AppCompatButton login_button;
    TextInputEditText forgotPassword;
    Dialog passwordDialog, signUpDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        passwordDialog = new Dialog(this);
        signUpDialog = new Dialog(this);

        textView1 = findViewById(R.id.login_google);
        textView2 = findViewById(R.id.login_facebook);
        textView3 = findViewById(R.id.login_twitter);
        textView4 = findViewById(R.id.forgot_password);
        textView5 = findViewById(R.id.create_account);



        login_button = findViewById(R.id.login_app);
        Typeface font = Typeface.createFromAsset(getAssets(), "font/fontawesomee.ttf");
        textView1.setTypeface(font);
        textView2.setTypeface(font);
        textView3.setTypeface(font);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startActivity = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(startActivity);
                finish();
            }
        });


        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateAccountDialog();
            }
        });

    }


    public void showDialog(){

        passwordDialog.setContentView(R.layout.layout_forgot_password);

        //edit text takes the username or email for authentification
        forgotPassword = passwordDialog.findViewById(R.id.forgotpassword_text_view);
        heading = passwordDialog.findViewById(R.id.big_text);
        //Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "font/montaga.ttf");
        //heading.setTypeface(font);

        Button positiveButton = passwordDialog.findViewById(R.id.reset_button);
        Button negativeButton = passwordDialog.findViewById(R.id.cancel_button);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordDialog.dismiss();
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordDialog.dismiss();
            }
        });

        passwordDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        passwordDialog.show();
    }


    public void showCreateAccountDialog(){

        signUpDialog.setContentView(R.layout.layout_create_account);

        //edit text takes the username or email for authentification

        TextView signUp1 = signUpDialog.findViewById(R.id.signup_google);
        TextView signUp2 = signUpDialog.findViewById(R.id.signup_facebook);
        TextView signUp3 = signUpDialog.findViewById(R.id.signup_twitter);


        Typeface font = Typeface.createFromAsset(getAssets(), "font/fontawesomee.ttf");
        signUp1.setTypeface(font);
        signUp2.setTypeface(font);
        signUp3.setTypeface(font);


        ImageView cancelButton = signUpDialog.findViewById(R.id.close);
        Button positiveButton = signUpDialog.findViewById(R.id.signup_button);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpDialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpDialog.dismiss();
            }
        });

        signUpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        signUpDialog.show();
    }



}
