package ru.ex.ktane.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import ru.ex.ktane.tasks.ButtonTask;
import ru.ex.ktane.tasks.SimpleWire;
import ru.ex.ktane.tasks.SymbolsTask;
import ru.ex.ktane.utils.ButtonColor;
import ru.ex.ktane.utils.ButtonLabel;
import ru.ex.ktane.utils.ButtonOther;
import ru.ex.ktane.utils.Symbols;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class GeneralController {

    //SimpleWire
    @FXML private VBox simpleWire;
    @FXML private TextField simpleWireSolution;
    @FXML private Button resetSimpleWires;
    @FXML private CheckBox simpleWiresEvenSerial;
    private ArrayList<ArrayList<RadioButton>> simpleWiresArray;
    //SimpleWire

    //Button
    @FXML private VBox button;
    @FXML private Button buttonReset;
    @FXML private TextArea buttonSolution;
    private ArrayList<RadioButton> buttonColor;
    private ArrayList<RadioButton> buttonLabel;
    private ArrayList<CheckBox> buttonOther;
    //Button

    //Symbols
    @FXML private GridPane symbolsPane;
    @FXML private Button resetSymbols;
    @FXML private HBox symbolsAnsverPane;
    ArrayList<ImageView> ansverImages;
    ArrayList<File> symbolsImages;
    ArrayList<Symbols> symbolsActiveSymbols;
    //Symbols


    public void initialize() {
        initSimpleWires();
        initButton();
        initSymbols();
    }

    private void initSymbols(){
        ansverImages = new ArrayList<>();
        List<ImageView> imageViews = symbolsAnsverPane.getChildren().stream().filter(node -> node instanceof ImageView).map(node -> (ImageView) node).collect(Collectors.toList());
        ansverImages.addAll(imageViews);

        List<File> files;
        symbolsImages = new ArrayList<>();
        symbolsActiveSymbols = new ArrayList<>();
        try {
            files = Files.walk(Paths.get(getClass().getResource("../img/symbols/").toURI()))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
            symbolsImages.addAll(files);
            files.clear();
            byte cellNumb = 0;
            for (int r = 0; r <= 2; r++) {
                for (int c = 0; c <= 8; c++) {
                    ImageView imageView = new ImageView(symbolsImages.get(cellNumb).toURI().toString());
                    symbolsPane.add(imageView, c, r);
                    imageView.setUserData(Symbols.valueOf(symbolsImages.get(cellNumb).getName().split(".png")[0]));
                    imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        if (symbolsActiveSymbols.contains(imageView.getUserData())){
                            symbolsActiveSymbols.remove(imageView.getUserData());
                            imageView.setEffect(null);
                            updateUiSymbols();
                        }
                        else if (symbolsActiveSymbols.size() < 4){
                            symbolsActiveSymbols.add((Symbols) imageView.getUserData());
                            imageView.setEffect(new ColorAdjust(0.5, 0.8, 0.5, 0.5));
                            updateUiSymbols();
                        }
                    });
                    cellNumb++;
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        resetSymbols.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> resetSymbols());
    }

    private void updateUiSymbols(){
        ArrayList<Symbols> solution = SymbolsTask.getInstance().foundSolution(symbolsActiveSymbols);
        for (int i = 0; i < 4; i++) {
            if (solution != null && solution.size() == 4) {
                int finalI = i;
                symbolsImages.forEach(file -> {
                    if (file.getName().split(".png")[0].equals(solution.get(finalI).name())) {
                        ansverImages.get(finalI).setImage(new Image(file.toURI().toString()));
                    }
                });
            } else {
                ansverImages.forEach(imageView1 -> {
                    imageView1.setImage(null);
                });
            }
        }
    }

    private void initButton(){
        List<HBox> buttonList = button.getChildren().stream().filter(node -> node instanceof HBox).map(node -> (HBox) node).collect(Collectors.toList());

        buttonColor = new ArrayList<>();
        List<RadioButton> radioColor = buttonList.get(1).getChildren().stream().filter(node -> node instanceof RadioButton).map(node -> (RadioButton) node).collect(Collectors.toList());
        radioColor.forEach(radioButtonA -> {
            buttonColor.add(radioButtonA);
            if (radioButtonA.getText().equals("Красный")){
                radioButtonA.setSelected(true);
                ButtonTask.getInstance().setButtonColor(ButtonColor.red);
            }
            radioButtonA.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                buttonColor.forEach(radioButtonB -> {
                    if (!radioButtonA.equals(radioButtonB)) radioButtonB.setSelected(false);
                    if (!radioButtonA.isSelected()) radioButtonA.setSelected(true);
                });
                switch (radioButtonA.getText()){
                    case "Красный":
                        ButtonTask.getInstance().setButtonColor(ButtonColor.red);
                        break;
                    case "Жёлтый":
                        ButtonTask.getInstance().setButtonColor(ButtonColor.yellow);
                        break;
                    case "Синий":
                        ButtonTask.getInstance().setButtonColor(ButtonColor.blue);
                        break;
                    case "Белый":
                        ButtonTask.getInstance().setButtonColor(ButtonColor.white);
                        break;
                }
                buttonSolution.deleteText(0, buttonSolution.getLength());
                buttonSolution.appendText(ButtonTask.getInstance().foundSolution());
            });
        });

        buttonLabel = new ArrayList<>();
        List<RadioButton> radioLabel = buttonList.get(2).getChildren().stream().filter(node -> node instanceof RadioButton).map(node -> (RadioButton) node).collect(Collectors.toList());
        radioLabel.forEach(radioButtonA -> {
            buttonLabel.add(radioButtonA);
            if (radioButtonA.getText().equals("Abort")){
                radioButtonA.setSelected(true);
                ButtonTask.getInstance().setButtonLabel(ButtonLabel.abort);
            }
            radioButtonA.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                buttonLabel.forEach(radioButtonB -> {
                    if (!radioButtonA.isSelected()) radioButtonA.setSelected(true);
                    if (!radioButtonA.equals(radioButtonB)) radioButtonB.setSelected(false);
                });
                switch (radioButtonA.getText()){
                    case "Abort":
                        ButtonTask.getInstance().setButtonLabel(ButtonLabel.abort);
                        break;
                    case "Detonate":
                        ButtonTask.getInstance().setButtonLabel(ButtonLabel.detonate);
                        break;
                    case "Hold":
                        ButtonTask.getInstance().setButtonLabel(ButtonLabel.hold);
                        break;
                    case "Другое":
                        ButtonTask.getInstance().setButtonLabel(ButtonLabel.other);
                        break;
                }
                buttonSolution.deleteText(0, buttonSolution.getLength());
                buttonSolution.appendText(ButtonTask.getInstance().foundSolution());
            });
        });

        buttonOther = new ArrayList<>();
        List<CheckBox> otherBoxes = buttonList.get(3).getChildren().stream().filter(node -> node instanceof CheckBox).map(node -> (CheckBox) node).collect(Collectors.toList());
        otherBoxes.forEach(checkBoxA -> {
            buttonOther.add(checkBoxA);
            checkBoxA.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                buttonOther.forEach(checkBoxB -> {
                    if (checkBoxA.getText().equals("Больше 1-ой батарейки")){
                        if (checkBoxB.getText().equals("Больше 2-ух батареек")){
                            if (checkBoxB.isSelected()) checkBoxB.setSelected(false);
                            ButtonTask.getInstance().removeButtonOthers(ButtonOther.twoOrMoreBatt);
                        }
                    }
                    if (checkBoxA.getText().equals("Больше 2-ух батареек")){
                        if (checkBoxB.getText().equals("Больше 1-ой батарейки")){
                            if (checkBoxB.isSelected()) checkBoxB.setSelected(false);
                            ButtonTask.getInstance().removeButtonOthers(ButtonOther.oneOrMoreBatt);
                        }
                    }
                });
                switch (checkBoxA.getText()){
                    case "Нет CAR":
                        if (checkBoxA.isSelected()) ButtonTask.getInstance().addButtonOthers(ButtonOther.noCar);
                        else ButtonTask.getInstance().removeButtonOthers(ButtonOther.noCar);
                        break;
                    case "Нет FRK":
                        if (checkBoxA.isSelected()) ButtonTask.getInstance().addButtonOthers(ButtonOther.noFrk);
                        else ButtonTask.getInstance().removeButtonOthers(ButtonOther.noFrk);
                        break;
                    case "Больше 1-ой батарейки":
                        if (checkBoxA.isSelected()) ButtonTask.getInstance().addButtonOthers(ButtonOther.oneOrMoreBatt);
                        else ButtonTask.getInstance().removeButtonOthers(ButtonOther.oneOrMoreBatt);
                        break;
                    case "Больше 2-ух батареек":
                        if (checkBoxA.isSelected()) ButtonTask.getInstance().addButtonOthers(ButtonOther.twoOrMoreBatt);
                        else ButtonTask.getInstance().removeButtonOthers(ButtonOther.twoOrMoreBatt);
                        break;
                }
                buttonSolution.deleteText(0, buttonSolution.getLength());
                buttonSolution.appendText(ButtonTask.getInstance().foundSolution());
            });
        });
        buttonSolution.deleteText(0, buttonSolution.getLength());
        buttonSolution.appendText(ButtonTask.getInstance().foundSolution());
        buttonReset.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            resetButton();
        });
    }

    private void initSimpleWires(){
        simpleWiresArray = new ArrayList<>();
        List<HBox> wiresList = simpleWire.getChildren().stream().filter(node -> node instanceof HBox).map(node -> (HBox) node).collect(Collectors.toList());
        wiresList.forEach(hBox -> {
            ArrayList<RadioButton> radioButtonsArray = new ArrayList<>();
            List<RadioButton> radioButtonList = hBox.getChildren().stream().filter(node -> node instanceof RadioButton).map(node -> (RadioButton) node).collect(Collectors.toList());
            radioButtonList.forEach(radioButtonA -> {
                radioButtonsArray.add(radioButtonA);
                if (radioButtonA.getText().equals("Отсутствует")) radioButtonA.setSelected(true);
                radioButtonA.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                    radioButtonsArray.forEach(radioButtonB -> {
                        if (!radioButtonB.equals(radioButtonA)) radioButtonB.setSelected(false);
                        SimpleWire.getInstance().removeFromActiveArray(radioButtonB);
                    });
                    if (!radioButtonA.getText().equals("Отсутствует")) SimpleWire.getInstance().addToActiveArray(radioButtonA);
                    if (!radioButtonA.isSelected()) radioButtonA.setSelected(true);
                    simpleWireSolution.deleteText(0, simpleWireSolution.getLength());
                    simpleWireSolution.appendText(SimpleWire.getInstance().foundSolution(SimpleWire.getInstance().computeCountWires(simpleWiresArray)));
                });
                simpleWiresArray.add(radioButtonsArray);
            });
        });
        simpleWireSolution.appendText(SimpleWire.getInstance().foundSolution(SimpleWire.getInstance().computeCountWires(simpleWiresArray)));
        simpleWiresEvenSerial.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            SimpleWire.getInstance().setEvenSerial(simpleWiresEvenSerial.isSelected());
            simpleWireSolution.deleteText(0, simpleWireSolution.getLength());
            simpleWireSolution.appendText(SimpleWire.getInstance().foundSolution(SimpleWire.getInstance().computeCountWires(simpleWiresArray)));
        });
        resetSimpleWires.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            resetSimpleWires();
        });
    }

    private void resetSimpleWires(){
        simpleWiresArray.forEach(radioButtons -> {
            radioButtons.forEach(radioButton -> {
                if (radioButton.getText().equals("Отсутствует")) radioButton.setSelected(true);
                else radioButton.setSelected(false);
                SimpleWire.getInstance().removeFromActiveArray(radioButton);
                simpleWireSolution.deleteText(0, simpleWireSolution.getLength());
                simpleWireSolution.appendText(SimpleWire.getInstance().foundSolution(SimpleWire.getInstance().computeCountWires(simpleWiresArray)));
            });
        });
        simpleWiresEvenSerial.setSelected(false);
        SimpleWire.getInstance().setEvenSerial(false);
    }

    private void resetSymbols(){
        symbolsActiveSymbols.clear();
        updateUiSymbols();
        List<ImageView> symboslImages = symbolsPane.getChildren().stream().filter(node -> node instanceof ImageView).map(node -> (ImageView) node).collect(Collectors.toList());
        ArrayList<ImageView> symbImages = new ArrayList<>();
        symbImages.addAll(symboslImages);
        symbImages.forEach(imageView -> {
            imageView.setEffect(null);
        });
        List<ImageView> symboslAnsImages = symbolsAnsverPane.getChildren().stream().filter(node -> node instanceof ImageView).map(node -> (ImageView) node).collect(Collectors.toList());
        ArrayList<ImageView> symbImagesAns = new ArrayList<>();
        symbImagesAns.addAll(symboslAnsImages);
        symbImagesAns.forEach(imageView -> {
            imageView.setImage(null);
        });
    }

    private void resetButton(){
        buttonColor.forEach(radioButton -> {
            if (radioButton.getText().equals("Красный")){
                radioButton.setSelected(true);
                ButtonTask.getInstance().setButtonColor(ButtonColor.red);
            } else {
                radioButton.setSelected(false);
            }
        });
        buttonLabel.forEach(radioButton -> {
            if (radioButton.getText().equals("Abort")){
                radioButton.setSelected(true);
                ButtonTask.getInstance().setButtonLabel(ButtonLabel.abort);
            } else {
                radioButton.setSelected(false);
            }
        });
        buttonOther.forEach(checkBox -> {
            checkBox.setSelected(false);
            switch (checkBox.getText()){
                case "Нет CAR":
                    ButtonTask.getInstance().removeButtonOthers(ButtonOther.noCar);
                    break;
                case "Нет FRK":
                    ButtonTask.getInstance().removeButtonOthers(ButtonOther.noFrk);
                    break;
                case "Больше 1-ой батарейки":
                    ButtonTask.getInstance().removeButtonOthers(ButtonOther.oneOrMoreBatt);
                    break;
                case "Больше 2-ух батареек":
                    ButtonTask.getInstance().removeButtonOthers(ButtonOther.twoOrMoreBatt);
                    break;
            }
        });
        buttonSolution.deleteText(0, buttonSolution.getLength());
        buttonSolution.appendText(ButtonTask.getInstance().foundSolution());
    }

}
