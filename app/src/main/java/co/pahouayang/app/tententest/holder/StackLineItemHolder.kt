package co.pahouayang.app.tententest.holder

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import co.pahouayang.app.tententest.R
import co.pahouayang.app.tententest.common.BaseHolder
import co.pahouayang.app.tententest.model.MessageType
import co.pahouayang.app.tententest.model.StackLine
import kotlinx.android.synthetic.main.holder_stack.view.*


class StackLineItemHolder(itemView: View) : BaseHolder(itemView) {

    //region bind view
    val tvStackLine: TextView = itemView.tv_stack
    //endregion bind view

    //private data
    private lateinit var mMessage: StackLine

    override fun bind(item: Any, modelPosition: Int) = with(itemView) {

        //init the item form
        if (item is StackLine) {
            mMessage = item

            //set text
            tvStackLine.text = mMessage.message

            //set color
            when(mMessage.type){
                MessageType.INFO -> tvStackLine.setTextColor(ContextCompat.getColor(itemView.context, R.color.color_grey))
                MessageType.SUCCESS -> tvStackLine.setTextColor(ContextCompat.getColor(itemView.context, R.color.color_black))
                MessageType.FAILURE -> tvStackLine.setTextColor(ContextCompat.getColor(itemView.context, R.color.color_orange))
                MessageType.ERROR -> tvStackLine.setTextColor(ContextCompat.getColor(itemView.context, R.color.color_red))
            }

        }
    }

}