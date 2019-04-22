package nyc.bbah.mydaggerjava.data.module;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication_MembersInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class AppController extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
//        DaggerAppComponent.builder()
//                .application(this)
//                .build()
//                .inject(this);
    }
}
