package mhadhbi.sirine.mycallerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Profil {
    String name , lastNamee , number ;

    public Profil(String name, String lastNamee, String number) {
        this.name = name;
        this.lastNamee = lastNamee;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Profil{" +
                "name='" + name + '\'' +
                ", lastNamee='" + lastNamee + '\'' +
                ", number='" + number + '\'' +
                '}';
    }

    public void notifyDataSetChange() {
    }

    public static class MyProfileHelper extends SQLiteOpenHelper {

        // Declaration of table name and field titles
        public static final String table_profil = "Profil";
        //public static final String col_id = "Identifiant";
        public static final String col_name = "Name";
        public static final String col_lastName = "LastName";
        public static final String col_number = "Number";

        String req = "create table " + table_profil + "("
                + col_number+ " Text primary Key ,"
                + col_name + " Text not null,"
                + col_lastName + " Text not null"

                + ")";

        public MyProfileHelper(@Nullable Context context, @Nullable String name,
                               @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(req);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + table_profil);
            onCreate(db);
        }
    }

    public static class ProfileManager {
        Context context;
        SQLiteDatabase database = null;

        // Constructor
        public ProfileManager(Context context) {
            this.context = context;
        }

        // Open the database
        public void open() {
            MyProfileHelper dbHelper = new MyProfileHelper(context, "sirine", null, 1);
            database = dbHelper.getWritableDatabase();
        }

        // Insert a new profile into the database
        public long insert(String name, String lastName, String number) {
            long a =0;
            ContentValues values = new ContentValues();
            values.put(MyProfileHelper.col_name, name);
            values.put(MyProfileHelper.col_lastName, lastName);
            values.put(MyProfileHelper.col_number, number);

            a=database.insert(MyProfileHelper.table_profil, null, values);
            return a ;
        }

        // Retrieve all profiles from the database
        public ArrayList<Profil> selectAll() {
            ArrayList<Profil> data = new ArrayList<>();
            Cursor cursor = database.query(
                    MyProfileHelper.table_profil,
                    new String[]{ MyProfileHelper.col_name, MyProfileHelper.col_lastName, MyProfileHelper.col_number},
                    null, null, null, null, null
            );

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                String name = cursor.getString(0);
                String lastName = cursor.getString(1);
                String number = cursor.getString(2);

                data.add(new Profil(name, lastName, number));
                cursor.moveToNext();
            }
            cursor.close();

            return data;
        }



        // Delete a profile from the database
        public long delete(String number) {
            long result = -1;
            result = database.delete(
                    MyProfileHelper.table_profil,
                    MyProfileHelper.col_number + "=" + number,
                    null
            );
            return result;
        }

        public void close() {
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
    }
}
