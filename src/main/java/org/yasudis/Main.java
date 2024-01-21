package org.yasudis;

import org.yasudis.Controller.DataTypeSorterController;
import org.yasudis.model.dataSorte.DataTypeSorterModel;
import org.yasudis.view.DataTypeSorterView;

/**
 * Программа предназначена для фильтрации типов данных содержимых из файлов, а также их записи в другие файлы.
 * Выполнение программы происходит через командную строку согласно Техническому Заданию.
 * Основная архитектура сделана по шаблону MVС.
 * В пакете model лежит бизнес логика Модели, view - представление данных(вывод статистики на консоль), и пакете Controller
 *      который управляет model и view. В main создаются только классы dataTypeSorterController, DataTypeSorterModel и DataTypeSorterView.
 *      Все остальные классы-вспомогательные для решения задачи.
 * Перед запуском сортировки сначала нужно установить значения сортировки через dataTypeSorterController.setup(args),
 *      затем запустить dataTypeSorterController.run(), а чтобы показать статистику-напишите dataTypeSorterController.showStatics();
 */
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