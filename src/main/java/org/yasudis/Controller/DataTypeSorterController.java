package org.yasudis.Controller;

import org.yasudis.model.dataSorte.DataTypeSorterModel;
import org.yasudis.view.DataTypeSorterView;

public class DataTypeSorterController {
    private DataTypeSorterModel dataTypeSorterModel;
    private DataTypeSorterView dataTypeSorterView;

    public DataTypeSorterController(DataTypeSorterModel model, DataTypeSorterView view) {
        dataTypeSorterModel = model;
        dataTypeSorterView = view;
    }

    public void run() {
        dataTypeSorterModel.run();
    }

    public void setup(String[] args) {
        dataTypeSorterModel.setSorterParameterByDataType(args);
    }
}
