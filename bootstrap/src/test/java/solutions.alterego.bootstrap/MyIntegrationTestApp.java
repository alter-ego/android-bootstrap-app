package solutions.alterego.bootstrap;

import solutions.alterego.bootstrap.di.Component;

public class MyIntegrationTestApp extends App {

    @Override
    public Component buildComponentAndInject() {
        return Component.Initializer.init(this);
    }

}
