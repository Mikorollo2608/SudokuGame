package org.example;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class SudokuField {
    private int value = 0;

    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int value) {
        if (value > 0 & value <= 9) {
            this.value = value;
        } else {
            this.value = 0;
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SudokuField that)) {
            return false;
        }
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
