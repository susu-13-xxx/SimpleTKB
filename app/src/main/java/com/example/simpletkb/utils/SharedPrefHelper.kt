package com.example.simpletkb.utils

import android.content.Context
import com.example.simpletkb.data.Subject
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPrefHelper {
    private const val PREF_NAME = "tkb_prefs"
    private const val KEY_SUBJECTS = "subjects_list"

    fun saveSubjects(context: Context, list: List<Subject>) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = Gson().toJson(list)
        prefs.edit().putString(KEY_SUBJECTS, json).apply()
    }

    fun getSubjects(context: Context): MutableList<Subject> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_SUBJECTS, null) ?: return mutableListOf()
        val type = object : TypeToken<MutableList<Subject>>() {}.type
        return Gson().fromJson(json, type)
    }
}