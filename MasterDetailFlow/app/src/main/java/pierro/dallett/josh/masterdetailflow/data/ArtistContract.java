package pierro.dallett.josh.masterdetailflow.data;

import android.app.SearchManager;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jpierro on 7/10/2015.
 */

public class ArtistContract {

    public static final String CONTENT_AUTHORITY = "pierro.dallett.josh.artistProvider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_ARTIST = ArtistEntry.TABLE_NAME;

    public static final class ArtistEntry implements BaseColumns {

        //Use BASE_CONTENT_URI to create the unique URI for Artist Table that apps will use to contact the content provider
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ARTIST).build();

        //When the Cursor returned for a given URI by the ContentProvider contains >1 items.
        public static final String CONTENT_ITEMS_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY  + "/" + PATH_ARTIST;
        // When the Cursor returned for a given URI by the ContentProvider contains 1 item.
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_ARTIST;

        public static final String TABLE_NAME = "artist_table";
        public static final String COLUMN_ARTIST = "artist";
        public static final String SUGGEST_COLUMN_TEXT_1 = SearchManager.SUGGEST_COLUMN_TEXT_1;

        //Columns to display.
        public static final String sColumnsToDisplay [] =
                new String[] {
                        ArtistContract.ArtistEntry._ID,
                        ArtistContract.ArtistEntry.COLUMN_ARTIST,
                        ArtistContract.ArtistEntry.SUGGEST_COLUMN_TEXT_1
                };

        public static final int[] sColumnResIds =  new int[] {1,2,3};

       /** return Uri that points to the row containing a given id.
                ** @param id
                ** @return Uri
       */
        public static Uri buildUri(Long id) {
            return ContentUris.withAppendedId(CONTENT_URI,
                    id);


        }
    }

}
