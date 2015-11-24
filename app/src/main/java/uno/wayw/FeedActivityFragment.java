package uno.wayw;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class FeedActivityFragment extends Fragment {

    private UserAdapter adapter;
    private ArrayList<User> friends;

    private FeedActivityFragmentItemCLickListener itemCLickListener;
    private RecyclerView recyclerView;
    public User currentUser;

    public FeedActivityFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FeedActivityFragmentItemCLickListener) {
            itemCLickListener = (FeedActivityFragmentItemCLickListener) context;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        recyclerView =  (RecyclerView) view.findViewById(R.id.friendList);

        // create ArrayAdapter and use it to bind tags to the ListView

        adapter = new UserAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        friends = new ArrayList<>();
        FeedActivity feedActivity = (FeedActivity) getActivity();
        //Get the global variable "currentUser" from FeedActivity
        currentUser = feedActivity.getLoggedInUser();
        initFriends(currentUser);

        return view;
    }

    public void initFriends(User loggedInUser) {
        friends = loggedInUser.getFriends();
    }

    // Create the basic adapter extending from RecyclerView.Adapter
    // Note that we specify the custom ViewHolder which gives us access to our views
    public class UserAdapter extends RecyclerView.Adapter<ViewHolder> {

        // Usually involves inflating a layout from XML and returning the holder
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View friendView = inflater.inflate(R.layout.friend, parent, false);

            friendView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    int itemPosition = recyclerView.getChildAdapterPosition(v);
                    User item = friends.get(itemPosition);

                    itemCLickListener.onItemClick(item);
                }

            });

            // Return a new holder instance
            return new ViewHolder(friendView);
        }

        // Involves populating data into the item through holder
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            User friend = friends.get(position);
            viewHolder.textView.setText(friend.getUserName() );
        }

        // Return the total count of items
        @Override
        public int getItemCount() {
            return friends.size();
        }
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView textView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.friendName);
        }
    }

    public interface FeedActivityFragmentItemCLickListener {
        public void onItemClick(User friend);
    }

}

/**
public FeedActivityFragment() {
}

public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_feed, container, false);



    return view;
}
}
 **/
