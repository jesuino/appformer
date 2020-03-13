package org.dashbuilder.renderer.c3.client;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

import elemental2.dom.DomGlobal;
import org.dashbuilder.displayer.AnimationTarget;

public class DataGenerator {

    private long transitionMs = 0;
    private boolean isAnimated;
    private String[][] allData;

    private Consumer<String[][]> consumer;
    private double lastTimeoutId = -1;
    private int currentStep;
    private int nSteps;
    Function<Integer, String[][]> partialDataFunction;
    private boolean isAutoStart;

    public DataGenerator(boolean isAnimated, boolean isAutoStart, AnimationTarget animationType, long transitionMs, String[][] columns) {
        this.isAutoStart = isAutoStart;
        this.transitionMs = transitionMs;
        this.allData = columns;
        this.isAnimated = isAnimated;
        switch (animationType) {
            case VALUES:
                this.nSteps = columns[0].length;
                partialDataFunction = this::partialValues;
                break;
            case SERIES:
            default:
                this.nSteps = columns.length;
                partialDataFunction = this::partialSeries;
                break;
        }
    }

    public void start(Consumer<String[][]> consumer) {
        this.consumer = consumer;
        this.currentStep = 0;
        DomGlobal.clearTimeout(lastTimeoutId);
        if (!isAnimated || nSteps == 1) {
            consumer.accept(allData);
            return;
        }
        doStep();
    }

    public boolean isAnimated() {
        return isAnimated;
    }

    public String[][] getAllData() {
        return allData;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public int getnSteps() {
        return nSteps;
    }

    public boolean isAutoStart() {
        return isAutoStart;
    }

    private void doStep() {
        if (currentStep < nSteps) {
            String[][] partialData = partialDataFunction.apply(currentStep);
            consumer.accept(partialData);
            currentStep++;
            lastTimeoutId = DomGlobal.setTimeout(values -> doStep(), transitionMs);
        } else {
            DomGlobal.clearTimeout(lastTimeoutId);
        }
    }

    private String[][] partialValues(int step) {
        String[][] partialData = new String[allData.length][];
        for (int i = 0; i < partialData.length; i++) {
            partialData[i] = Arrays.copyOfRange(allData[i], 0, step + 2);
        }
        return partialData;
    }

    private String[][] partialSeries(int length) {
        String[][] partialData = new String[length][];
        for (int i = 0; i < partialData.length; i++) {
            partialData[i] = Arrays.copyOf(allData[i], allData[i].length);
        }
        return partialData;
    }

}
