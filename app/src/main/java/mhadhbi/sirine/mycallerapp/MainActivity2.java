package mhadhbi.sirine.mycallerapp;

import static mhadhbi.sirine.mycallerapp.MainActivity.KEY_CONNECTED;
import static mhadhbi.sirine.mycallerapp.MainActivity.PREFS_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    public static ArrayList<Profil> data = new ArrayList<Profil>();
    Button btnadd, btnquitter, btnlist , btnLogoutMain2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btnadd = findViewById(R.id.btn_add);
        btnlist = findViewById(R.id.btn_list);
        btnLogoutMain2 = findViewById(R.id.btn_logout_main2);


        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity2.this, addprofil.class);
                startActivity(i);

            }
        });

        btnlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity2.this, Listprofil.class);
                startActivity(i);

            }
        });

        btnLogoutMain2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Effacer l'état de connexion dans les préférences partagées
                SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                editor.putBoolean(KEY_CONNECTED, false);
                editor.apply();

                // Rediriger vers l'écran de connexion
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
                finish(); // Fermer l'écran actuel
            }
        });
    }
    }
