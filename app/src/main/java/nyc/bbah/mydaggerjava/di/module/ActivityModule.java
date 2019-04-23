package nyc.bbah.mydaggerjava.di.module;

import dagger.android.ContributesAndroidInjector;
import nyc.bbah.mydaggerjava.ui.activity.MainActivity;

public abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract MainActivity contributeMainActivity();

}
