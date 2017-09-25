package ru.ex.ktane.tasks;

import ru.ex.ktane.utils.Symbols;

import java.util.ArrayList;
import java.util.Arrays;

public class SymbolsTask {
    private static SymbolsTask instance;
    private ArrayList<ArrayList<Symbols>> conditions;
    private boolean containsSymbol = false;
    private boolean contains = false;
    private ArrayList<ArrayList<Symbols>> todeleteArr;
    private ArrayList<Symbols> todeleteSymb;

    private SymbolsTask(){
        conditions = new ArrayList<>();
        ArrayList<Symbols> condition = new ArrayList<>();
        final Symbols[] symbols1 = {Symbols.chupa, Symbols.at, Symbols.lambda, Symbols.longN, Symbols.octopus, Symbols.nc, Symbols.dotLeftC};
        final Symbols[] symbols2 = {Symbols.oe, Symbols.chupa, Symbols.dotLeftC, Symbols.peel, Symbols.starEmpty, Symbols.nc, Symbols.qMark};
        final Symbols[] symbols3 = {Symbols.copyright, Symbols.ass, Symbols.peel, Symbols.jc, Symbols.zc, Symbols.lambda, Symbols.starEmpty};
        final Symbols[] symbols4 = {Symbols.b, Symbols.paragraph, Symbols.soft, Symbols.octopus, Symbols.jc, Symbols.qMark, Symbols.smile};
        final Symbols[] symbols5 = {Symbols.trident, Symbols.smile, Symbols.soft, Symbols.dotRightC, Symbols.paragraph, Symbols.animal, Symbols.starFull};
        final Symbols[] symbols6 = {Symbols.b, Symbols.oe, Symbols.equally, Symbols.ae, Symbols.trident, Symbols.ic, Symbols.omega};
        condition.addAll(Arrays.asList(symbols1));
        conditions.add(condition);
        condition = new ArrayList<>();
        condition.addAll(Arrays.asList(symbols2));
        conditions.add(condition);
        condition = new ArrayList<>();
        condition.addAll(Arrays.asList(symbols3));
        conditions.add(condition);
        condition = new ArrayList<>();
        condition.addAll(Arrays.asList(symbols4));
        conditions.add(condition);
        condition = new ArrayList<>();
        condition.addAll(Arrays.asList(symbols5));
        conditions.add(condition);
        condition = new ArrayList<>();
        condition.addAll(Arrays.asList(symbols6));
        conditions.add(condition);
    }

    public ArrayList<Symbols> foundSolution(ArrayList<Symbols> selectedSymbosl){
        ArrayList<ArrayList<Symbols>> ansvers = new ArrayList<>();
        ansvers.addAll(conditions);
        if (selectedSymbosl.size() > 0){
            selectedSymbosl.forEach(symbolA -> {
                todeleteArr = new ArrayList<>();
                ansvers.forEach(condition -> {
                    containsSymbol = false;
                    condition.forEach(symbolB -> {
                        if (symbolB.equals(symbolA)) containsSymbol = true;
                    });
                    if (!containsSymbol) todeleteArr.add(condition);
                });
                ansvers.removeAll(todeleteArr);
            });
        } else {
            todeleteArr = new ArrayList<>();
        }
        ArrayList<Symbols> tempAnsver = new ArrayList<>();
        if((ansvers.size() == 1) && selectedSymbosl.size() == 4){
            todeleteSymb = new ArrayList<>();
            ansvers.get(0).forEach(symbolA -> {
                contains = false;
                selectedSymbosl.forEach(symbolB -> {
                    if (symbolA.equals(symbolB)) contains = true;
                });
                if (!contains) todeleteSymb.add(symbolA);
            });
            tempAnsver.addAll(ansvers.get(0));
            tempAnsver.removeAll(todeleteSymb);
        }
        if (tempAnsver.size() == 4 && selectedSymbosl.size() == 4) return tempAnsver;
        else return null;
    }

    public static SymbolsTask getInstance(){
        if (instance == null) instance = new SymbolsTask();
        return instance;
    }
}
