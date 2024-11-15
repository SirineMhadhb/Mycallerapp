package mhadhbi.sirine.mycallerapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mhadhbi.sirine.mycallerapp.MainActivity2;
import mhadhbi.sirine.mycallerapp.Profil;

public class addprofil extends AppCompatActivity {

    Button btnback, btncancel, btnsave;
    EditText edename, edelastname, edenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addprofil);

        btnback = findViewById(R.id.btn_back);
        btnsave = findViewById(R.id.btn_save);
        btncancel = findViewById(R.id.btn_cancel);
        edename = findViewById(R.id.ed_name);
        edelastname = findViewById(R.id.ed_lastname);
        edenumber = findViewById(R.id.ed_number);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMainActivity();

            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearInputFields();

            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();

            }
        });
    }

    private void navigateToMainActivity() {
        Intent i = new Intent(addprofil.this, MainActivity2.class);
        startActivity(i);
    }

    private void clearInputFields() {
        edename.setText("");
        edelastname.setText("");
        edenumber.setText("");
    }

    private void saveProfile() {
        // Création d'une instance de ProfileManager
        ProfileManager profileManager = new ProfileManager(addprofil.this);

        // Ouverture de la base de données
        profileManager.open();

        // Récupération des données des champs de saisie
        String name = edename.getText().toString();
        String lastName = edelastname.getText().toString();
        String number = edenumber.getText().toString();

        // Validation
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(number)) {
            Toast.makeText(addprofil.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Inserer du profil dans la base de données
        long result = profileManager.insert(name, lastName, number);

        // Fermer  la base de données
        profileManager.close();

        // Vérification du succès de l'insertion
        if (result != -1) {

            Profil p = new Profil(name, lastName, number);
            MainActivity2.data.add(p);

            Toast.makeText(addprofil.this, "Profil enregistré avec succès", Toast.LENGTH_SHORT).show();


            navigateToMainActivity();
        } else {

            Toast.makeText(addprofil.this, "Échec de l'enregistrement du profil", Toast.LENGTH_SHORT).show();

        }
    }
}