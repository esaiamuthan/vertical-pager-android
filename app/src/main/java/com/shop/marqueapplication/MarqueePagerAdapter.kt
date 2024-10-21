package com.shop.marqueapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MarqueePagerAdapter(private val items: List<List<String>>) :
    RecyclerView.Adapter<MarqueePagerAdapter.MarqueeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarqueeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.marque_item, parent, false)
        return MarqueeViewHolder(view)
    }

    override fun onBindViewHolder(holder: MarqueeViewHolder, position: Int) {
        // Handle circular scrolling by using modulus to cycle through items
        val actualPosition = position % items.size
        holder.bind(items[actualPosition])
    }

    override fun getItemCount(): Int = Int.MAX_VALUE // Circular scrolling illusion

    class MarqueeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recyclerview: RecyclerView = itemView.findViewById(R.id.recyclerview)

        fun bind(items: List<String>) {
            recyclerview.adapter = MarqueeListAdapter(items)
        }

    }
}
