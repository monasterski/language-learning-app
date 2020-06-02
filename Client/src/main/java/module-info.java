module language.learning.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.web;
    requires static lombok;
    requires java.compiler;
    requires java.instrument;
    requires jdk.unsupported;
    requires org.mapstruct.processor;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    //requires javafx.maven.plugin;

    opens pl.edu.agh.languagelearningclient;
    opens pl.edu.agh.languagelearningclient.controllers;
    opens pl.edu.agh.languagelearningclient.model;

    exports pl.edu.agh.languagelearningclient.controllers;
    exports pl.edu.agh.languagelearningclient.model;
}