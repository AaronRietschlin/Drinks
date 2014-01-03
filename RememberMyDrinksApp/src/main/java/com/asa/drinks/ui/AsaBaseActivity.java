package com.asa.drinks.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.asa.drinks.R;
import com.asa.drinks.utils.ActivityUtils;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
public class AsaBaseActivity extends FragmentActivity {

    private static final String CURRENT_FRAGMENT_TAG = "fragment_tag";
    protected FragmentManager mFm;
    private String mCurrentFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFm = getSupportFragmentManager();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!TextUtils.isEmpty(mCurrentFragmentTag)) {
            outState.putString(CURRENT_FRAGMENT_TAG, mCurrentFragmentTag);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Displays the fragment inside of the Activity.
     * <strong>Deprecated</strong> - You should use one of replaceFragment or addFragment.
     */
    @Deprecated()
    protected void displayFragment(Fragment fragment, boolean animate) {
        // Populate the Fragment.
        FragmentTransaction ft = mFm.beginTransaction();
        if (animate) {
            setAnimation(ft);
        }
        ft.add(R.id.fragment_container, fragment);
        ft.commit();
    }

    /**
     * Displays the fragment inside of the Activity. This one sets a tag for the
     * fragment.
     * <p/>
     * <strong>Deprecated</strong> - You should use one of replaceFragment or addFragment.
     */
    @Deprecated()
    protected void displayFragment(Fragment fragment, String tag, boolean animate) {
        // Populate the Fragment.
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (animate) {
            setAnimation(ft);
        }
        ft.add(R.id.fragment_container, fragment, tag);
        mCurrentFragmentTag = tag;
        ft.commit();
    }

    /**
     * Restores the current fragment if it can. This will only be successful if you utilize the
     * {@link #mCurrentFragmentTag}.
     *
     * @param savedInstanceState
     */
    protected void restoreFragmentIfPossible(Bundle savedInstanceState) {
        mCurrentFragmentTag = savedInstanceState.getString(CURRENT_FRAGMENT_TAG);
        ActivityUtils.restoreFragmentIfPossible(mFm, savedInstanceState, mCurrentFragmentTag);
    }

    protected void restoreFragmentIfPossible(Bundle savedInstanceState, String tag) {
        ActivityUtils.restoreFragmentIfPossible(mFm, savedInstanceState, tag);
    }

    /**
     * Sets the animation on the FragmentTransaction. If nothing is set, then it
     * defaults to the animations that are included in the library. Recommended
     * that you just use those.
     *
     * @param ft
     * @return
     */
    private FragmentTransaction setAnimation(FragmentTransaction ft) {
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        return ft;
    }

    /**
     * Removes a fragment from the Activity.
     *
     * @param fragmentToRemove
     */
    public void removeFragment(Fragment fragmentToRemove) {
        ActivityUtils.removeFragment(mFm, fragmentToRemove);
    }

    /**
     * Replaces the current fragment with the fragment passed in.
     *
     * @param newFragment    The fragment to display.
     * @param addToBackStack Tells whether or not to add this transaction to the back
     *                       stack.
     */
    public void replaceFragment(Fragment newFragment, boolean addToBackStack) {
        ActivityUtils.replaceFragment(mFm, newFragment, addToBackStack);
    }

    /**
     * Replaces the current fragment with the fragment passed in.
     *
     * @param newFragment    The fragment to display.
     * @param tag            The tag of the new fragment.
     * @param addToBackStack Tells whether or not to add this transaction to the back
     *                       stack.
     */
    public void replaceFragment(Fragment newFragment, String tag, boolean addToBackStack) {
        ActivityUtils.replaceFragment(mFm, newFragment, tag, addToBackStack);
    }

    /**
     * Replaces the current fragment with the fragment passed in. This can allow you to use the default
     * animation. The default animation is a slide infrom the right and a slide out to the left with
     * slide animations for pops as well.
     *
     * @param newFragment    The fragment to display
     * @param tag            The tag of the new fragment.
     * @param addToBackStack Tells whether or not to add this transaction to the back stack.
     * @param animate        Tells whether or not to perform the default animation.
     */
    public void replaceFragment(Fragment newFragment, String tag, boolean addToBackStack, boolean animate) {
        ActivityUtils.replaceFragment(mFm, newFragment, tag, addToBackStack, animate);
    }

    /**
     * @param newFragment    The fragment to display
     * @param tag            The tag of the new fragment.
     * @param addToBackStack Tells whether or not to add this transaction to the back stack.
     * @param animate        Tells whether or not to perform the default animation.
     * @param enterAnimId    The enter animation resource id
     * @param exitAnimId     The exit animation resource id
     * @param popEnterId     The enter animation resource id for when a pop occurs
     * @param popExitId      The exit animation resource id for when a pop occurs
     */
    public void replaceFragment(int containerId, Fragment newFragment, String tag, boolean addToBackStack, boolean animate, int enterAnimId, int exitAnimId, int popEnterId, int popExitId) {
        ActivityUtils.replaceFragment(mFm, containerId, newFragment, tag, addToBackStack, animate, enterAnimId, exitAnimId, popEnterId, popExitId);
    }

    public void replaceFragment(int containerId, Fragment newFragment, String tag, boolean addToBackStack, boolean animate) {
        ActivityUtils.replaceFragment(mFm, containerId, newFragment, tag, addToBackStack, animate);
    }

    /**
     * Adds a fragment.
     *
     * @param newFragment    The fragment to add
     * @param addToBackStack Tells whether or not to add this transaction to the back
     *                       stack.
     */
    public void addFragment(Fragment newFragment, boolean addToBackStack) {
        ActivityUtils.addFragment(mFm, newFragment, addToBackStack);
    }

    /**
     * Adds a fragment.
     *
     * @param newFragment    The fragment to add
     * @param tag            The tag of the fragment added.
     * @param addToBackStack Tells whether or not to add this transaction to the back
     *                       stack.
     */
    public void addFragment(Fragment newFragment, String tag, boolean addToBackStack) {
        ActivityUtils.addFragment(mFm, newFragment, tag, addToBackStack);
    }

    public void addFragment(Fragment newFragment, String tag, boolean addToBackStack, boolean animate) {
        ActivityUtils.addFragment(mFm, newFragment, tag, addToBackStack, animate);
    }

    public void addFragment(int containerId, Fragment newFragment, String tag, boolean addToBackStack, boolean animate, int enterAnimId, int exitAnimId, int popEnterId, int popExitId) {
        ActivityUtils.addFragment(mFm, containerId, newFragment, tag, addToBackStack, animate, enterAnimId, exitAnimId, popEnterId, popEnterId);
    }

    public void addFragment(int containerId, Fragment newFragment, String tag, boolean addToBackStack, boolean animate) {
        ActivityUtils.addFragment(mFm, containerId, newFragment, tag, addToBackStack, animate);
    }

    public void popBackStack() {
        mFm.popBackStack();
    }

}
