package com.example.foxproject.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.foxproject.R
import com.example.foxproject.models.Note
import kotlin.random.Random

class NotesAdapter(private val context: Context, val listener: NotesItemClickListener) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val NoteList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = NoteList[position]
        holder.title.text = currentNote.title
        holder.title.isSelected  = true

        holder.noteTV.text = currentNote.note
        holder.date.text = currentNote.date
        holder.date.isSelected = true

        holder.notes_layout.setBackgroundColor(holder.itemView.resources.getColor(randomColor(), null))
        holder.notes_layout.setOnClickListener{
            listener.onItemClicked(NoteList[holder.adapterPosition])

        }
        holder.notes_layout.setOnClickListener{
            listener.onLongItemClicked(NoteList[holder.adapterPosition], holder.notes_layout)
            true

        }

    }

    override fun getItemCount(): Int {
        return NoteList.size
    }

    fun updateList(newlist: List<Note>){
        fullList.clear()
        fullList.addAll(newlist)
        NoteList.clear()
        NoteList.addAll(fullList)
        notifyDataSetChanged()

    }

    fun filterList(search: String){
        NoteList.clear()
            for (item in fullList){
                if (item.title?.lowercase()?.contains(search.lowercase()) == true ||
                         item.note?.lowercase()?.contains(search.lowercase()) == true){

                    NoteList.add(item)

                }
            }
notifyDataSetChanged()
    }
    fun randomColor(): Int{
        val list = ArrayList<Int>()
        list.add(R.color.notecolor1)
        list.add(R.color.notecolor2)
        list.add(R.color.notecolor3)
        list.add(R.color.notecolor4)
        list.add(R.color.notecolor5)
        list.add(R.color.notecolor6)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex= Random(seed).nextInt(list.size)
        return list[randomIndex]
    }

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var notes_layout = itemView.findViewById<CardView>(R.id.card_layout)
        var title = itemView.findViewById<TextView>(R.id.tv_title)
        var noteTV = itemView.findViewById<TextView>(R.id.tv_note)
        var date = itemView.findViewById<TextView>(R.id.tv_date)
    }
    interface NotesItemClickListener{
        fun onItemClicked(note: Note)
        fun onLongItemClicked(note: Note, cardView: CardView)
    }
}