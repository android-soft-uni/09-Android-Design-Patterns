package com.cocacola.besanta.di;

import com.cocacola.besanta.api.BeSantaService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
class AppModule {

    @Singleton
    @Provides
    HttpLoggingInterceptor provideInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Singleton
    @Provides
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    @Singleton
    @Provides
    BeSantaService provideBeSantaService(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("https://us-central1-ko-hackathon-mentormate.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(BeSantaService.class);
    }

}
