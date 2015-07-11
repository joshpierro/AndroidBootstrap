package pierro.dallett.josh.masterdetailflow.data;

import android.app.SearchManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

/**
 * Created by jpierro on 7/10/2015.
 */
public class ArtistDatabaseHelper  extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "artist_db";
    private static int DATABASE_VERSION = 11;

    final String SQL_CREATE_ARTIST_TABLE =
            "CREATE TABLE "
                    + ArtistContract.ArtistEntry.TABLE_NAME + " ("
                    + ArtistContract.ArtistEntry._ID + " INTEGER PRIMARY KEY, "
                    + ArtistContract.ArtistEntry.COLUMN_ARTIST + " TEXT NOT NULL, "
                    + ArtistContract.ArtistEntry.SUGGEST_COLUMN_TEXT_1 + " TEXT NULL,"
                    + "suggest_text_1 TEXT NOT NULL"
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

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ARTIST_TABLE);
        db.execSQL("INSERT INTO artist_table (artist,suggest_text_1) VALUES ('A_test','A_test')");
        db.execSQL("INSERT INTO artist_table (artist,suggest_text_1) VALUES ('B_test','B_test')");
        db.execSQL("INSERT INTO artist_table (artist,suggest_text_1) VALUES ('C_test','C_test')");
        db.execSQL("INSERT INTO artist_table (artist,suggest_text_1) VALUES ('D_test','D_test')");
        db.execSQL("INSERT INTO artist_table (artist,suggest_text_1) VALUES ('E_test','E_test')");
        db.execSQL("INSERT INTO artist_table (artist,suggest_text_1) VALUES ('F_test','F_test')");
        db.execSQL("INSERT INTO artist_table (artist,suggest_text_1) VALUES ('G_test','G_test')");
        db.execSQL("INSERT INTO artist_table (artist,suggest_text_1) VALUES ('H_test','H_test')");

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ArtistContract.ArtistEntry.TABLE_NAME);
        onCreate(db);
    }

}
