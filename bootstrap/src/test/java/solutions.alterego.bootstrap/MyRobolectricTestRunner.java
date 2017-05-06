package solutions.alterego.bootstrap;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.Fs;

import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class MyRobolectricTestRunner extends RobolectricGradleTestRunner {

    public MyRobolectricTestRunner(Class<?> klass) throws InitializationError {
        super(klass);

        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    protected AndroidManifest getAppManifest(Config config) {

        String manifest = "src/main/AndroidManifest.xml";
        String res = String.format("../app/build/intermediates/res/merged/%1$s/%2$s",
                BuildConfig.FLAVOR, BuildConfig.BUILD_TYPE);
        String asset = "src/main/assets";

        return new AndroidManifest(Fs.fileFromPath(manifest), Fs.fileFromPath(res),
                Fs.fileFromPath(asset)) {
            @Override
            public String getRClassName() throws Exception {
                return solutions.alterego.bootstrap.R.class.getName();
            }
        };
    }
}
