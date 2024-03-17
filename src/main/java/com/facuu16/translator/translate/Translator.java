package com.facuu16.translator.translate;

import com.facuu16.translator.enums.Language;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public interface Translator {

    @NotNull
    String request(@NotNull String text, @NotNull Language from, @NotNull Language to) throws Exception;

    @NotNull
    default CompletableFuture<String> requestAsync(@NotNull String text, @NotNull Language from, @NotNull Language to) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return request(text, from, to);
            } catch (Exception e) {
                throw new RuntimeException("Error requesting: ", e);
            }
        });
    };

}
