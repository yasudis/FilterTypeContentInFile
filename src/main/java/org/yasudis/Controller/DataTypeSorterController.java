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
        try {
            dataTypeSorterModel.run();
        } catch (NullPointerException e){
            System.err.println("Не установлены параметры для сортировки. Установите параметры сортировки с помощью метода setup(String[] args). ");
            System.exit(1);
        }

    }

    public void setup(String[] args) {
       try {
        dataTypeSorterModel.setSorterParameter(args);
        } catch (NullPointerException x){
           System.err.println("Переданная пустая строка в метод.");
           System.exit(1);
        }
    }

    public void showStatics() {
        dataTypeSorterView.printStatistics(dataTypeSorterModel.getStatics());
    }
}
