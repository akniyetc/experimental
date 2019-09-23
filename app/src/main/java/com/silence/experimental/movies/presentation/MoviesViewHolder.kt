package com.silence.experimental.movies.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.silence.experimental.common.extension.loadFromUrl
import com.silence.experimental.movies.presentation.entity.MoviePresentationModel
import com.silence.experimental.movies.presentation.entity.PosterUrls
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(
        movie: MoviePresentationModel,
        clickListener: (MoviePresentationModel) -> Unit
    ) {
        itemView.imgMoviePoster.loadFromUrl(PosterUrls.BASE_URL + movie.posterPath)
        itemView.tvMovieTitle.text = movie.title
        itemView.tvMovieOverview.text = movie.overview
        itemView.ratingBarMovies.rating = movie.voteAverage.toFloat()
        itemView.setOnClickListener { clickListener.invoke(movie) }
    }
}