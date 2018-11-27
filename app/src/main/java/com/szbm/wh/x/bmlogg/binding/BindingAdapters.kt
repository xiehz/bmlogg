/*
 * Copyright (C) 2017 The Android Open Source ProjectArea
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.szbm.wh.x.bmlogg.binding
import android.content.Context
import android.os.Build
import androidx.databinding.BindingAdapter
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseMethod
import com.google.android.material.navigation.NavigationView
import com.szbm.wh.x.bmlogg.R


/**
 * Data Binding adapters specific to the app.
 */
object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }
    @JvmStatic
    @BindingAdapter("textSet")
    fun<T> textSet(view: TextView,value:T){
        view.text = value?.toString()
    }

    @JvmStatic
    @BindingAdapter("boreholeDepth")
    fun boreholeDepth(view: TextView,value:Float?){
        view.text = view.resources.getString(R.string.boreholeDepth,value)
    }


    @JvmStatic
    @BindingAdapter("app:headerLayout", requireAll = false)
    fun inflateHeaderLayout(navigationView: NavigationView, @LayoutRes layoutRes: Int) {
        navigationView.inflateHeaderView(layoutRes)
    }

    @BindingAdapter(value=["android:max", "android:progress"], requireAll = true)
    @JvmStatic fun updateProgress(progressBar: ProgressBar, max: Int, progress: Int) {
        progressBar.max = max
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(progress, false)
        } else {
            progressBar.progress = progress
        }
    }

    @BindingAdapter("numberOfSets_alternative")
    @JvmStatic fun setNumberOfSets_alternative(view: EditText, value: Array<Int>) {
        view.setText(String.format(
                view.resources.getString(R.string.sets_format,
                        value[0] + 1,
                        value[1])))
    }

    @InverseBindingAdapter(attribute = "numberOfSets_alternative")
    @JvmStatic fun getNumberOfSets_alternative(editText: EditText): Array<Int> {
        if (editText.text.isEmpty()) {
            return arrayOf(0, 0)
        }

        return try {
            arrayOf(0, editText.text.toString().toInt()) // First item is not passed
        } catch (e: NumberFormatException) {
            arrayOf(0, 0)
        }
    }

    /**
     * Converters for the number of sets attribute.
     */
    object NumberOfSetsConverters {

        /**
         * Used with `numberOfSets` to convert from array to String.
         */
        @InverseMethod("stringToSetArray")
        @JvmStatic fun setArrayToString(context: Context, value: Array<Int>): String {
            return context.getString(R.string.sets_format, value[0] + 1, value[1])
        }

        /**
         * This is the Inverse Method used in `numberOfSets`, to convert from String to array.
         *
         * Note that Context is passed
         */
        @JvmStatic fun stringToSetArray(unused: Context, value: String): Array<Int> {
            // Converts String to long
            if (value.isEmpty()) {
                return arrayOf(0, 0)
            }

            return try {
                arrayOf(0, value.toInt()) // First item is not passed
            } catch (e: NumberFormatException) {
                arrayOf(0, 0)
            }
        }
    }

    @BindingAdapter("hideKeyboardOnInputDone")
    @JvmStatic fun hideKeyboardOnInputDone(view: EditText, enabled: Boolean) {
        if (!enabled) return
        val listener = TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                view.clearFocus()
                val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE)
                        as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            false
        }
        view.setOnEditorActionListener(listener)
    }
}
