package nyc.bbah.mydaggerjava.ui.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import nyc.bbah.mydaggerjava.data.MovieRepository;
import nyc.bbah.mydaggerjava.data.local.dao.MovieDao;
import nyc.bbah.mydaggerjava.data.local.entity.MovieEntity;
import nyc.bbah.mydaggerjava.data.remote.api.MovieApiService;

public class MovieListViewModel extends ViewModel {

    MovieRepository movieRepository;

    private MutableLiveData<Resource<List<MovieEntity>>> moviesLiveData = new MutableLiveData<>();

    @Inject
    public MovieListViewModel(MovieDao movieDao, MovieApiService movieApiService){

        movieRepository = new MovieRepository(movieDao, movieApiService);

    }

    /*
     * Method called by UI to fetch movies list
     * */
    public void loadMoreMovies() {
        movieRepository.loadMoviesByType()
                .subscribe(resource -> getMoviesLiveData().postValue(resource));
    }

    /*
     * LiveData observed by the UI
     * */
    public MutableLiveData<Resource<List<MovieEntity>>> getMoviesLiveData() {
        return moviesLiveData;
    }
}
