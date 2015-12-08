package uno.wayw.adapter;

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
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import uno.wayw.FeedImageView;
import uno.wayw.R;
import uno.wayw.app.AppController;
import uno.wayw.data.Fit;

/**
 * Created by Breezy on 12/7/15.
 */
public class DetailListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Fit> detailItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public DetailListAdapter(Activity activity, List<Fit> feedItems) {
        this.activity = activity;
        this.detailItems = feedItems;
    }

    @Override
    public int getCount() {
        return detailItems.size();
    }

    @Override
    public Object getItem(int location) {
        return detailItems.get(location);
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
            convertView = inflater.inflate(R.layout.detail_item, null);
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
        FeedImageView feedImageView = (FeedImageView) convertView
                .findViewById(R.id.feedImage1);

        Fit item = detailItems.get(position);

        ownerText.setText(item.getOwner());

        // Converting timestamp into x ago format
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(item.getTimestamp()),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        timestamp.setText(timeAgo);

        // Chcek for empty status message
        if (!TextUtils.isEmpty(item.getTitle())) {
            titleText.setText(item.getTitle());
            titleText.setVisibility(View.VISIBLE);
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
            byte [] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
