package ru.ex.ktane.controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.ex.ktane.tasks.SimpleWire;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class GeneralController {
    @FXML private VBox simpleWire;
    @FXML private TextField simpleWireSolution;
    @FXML private Button resetSimpleWires;
    @FXML private CheckBox simpleWiresEvenSerial;

    private ArrayList<ArrayList<RadioButton>> simpleWiresArray;
    private byte simpleWireCount = 0;

    public void initialize() {
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
                        if (radioButtonB != radioButtonA) radioButtonB.setSelected(false);
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

}
