package org.example;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import org.example.exceptions.CloningException;
import org.example.exceptions.InvalidArgumentException;
import org.example.exceptions.NullArgumentException;
import org.example.exceptions.OutOfBoundsException;


public class SudokuStructure implements Serializable, Cloneable {

    ResourceBundle resourceBundle = ResourceBundle.getBundle("ExceptionsMessages");
    private List<SudokuField> contents = Arrays.asList(new SudokuField[9]);

    public void setContents(SudokuField field, int index) throws InvalidArgumentException {
        if (field == null) {
            throw new NullArgumentException("NullArgumentException");
        }
        try {
            contents.set(index, new SudokuField());
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new OutOfBoundsException(resourceBundle.getString("OutOfBoundsException"));
        }
        contents.get(index).setFieldValue(field.getFieldValue());
    }

    public SudokuField get(int index) throws OutOfBoundsException {
        try {
            return contents.get(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new OutOfBoundsException(resourceBundle.getString("OutOfBoundsException"));
        }
    }

    public boolean verify() {
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 9; j++) {
                if (contents.get(j).getFieldValue() == contents.get(i).getFieldValue()
                        && contents.get(j).getFieldValue() != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("contents", contents)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SudokuStructure that)) {
            return false;
        }
        return Objects.equal(contents, that.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(contents);
    }

    @Override
    public Object clone() throws CloningException {
        SudokuStructure structureClone;
        try {
            structureClone = (SudokuStructure) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new CloningException(resourceBundle.getString("CloningExceptionSudokuStructure"));
        }
        structureClone.contents = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            if (this.get(i) != null) {
                structureClone.setContents((SudokuField) this.get(i).clone(), i);
            }
        }
        return structureClone;
    }
}
