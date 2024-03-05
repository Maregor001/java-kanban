package ru.practicum.kanbanApp.service.taskManager;

import ru.practicum.kanbanApp.model.Epic;
import ru.practicum.kanbanApp.model.Subtask;
import ru.practicum.kanbanApp.model.Task;

import java.util.List;

public interface TaskManager {
    void createTask(Task task);

    void createEpic(Epic epic);

    void createSubtask(Subtask subtask);

    List<Task> getAllTasksList();

    List<Epic> getAllEpicsList();

    List<Task> getAllSubtasksList();

    void deleteTasks();

    void deleteSubtasks();

    void deleteEpics();

    Integer deleteTaskById(int taskId);

    Integer deleteSubtaskById(int subtaskId);

    Integer deleteEpicById(Integer epicId);

    Task getTaskById(Integer taskId);

    Subtask getSubtaskById(Integer subtaskId);

    Epic getEpicById(Integer epicId);

    List<Subtask> getSubtaskListByEpicId(Integer epicId);

    Task updateTask(Task task);

    Task updateEpic(Epic epic);

    Subtask updateSubtask(Subtask subtask);

    List<Task> getHistory();
}
