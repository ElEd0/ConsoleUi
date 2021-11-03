package es.ed0.terminalui.ui;

import es.ed0.terminalui.ui.style.BorderStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Component to draw graphs and plot or scatter custom data
 */
public class Graph extends Canvas {

    private BorderStyle style;
    private int width, height;

    private List<String> xLabels;
    private double min, max;
    private boolean enableMin = false, enableMax = false;

    public Graph(BorderStyle style, int width, int height) {
        super(width, height);
        this.style = style;
        this.width = width;
        this.height = height;
        this.xLabels = new ArrayList<>();
    }

    @Override
    protected void print(StringBuilder sb) {



    }

    public void setXLabels(List<String> xLabels) {
        this.xLabels = xLabels;
    }

    public void setMin(double min) {
        this.min = min;
        this.enableMin = true;
    }

    public void removeMin() {
        this.min = 0;
        this.enableMin = false;
    }

    public double getMin() {
        return min;
    }

    public void setMax(double max) {
        this.max = max;
        this.enableMax = true;
    }

    public void removeMax() {
        this.max = 0;
        this.enableMax = false;
    }

    public double getMax() {
        return max;
    }
}
