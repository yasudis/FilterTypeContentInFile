package org.yasudis;

import org.yasudis.Controller.DataTypeSorterController;
import org.yasudis.model.dataSorte.DataTypeSorterModel;
import org.yasudis.view.DataTypeSorterView;

public class Main {
    public static void main(String[] args) {
        DataTypeSorterModel dataTypeSorterModel = new DataTypeSorterModel();
        DataTypeSorterView dataTypeSorterView = new DataTypeSorterView();
        DataTypeSorterController dataTypeSorterController = new DataTypeSorterController(dataTypeSorterModel, dataTypeSorterView);

        dataTypeSorterController.setup(args);
        dataTypeSorterController.run();
        dataTypeSorterController.showStatics();
    }
}