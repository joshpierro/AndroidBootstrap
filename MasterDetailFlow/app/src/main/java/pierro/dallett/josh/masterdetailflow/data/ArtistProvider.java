package pierro.dallett.josh.masterdetailflow.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by jpierro on 7/10/2015.
 */
public class ArtistProvider extends ContentProvider {

    static final String PROVIDER_NAME = ArtistContract.CONTENT_AUTHORITY;
    static final Uri CONTENT_URI = ArtistContract.ArtistEntry.CONTENT_URI;

    private SQLiteDatabase mArtistDatabase;

    @Override
    public boolean onCreate() {
        ArtistDatabaseHelper artistDatabaseHelper = new ArtistDatabaseHelper(getContext());
        mArtistDatabase = artistDatabaseHelper.getReadableDatabase();
        if(mArtistDatabase==null){
            return false;
        }else{
            return true;
        }
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(ArtistContract.ArtistEntry.TABLE_NAME);
        Cursor cursor = queryBuilder.query(mArtistDatabase,null,null,null,null,null,null);
       // cursor.getColumnName(2);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
