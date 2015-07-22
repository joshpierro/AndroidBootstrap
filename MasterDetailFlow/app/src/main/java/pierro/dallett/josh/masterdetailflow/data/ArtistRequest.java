package pierro.dallett.josh.masterdetailflow.data;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import pierro.dallett.josh.masterdetailflow.R;
import retrofit.RetrofitError;

/**
 * Created by pierro on 7/11/15.
 */

public class ArtistRequest extends AsyncTask<String,Void,ArtistsPager>{

    ArrayList<ArtistListItem> mArtistsList = new ArrayList<ArtistListItem>();
    private static final int MIN_ARRAY_SIZE = 1;

    private Context mContext;

    public ArtistRequest(Context context) {
        mContext = context;
    }

    @Override
    protected ArtistsPager doInBackground(String... params) {
        SpotifyApi api = new SpotifyApi();
        SpotifyService spotify = api.getService();
        ArtistsPager artistsPager = null;
        try{
            artistsPager = spotify.searchArtists(params[0]);
        }catch(RetrofitError retrofitError){
            Log.e("Retrofit Error", retrofitError.getBody().toString());
        }
        return artistsPager;
    }


    @Override
    protected void onPostExecute(ArtistsPager artistsPager) {

        super.onPostExecute(artistsPager);
        mArtistsList.clear();


        for (Artist artist : artistsPager.artists.items) {
            ArtistListItem artistListItem = new ArtistListItem();
            artistListItem.Id = artist.id;
            artistListItem.Name = artist.name;

            if(artist.images.size() >= MIN_ARRAY_SIZE){
                artistListItem.ImageURL = artist.images.get(artist.images.size()-1).url;
            }else{
                artistListItem.ImageURL = mContext.getString(R.string.placeholder_image);
            }

            mArtistsList.add(artistListItem);
        };

        if(mArtistsList.size() >= MIN_ARRAY_SIZE) {
            Settings.getInstance().ArtistList = mArtistsList;
           // mNoResultsLabel.setVisibility(View.GONE);
        }else{
         //   mNoResultsLabel.setVisibility(View.VISIBLE);
        }

        //ArtistListFragment.

        Intent intent = new Intent("recieveArtistUpdate");
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

        // mArtistListAdapter.notifyDataSetChanged();

    }


}
