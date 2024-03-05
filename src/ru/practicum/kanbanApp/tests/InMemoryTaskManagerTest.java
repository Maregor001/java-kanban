package ru.practicum.kanbanApp.tests;

import org.junit.jupiter.api.Test;
import ru.practicum.kanbanApp.model.Epic;
import ru.practicum.kanbanApp.model.Subtask;
import ru.practicum.kanbanApp.model.Task;
import ru.practicum.kanbanApp.service.Managers;
import ru.practicum.kanbanApp.service.taskManager.TaskManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private final TaskManager taskManager = Managers.getDefault();

    @Test
    public void equalTitleAndDescriptionOfTasksShouldNotHaveEqualId() {
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 1", "Описание задачи 1");
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        assertNotEquals(taskManager.getTaskById(task1.getId()), taskManager.getTaskById(task2.getId()));
    }

    @Test
    public void epicsShouldEqualIfEqualId() {
        Epic epic = new Epic("Эпик", "Описание эпика");
        taskManager.createEpic(epic);
        Epic expectedEpic = taskManager.getEpicById(epic.getId());
        epic.setDescription("Обновленный эпик");
        assertEquals(expectedEpic, taskManager.getEpicById(epic.getId()));

        Subtask subtask = new Subtask("Подзадача", "Описание подзадачи", epic.getId());
        taskManager.createSubtask(subtask);
        Subtask expectedSubtask = taskManager.getSubtaskById(subtask.getId());
        subtask.setTitle("Обновленная подзадача");
        assertEquals(expectedSubtask, taskManager.getSubtaskById(subtask.getId()));
    }

    @Test
    public void shouldCreateAndGetByIdAllTypesByInMemoryTaskManager() {
        Task task = new Task("Задача 1", "Описание задачи 1");
        taskManager.createTask(task);
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", epic.getId());
        taskManager.createSubtask(subtask);

        assertNotNull(taskManager.getTaskById(task.getId()), "Task is null");
        assertNotNull(taskManager.getEpicById(epic.getId()), "Epic is null");
        assertNotNull(taskManager.getSubtaskById(subtask.getId()), "Subtask is null");
    }

    @Test
    public void shouldGetTaskAfterUpdatingId() {
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        taskManager.createTask(task1);
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        taskManager.createTask(task2);
        task2.setId(2);
        assertNotNull(taskManager.getTaskById(2));
    }

    @Test
    public void tasksShouldBeEqualIfEqualTaskId() {
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        task1.setId(12);
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        task2.setId(12);
        assertEquals(task1, task2);
    }

    @Test
    public void epicsShouldBeEqualIfEqualEpicId() {
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        epic1.setId(12);
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        epic2.setId(12);
        assertEquals(epic1, epic2);
    }

    @Test
    public void subtasksShouldBeEqualIfEqualSubtaskId() {
        Subtask subtask1 = new Subtask("Задача 1", "Описание задачи 1", 2);
        subtask1.setId(12);
        Subtask subtask2 = new Subtask("Задача 2", "Описание задачи 2", 2);
        subtask2.setId(12);
        assertEquals(subtask1, subtask2);
    }

    @Test
    public void notShouldBeAddSubtaskInNotCreatedEpic() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Задача 1", "Описание задачи 1", epic.getId());
        subtask.setEpicId(404);
        taskManager.createSubtask(subtask);
        assertEquals(0, epic.getSubtasksIdListInEpic().size(), epic.toString());
    }

    @Test
    public void notUpdateNonExistentEpic() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        taskManager.createEpic(epic);
        epic.setId(404);
        assertNull(taskManager.updateEpic(epic));
    }
}