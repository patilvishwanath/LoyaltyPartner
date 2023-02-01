package com.vp.loyaltypartner.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.vp.loyaltypartner.data.model.Hits
import com.vp.loyaltypartner.databinding.ItemRowBinding

class DataAdapter(private val itemsList: ArrayList<Hits>,
                  private val onClickListener: OnClickListener
) : RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    DataViewHolder(ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun getItemCount() = itemsList.size

  override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
    holder.itemView.setOnClickListener {
      onClickListener.onClick(itemsList[position])
    }
    holder.bind(itemsList[position])
}

  class DataViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(response: Hits) {
      with(binding) {
          ivThumbnail.load(response.previewURL)
          tvUserName.text = response.user
          tvUserTags.text = response.tags
      }
    }
  }

  class OnClickListener(val clickListener: (hit: Hits) -> Unit) {
    fun onClick(hit: Hits) = clickListener(hit)
  }

  fun updateList(list: List<Hits>) {
    itemsList.clear()
    itemsList.addAll(list)
    notifyDataSetChanged()
  }
}
