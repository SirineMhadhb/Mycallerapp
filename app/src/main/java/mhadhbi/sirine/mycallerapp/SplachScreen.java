package mhadhbi.sirine.mycallerapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;

public class SplachScreen extends AppCompatActivity {

    private static final int SPLASH_TIMEOUT = 5000; // Splash screen duration in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splach);

       
        ImageView splashLogo = findViewById(R.id.splash_logo);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplachScreen.this, MainActivity.class);

                startActivity(intent);

                finish();
            }
        }, SPLASH_TIMEOUT);
    }
}
