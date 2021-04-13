package com.misla.popularmovies.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.misla.popularmovies.R;
import com.misla.popularmovies.internet.model.Review;

import java.util.List;

/**
 * Adapter for the recycler view of reviews.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    /**
     * Listener to handel clicks.
     */
    public interface OnReviewClickListener {
        void OnReviewClick(Review review);
    }

    /**
     * Tag for debugging purposes.
     */
    private static final String TAG = MoviesAdapter.class.getSimpleName();

    /**
     * Arrays of trailer to be shown.
     */
    private List<Review> reviews;

    /**
     * Click listener.
     */
    private OnReviewClickListener onReviewClickListener;

    /**
     * Constructor of the class.
     */
    public ReviewAdapter(OnReviewClickListener onReviewClickListener) {
        this.onReviewClickListener = onReviewClickListener;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.review_item, viewGroup, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder reviewViewHolder, int position) {
        Review review = reviews.get(position);
        reviewViewHolder.author.setText(review.getAuthor());
        reviewViewHolder.content.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        if (reviews == null) return 0;
        return reviews.size();
    }

    /**
     * Sets the review list to be displayed.
     * @param reviews list of reviews to be displayed.
     */
    public void setReviewList(List<Review> reviews) {
        this.reviews = reviews;

        notifyDataSetChanged();
    }

    /**
     * Class that holds the recycling.
     */
    public final class ReviewViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        /**
         * Textview with the author.
         */
        private TextView author;

        /**
         * Textview with the content.
         */
        private TextView content;

        /**
         * Constructor of the class.
         *
         * @param view View to be drawn.
         */
        ReviewViewHolder(View view) {
            super(view);
            author = view.findViewById(R.id.review_author);
            content = view.findViewById(R.id.review_content);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Review review = reviews.get(getAdapterPosition());
            onReviewClickListener.OnReviewClick(review);
        }
    }
}
