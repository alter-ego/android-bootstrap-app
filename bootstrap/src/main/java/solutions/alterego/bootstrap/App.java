package solutions.alterego.bootstrap;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import net.danlew.android.joda.JodaTimeAndroid;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.multidex.MultiDexApplication;

import solutions.alterego.bootstrap.di.Component;

public class App extends MultiDexApplication {

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

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        component.inject(this);

        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            initDebug();
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
        LeakCanary.install(this);
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }

}
