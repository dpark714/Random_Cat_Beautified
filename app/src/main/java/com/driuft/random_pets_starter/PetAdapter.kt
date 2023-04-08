package com.driuft.random_cats

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

data class Pet(val name: String, val origin: String, val imageUrl: String)

class PetAdapter(private val petList: MutableList<Pet>): RecyclerView.Adapter<PetAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val petImage: ImageView
        val petName: TextView
        val petOrigin: TextView

        init {
            petImage = view.findViewById(R.id.pet_image)
            petName = view.findViewById(R.id.pet_name)
            petOrigin = view.findViewById(R.id.pet_origin)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pet_item, parent, false)
        Log.d("create", "petList ${petList}")
        return ViewHolder(view)
    }

    override fun getItemCount() = petList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.itemView.context)
            .load(petList[position].imageUrl)
            .centerCrop()
            .into(holder.petImage)
        holder.petName.text = petList[position].name
        holder.petOrigin.text = petList[position].origin
    }
}