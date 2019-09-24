package com.silence.experimental.movies.presentation.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.silence.experimental.R
import com.silence.experimental.common.extension.inflate
import com.silence.experimental.movies.presentation.entity.MoviePresentationModel
import javax.inject.Inject
import kotlin.properties.Delegates

class MoviesAdapter @Inject constructor(): RecyclerView.Adapter<MoviesViewHolder>() {

    internal var collection: List<MoviePresentationModel> by Delegates.observable(emptyList()) {
            _, _, _ -> notifyDataSetChanged()
    }

    internal var clickListener: (MoviePresentationModel) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder =
        MoviesViewHolder(parent.inflate(R.layout.item_movie))

    override fun getItemCount(): Int  = collection.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(collection[position], clickListener)
    }
}