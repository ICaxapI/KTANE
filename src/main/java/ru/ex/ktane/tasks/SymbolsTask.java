package ru.ex.ktane.tasks;

import ru.ex.ktane.utils.Symbols;

import java.util.ArrayList;
import java.util.Arrays;

public class SymbolsTask {
    private static SymbolsTask instance;
    private ArrayList<ArrayList<Symbols>> conditions;

    private SymbolsTask(){
        conditions = new ArrayList<>();
        ArrayList<Symbols> condition = new ArrayList<>();
        Symbols[] symbols1 = {Symbols.chupa, Symbols.at, Symbols.lambda, Symbols.longN, Symbols.octopus, Symbols.nc, Symbols.dotLeftC};
        Symbols[] symbols2 = {Symbols.oe, Symbols.chupa, Symbols.dotLeftC, Symbols.peel, Symbols.starEmpty, Symbols.nc, Symbols.qMark};
        Symbols[] symbols3 = {Symbols.copyright, Symbols.ass, Symbols.peel, Symbols.jc, Symbols.ic, Symbols.lambda, Symbols.starEmpty};
        Symbols[] symbols4 = {Symbols.b, Symbols.paragraph, Symbols.soft, Symbols.octopus, Symbols.jc, Symbols.qMark, Symbols.smile};
        Symbols[] symbols5 = {Symbols.trident, Symbols.smile, Symbols.soft, Symbols.dotRightC, Symbols.paragraph, Symbols.animal, Symbols.starFull};
        Symbols[] symbols6 = {Symbols.b, Symbols.oe, Symbols.equally, Symbols.ae, Symbols.trident, Symbols.ic, Symbols.omega};
        condition.addAll(Arrays.asList(symbols1));
        conditions.add(condition);
        condition.clear();
        condition.addAll(Arrays.asList(symbols2));
        conditions.add(condition);
        condition.clear();
        condition.addAll(Arrays.asList(symbols3));
        conditions.add(condition);
        condition.clear();
        condition.addAll(Arrays.asList(symbols4));
        conditions.add(condition);
        condition.clear();
        condition.addAll(Arrays.asList(symbols5));
        conditions.add(condition);
        condition.clear();
        condition.addAll(Arrays.asList(symbols6));
        conditions.add(condition);
    }



    public static SymbolsTask getInstance(){
        if (instance == null) instance = new SymbolsTask();
        return instance;
    }
}
