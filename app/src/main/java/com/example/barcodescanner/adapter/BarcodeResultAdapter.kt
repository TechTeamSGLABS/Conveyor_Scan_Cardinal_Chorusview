package com.example.barcodescanner.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.barcodescanner.R

class BarcodeResultAdapter(
    private val barcodes: List<String>,
    private val isScanned: Boolean
) : RecyclerView.Adapter<BarcodeResultAdapter.BarcodeViewHolder>() {

    inner class BarcodeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val barcodeText: TextView = view.findViewById(R.id.barcodeText)
        val barcodeStatus: TextView = view.findViewById(R.id.barcodeStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarcodeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_barcode_result, parent, false)
        return BarcodeViewHolder(view)
    }

    override fun onBindViewHolder(holder: BarcodeViewHolder, position: Int) {
        holder.barcodeText.text = barcodes[position]
        holder.barcodeStatus.text = if (isScanned) "✅ Scanned" else "❌ Unscanned"
        holder.barcodeStatus.setTextColor(
            holder.itemView.resources.getColor(
                if (isScanned) R.color.green else R.color.red,
                null
            )
        )
    }

    override fun getItemCount(): Int = barcodes.size
}
