package ru.practicum.kanbanApp;

import ru.practicum.kanbanApp.service.*;
import ru.practicum.kanbanApp.model.*;
import ru.practicum.kanbanApp.service.taskManager.TaskManager;

public class Main {
    public static TaskManager taskManager = Managers.getDefault();

    public static void main(String[] args) {
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        taskManager.createTask(task1);
        System.out.println(task1.toString());

        Task task2 = new Task("Задача 2", "Описание задачи 2");
        taskManager.createTask(task2);
        System.out.println(task2.toString());

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        taskManager.createEpic(epic1);
        System.out.println(epic1.toString());

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", epic1.getId());
        taskManager.createSubtask(subtask1);
        System.out.println(subtask1.toString());

        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", epic1.getId());
        taskManager.createSubtask(subtask2);
        System.out.println(subtask2.toString());

        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        taskManager.createEpic(epic2);
        System.out.println(epic2.toString());

        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", epic2.getId());
        taskManager.createSubtask(subtask3);
        System.out.println(subtask3.toString());

        Subtask subtask4 = new Subtask("Подзадача 4", "Описание подзадачи 4", epic2.getId());
        taskManager.createSubtask(subtask4);
        System.out.println(subtask4.toString());

        System.out.println("Список задач:");
        System.out.println(taskManager.getAllTasksList());
        System.out.println("Список эпиков:");
        System.out.println(taskManager.getAllEpicsList());
        System.out.println("Список подзадач:");
        System.out.println(taskManager.getAllSubtasksList());

        System.out.println("Получение задачи по идентификатору");
        System.out.println(taskManager.getTaskById(task1.getId()));
        System.out.println(taskManager.getTaskById(111));

        System.out.println("Получение подзадачи по идентификатору");
        System.out.println(taskManager.getSubtaskById(subtask1.getId()));

        System.out.println("Получение эпика по идентификатору");
        System.out.println(taskManager.getEpicById(epic1.getId()));

        System.out.println("Обновление задачи");
        task1.setTitle("Обновленное наименование");
        task1.setDescription("Обновленное описание");
        task1.setTaskStatus(TaskStatus.IN_PROGRESS);
        System.out.println(taskManager.updateTask(task1));

        System.out.println("Обновление подзадачи");
        subtask1.setTitle("Обновленное наименование подзадачи");
        subtask1.setDescription("Обновленное описание подзадачи");
        subtask1.setTaskStatus(TaskStatus.DONE);
        System.out.println(taskManager.updateSubtask(subtask1));

        System.out.println("Обновление эпика");
        epic1.setTitle("Обновленное наименование эпика");
        epic1.setDescription("Обновленное описание эпика");
        System.out.println(taskManager.updateEpic(epic1));

        System.out.println("Удаление задачи по идентификатору");
        taskManager.deleteTaskById(task2.getId());
        System.out.println("Удаление подзадачи по идентификатору");
        taskManager.deleteSubtaskById(subtask1.getId());
        System.out.println("Удаление эпика по идентификатору");
        taskManager.deleteEpicById(epic1.getId());

        System.out.println("Получение списка подзадач эпика");
        System.out.println(taskManager.getSubtaskListByEpicId(epic2.getId()));

        System.out.println("Удаление всех задач");
        taskManager.deleteTasks();
        taskManager.deleteEpics();
        taskManager.deleteSubtasks();
        System.out.println(taskManager.getAllTasksList());
        System.out.println(taskManager.getAllEpicsList());
        System.out.println(taskManager.getAllSubtasksList());
    }
}
