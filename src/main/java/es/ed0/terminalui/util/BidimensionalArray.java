package es.ed0.terminalui.util;

import java.util.ArrayList;

public class BidimensionalArray<T> {

    private final ArrayList<ArrayList<T>> data;
    private boolean dynamicSize;

    public BidimensionalArray(int rows, int cols, boolean dynamicSize) {
        this.dynamicSize = dynamicSize;
        this.data = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            this.data.add(new ArrayList<T>(cols));
            for (int c = 0; c < cols; c++) {
                this.data.get(r).add(null);
            }
        }
    }

    public int getRowCount() {
        return this.data.size();
    }

    public int getColCount(int row) {
        return this.data.get(row).size();
    }

    public int getMaxColCount() {
        int max = 0, curr = 0;
        for (ArrayList<T> rows : data) {
            if ((curr = rows.size()) > max) max = curr;
        }
        return max;
    }

    public T get(int row, int col) {
        return this.data.get(row).get(col);
    }

    /**
     * Finds the first position with null and inserts the element.
     * If the array is full the element will not be inserted, {@link #setDynamicSize(boolean)}
     * will not have an effect in this behaviour
     * @param t Element to insert
     */
    public void add(T t) {
        //  find the first empty position
        int r = 0, c = 0;
        boolean found = false;
        for (int rs = this.getRowCount(); r < rs; r++) {
            for (c = 0; c < this.getColCount(r); c++) {
                if (this.get(r, c) == null) {
                    found = true;
                    break;
                }
            }
            if (found) break;
        }
        if (found) add(t, r, c);
    }

    public void add(T t, int row, int col) {
        if (this.dynamicSize) {
            while (row >= this.getRowCount()) {
                this.data.add(new ArrayList<T>());
            }
            while (col >= this.getColCount(row)) {
                this.data.get(row).add(null);
            }
        }
        this.data.get(row).set(col, t);
    }

    public void remove(int row, int col) {
        add(null, row, col);
    }

    public void setRowCount(int rows) {
        int curr = getRowCount();
        if (rows > curr) {
            while (rows != getRowCount())
                this.data.add(new ArrayList<T>());
        } else if (rows < curr) {
            while ((curr = getRowCount()) != rows) {
                this.data.remove(curr - 1);
            }
        }
    }

    public void setColCount(int cols) {
        for (ArrayList<T> row : data) {
            int curr = row.size();
            if (cols > curr) {
                while (cols != row.size())
                    row.add(null);
            } else if (cols < curr) {
                while ((curr = row.size()) != cols) {
                    row.remove(curr - 1);
                }
            }
        }
    }

    /**
     * Dynamic sizing will resize the array to fit in elements
     * when the given row and column exceed the initial values
     * @return true if dynamic size is enabled, false otherwise
     */
    public boolean isDynamicSize() {
        return dynamicSize;
    }

    /**
     * Dynamic sizing will resize the array to fit in elements
     * when the given row and column exceed the initial values
     * @param dynamicSize
     */
    public void setDynamicSize(boolean dynamicSize) {
        this.dynamicSize = dynamicSize;
    }
}
