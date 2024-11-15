package mhadhbi.sirine.mycallerapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.Manifest;


public class MyRecycleProfilAdapter extends RecyclerView.Adapter<MyRecycleProfilAdapter.MyViewHolder> {

    Context con;
    ProfileManager pf;
    ArrayList<Profil> data;
    private ArrayList<Profil> filteredData; // Ajoutez cette ligne
    private static final int REQUEST_CALL = 1;
    public MyRecycleProfilAdapter(Context con, ArrayList<Profil> data) {
        this.con = con;
        this.data = data;
        pf = new ProfileManager(con);
    }

    @NonNull
    @Override
    public MyRecycleProfilAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(con);
        View v = inf.inflate(R.layout.view_profil, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecycleProfilAdapter.MyViewHolder holder, int position) {
        Profil p = data.get(position);
        holder.tvname.setText(p.name);
        holder.tvnumber.setText(p.number);
        holder.tvlastname.setText(p.lastNamee);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvname, tvnumber, tvlastname;
        ImageView imageDelete, imgEdit, imagecall;
        Button btn_enregister, btn_annuler;

        public MyViewHolder(@NonNull View v) {
            super(v);

            tvname = v.findViewById(R.id.tvname_prof);
            tvnumber = v.findViewById(R.id.tvnumber_prof);
            tvlastname = v.findViewById(R.id.tvlastname_prof);

            imageDelete = v.findViewById(R.id.imageViewdelete_prof);
            imgEdit = v.findViewById(R.id.imageViewedite_prof);
            imagecall = v.findViewById(R.id.imageViewcall_prof);

            btn_enregister = v.findViewById(R.id.btn_enrg);
            btn_annuler = v.findViewById(R.id.btn_Annuler);

            imagecall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        String phoneNumber = data.get(position).number;

                        // Vérifier la permission CALL_PHONE
                        if (ContextCompat.checkSelfPermission(con, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            // Si la permission est accordée, effectuer l'appel
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + phoneNumber));
                            con.startActivity(callIntent);
                        } else {
                            // Si la permission n'est pas accordée, demander à l'utilisateur
                            ActivityCompat.requestPermissions((Activity) con, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                        }
                    }
                }
            });

            imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(con);
                    alert.setTitle("Suppression");
                    alert.setMessage("Confirmer la suppression ");
                    alert.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                Profil deletedProfile = data.get(position);
                                data.remove(position);

                                pf.open();
                                pf.delete(deletedProfile.number);
                                notifyDataSetChanged();
                                pf.close();
                            } else {
                                // Handle the case where the position is invalid
                                // (This could happen if the item is no longer in the adapter)
                            }
                        }
                    });
                    alert.setNeutralButton("Exit", null);
                    alert.show();
                }
            });


            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create AlertDialog instance
                    AlertDialog alertDialog = new AlertDialog.Builder(con).create();

                    // Set the custom layout to the AlertDialog
                    LayoutInflater inflater = LayoutInflater.from(con);
                    View v1 = inflater.inflate(R.layout.edit, null);
                    alertDialog.setView(v1);

                    // Find views in the custom layout
                    EditText editName = v1.findViewById(R.id.editName);
                    EditText editLastName = v1.findViewById(R.id.editLastName);
                    EditText editNumber = v1.findViewById(R.id.editNumber);
                    Button btn_enregister = v1.findViewById(R.id.btn_enrg);
                    Button btn_annuler = v1.findViewById(R.id.btn_Annuler);

                    // Get the current data
                    int position = getAdapterPosition();
                    Profil currentProfile = data.get(position);

                    // Set the current values to EditText fields
                    editName.setText(currentProfile.name);
                    editLastName.setText(currentProfile.lastNamee);
                    editNumber.setText(currentProfile.number);

                    btn_enregister.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Récupérer les valeurs modifiées
                            String updatedName = editName.getText().toString();
                            String updatedLastName = editLastName.getText().toString();
                            String updatedNumber = editNumber.getText().toString();

                            // Mettre à jour les données dans la liste
                            Profil updatedProfile = data.get(position);
                            updatedProfile.name = updatedName;
                            updatedProfile.lastNamee = updatedLastName;
                            updatedProfile.number = updatedNumber;

                            // Mettre à jour la base de données
                            pf.open();
                            long result = pf.update(updatedProfile);
                            pf.close();

                            if (result != -1) {
                                // Notifier le changement aux observateurs
                                notifyDataSetChanged();

                                // Fermer la boîte de dialogue
                                alertDialog.dismiss();
                            } else {
                                // Gérer l'échec de la mise à jour de la base de données
                                // (Afficher un message d'erreur, journalisation, etc.)
                            }
                        }
                    });

                    btn_annuler.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Fermer la boîte de dialogue sans enregistrer
                            alertDialog.dismiss();
                        }
                    });

                    // Show the AlertDialog
                    alertDialog.show();
                }
            });



        }
    }
    public void setFilteredData(ArrayList<Profil> filteredData) {
        this.data = filteredData;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

}

