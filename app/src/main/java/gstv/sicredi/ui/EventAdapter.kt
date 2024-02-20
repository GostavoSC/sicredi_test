package gstv.sicredi.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gstv.sicredi.databinding.CardViewItemBinding
import gstv.sicredi.domain.Event

class EventAdapter() : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    var events: List<Event> = emptyList()
        set(value) {
            field = value
            notifyItemChanged(events.size)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    class ViewHolder(private val binding: CardViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.txtTitle.text = event.title
            binding.txtDate.text = event.date
        }

    }

}