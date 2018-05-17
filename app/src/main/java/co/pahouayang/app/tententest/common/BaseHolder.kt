package co.pahouayang.app.tententest.common

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: Any, modelPosition : Int)
}