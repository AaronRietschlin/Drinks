package com.asa.drinks.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.asa.drinks.R;


public class ActivityUtils {

    /**
     * Sets the animation on the FragmentTransaction. If nothing is set, then it
     * defaults to the animations that are included in the library. Recommended
     * that you just use those.
     *
     * @param ft
     * @return
     */
    public static FragmentTransaction setAnimation(FragmentTransaction ft) {
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        return ft;
    }

    /**
     * Restores the current fragment if it can.
     *
     * @param savedInstanceState
     */
    public static void restoreFragmentIfPossible(FragmentManager fm, Bundle savedInstanceState, String tag) {
        Fragment fragment = fm.findFragmentByTag(tag);
        if (fragment != null && !fragment.isAdded()) {
            fm.beginTransaction().attach(fragment);
        }
    }

    /**
     * Removes a fragment from the Activity.
     *
     * @param fragmentToRemove
     */
    public static void removeFragment(FragmentManager fm, Fragment fragmentToRemove) {
        FragmentTransaction ft = fm.beginTransaction();
        setAnimation(ft);
        ft.remove(fragmentToRemove);
        ft.commit();
    }

    /**
     * Replaces the current fragment with the fragment passed in.
     *
     * @param newFragment    The fragment to display.
     * @param addToBackStack Tells whether or not to add this transaction to the back
     *                       stack.
     */
    public static void replaceFragment(FragmentManager fm, Fragment newFragment, boolean addToBackStack) {
        replaceFragment(fm, newFragment, null, addToBackStack, true);
    }

    /**
     * Replaces the current fragment with the fragment passed in.
     *
     * @param newFragment    The fragment to display.
     * @param tag            The tag of the new fragment.
     * @param addToBackStack Tells whether or not to add this transaction to the back
     *                       stack.
     */
    public static void replaceFragment(FragmentManager fm, Fragment newFragment, String tag, boolean addToBackStack) {
        replaceFragment(fm, newFragment, tag, addToBackStack, true);
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
    public static void replaceFragment(FragmentManager fm, Fragment newFragment, String tag, boolean addToBackStack, boolean animate) {
        FragmentTransaction ft = fm.beginTransaction();
        if (animate)
            setAnimation(ft);
        ft.replace(R.id.fragment_container, newFragment, tag);
        if (addToBackStack)
            ft.addToBackStack(null);
        ft.commit();
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
    public static void replaceFragment(FragmentManager fm, int containerId, Fragment newFragment, String tag, boolean addToBackStack, boolean animate, int enterAnimId, int exitAnimId, int popEnterId, int popExitId) {
        FragmentTransaction ft = fm.beginTransaction();
        if (animate) {
            if (popEnterId > 0 && popExitId > 0) {
                ft.setCustomAnimations(enterAnimId, exitAnimId, popEnterId, popExitId);
            } else if (enterAnimId > 0 && exitAnimId > 0) {
                ft.setCustomAnimations(enterAnimId, exitAnimId);
            }
        }
        ft.replace(containerId, newFragment, tag);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }

        ft.commit();
    }

    public static void replaceFragment(FragmentManager fm, int containerId, Fragment newFragment, String tag, boolean addToBackStack, boolean animate) {
        FragmentTransaction ft = fm.beginTransaction();
        if (animate) {
            setAnimation(ft);
        }
        ft.replace(containerId, newFragment, tag);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }

        ft.commit();
    }

    /**
     * Adds a fragment.
     *
     * @param newFragment    The fragment to add
     * @param addToBackStack Tells whether or not to add this transaction to the back
     *                       stack.
     */
    public static void addFragment(FragmentManager fm, Fragment newFragment, boolean addToBackStack) {
        addFragment(fm, newFragment, null, addToBackStack, true);
    }

    /**
     * Adds a fragment.
     *
     * @param newFragment    The fragment to add
     * @param tag            The tag of the fragment added.
     * @param addToBackStack Tells whether or not to add this transaction to the back
     *                       stack.
     */
    public static void addFragment(FragmentManager fm, Fragment newFragment, String tag, boolean addToBackStack) {
        addFragment(fm, newFragment, tag, addToBackStack, true);
    }

    public static void addFragment(FragmentManager fm, Fragment newFragment, String tag, boolean addToBackStack, boolean animate) {
        FragmentTransaction ft = fm.beginTransaction();
        if (animate)
            setAnimation(ft);
        ft.add(R.id.fragment_container, newFragment, tag);
        if (addToBackStack)
            ft.addToBackStack(null);
        ft.commit();
    }

    public static void addFragment(FragmentManager fm, int containerId, Fragment newFragment, String tag, boolean addToBackStack, boolean animate, int enterAnimId, int exitAnimId, int popEnterId, int popExitId) {
        FragmentTransaction ft = fm.beginTransaction();
        if (animate) {
            if (popEnterId > 0 && popExitId > 0) {
                ft.setCustomAnimations(enterAnimId, exitAnimId, popEnterId, popExitId);
            } else if (enterAnimId > 0 && exitAnimId > 0) {
                ft.setCustomAnimations(enterAnimId, exitAnimId);
            }
        }
        ft.add(containerId, newFragment, tag);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    public static void addFragment(FragmentManager fm, int containerId, Fragment newFragment, String tag, boolean addToBackStack, boolean animate) {
        FragmentTransaction ft = fm.beginTransaction();
        if (animate) {
            setAnimation(ft);
        }
        ft.add(containerId, newFragment, tag);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }

}
