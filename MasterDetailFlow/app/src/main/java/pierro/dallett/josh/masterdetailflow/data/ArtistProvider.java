package pierro.dallett.josh.masterdetailflow.data;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
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

        //TODO un-hard code the parameters and operators used in the query
        //These are hardcoded to support the fuzzy search from the search widget
        //However, the search is the only thing this provider is used for.

        projection = new String[]{"_ID", SearchManager.SUGGEST_COLUMN_TEXT_1,"_ID AS " + SearchManager.SUGGEST_COLUMN_INTENT_DATA};
        sortOrder = SearchManager.SUGGEST_COLUMN_TEXT_1;
        selection = String.format("%s Like ?",SearchManager.SUGGEST_COLUMN_TEXT_1);
        selectionArgs[0] = selectionArgs[0] + "%";

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(ArtistContract.ArtistEntry.TABLE_NAME);
        Cursor cursor = queryBuilder.query(mArtistDatabase,projection,selection,selectionArgs,null,null,sortOrder + " LIMIT 25");
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
