package ru.practicum.kanbanApp.tests;

import ru.practicum.kanbanApp.model.Task;
import ru.practicum.kanbanApp.service.Managers;
import ru.practicum.kanbanApp.service.taskManager.TaskManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private final TaskManager taskManager = Managers.getDefault();

    @Test
    public void historyManagerMustSaveOldVersionToHistory() {
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        taskManager.createTask(task1);
        taskManager.getTaskById(task1.getId());
        task1.setTitle("Обновленное наименование");
        taskManager.getTaskById(task1.getId());
        assertEquals(2, taskManager.getHistory().size());

    }
}