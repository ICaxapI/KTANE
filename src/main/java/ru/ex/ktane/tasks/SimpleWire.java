package ru.ex.ktane.tasks;

import javafx.scene.control.RadioButton;
import ru.ex.ktane.utils.Wire;

import java.util.ArrayList;
import java.util.Arrays;

public class SimpleWire {
    private static SimpleWire instance;
    private static ArrayList<RadioButton> activeWires;

    private SimpleWire (){
        activeWires = new ArrayList<>();
    }

    private boolean evenSerial = false;

    public void removeFromActiveArray(RadioButton radioButton){
        if (activeWires.contains(radioButton)){
            activeWires.remove(radioButton);
        }
    }

    public void addToActiveArray(RadioButton radioButton){
        if (!activeWires.contains(radioButton)){
            activeWires.add(radioButton);
        }
    }

    public byte findLastWire(ArrayList<ArrayList<RadioButton>> arrayListArrayList){
        final byte[] returnValue = new byte[1];
        returnValue[0] = -1;
        arrayListArrayList.forEach(radioButtons -> {
            radioButtons.forEach(radioButton -> {
                if (!radioButton.getText().equals("Отсутствует") && activeWires.contains(radioButton))
                    switch (radioButton.getText()){
                        case "Красный":
                            returnValue[0] = 1;
                            break;
                        case "Жёлтый":
                            returnValue[0] = 2;
                            break;
                        case "Синий":
                            returnValue[0] = 3;
                            break;
                        case "Белый":
                            returnValue[0] = 4;
                            break;
                        case "Чёрный":
                            returnValue[0] = 5;
                            break;
                    }
            });
        });
        return returnValue[0];
    }

    public byte[] computeCountWires(ArrayList<ArrayList<RadioButton>> arrayListArrayList){
        byte[] colors = new byte[7]; //первый байт - колво проводов, остальные - цвета в пос-ти К Ж С Б Ч
        colors[1] = 0; //Красный
        colors[2] = 0; //Жёлтый
        colors[3] = 0; //Синий
        colors[4] = 0; //Белый
        colors[5] = 0; //Чёрный
        colors[6] =  findLastWire(arrayListArrayList);; //Хранит цвет последнего провода. 1=К 2=Ж 3=С 4=Б 5=Ч
        activeWires.forEach(radioButton -> {
            switch (radioButton.getText()){
                case "Красный":
                    colors[Wire.red.ordinal()]++;
                    colors[Wire.all.ordinal()]++;
                    break;
                case "Жёлтый":
                    colors[Wire.yellow.ordinal()]++;
                    colors[Wire.all.ordinal()]++;
                    break;
                case "Синий":
                    colors[Wire.blue.ordinal()]++;
                    colors[Wire.all.ordinal()]++;
                    break;
                case "Белый":
                    colors[Wire.white.ordinal()]++;
                    colors[Wire.all.ordinal()]++;
                    break;
                case "Чёрный":
                    colors[Wire.black.ordinal()]++;
                    colors[Wire.all.ordinal()]++;
                    break;
            }
        });
        return colors;
    }

    public String foundSolution(byte[] colors){
        switch (colors[Wire.all.ordinal()]){
            case 3:
                if (colors[Wire.red.ordinal()] == 0) return "Режьте второй.";
                else if (colors[Wire.last.ordinal()] == Wire.white.ordinal()) return "Режьте последний.";
                else if (colors[Wire.blue.ordinal()] > 1) return "Режьте последний синий.";
                else return "Режьте последний.";
            case 4:
                if (colors[Wire.red.ordinal()] > 1 && !evenSerial) return "Если последняя цифра серийного номера нечетная, режьте последний красный.";
                else if (colors[Wire.last.ordinal()] == Wire.yellow.ordinal() && colors[Wire.red.ordinal()] == 0) return "Режьте первый.";
                else if (colors[Wire.blue.ordinal()] == 1) return "Режьте первый.";
                else if (colors[Wire.yellow.ordinal()] > 1) return "Режьте последний.";
                else return "Режьте второй.";
            case 5:
                if (colors[Wire.last.ordinal()] == Wire.black.ordinal() && !evenSerial) return "Если последняя цифра серийного номера нечетная, режьте четвертый.";
                else if (colors[Wire.red.ordinal()] == 1 && colors[Wire.yellow.ordinal()] > 1) return "Режьте первый.";
                else if (colors[Wire.black.ordinal()] == 0) return "Режьте второй.";
                else return "Режьте первый.";
            case 6:
                if (colors[Wire.yellow.ordinal()] == 0 && !evenSerial) return "Если последняя цифра серийного номера нечетная, режьте третий.";
                else if (colors[Wire.yellow.ordinal()] == 1 && colors[Wire.white.ordinal()] > 1) return "Режьте четвёртый.";
                else if (colors[Wire.red.ordinal()] == 0) return "Режьте последний.";
                else return "Режьте четвёртый.";
        }
        return "Error, решение не найденно." + Arrays.toString(colors);
    }

    static public SimpleWire getInstance(){
        if (instance == null) instance = new SimpleWire();
        return instance;
    }

    public void setEvenSerial(boolean evenSerial) {
        this.evenSerial = evenSerial;
    }
}
