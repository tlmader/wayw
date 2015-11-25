package uno.wayw;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailUserActivityFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String FRIEND_PARAM = "parm1";

    private User friend;
    //private FrameLayout containerLayout;
    private TextView containerLayout;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param friend The User to make the fragment friend details.
     * @return A new instance of fragment DetailUserActivityFragment.
     */
    public static DetailUserActivityFragment newInstance(User friend) {
        DetailUserActivityFragment fragment = new DetailUserActivityFragment();
        Bundle args = new Bundle();
        args.putParcelable(FRIEND_PARAM, friend);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailUserActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            friend = getArguments().getParcelable(FRIEND_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_user, container, false);
        //containerLayout = (FrameLayout) view.findViewById(R.id.container);
        containerLayout = (TextView) view.findViewById(R.id.friendInfo);

        if (friend != null) {
            containerLayout.setText("User Name: " + friend.userName);
        }

        return view;
    }
}
