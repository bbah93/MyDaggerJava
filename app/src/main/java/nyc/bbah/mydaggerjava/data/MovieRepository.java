package nyc.bbah.mydaggerjava.data;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Observable;

import javax.inject.Singleton;

import io.reactivex.Flowable;
import nyc.bbah.mydaggerjava.db.MovieDao;
import nyc.bbah.mydaggerjava.db.MovieEntity;
import nyc.bbah.mydaggerjava.data.remote.model.MovieApiResponse;
import nyc.bbah.mydaggerjava.data.remote.api.MovieApiService;

@Singleton
public class MovieRepository {

   private MovieDao movieDao;
   private MovieApiService movieApiService;

    public MovieRepository(MovieDao movieDao,
                           MovieApiService movieApiService) {
        this.movieDao = movieDao;
        this.movieApiService = movieApiService;
    }



    public Observable<Resource<List<MovieEntity>>> loadMoviesByType(){

        return new NetworkBoundResource<List<MovieEntity>, MovieApiResponse>(){

            @Override
            protected void saveCallResult(@NonNull MovieApiResponse item) {
                movieDao.insertMovies(item.getResults());
            }

            @Override
            protected boolean shouldFetch() {
                return true;
            }

            @NonNull
            @Override
            protected Flowable<List<MovieEntity>> loadFromDb() {
                List<MovieEntity> movieEntities = movieDao.getMoviesByPage();
                if(movieEntities == null || movieEntities.isEmpty()) {
                    return Flowable.empty();
                }
                return Flowable.just(movieEntities);
            }

            @NonNull
            @Override
            protected Observable<Resource<MovieApiResponse>> createCall() {
                return movieApiService.fetchMovies()
                        .flatMap(movieApiResponse -> Observable.just(movieApiResponse == null
                                ? Resource.error("", new MovieApiResponse())
                                : Resource.success(movieApiResponse)));
            }
        }.getAsObservable();
    }

}
