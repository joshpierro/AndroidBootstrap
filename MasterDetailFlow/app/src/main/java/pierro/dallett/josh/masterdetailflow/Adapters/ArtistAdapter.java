package pierro.dallett.josh.masterdetailflow.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pierro.dallett.josh.masterdetailflow.R;
import pierro.dallett.josh.masterdetailflow.data.ArtistListItem;

/**
 * Created by pierro on 7/18/15.
 */
public class ArtistAdapter extends ArrayAdapter{

    private Context mContext;
    private List<ArtistListItem> mArtistList;
    private final String URLERROR = "Bad URL";

    public ArtistAdapter(Context context, int resource, List<ArtistListItem> artistList){
        super(context,resource,artistList);
        this.mContext = context;
        this.mArtistList = artistList;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_artists, parent, false);
        }

        View view = convertView;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        view = inflater.inflate(R.layout.list_item_artists, null);

        ArtistListItem artistListItem = mArtistList.get(position);

        TextView artistTextView = (TextView) view.findViewById(R.id.list_item_artist_textview);
        artistTextView.setText(artistListItem.Name);

        ImageView artistImageView = (ImageView) view.findViewById(R.id.list_item_artist_image);

        try{
            Picasso.with(mContext).load(artistListItem.ImageURL).transform(new RoundedTransformation(8, 4)).into(artistImageView);
        }catch(IllegalArgumentException e){
            Log.e(URLERROR, e.getMessage().toString());
        }

        return view;

    }

    public class RoundedTransformation implements com.squareup.picasso.Transformation {
        private final int radius;
        private final int margin;  // dp

        // radius is corner radii in dp
        // margin is the board in dp
        public RoundedTransformation(final int radius, final int margin) {
            this.radius = radius;
            this.margin = margin;
        }

        @Override
        public Bitmap transform(final Bitmap source) {

            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

            Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            canvas.drawRoundRect(new RectF(margin, margin, source.getWidth() - margin, source.getHeight() - margin), radius, radius, paint);

            if (source != output) {
                source.recycle();
            }


            return output;
        }

        @Override
        public String key() {
            return "rounded";
        }
    }
}
