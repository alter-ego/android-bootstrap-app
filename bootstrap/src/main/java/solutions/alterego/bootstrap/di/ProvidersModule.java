package solutions.alterego.bootstrap.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.alterego.advancedandroidlogger.implementations.AndroidLogger;
import com.alterego.advancedandroidlogger.implementations.DetailedAndroidLogger;
import com.alterego.advancedandroidlogger.interfaces.IAndroidLogger;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.logging.HttpLoggingInterceptor;
import solutions.alterego.bootstrap.BuildConfig;
import solutions.alterego.bootstrap.util.DateTimeSerializer;

@Module
public class ProvidersModule {

    public static final String LOGGING_TAG = "BOOTSTRAP";

    public static DateTimeSerializer dateTimeSerializer = new DateTimeSerializer(ISODateTimeFormat.dateTimeParser().withZoneUTC());

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapter(DateTime.class, dateTimeSerializer)
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @Provides
    @Singleton
    @Named("logger")
    public IAndroidLogger provideLogger() {
        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            return new DetailedAndroidLogger(LOGGING_TAG, IAndroidLogger.LoggingLevel.VERBOSE);
        } else {
            return new AndroidLogger(LOGGING_TAG, IAndroidLogger.LoggingLevel.WARNING);
        }
    }

    @Provides
    public HttpLoggingInterceptor.Level provideHttpLoggingLevel() {
        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            return HttpLoggingInterceptor.Level.BODY;
        } else {
            return HttpLoggingInterceptor.Level.NONE;
        }
    }
}
