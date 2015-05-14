package solutions.alterego.bootstrap;

import com.alterego.advancedandroidlogger.implementations.DetailedAndroidLogger;
import com.alterego.advancedandroidlogger.implementations.NullAndroidLogger;
import com.alterego.advancedandroidlogger.interfaces.IAndroidLogger;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import net.danlew.android.joda.JodaTimeAndroid;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import solutions.alterego.bootstrap.di.Component;

public class App extends MultiDexApplication {

    public static Application AppInstance;

    public static IAndroidLogger L;

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
        buildComponentAndInject();
        super.onCreate();

        AppInstance = this;

        if (BuildConfig.DEBUG) {
            L = new DetailedAndroidLogger("BOOTSTRAP", IAndroidLogger.LoggingLevel.VERBOSE);
            LeakCanary.install(this);
            Stetho.initialize(Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .build());
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
