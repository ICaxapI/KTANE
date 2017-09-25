package ru.ex.ktane.tasks;

import ru.ex.ktane.utils.ButtonColor;
import ru.ex.ktane.utils.ButtonLabel;
import ru.ex.ktane.utils.ButtonOther;

import java.awt.*;
import java.util.ArrayList;

public class ButtonTask {
    private static ButtonTask instance;
    private ButtonLabel buttonLabel;
    private ButtonColor buttonColor;
    private ArrayList<ButtonOther> buttonOthers;
    private String holdingButton;

    private ButtonTask(){
        buttonOthers = new ArrayList<>();
        holdingButton = "\nСиняя полоска: отпустите, когда любая цифра таймера будет равна 4." +
                "\nБелая полоска: отпустите, когда любая цифра таймера будет равна 1." +
                "\nЖелтая полоска: отпустите, когда любая цифра таймера будет равна 5." +
                "\nЛюбая другая полоска: отпустите, когда любая цифра таймера будет равна 1.";
    }

    public String foundSolution(){
        if (buttonColor == ButtonColor.blue && buttonLabel == ButtonLabel.abort) return "Задержите кнопку." + holdingButton;
        else if (buttonLabel == ButtonLabel.detonate && (buttonOthers.contains(ButtonOther.oneOrMoreBatt) || buttonOthers.contains(ButtonOther.twoOrMoreBatt))) return "Нажмите и резко отпустите кнопку.";
        else if (buttonColor == ButtonColor.white && !buttonOthers.contains(ButtonOther.noCar)) return "Задержите кнопку." + holdingButton;
        else if (buttonOthers.contains(ButtonOther.twoOrMoreBatt) && !buttonOthers.contains(ButtonOther.noFrk)) return "Нажмите и резко отпустите кнопку.";
        else if (buttonColor == ButtonColor.yellow) return "Задержите кнопку." + holdingButton;
        else if (buttonColor == ButtonColor.red && buttonLabel == ButtonLabel.hold) return "Нажмите и резко отпустите кнопку.";
        else return "Задержите кнопку." + holdingButton;
        //return "Error, не найдено решений для " + buttonColor.name() + " " + buttonLabel.name() + " " + buttonOthers.toString();
    }

    public static ButtonTask getInstance(){
        if (instance == null){
            instance = new ButtonTask();
        }
        return instance;
    }

    public void addButtonOthers(ButtonOther buttonOther){
        if (!buttonOthers.contains(buttonOther)) buttonOthers.add(buttonOther);
    }

    public void removeButtonOthers(ButtonOther buttonOther){
        if (buttonOthers.contains(buttonOther)) buttonOthers.remove(buttonOther);
    }

    public void setButtonLabel(ButtonLabel buttonLabel) {
        this.buttonLabel = buttonLabel;
    }

    public void setButtonColor(ButtonColor buttonColor) {
        this.buttonColor = buttonColor;
    }
}
