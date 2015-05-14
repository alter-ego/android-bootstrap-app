package solutions.alterego.bootstrap;

import com.alterego.advancedandroidlogger.implementations.DetailedAndroidLogger;
import com.alterego.advancedandroidlogger.implementations.NullAndroidLogger;
import com.alterego.advancedandroidlogger.interfaces.IAndroidLogger;
import com.facebook.drawee.backends.pipeline.Fresco;

import net.danlew.android.joda.JodaTimeAndroid;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import rx.subjects.BehaviorSubject;
import solutions.alterego.bootstrap.di.Component;

public class App extends Application {

    public static IAndroidLogger L;

    public static BehaviorSubject<Activity> Activity = BehaviorSubject.create();

    ActivityLifecycleCallbacks mTrackingActivityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            Activity.onNext(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

    private Component component;

    public static Component component(Context context) {
        return ((App) context.getApplicationContext()).component;
    }

    @Override
    public void onCreate() {
        buildComponentAndInject();
        super.onCreate();

        if (BuildConfig.DEBUG) {
            L = new DetailedAndroidLogger("BOOTSTRAP", IAndroidLogger.LoggingLevel.VERBOSE);
        } else {
            L = NullAndroidLogger.instance;
        }

        JodaTimeAndroid.init(this);
        Fresco.initialize(this);
        registerActivityLifecycleCallbacks(mTrackingActivityLifecycleCallbacks);
    }

    public void buildComponentAndInject() {
        component = Component.Initializer.init(this);
    }
}
