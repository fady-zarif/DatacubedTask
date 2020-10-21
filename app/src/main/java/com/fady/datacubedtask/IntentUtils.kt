package com.fady.datacubedtask

import android.app.Activity
import android.content.Intent
import android.os.Parcelable

object IntentUtils {
    private const val CALLING_ACTIVITY = "CALLING_ACTIVITY"

    /** Generic fun to pass extra with activity */
    fun <T> startActivity(
        fromActivity: Activity,
        targetActivity: Class<*>,
        extra: IntentExtra<T>? = null
    ) {
        val nextActivityIntent = Intent(fromActivity, targetActivity)
        nextActivityIntent.putExtra(CALLING_ACTIVITY, fromActivity.localClassName)
        if (extra != null)
            when (extra.item) {
                is Boolean -> nextActivityIntent.putExtra(extra.extraId, extra.item)
                is String -> nextActivityIntent.putExtra(extra.extraId, extra.item)
                is Int -> nextActivityIntent.putExtra(extra.extraId, extra.item)
                is Parcelable -> nextActivityIntent.putExtra(extra.extraId, extra.item)
            }
        fromActivity.startActivity(nextActivityIntent)
    }

    data class IntentExtra<T>(val extraId: String, val item: T)
}