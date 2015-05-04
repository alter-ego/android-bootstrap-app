package solutions.alterego.bootstrap.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.Activity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Observable;
import solutions.alterego.bootstrap.App;

@Module
public class ProvidersModule {

    @Provides
    @Singleton
    Observable<Activity> provideActivity() {
        return App.Activity;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }
}
