package nyc.bbah.mydaggerjava.data.remote.api;

import io.reactivex.Observable;
import nyc.bbah.mydaggerjava.data.remote.model.MovieApiResponse;
import retrofit2.http.GET;

public interface MovieApiService {

    @GET("movie/popular?language=en-US&region=US&page=1")
    Observable<MovieApiResponse> fetchMovies();
}
