package gstv.sicredi.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gstv.sicredi.databinding.CardViewItemBinding
import gstv.sicredi.model.domain.Event

class EventAdapter(private val clickListener: OnCardClickListener) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

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
            ),
            onCardClicked = {
                clickListener.clicked(it)
            }
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    class ViewHolder(
        private val binding: CardViewItemBinding,
        private val onCardClicked: (String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            with(binding) {
                txtTitle.text = event.title
                txtDate.text = event.date
                eventCard.setOnClickListener {
                    onCardClicked.invoke(event.id)
                }
            }
        }

    }

}

interface OnCardClickListener {
    fun clicked(id: String)
}