package ru.practicum.kanbanApp.service.historyManager;

import ru.practicum.kanbanApp.model.Task;

import java.util.List;

public interface HistoryManager {

    void addTask(Task task);

    List<Task> getHistory();

    void remove(int id);
}
