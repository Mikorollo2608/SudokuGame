package org.example;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.io.Serializable;
import java.util.ResourceBundle;
import org.example.exceptions.CloningException;
import org.example.exceptions.NullArgumentException;

public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {

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

    @Override
    public Object clone() throws CloningException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ExceptionsMessages");
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new CloningException(resourceBundle.getString("CloningExceptionSudokuField"));
        }
    }

    @Override
    public int compareTo(SudokuField s) throws NullArgumentException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ExceptionsMessages");
        if (s == null) {
            throw new NullArgumentException(resourceBundle.getString("NullArgumentException"));
        }
        return Integer.compare(this.value, s.getFieldValue());
    }
}
