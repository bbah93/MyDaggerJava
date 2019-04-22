package nyc.bbah.mydaggerjava.data.module;

import dagger.android.ContributesAndroidInjector;
import nyc.bbah.mydaggerjava.MainActivity;

public abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract MainActivity contributeMainActivity();

}
