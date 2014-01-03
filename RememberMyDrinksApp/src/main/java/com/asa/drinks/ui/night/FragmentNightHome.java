package com.asa.drinks.ui.night;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asa.drinks.R;
import com.asa.drinks.model.contracts.DrinksContract.DrinkCountEntry;
import com.asa.drinks.ui.AsaBaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentNightHome extends AsaBaseFragment {
	public static final String TAG = "FragmentNightHome";
	
	public static FragmentNightHome newInstance(){
		FragmentNightHome frag = new FragmentNightHome();
		return frag;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_night_home, container, false);
        ButterKnife.inject(this, v);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ContentResolver cr = getActivity().getContentResolver();
		Uri uri = ContentUris.withAppendedId(DrinkCountEntry.CONTENT_URI, 1);
		Cursor cursor = cr.query(uri, null, null, null, null);
		Log.d(TAG, "TEST");
	}

    @OnClick(R.id.night_btn_action)
    public void onActionButtonClicked(){
        
    }

}
