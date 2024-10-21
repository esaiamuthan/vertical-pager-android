package com.shop.marqueapplication

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MarqueeListAdapter(private val items: List<String>) :
    RecyclerView.Adapter<MarqueeListAdapter.MarqueeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarqueeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.marque_list_item, parent, false)
        return MarqueeViewHolder(view)
    }

    override fun onBindViewHolder(holder: MarqueeViewHolder, position: Int) {
        // Handle circular scrolling by using modulus to cycle through items
        val actualPosition = position % items.size
        holder.bind(items[actualPosition])
    }

    override fun getItemCount(): Int = items.size // Circular scrolling illusion

    class MarqueeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.marqueeText)

        fun bind(text: String) {
            textView.text = text

            itemView.setBackgroundColor(Color.RED)
        }

    }
}
