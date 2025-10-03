package com.example.barcodescanner.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.barcodescanner.R
import com.example.barcodescanner.data.local.AssociationEntity

/**
 * RecyclerView adapter for displaying ToteID-OLPN associations
 * with sync status and retry functionality
 */
class AssociationAdapter(
    private val onItemClick: (AssociationEntity) -> Unit,
    private val onRetryClick: (AssociationEntity) -> Unit
) : RecyclerView.Adapter<AssociationAdapter.AssociationViewHolder>() {

    private var associations = listOf<AssociationEntity>()

    inner class AssociationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val toteIdTextView: TextView = view.findViewById(R.id.toteIdTextView)
        val olpnTextView: TextView = view.findViewById(R.id.olpnTextView)
        val timestampTextView: TextView = view.findViewById(R.id.timestampTextView)
        val statusTextView: TextView = view.findViewById(R.id.statusTextView)
        val retryButton: Button = view.findViewById(R.id.retryButton)
        val statusIndicator: View = view.findViewById(R.id.statusIndicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssociationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_association, parent, false)
        return AssociationViewHolder(view)
    }

    override fun onBindViewHolder(holder: AssociationViewHolder, position: Int) {
        val association = associations[position]

        with(holder) {
            // Set basic information
            toteIdTextView.text = "📦 ToteID: ${association.toteId}"
            olpnTextView.text = "📋 OLPN: ${association.olpn}"
            timestampTextView.text = "⏰ ${association.getFormattedTimestamp()}"
            statusTextView.text = association.getDisplayStatus()

            // Set status indicator color and retry button visibility
            when {
                association.submitted -> {
                    statusIndicator.setBackgroundColor(
                        ContextCompat.getColor(itemView.context, android.R.color.holo_green_dark)
                    )
                    retryButton.visibility = View.GONE
                }
                association.retryCount > 0 -> {
                    statusIndicator.setBackgroundColor(
                        ContextCompat.getColor(itemView.context, android.R.color.holo_orange_dark)
                    )
                    retryButton.visibility = View.VISIBLE
                    retryButton.text = "🔄 Retry (${association.retryCount})"
                }
                else -> {
                    statusIndicator.setBackgroundColor(
                        ContextCompat.getColor(itemView.context, android.R.color.holo_blue_dark)
                    )
                    retryButton.visibility = View.GONE
                }
            }

            // Set click listeners
            itemView.setOnClickListener {
                onItemClick(association)
            }

            retryButton.setOnClickListener {
                onRetryClick(association)
            }

            // Add visual feedback for error states
            if (association.errorMessage != null) {
                itemView.setBackgroundColor(
                    ContextCompat.getColor(itemView.context, R.color.error_background)
                )
            } else {
                itemView.setBackgroundColor(
                    ContextCompat.getColor(itemView.context, android.R.color.white)
                )
            }
        }
    }

    override fun getItemCount(): Int = associations.size

    /**
     * Update associations list with DiffUtil for efficient updates
     */
    fun updateAssociations(newAssociations: List<AssociationEntity>) {
        val diffCallback = AssociationDiffCallback(associations, newAssociations)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        associations = newAssociations
        diffResult.dispatchUpdatesTo(this)
    }

    /**
     * Get association at position
     */
    fun getAssociationAt(position: Int): AssociationEntity? {
        return if (position in 0 until associations.size) {
            associations[position]
        } else {
            null
        }
    }

    /**
     * Get all submitted associations
     */
    fun getSubmittedAssociations(): List<AssociationEntity> {
        return associations.filter { it.submitted }
    }

    /**
     * Get all pending associations
     */
    fun getPendingAssociations(): List<AssociationEntity> {
        return associations.filter { !it.submitted }
    }

    /**
     * Get associations with errors
     */
    fun getErrorAssociations(): List<AssociationEntity> {
        return associations.filter { it.errorMessage != null }
    }

    /**
     * DiffUtil callback for efficient list updates
     */
    private class AssociationDiffCallback(
        private val oldList: List<AssociationEntity>,
        private val newList: List<AssociationEntity>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            return oldItem.toteId == newItem.toteId &&
                    oldItem.olpn == newItem.olpn &&
                    oldItem.submitted == newItem.submitted &&
                    oldItem.retryCount == newItem.retryCount &&
                    oldItem.errorMessage == newItem.errorMessage &&
                    oldItem.submitTimestamp == newItem.submitTimestamp
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            return when {
                oldItem.submitted != newItem.submitted -> "status_change"
                oldItem.retryCount != newItem.retryCount -> "retry_change"
                oldItem.errorMessage != newItem.errorMessage -> "error_change"
                else -> null
            }
        }
    }
}