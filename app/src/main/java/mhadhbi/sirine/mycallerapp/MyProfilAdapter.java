package mhadhbi.sirine.mycallerapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyProfilAdapter extends BaseAdapter {

    Context con ;

    ArrayList<Profil> data ;

    MyProfilAdapter(Listprofil con , ArrayList<Profil> data)
    {
        this.con = con ;
        this.data = data ;

    }
    @Override
    public int getCount() {
        return data.size() ;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Creation de prototype
//        Button b =new Button(con ) ;

        //convertir code XML

        LayoutInflater inf = LayoutInflater.from(con) ;
        View v = inf.inflate(R.layout.view_profil,null) ;

        //recupiration des sous viewsw / Holder
        TextView tvname = v.findViewById((R.id.tvname_prof));
        TextView tvnumber = v.findViewById((R.id.tvnumber_prof));
        TextView tvlastname= v.findViewById((R.id.tvlastname_prof));

        ImageView imageDelete= v.findViewById((R.id.imageViewdelete_prof));
        ImageView imdEdit= v.findViewById((R.id.imageViewedite_prof));
        ImageView imagecall= v.findViewById((R.id.imageViewcall_prof));
        ProfileManager pf = new ProfileManager(con);
        pf.open();


        // recuperation de la donnee
        Profil p = data.get(position) ;

        //affecter le view/HOLDER: sont view
        tvname.setText(p.name);
        tvnumber.setText(p.number);
        tvlastname.setText(p.lastNamee);

//        imagecall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i =new Intent() ;
//                i.setAction(Intent.ACTION_DIAL);
//                i.setData(Uri.parse("tel:"+p.number));
//                con.startActivity(i);
//
//            }
//        });

//action sur les HOLDER
//        imageDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                AlertDialog.Builder alert = new AlertDialog.Builder(con ) ;
//                alert.setTitle("Suppression") ;
//                alert.setMessage("confirmre le suppresion ");
//                alert.setPositiveButton("conformer", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        data.remove(position).// supprision
//                                notifyDataSetChange();// refresh
//                    }
//                });
//                alert.setNeutralButton("exit",null) ;
//                alert.show();
//
//
//
//            }
//        });

        return  v ;
    }}
