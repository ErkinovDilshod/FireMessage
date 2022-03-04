package uz.myprog.firemessage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.myprog.firemessage.UserAdapter.*
import uz.myprog.firemessage.databinding.UserListItemBinding
import java.text.SimpleDateFormat
import java.util.*

class UserAdapter : ListAdapter<User, ItemHolder>(ItemComparator()) {
    class ItemHolder(private val binding:UserListItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(user:User) = with(binding){
            val currentTime: String =
                SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            tvSenderMessage.text = user.message
            tvSenderCurrentTime.text = currentTime
        }
        companion object{
            fun create(parent:ViewGroup):ItemHolder{
                return ItemHolder(UserListItemBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

    class ItemComparator :DiffUtil.ItemCallback<User>(){
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
       holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}