package ru.practicum.kanbanApp.test;

import org.junit.jupiter.api.Test;
import ru.practicum.kanbanApp.model.Epic;
import ru.practicum.kanbanApp.model.Subtask;
import ru.practicum.kanbanApp.model.Task;

import static org.junit.jupiter.api.Assertions.*;
import static ru.practicum.kanbanApp.Main.taskManager;

class InMemoryHistoryManagerTest {
    @Test
    public void historyManagerMustDeleteOldVersionToHistory() {
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        taskManager.createTask(task1);
        taskManager.getTaskById(task1.getId());
        task1.setTitle("Обновленное наименование");
        taskManager.getTaskById(task1.getId());
        assertEquals(1, taskManager.getHistory().size());
        taskManager.deleteTaskById(task1.getId());
    }

    @Test
    public void historyManagerMustGetTaskFromNode() {
        Task task1 = new Task("Задача 2", "Описание задачи 2");
        taskManager.createTask(task1);
        taskManager.getTaskById(task1.getId());
        assertEquals(task1, taskManager.getHistory().get(0));
        taskManager.deleteTaskById(task1.getId());
    }

    @Test
    public void historyMustClearAfterDeletingTask() {
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        taskManager.createEpic(epic1);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", epic1.getId());
        taskManager.createSubtask(subtask1);
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        taskManager.createTask(task1);

        taskManager.getTaskById(task1.getId());
        taskManager.getSubtaskById(subtask1.getId());
        taskManager.getEpicById(epic1.getId());

        taskManager.deleteTaskById(task1.getId());
        taskManager.deleteEpicById(epic1.getId());
        taskManager.deleteSubtaskById(subtask1.getId());

        taskManager.getEpicById(epic1.getId());

        assertEquals(0, taskManager.getHistory().size());
    }

}