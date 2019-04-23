package nyc.bbah.mydaggerjava;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.SnapHelper;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import nyc.bbah.mydaggerjava.data.MovieListViewModel;
import nyc.bbah.mydaggerjava.data.ViewModelFactory;
import nyc.bbah.mydaggerjava.db.MovieEntity;

public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelFactory viewModelFactory;

    //Data Binding

    private MainActivityBinding binding;

    private MovieListViewModel movieListViewModel;

    private MoviesListAdapter moviesListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        initialiseView();
        initialiseViewModel();

    }

    /*
     * Initialising the View using Data Binding
     * */
    private void initialiseView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        moviesListAdapter = new MoviesListAdapter(this);
        binding.moviesList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.moviesList.setAdapter(moviesListAdapter);

        /* SnapHelper to change the background of the activity based on the list item
         * currently visible */
        SnapHelper startSnapHelper = new PagerSnapHelper(position -> {
            MovieEntity movie = moviesListAdapter.getItem(position);
            binding.overlayLayout.updateCurrentBackground(movie.getPosterPath());
        });
        startSnapHelper.attachToRecyclerView(binding.moviesList);
    }


    private void initialiseViewModel() {
        movieListViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel.class);
        movieListViewModel.getMoviesLiveData().observe(this, resource -> {
            if(resource.isLoading()) {
                displayLoader();

            } else if(!resource.data.isEmpty()) {
                updateMoviesList(resource.data);

            } else handleErrorResponse();
        });

        /* Fetch movies list  */
        movieListViewModel.loadMoreMovies();
    }

    private void displayLoader() {
        binding.moviesList.setVisibility(View.GONE);
        binding.loaderLayout.rootView.setVisibility(View.VISIBLE);
        binding.loaderLayout.loader.start();
    }

    private void hideLoader() {
        binding.moviesList.setVisibility(View.VISIBLE);
        binding.loaderLayout.rootView.setVisibility(View.GONE);
        binding.loaderLayout.loader.stop();
    }

    private void updateMoviesList(List<MovieEntity> movies) {
        hideLoader();
        binding.emptyLayout.emptyContainer.setVisibility(View.GONE);
        binding.moviesList.setVisibility(View.VISIBLE);
        moviesListAdapter.setItems(movies);
    }

    private void handleErrorResponse() {
        hideLoader();
        binding.moviesList.setVisibility(View.GONE);
        binding.emptyLayout.emptyContainer.setVisibility(View.VISIBLE);
    }

}
