package com.facuu16.translator.translate;

import com.facuu16.translator.enums.Language;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import team.unnamed.inject.Inject;
import team.unnamed.inject.Named;
import team.unnamed.inject.Singleton;
import team.unnamed.inject.util.Validate;

import java.io.IOException;
import java.net.URLEncoder;

@Singleton
public class GoogleTranslator implements Translator {

    private static final String API_URL = "https://www.googleapis.com/language/translate/v2?";
    private static final String API_PARAMS = "key=%s&q=%s&source=%s&target=%s";

    @Inject
    @Named("google-key")
    private String key;

    @Override
    @NotNull
    public String request(@NotNull String text, @NotNull Language from, @NotNull Language to) throws IOException {
        Validate.notNull(text);
        Validate.notNull(from);
        Validate.notNull(to);

        final String params = String.format(API_PARAMS, key, URLEncoder.encode(text, "UTF-8"), from.tag(), to.tag());
        final Request request = new Request.Builder()
                .get()
                .url(API_URL + params)
                .build();

        try (final Response response = new OkHttpClient().newCall(request).execute()) {
            return response.isSuccessful() ? response.body().string() : "Error: " + response.code() + " " + response.message();
        }
    }

}
