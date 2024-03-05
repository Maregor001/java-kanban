package ru.practicum.kanbanApp.service;

import ru.practicum.kanbanApp.service.historyManager.HistoryManager;
import ru.practicum.kanbanApp.service.historyManager.InMemoryHistoryManager;
import ru.practicum.kanbanApp.service.taskManager.InMemoryTaskManager;
import ru.practicum.kanbanApp.service.taskManager.TaskManager;

public class Managers {

    private Managers() {
    }

    public static TaskManager getDefault(){
        return new InMemoryTaskManager();

    }
    public static HistoryManager getDefaultHistory(){
    return new InMemoryHistoryManager();
    }
}
