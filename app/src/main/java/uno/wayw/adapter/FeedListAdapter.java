package uno.wayw.adapter;

/**
 * Created by Breezy on 11/29/15.
 */

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
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
import uno.wayw.data.FeedItem;
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
        FeedImageView feedImageView = (FeedImageView) convertView
                .findViewById(R.id.feedImage1);

        Fit item = feedItems.get(position);

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
            styleText.setText("#" + item.getStyle().toLowerCase());
            styleText.setVisibility(View.VISIBLE);
        } else {
            // url is null, remove from the view
            styleText.setVisibility(View.GONE);
        }

        // user profile pic

        final String TEMP_PIC = ("https://media-members.nationalgeographic.com/static-media/images/css_images/nationalGeographic_default_avatar.jpg");
        profilePic.setImageUrl(TEMP_PIC, imageLoader);

        // Feed image
        if (item.getImage() != null) {
            Uri imageUri = Uri.parse(item.getImage());
            feedImageView.setImageURI(imageUri);
        } else {
            feedImageView.setVisibility(View.GONE);
        }

        return convertView;
    }

}
