package solutions.alterego.bootstrap;

import com.alterego.advancedandroidlogger.implementations.AndroidLogger;
import com.alterego.advancedandroidlogger.implementations.DetailedAndroidLogger;
import com.alterego.advancedandroidlogger.implementations.NullAndroidLogger;
import com.alterego.advancedandroidlogger.interfaces.IAndroidLogger;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import net.danlew.android.joda.JodaTimeAndroid;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.multidex.MultiDexApplication;

import retrofit.RestAdapter;
import solutions.alterego.bootstrap.di.Component;

public class App extends MultiDexApplication {

    public static final String LOGGING_TAG = "BOOTSTRAP";

    public static IAndroidLogger L = NullAndroidLogger.instance;

    public static RestAdapter.LogLevel ApiServiceLogLevel = RestAdapter.LogLevel.NONE;

    ActivityLifecycleCallbacks mTrackingActivityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
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
        component = buildComponentAndInject();
        super.onCreate();

        component.inject(this);

        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            initDebug();
        } else {
            initRelease();
        }

        JodaTimeAndroid.init(this);
        registerActivityLifecycleCallbacks(mTrackingActivityLifecycleCallbacks);
    }

    public Component buildComponentAndInject() {
        return Component.Initializer.init(this);
    }

    @VisibleForTesting
    public void setComponent(Component component) {
        this.component = component;
    }

    protected void initDebug() {
        L = new DetailedAndroidLogger(LOGGING_TAG, IAndroidLogger.LoggingLevel.VERBOSE);
        ApiServiceLogLevel = RestAdapter.LogLevel.FULL;
        LeakCanary.install(this);
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }

    protected void initRelease() {
        L = new AndroidLogger(LOGGING_TAG, IAndroidLogger.LoggingLevel.WARNING);
        ApiServiceLogLevel = RestAdapter.LogLevel.NONE;
    }
}
