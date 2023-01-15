package com.hamedsafari.filckrphotos.features.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hamedsafari.filckrphotos.databinding.ItemSearchSuggestionBinding

class SearchSuggestionAdapter(private val onItem: (String) -> Unit) :
    ListAdapter<String, SearchSuggestionAdapter.SearchSuggestionViewHolder>(ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchSuggestionViewHolder {
        return SearchSuggestionViewHolder(
            ItemSearchSuggestionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchSuggestionViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, onItem) }
    }

    inner class SearchSuggestionViewHolder(private val view: ItemSearchSuggestionBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(item: String, onItem: (String) -> Unit) {
            view.title.text = item
            view.root.setOnClickListener { onItem(item) }
        }
    }
}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}
