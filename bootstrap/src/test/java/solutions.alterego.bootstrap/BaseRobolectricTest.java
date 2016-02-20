package solutions.alterego.bootstrap;

import org.junit.runner.RunWith;

@RunWith(MyRobolectricTestRunner.class)
@org.robolectric.annotation.Config(
        constants = BuildConfig.class,
        sdk = BuildConfig.TEST_TARGET_SDK_VERSION,
        application = MyIntegrationTestApp.class,
        packageName = BuildConfig.TEST_APPLICATION_ID)
public abstract class BaseRobolectricTest {

}
