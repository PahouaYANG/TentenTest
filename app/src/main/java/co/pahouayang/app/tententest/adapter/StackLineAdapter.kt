package co.pahouayang.app.tententest.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.pahouayang.app.tententest.R
import co.pahouayang.app.tententest.common.BaseHolder
import co.pahouayang.app.tententest.holder.StackLineItemHolder
import co.pahouayang.app.tententest.model.MessageType
import co.pahouayang.app.tententest.model.StackLine


class StackLineAdapter : RecyclerView.Adapter<BaseHolder>(){

    private var mMessages: ArrayList<StackLine> = ArrayList<StackLine>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return StackLineItemHolder(layoutInflater.inflate(R.layout.holder_stack, parent, false))
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        if (holder is StackLineItemHolder) {
            //bind the holder with the data
            holder.bind(mMessages.get(position), position)
        }
    }

    override fun getItemCount(): Int {
        return mMessages.size
    }

    //region getter setter
    fun clearMessages(){
        mMessages.clear()
        notifyDataSetChanged()
    }
    fun addMessage(messageItems: String, messageType: MessageType) {
        mMessages.add(StackLine(messageItems,messageType))
        notifyItemInserted(mMessages.size-1)
    }
    //endregion getter setter


}