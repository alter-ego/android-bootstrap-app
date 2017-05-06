package solutions.alterego.bootstrap.di;

import javax.inject.Singleton;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import solutions.alterego.bootstrap.App;
import solutions.alterego.bootstrap.screens.main.MainActivity;

@Singleton
@dagger.Component(modules = {ManagersModule.class, ProvidersModule.class})
public interface Component {

    void inject(App app);

    void inject(MainActivity mainActivity);

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    final class Initializer {
        public static Component init(App app) {
            return DaggerComponent.builder()
                    .managersModule(new ManagersModule(app))
                    .providersModule(new ProvidersModule())
                    .build();
        }
    }
}