package com.silence.experimental.common.data

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceHelper @Inject constructor(context: Context) {

    companion object {
        private const val PREF_EXPERIMENTAL_PACKAGE_NAME = "com.silence.experimental.preferences"
        private const val PREF_KEY_LAST_CACHE = "last_cache"
    }

    private val experimentalPref: SharedPreferences =
        context.getSharedPreferences(PREF_EXPERIMENTAL_PACKAGE_NAME, Context.MODE_PRIVATE)

    var lastCacheTime: Long
        get() = experimentalPref.getLong(PREF_KEY_LAST_CACHE, 0)
        set(lastCache) = experimentalPref.edit().putLong(PREF_KEY_LAST_CACHE, lastCache).apply()

}