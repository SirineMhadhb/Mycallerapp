

package mhadhbi.sirine.mycallerapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import mhadhbi.sirine.mycallerapp.MainActivity2;
import mhadhbi.sirine.mycallerapp.R;

public class MainActivity extends AppCompatActivity {
    // Déclaration des composants
    EditText edemail, edpwd;
    Button btnexit, btnlogin;
    CheckBox checkBox;

    public static final String PREFS_NAME = "MyPrefsFile";
    // Clé pour stocker l'état de connexion
    public static final String KEY_CONNECTED = "connected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "onCreate called");
        setContentView(R.layout.activity_main);

        // Récupération des composants
        btnexit = findViewById(R.id.btn_exit_auth);
        btnlogin = findViewById(R.id.btn_login_auth);
        edemail = findViewById(R.id.edemail_auth);
        edpwd = findViewById(R.id.edpwd_auth);
        checkBox = findViewById(R.id.checkBox);

        // Récupération de l'état de connexion depuis les préférences partagées
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean connected = preferences.getBoolean(KEY_CONNECTED, false);

        // Si l'utilisateur est déjà connecté, passer à l'écran d'accueil
        if (connected) {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
            finish(); // Fermer l'activité actuelle pour empêcher l'utilisateur de revenir en arrière avec le bouton retour
        }

        // Bouton exit
        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les chaînes saisies
                String email = edemail.getText().toString();
                String pwd = edpwd.getText().toString();

                Log.d("MainActivity", "Email: " + email + ", Password: " + pwd);

                if (email.equalsIgnoreCase("sirinee") && pwd.equals("123")) {
                    Log.d("MainActivity", "Login successful");
                    // Passage vers une autre activité
                    Intent i = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(i);
                } else {
                    Log.d("MainActivity", "Login failed");
                }

            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Sauvegarder l'état de la case à cocher dans les préférences partagées
                SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                editor.putBoolean(KEY_CONNECTED, isChecked);
                editor.apply();
            }
        });
    }
}
