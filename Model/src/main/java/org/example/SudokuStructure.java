package org.example;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;


public class SudokuStructure implements Serializable, Cloneable {

    private static final Logger logger = Logger.getLogger(SudokuStructure.class.getName());
    private List<SudokuField> contents = Arrays.asList(new SudokuField[9]);

    public void setContents(SudokuField field, int index) {
        logger.trace("Setting field: " + index + " to: " + field.getFieldValue());
        contents.set(index, new SudokuField());
        contents.get(index).setFieldValue(field.getFieldValue());
    }

    public SudokuField get(int index) {
        logger.trace("Trying to retrieve value of field: " + index);
        return contents.get(index);
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
    public Object clone() throws CloneNotSupportedException {
        logger.info("Cloning SudokuStructure");
        SudokuStructure structureClone = (SudokuStructure) super.clone();
        structureClone.contents = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            if (this.get(i) != null) {
                structureClone.setContents((SudokuField) this.get(i).clone(), i);
            }
        }
        return structureClone;
    }
}
