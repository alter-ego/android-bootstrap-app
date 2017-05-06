package solutions.alterego.bootstrap;

import com.alterego.advancedandroidlogger.implementations.NullAndroidLogger;

import solutions.alterego.bootstrap.di.Component;

import retrofit.RestAdapter;

public class MyIntegrationTestApp extends App {

    @Override
    public Component buildComponentAndInject() {
        return Component.Initializer.init(this);
    }

    @Override
    protected void initDebug() {
        L = NullAndroidLogger.instance;
        ApiServiceLogLevel = RestAdapter.LogLevel.BASIC;
    }

    @Override
    protected void initRelease() {
        L = NullAndroidLogger.instance;
        ApiServiceLogLevel = RestAdapter.LogLevel.NONE;
    }

}
