package uno.wayw.adapter;

/**
 * Created by Breezy on 11/29/15.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import uno.wayw.R;
import uno.wayw.app.AppController;
import uno.wayw.data.Fit;

public class FeedListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Fit> feedItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public FeedListAdapter(Activity activity, List<Fit> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.feed_item, null);
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView ownerText = (TextView) convertView.findViewById(R.id.name);
        TextView timestamp = (TextView) convertView
                .findViewById(R.id.timestamp);
        TextView titleText = (TextView) convertView
                .findViewById(R.id.txtStatusMsg);
        TextView styleText = (TextView) convertView.findViewById(R.id.txtUrl);
        NetworkImageView profilePic = (NetworkImageView) convertView
                .findViewById(R.id.profilePic);
        ImageView feedImageView = (ImageView) convertView
                .findViewById(R.id.feedImage1);

        Fit item = feedItems.get(position);

        ownerText.setText(item.getOwner());

        // Converting timestamp into x ago format
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(item.getTimestamp()),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        timestamp.setText(timeAgo);

        // Check for empty status message
        if (!TextUtils.isEmpty(item.getTitle())) {
            titleText.setText(item.getTitle());
        } else {
            // status is empty, remove from view
            titleText.setVisibility(View.GONE);
        }

        // Checking for style
        if (item.getStyle() != null) {
            String style = "#" + item.getStyle().toLowerCase().replaceAll("\\s","");
            styleText.setText(style);
        } else {
            // url is null, remove from the view
            styleText.setVisibility(View.GONE);
        }

        // Feed image
        if (item.getImage() != null) {
            Bitmap image = convertImage(item.getImage());
            feedImageView.setImageBitmap(image);
        } else {
            feedImageView.setVisibility(View.GONE);
        }

        return convertView;
    }

    public Bitmap convertImage(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}