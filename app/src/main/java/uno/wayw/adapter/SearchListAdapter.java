package uno.wayw.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import uno.wayw.R;
import uno.wayw.app.AppController;
import uno.wayw.data.User;

/**
 * Created by Breezy on 12/2/15.
 */
public class SearchListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<User> searchItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public SearchListAdapter(Activity activity, List<User> searchItems) {
        this.activity = activity;
        this.searchItems = searchItems;
    }

    @Override
    public int getCount() {
        return searchItems.size();
    }

    @Override
    public Object getItem(int location) {
        return searchItems.get(location);
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
            convertView = inflater.inflate(R.layout.search_item, null);
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView userName = (TextView) convertView.findViewById(R.id.userName);
        TextView name = (TextView) convertView
                .findViewById(R.id.name);
        //NetworkImageView profilePic = (NetworkImageView) convertView
        //        .findViewById(R.id.profilePic);

        User person = searchItems.get(position);

        userName.setText(person.getUserName());

        // Check for empty status message
        name.setText(person.getName());
        name.setVisibility(View.VISIBLE);

        // user profile pic
        //breprofilePic.setImageUrl(person.getProfilePic(), imageLoader);

        return convertView;
    }
}
