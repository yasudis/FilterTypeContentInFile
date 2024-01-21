package org.yasudis.Controller;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ExpectedException;
import org.yasudis.model.dataSorte.DataTypeSorterModel;
import org.yasudis.model.sortOption.SorterParameterByDataType;
import org.yasudis.view.DataTypeSorterView;

import static org.junit.jupiter.api.Assertions.*;

public class DataTypeSorterControllerTest {
    private DataTypeSorterModel dataTypeSorterModelTest = new DataTypeSorterModel();
    private DataTypeSorterView dataTypeSorterViewTest = new DataTypeSorterView();
    private DataTypeSorterController dataTypeSorterControllerTest = new DataTypeSorterController(dataTypeSorterModelTest, dataTypeSorterViewTest);
    String[] args = new String[]{"-f", "-a", "in1.txt", "in2.txt", "in6.txt"};


    @Test
    public void run_Null() {

            dataTypeSorterControllerTest.run();
    }

    @Test
    public void setup() {
        dataTypeSorterControllerTest.setup(args);
        assertNotNull(dataTypeSorterModelTest);
    }

    @Test
    public void setup_NO_NULL() {
        SorterParameterByDataType expected;
        String[] args = null;


    }

    @Test
    public void showStatics() {
    }
}