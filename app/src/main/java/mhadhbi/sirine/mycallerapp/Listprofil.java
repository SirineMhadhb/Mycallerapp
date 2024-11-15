package mhadhbi.sirine.mycallerapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Listprofil extends AppCompatActivity {

    private static final String TAG = "Listprofil"; // Logging tag

    Button btnback;
    RecyclerView rv;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listprofil);

        btnback = findViewById(R.id.btn_back_list);
        search = findViewById(R.id.editsearch);
        rv = findViewById(R.id.rv);

        ProfileManager profileManager = new ProfileManager(Listprofil.this);
        profileManager.open();
        ArrayList<Profil> data = profileManager.selectAll();
        profileManager.close();

        MyRecycleProfilAdapter ad = new MyRecycleProfilAdapter(Listprofil.this, data);

        GridLayoutManager manager = new GridLayoutManager(Listprofil.this, 1, GridLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);
        rv.setAdapter(ad);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = s.toString().trim(); // Trim leading and trailing whitespaces
                Log.d(TAG, "Search Text: " + searchText);

                if (searchText.isEmpty()) {
                    ad.setFilteredData(data);
                } else {
                    ArrayList<Profil> filteredData = filter(data, searchText);
                    ad.setFilteredData(filteredData);
                }
            }
        });
    }

    private ArrayList<Profil> filter(ArrayList<Profil> profiles, String searchText) {
        if (profiles == null || profiles.isEmpty()) {
            Log.e(TAG, "Profiles list is null or empty.");
            return new ArrayList<>();
        }
        Log.d(TAG, "Filter method called. Search Text: " + searchText);

        ArrayList<Profil> filteredList = new ArrayList<>();

        for (Profil profile : profiles) {
            String profileName = profile.name.toLowerCase();
            String profileLastName = profile.lastNamee.toLowerCase();
            String profileNumber = profile.number.toLowerCase();

            Log.d(TAG, "Profile: " + profileName + " " + profileLastName + " " + profileNumber);

            if (profileName.contains(searchText) || profileLastName.contains(searchText) || profileNumber.contains(searchText)) {
                filteredList.add(profile);
                Log.d(TAG, "Added to Filtered List: " + profileName + " " + profileLastName + " " + profileNumber);
            }
        }
        Log.d(TAG, "Search Text: " + searchText);
        Log.d(TAG, "Filtered List Size: " + filteredList.size());

        Log.d(TAG, "Filtered List Size: " + filteredList.size());
        return filteredList;
    }
}
