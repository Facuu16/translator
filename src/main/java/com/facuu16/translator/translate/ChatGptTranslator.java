package com.facuu16.translator.translate;

import com.facuu16.translator.enums.Language;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import team.unnamed.inject.Inject;
import team.unnamed.inject.Named;
import team.unnamed.inject.Singleton;
import team.unnamed.inject.util.Validate;

import java.io.IOException;

@Singleton
public class ChatGptTranslator implements Translator {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_PARAMS = "{\"model\": \"%s\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}";

    private static final String MODEL = "gpt-3.5-turbo";

    @Inject
    @Named("openai-key")
    private String key;

    @Override
    @NotNull
    public String request(@NotNull String text, @NotNull Language from, @NotNull Language to) throws IOException {
        Validate.notNull(text);
        Validate.notNull(from);
        Validate.notNull(to);

        final String prompt = "Translate this from " + from.name() + " to " + to.name() + ": " + text;

        final Request request = new Request.Builder()
                .url(API_URL)
                .post(RequestBody.create(MediaType.parse("application/json"), String.format(API_PARAMS, MODEL, prompt)))
                .addHeader("Authorization", "Bearer " + key)
                .addHeader("Content-Type", "application/json")
                .build();

        try (final Response response = new OkHttpClient().newCall(request).execute()) {
            if (response.isSuccessful()) {
                final String body = response.body().string();
                final int start = body.indexOf("content") + 11;

                return body.substring(start, body.indexOf("\"", start));
            } else {
                return "Error: " + response.code() + " " + response.message();
            }
        }
    }

}
