package com.takehometest.acmerouter.ui.drivers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.takehometest.acmerouter.R
import com.takehometest.acmerouter.entity.Driver

/**
 * An adapter for the [Driver]s list.
 */
class DriverAdapter : RecyclerView.Adapter<DriverAdapter.DriverViewHolder>() {
    private val driverList = mutableListOf<Driver>()

    var onItemClick: ((Driver) -> Unit)? = null

    class DriverViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.driver_list_item, parent, false)

        return DriverViewHolder(view)
    }

    override fun onBindViewHolder(holder: DriverViewHolder, position: Int) {
        //TODO: use data binding
        val headlineTv: TextView = holder.itemView.findViewById(R.id.driver_full_name)

        val driver: Driver = driverList[position];

        headlineTv.text = driver.fullName
        headlineTv.setOnClickListener {
            onItemClick?.invoke(driver)
        }
    }

    override fun getItemCount(): Int = driverList.size

    fun resetData(data: List<Driver>) {
        driverList.apply {
            clear()
            addAll(data)
        }
        //TODO: implement more granular notifications
        notifyDataSetChanged()
    }
}
