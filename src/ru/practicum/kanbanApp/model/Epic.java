package ru.practicum.kanbanApp.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subtasksIdListInEpic = new ArrayList<>();

    public Epic(String title, String description) {
        super(title, description);
    }

    public List<Integer> getSubtasksIdListInEpic() {
        return subtasksIdListInEpic;
    }

    @Override
    public String toString() {
        return ("ID: " + id + " Наименование: " + title + " Описание: " + description
                + " Статус: " + taskStatus + " Список идентификаторов подзадач: " + subtasksIdListInEpic);
    }
}
