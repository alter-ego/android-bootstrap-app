package solutions.alterego.bootstrap;

import solutions.alterego.bootstrap.di.Component;

import retrofit.RestAdapter;

public class MyIntegrationTestApp extends App {

    @Override
    public Component buildComponentAndInject() {
        return Component.Initializer.init(this);
    }

    @Override
    protected void initDebug() {
        ApiServiceLogLevel = RestAdapter.LogLevel.BASIC;
    }

    @Override
    protected void initRelease() {
        ApiServiceLogLevel = RestAdapter.LogLevel.NONE;
    }

}
