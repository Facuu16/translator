package com.facuu16.translator;

import com.facuu16.translator.controller.TranslatorController;
import com.facuu16.translator.translate.ChatGptTranslator;
import com.facuu16.translator.translate.GoogleTranslator;
import com.facuu16.translator.translate.Translator;
import com.facuu16.translator.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import team.unnamed.inject.Injector;

import java.io.IOException;
import java.util.logging.Logger;

public final class TranslatorApplication extends Application {

    public static final Logger LOGGER = Logger.getLogger("Translator");

    public static void main(String[] args) {
        final String google = Objects.notNullOrElse(System.getenv("GOOGLE_TOKEN"), "");
        final String openai = Objects.notNullOrElse(System.getenv("OPENAI_TOKEN"), "");

        final Injector injector = Injector.create(
            binder -> {
                if (!google.isEmpty()) {
                    binder.bind(Translator.class).to(GoogleTranslator.class);
                    return;
                }

                if (!openai.isEmpty()) {
                    binder.bind(Translator.class).to(ChatGptTranslator.class);
                    return;
                }

                throw new RuntimeException("You must set at least one API Key!");
            },

            binder -> {
                binder.bind(String.class).named("google-key").toInstance(google);
                binder.bind(String.class).named("openai-key").toInstance(openai);
            }
        );

        injector.injectStaticMembers(TranslatorController.class);

        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/translator.fxml"));
        final BorderPane root = loader.load();

        final Scene scene = new Scene(root, 600, 400);

        stage.setResizable(false);
        stage.setTitle("Translator");
        stage.setScene(scene);
        stage.show();
    }

}