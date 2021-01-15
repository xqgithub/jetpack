package com.example.jetpack.ui.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.jetpack.R
import com.example.jetpack.data.entity.Shoe

/**
 * 分页适配器
 */
class ShoePageListAdapter constructor(private val context: Context) : PagedListAdapter<Shoe, ShoePageListAdapter.ShoeAdapterViewHolder>(ShoeDiffCallback()) {


    class ShoeAdapterViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val iv_shoe: ImageView = view.findViewById(R.id.iv_shoe) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoeAdapterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_shoe_item, parent, false)
        return ShoePageListAdapter.ShoeAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoeAdapterViewHolder, position: Int) {
        Glide.with(context).load(getItem(position)?.imageUrl)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                    println("loadUrlToImagaViewFragment =-= 图片加载失败")
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                    println("loadUrlToImagaViewFragment =-= 图片加载成功")
                    return false
                }
            })
//            .override(
//                ScreenUtils.dip2px(context, 250f),
//                ScreenUtils.dip2px(context, 250f)
//            )
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE) //DiskCacheStrategy.ALL  缓存源资源和转换后的资源;DiskCacheStrategy.NONE  不作任何磁盘缓存;DiskCacheStrategy.RESOURCE  缓存源资源;DiskCacheStrategy.RESULT  缓存转换后的资源
            .into(holder.iv_shoe)


        holder.itemView.setOnClickListener {
            getItem(position)?.let {
                println("${it.id}\n${it.name}\n${it.price}\n${it.brand}\n${it.description}")
            }
        }
    }


}