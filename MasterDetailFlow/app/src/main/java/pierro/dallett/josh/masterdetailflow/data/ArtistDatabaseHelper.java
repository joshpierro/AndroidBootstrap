package pierro.dallett.josh.masterdetailflow.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by jpierro on 7/10/2015.
 */
public class ArtistDatabaseHelper  extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "artist_db";
    private static int DATABASE_VERSION = 12;

    final String SQL_CREATE_ARTIST_TABLE =
            "CREATE TABLE "
                    + ArtistContract.ArtistEntry.TABLE_NAME + " ("
                    + ArtistContract.ArtistEntry._ID + " INTEGER PRIMARY KEY, "
                    + ArtistContract.ArtistEntry.SUGGEST_COLUMN_TEXT_1 + " TEXT NULL"
                    + " );";


    //create DB
    public ArtistDatabaseHelper(Context context) {
        super(context,
                context.getCacheDir()
                        + File.separator
                        + DATABASE_NAME,
                null,
                DATABASE_VERSION);
    }

    public String getSearchTerm(int id){
        SQLiteDatabase artistDatabse = this.getWritableDatabase();
        String artistQuery = String.format("SELECT * FROM artist_table WHERE _ID = %s", String.valueOf(id)) ;

        Cursor cursor = artistDatabse.rawQuery(artistQuery, null);
        String searchTerm = "";
        if (cursor.moveToFirst()){
            ArrayList<Artist> artistList = new ArrayList<Artist>();
            do{
                Artist artist = new Artist();
                artist.Id = id;
                artist.Artist = cursor.getString(1);
                artistList.add(artist);
            }while (cursor.moveToNext());

         searchTerm = artistList.get(0).Artist;
        };
        artistDatabse.close();

        return searchTerm;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ARTIST_TABLE);
        db.execSQL("INSERT INTO artist_table (suggest_text_1) VALUES ('Picasso')");
        db.execSQL("INSERT INTO artist_table (suggest_text_1) VALUES ('Titian')");
        db.execSQL("INSERT INTO artist_table (suggest_text_1) VALUES ('Munch')");
        db.execSQL("INSERT INTO artist_table (suggest_text_1) VALUES ('Escher')");
        db.execSQL("INSERT INTO artist_table (suggest_text_1) VALUES ('Warhol')");
        db.execSQL("INSERT INTO artist_table (suggest_text_1) VALUES ('Koons')");
        db.execSQL("INSERT INTO artist_table (suggest_text_1) VALUES ('Gilbert and George')");
        db.execSQL("INSERT INTO artist_table (suggest_text_1) VALUES ('Witkin')");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ArtistContract.ArtistEntry.TABLE_NAME);
        onCreate(db);
    }

}
