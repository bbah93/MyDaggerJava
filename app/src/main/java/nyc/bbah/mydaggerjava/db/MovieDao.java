package nyc.bbah.mydaggerjava.db;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertMovies(List<MovieEntity> movies);

    @Query("SELECT * FROM `MovieEntity`")
    List<MovieEntity> getMoviesByPage();
}
