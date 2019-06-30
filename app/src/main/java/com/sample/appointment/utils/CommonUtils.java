package com.sample.appointment.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class CommonUtils {

    public static boolean checkInternetConnection(Context mActivity) {
        // <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
        ConnectivityManager manager = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = manager.getActiveNetworkInfo();
        if (mNetworkInfo != null) return mNetworkInfo.isConnectedOrConnecting();
        return false;
    }

    private static ProgressDialog mProgressDialog;

    public static void showProgress(Context mContext) {

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Please Wait...!");
            mProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    mProgressDialog = null;
                }
            });
        }

        if (!mProgressDialog.isShowing()) mProgressDialog.show();
    }

    public static void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) mProgressDialog.dismiss();
        mProgressDialog = null;
    }

    public static void dial(Activity mActivity, String number) {
        Uri call = Uri.parse("tel:" + number);
        Intent surf = new Intent(Intent.ACTION_DIAL, call);
        mActivity.startActivity(surf);
    }

    /**
     * Hides the soft keyboard
     */
    public static void hideSoftKeyboard(Activity mActivity) {
        try {
            View focusedView = mActivity.getCurrentFocus();
            if (focusedView != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
            } else {
                EditText editText = new EditText(mActivity);
                InputMethodManager inputMethodManager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the soft keyboard
     */
    public static void showSoftKeyboard(Activity mActivity, View mView) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            mView.requestFocus();
            inputMethodManager.showSoftInput(mView, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
