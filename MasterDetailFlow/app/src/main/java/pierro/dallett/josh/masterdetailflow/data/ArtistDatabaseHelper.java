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
    private static int DATABASE_VERSION = 13;

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
            ArrayList<ArtistCacheItem> artistList = new ArrayList<ArtistCacheItem>();
            do{
                ArtistCacheItem artist = new ArtistCacheItem();
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
        db.execSQL("INSERT INTO artist_table (suggest_text_1) VALUES ('Bad Brains')");
        db.execSQL("INSERT INTO artist_table (suggest_text_1) VALUES ('Crass')");
        db.execSQL("INSERT INTO artist_table (suggest_text_1) VALUES ('Sex Pistols')");
        db.execSQL("INSERT INTO artist_table (suggest_text_1) VALUES ('Dead Milkmen')");
        db.execSQL("INSERT INTO artist_table (suggest_text_1) VALUES ('False Prophets')");
        db.execSQL("INSERT INTO artist_table (suggest_text_1) VALUES ('Dead Kennedys')");
        db.execSQL("INSERT INTO artist_table (suggest_text_1) VALUES ('Subhmans')");
        db.execSQL("INSERT INTO artist_table (suggest_text_1) VALUES ('Butthole Surfers')");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ArtistContract.ArtistEntry.TABLE_NAME);
        onCreate(db);
    }

}
