package com.ookie.polytechchat

import android.R
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri

import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.request.target.Target

/*

class MediaAdapter(context: Context, mediaList: ArrayList<String>) :
    RecyclerView.Adapter<MediaAdapter.MediaViewHolder>() {
    var mediaList: ArrayList<String>
    var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val layoutView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_media, null, false)
        return MediaViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        Glide.with(context).load(Uri.parse(mediaList[position]))
            .into(holder.mMedia)
    }

    override fun getItemCount(): Int {
        return mediaList.size()
    }

    inner class MediaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mMedia: ImageView

        init {
            mMedia = itemView.findViewById(R.id.media)
        }
    }

    init {
        this.context = context
        this.mediaList = mediaList
    }

 */


