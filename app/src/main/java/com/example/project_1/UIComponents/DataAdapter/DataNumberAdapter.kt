package com.example.project_1.UIComponents.DataAdapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.project_1.Constants.Constants
import com.example.project_1.R

class DataNumberAdapter(var mData: List<DataNumber>) : RecyclerView.Adapter<DataNumberAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var data_number: TextView
        var date: TextView
        var image: ImageView

        init {
            data_number = view.findViewById(R.id.data_holder)
            date = view.findViewById(R.id.time_holder)
            image = view.findViewById(R.id.data_image_holder)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context: Context = parent.context
        val inflater: LayoutInflater = LayoutInflater.from(context)

        val dataView: View = inflater.inflate(R.layout.number_layout, parent, false)
        return ViewHolder(dataView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val data: DataNumber = mData[position]
        viewHolder.data_number.text = data.number.toString()
        viewHolder.date.text = Constants.FORMATTER.format(data.date)!!
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}