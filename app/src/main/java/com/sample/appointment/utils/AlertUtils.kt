package com.sample.appointment.utils

import android.app.AlertDialog
import android.content.Context

import java.util.ArrayList

object AlertUtils {

    fun showMessage(mContext: Context, mMessage: String, iAlertMessageOkClick: IAlertMessageOkClick) {
        val mAlertBuilder = AlertDialog.Builder(mContext)
        mAlertBuilder.setMessage(mMessage)
        mAlertBuilder.setPositiveButton("OK") { dialog, which -> iAlertMessageOkClick.onOKClick() }
        //mAlertBuilder.setTitle(mTitle);
        mAlertBuilder.create().show()
    }

    interface IAlertMessageOkClick {
        fun onOKClick()
    }

    fun showMessageWithCancelOk(mContext: Context, mMessage: String, mIAlertCallback: IAlertCallback) {
        val mAlertBuilder = AlertDialog.Builder(mContext)
        mAlertBuilder.setMessage(mMessage)
        mAlertBuilder.setCancelable(true)
        mAlertBuilder.setPositiveButton("OK") { dialog, which -> mIAlertCallback.onOkClick() }
        mAlertBuilder.setNegativeButton("CANCEL") { dialog, which -> mIAlertCallback.onCancelClick() }
        mAlertBuilder.create().show()
    }

    interface IAlertCallback {
        fun onOkClick()
        fun onCancelClick()
    }

    fun showSingleSelectionDialog(
        mContext: Context,
        stateList: ArrayList<String>,
        stateSingleSelPosVal: Int,
        mIAlertSingleSelection: IAlertSingleSelection
    ) {
        val mAlertBuilder = AlertDialog.Builder(mContext)
        val charSequence = stateList.toTypedArray<CharSequence>()
        mAlertBuilder.setSingleChoiceItems(charSequence, stateSingleSelPosVal, null)
        mAlertBuilder.setCancelable(true)
        mAlertBuilder.setPositiveButton("OK") { dialog, _ ->
            val selectedPosition = (dialog as AlertDialog).listView.checkedItemPosition
            if (selectedPosition != -1) {
                mIAlertSingleSelection.onOkClick(stateList[selectedPosition], selectedPosition)
            }
        }

        mAlertBuilder.setNegativeButton("CANCEl") { _, _ -> mIAlertSingleSelection.onCancelClick() }
        mAlertBuilder.create().show()
    }

    interface IAlertSingleSelection {

        fun onOkClick(selectedName: String, selectedPosition: Int)

        fun onCancelClick()
    }

    fun showMultipleSelectionDialog(
        mContext: Context,
        stateList: ArrayList<String>,
        stateMulSelPosVal: ArrayList<Int>?,
        mAlertMultiSel: IAlertMultiSel
    ) {

        val mAlertBuilder = AlertDialog.Builder(mContext)
        val count = stateList.size
        val isChecked = BooleanArray(count)
        if (stateMulSelPosVal != null) {
            for (i in stateList.indices) {
                isChecked[i] = stateMulSelPosVal.contains(i)
            }
        }
        val charSequence = stateList.toTypedArray<CharSequence>()

        mAlertBuilder.setMultiChoiceItems(charSequence, isChecked, null)

        mAlertBuilder.setCancelable(true)
        mAlertBuilder.setPositiveButton("OK") { dialog, _ ->
            val mListView = (dialog as AlertDialog).listView
            val selPositions = mListView.checkedItemPositions

            val selectedNames = ArrayList<String>()
            val selectedFinalPositions = ArrayList<Int>()

            for (i in stateList.indices) {
                if (selPositions.get(i)) {
                    selectedNames.add(stateList[i])
                    isChecked[i] = true
                    selectedFinalPositions.add(i)
                }
            }

            mAlertMultiSel.onOkClick(selectedNames, selectedFinalPositions)
        }

        mAlertBuilder.setNegativeButton("CANCEL") { _, _ -> mAlertMultiSel.onCancelClick() }
        mAlertBuilder.create().show()
    }

    interface IAlertMultiSel {
        fun onOkClick(selectedNames: ArrayList<String>, selectedFinalPositions: ArrayList<Int>)
        fun onCancelClick()
    }
}
