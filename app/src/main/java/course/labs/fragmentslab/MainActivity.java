package course.labs.fragmentslab;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity implements
		FriendsFragment.SelectionListener {

	private static final String TAG = "Lab-Fragments";

	private FriendsFragment mFriendsFragment;
	private FeedFragment mFeedFragment;
    //private FriendsFragment mContent;
	
	private int mPosition = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        Log.i(TAG, "MainActivity onCreate");

		setContentView(R.layout.main_activity);

        // If the layout is single-pane, create the FriendsFragment
		// and add it to the Activity

		if (!isInTwoPaneMode()) {
            mFriendsFragment = new FriendsFragment();

            // add fragment to the fragment container layout
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, mFriendsFragment);
            transaction.commit();

		} else {

			// Otherwise, save a reference to the FeedFragment for later use					

			mFeedFragment = (FeedFragment) getFragmentManager()
					.findFragmentById(R.id.feed_frag);
		}

        if (savedInstanceState != null) {

            // Restore last state for checked position.
            mPosition = savedInstanceState.getInt("pos", 0);
            Log.i(TAG, "MainActivity position:"+ mPosition);

            mFriendsFragment = (FriendsFragment)getFragmentManager().getFragment( savedInstanceState, "mFriendsFragment");

            if (isInTwoPaneMode())
                this.onItemSelected(mPosition);

        }
		

	}

	// If there is no fragment_container ID, then the application is in
	// two-pane mode

	private boolean isInTwoPaneMode() {

		return findViewById(R.id.fragment_container) == null;
	
	}

	// Display selected Twitter feed

	public void onItemSelected(int position) {

		mPosition = position;

		
		Log.i(TAG, "Entered onItemSelected(" + position + ")");

		// If there is no FeedFragment instance, then create one

		if (mFeedFragment == null)
			mFeedFragment = new FeedFragment();

		// If in single-pane mode, replace single visible Fragment

		if (!isInTwoPaneMode()) {
            Log.i(TAG, "replace the fragment_container");

			 FragmentTransaction transaction = getFragmentManager().beginTransaction();
	         transaction.replace(R.id.fragment_container, mFeedFragment);
	         transaction.addToBackStack(null);
	         transaction.commit();


			// execute transaction now
			getFragmentManager().executePendingTransactions();

		}

		// Update Twitter feed display on FriendFragment
		mFeedFragment.updateFeedDisplay(position);

	}
	
	@Override
	public void onSaveInstanceState (Bundle outState){
		super.onSaveInstanceState(outState);
		
		Log.i(TAG, "MainActivity onSaveInstanceState");
		
		outState.putInt("pos", mPosition);
        getFragmentManager().putFragment(outState, "mFriendsFragment", mFriendsFragment);
	}

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG, "MainActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG, "MainActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG, "MainActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(TAG, "MainActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "MainActivity onDestroy");
    }


}
