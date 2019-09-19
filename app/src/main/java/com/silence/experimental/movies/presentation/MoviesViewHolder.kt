package com.silence.experimental.movies.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.silence.experimental.common.extension.loadFromUrl
import com.silence.experimental.movies.presentation.entity.MoviePresentationModel
import com.silence.experimental.movies.presentation.entity.PosterUrls
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(movie: MoviePresentationModel,
             clickListener: (MoviePresentationModel) -> Unit) {

        itemView.imgMoviePoster.loadFromUrl("https://m.media-amazon.com/images/M/MV5BMTA0MjI5ODA3MjReQTJeQWpwZ15BbWU3MDI1NTE3Njc@._V1_SX300.jpg")
        itemView.tvMovieTitle.text = movie.title
        itemView.tvMovieOverview.text = movie.overview
        //itemView.ratingBarMovies.numStars = movie.voteAverage.toInt()
        itemView.setOnClickListener { clickListener.invoke(movie) }
    }
}