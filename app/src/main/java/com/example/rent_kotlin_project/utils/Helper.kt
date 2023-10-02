package com.example.rent_kotlin_project.utils

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.lang.Exception

class Helper {

    companion object {
        fun isValidmobile(mobile: String): Boolean {
            return !TextUtils.isEmpty(mobile) && Patterns.PHONE.matcher(mobile).matches()
        }

        fun hideKeyboard(view: View) {
            try {
                val imm =
                    view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            } catch (e: Exception) {

            }
        }
    }
}