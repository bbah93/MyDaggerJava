package nyc.bbah.mydaggerjava.data;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import nyc.bbah.mydaggerjava.data.MovieRepository;
import nyc.bbah.mydaggerjava.db.MovieDao;
import nyc.bbah.mydaggerjava.db.MovieEntity;
import nyc.bbah.mydaggerjava.network.MovieApiService;

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
