package ru.ex.ktane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


public class Launcher extends Application {

    private static Launcher instance;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        primaryStage.setTitle("KTANE");
        primaryStage.setResizable(false);
        FXMLLoader loader = new FXMLLoader();
        TabPane root = loader.load(getClass().getResource("general.fxml").openStream());
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public List<File> getFiles() throws URISyntaxException, IOException {
        ArrayList<File> files = new ArrayList<>();
        URI uri = getClass().getResource("").toURI();
        Path myPath;
        if (uri.getScheme().equals("jar")) {
            String jarDir = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getFile()).getParent();
            myPath = Paths.get(jarDir + "/img/symbols/");
            files.addAll(Files.walk(myPath, 1).filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList()));
        } else {
            myPath = Paths.get(getClass().getResource("img/symbols/").toURI());
            files.addAll(Files.walk(myPath, 1).filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList()));
        }
        return files;
    }

    public static Launcher getInstance(){
        return instance;
    }
}
