package com.uxharshit.dinevibes.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uxharshit.dinevibes.data.models.MaintenanceRequest
import com.uxharshit.dinevibes.databinding.ItemRequestBinding
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class RequestAdapter(private val list : List<MaintenanceRequest>) : RecyclerView.Adapter<RequestAdapter.RequestViewHolder>() {

    inner class RequestViewHolder(val binding: ItemRequestBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRequestBinding.inflate(inflater, parent, false)
        return RequestViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val item = list[position]
        val formatter = DateTimeFormatter.ofPattern("'Submitted on' MMM dd, yyyy")
            .withZone(ZoneId.systemDefault())  // Convert UTC to local timezone
        Log.d("RequestAdapter", "id: ${item.id} status: ${item.status} createdAt: ${item.createdAt}")

        holder.binding.apply {
            title.text = item.category
            description.text = "Description: ${item.description}"
            location.text = "Location: ${item.location}"
            submittedDate.text = formatter.format(Instant.parse(item.createdAt))
            when (item.status.lowercase()) {
                "resolved" -> {
                    statusChip.text = "Approved"
                    statusChip.setTextColor(Color.parseColor("#047857"))
                    statusChip.chipBackgroundColor = ColorStateList.valueOf(Color.parseColor("#d1fae5"))
                }
                "pending" -> {
                    statusChip.text = "Pending"
                    statusChip.setTextColor(Color.parseColor("#B45309"))
                    statusChip.chipBackgroundColor = ColorStateList.valueOf(Color.parseColor("#fef3c7"))
                }
                "rejected" -> {
                    statusChip.text = "Rejected"
                    statusChip.setTextColor(Color.parseColor("#dc2626"))
                    statusChip.chipBackgroundColor = ColorStateList.valueOf(Color.parseColor("#fee2e2"))
                }
                else -> {
                    statusChip.text = "Unknown"
                    statusChip.setTextColor(Color.parseColor("#B91C1C"))
                    statusChip.chipBackgroundColor = ColorStateList.valueOf(Color.parseColor("#fee2e2"))
                }
            }

        }
    }

    override fun getItemCount(): Int {
        Log.d("RequestAdapter", "getItemCount called, size: ${list.size}")
        return list.size
    }
}