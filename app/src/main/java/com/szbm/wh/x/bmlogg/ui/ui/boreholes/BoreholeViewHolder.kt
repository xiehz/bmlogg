/*
 * Copyright (C) 2017 The Android Open Source Project
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

package com.szbm.wh.x.bmlogg.ui.ui.boreholes

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.szbm.wh.x.bmlogg.R
import com.szbm.wh.x.bmlogg.databinding.BoreholeItemBinding
import com.szbm.wh.x.bmlogg.vo.BH_BoreholeInfo

/**
 * A RecyclerView ViewHolder that displays a reddit post.
 */
class BoreholeViewHolder(view: View,var clickListener: ((bh_BoreholeInfo:BH_BoreholeInfo?)->Unit)? ) : RecyclerView.ViewHolder(view) {
    private var binding:BoreholeItemBinding? = null
    private var bH_BoreholeInfo : BH_BoreholeInfo? = null

    init {
        binding = DataBindingUtil.bind(view)
        view.setOnClickListener{
            clickListener?.invoke(bH_BoreholeInfo)
        }
    }

    fun bind(bh: BH_BoreholeInfo?) {
        this.bH_BoreholeInfo = bh
        binding?.borehole = this.bH_BoreholeInfo
    }

    companion object {
        fun create(parent: ViewGroup,clickListener: ((bh_BoreholeInfo:BH_BoreholeInfo?)->Unit)?): BoreholeViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.borehole_item, parent, false)

            return BoreholeViewHolder(view,clickListener)
        }
    }

    fun updateScore(item: BH_BoreholeInfo?) {
        bH_BoreholeInfo = item
    }
}