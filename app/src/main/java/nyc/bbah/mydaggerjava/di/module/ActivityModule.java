package nyc.bbah.mydaggerjava.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import nyc.bbah.mydaggerjava.ui.activity.MainActivity;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract MainActivity contributeMainActivity();

}
