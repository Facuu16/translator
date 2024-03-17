package com.facuu16.translator.controller;

import com.facuu16.translator.enums.Language;
import com.facuu16.translator.translate.Translator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import team.unnamed.inject.Inject;

public class TranslatorController {

    @Inject
    private static Translator translator;

    @FXML
    private ComboBox<Language> source;

    @FXML
    private ComboBox<Language> target;

    @FXML
    private TextArea text;

    @FXML
    private TextArea translated;

    @FXML
    public void initialize() {
        final Language[] values = Language.values();

        for (final Language language : values) {
            source.getItems().add(language);

            if (language != Language.AUTO)
                target.getItems().add(language);
        }

        source.setValue(Language.AUTO);
        target.setValue(Language.ENGLISH);

        source.setPromptText(Language.AUTO.name());
        target.setPromptText(Language.ENGLISH.name());

        source.getSelectionModel().selectedItemProperty().addListener((observable, old, current) -> {
            source.setPromptText(current.name());
        });

        target.getSelectionModel().selectedItemProperty().addListener((observable, old, current) -> {
            target.setPromptText(current.name());
        });
    }

    @FXML
    public void translate() throws Exception {
        final Language from = source.getValue();
        final Language to = target.getValue();

        if (from == to) {
            final Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setHeaderText("Error");
            alert.setContentText("The languages must be different!");
            alert.show();
            return;
        }

        translated.setText(translator.request(text.getText(), from, to));
    }

}