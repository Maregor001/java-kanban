package ru.practicum.kanbanApp.service.taskManager;

import ru.practicum.kanbanApp.model.*;
import ru.practicum.kanbanApp.service.Managers;
import ru.practicum.kanbanApp.service.historyManager.HistoryManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private final Map<Integer, Task> taskStorage = new HashMap<>();
    private final Map<Integer, Epic> epicStorage = new HashMap<>();
    private final Map<Integer, Subtask> subtaskStorage = new HashMap<>();

    private int taskId = 0;
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    protected int calculateId() {
        taskId++;
        return taskId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void createTask(Task task) {
        task.setId(calculateId());
        taskStorage.put(getTaskId(), task);
    }

    public void createEpic(Epic epic) {
        epic.setId(calculateId());
        epicStorage.put(getTaskId(), epic);
    }

    public void createSubtask(Subtask subtask) {
        int subtaskEpicId = subtask.getEpicId();
        subtask.setId(calculateId());
        subtask.setEpicId(subtaskEpicId);
        subtaskStorage.put(getTaskId(), subtask);
        Epic epic = epicStorage.get(subtaskEpicId);
        if (epic == null) {
            return;
        } else {
            epic.getSubtasksIdListInEpic().add(subtask.getId());
            recalculateEpicStatus(subtask.getEpicId());
        }
    }

    public List<Task> getAllTasksList() {
        return new ArrayList<>(taskStorage.values());
    }

    public List<Epic> getAllEpicsList() {
        return new ArrayList<>(epicStorage.values());
    }

    public List<Task> getAllSubtasksList() {
        return new ArrayList<>(subtaskStorage.values());
    }

    public void deleteTasks() {
        taskStorage.clear();
    }

    public void deleteSubtasks() {
        List<Subtask> subTasksList = new ArrayList<>(subtaskStorage.values());
        for (Subtask subtask : subTasksList) {
            Epic epic = epicStorage.get(subtask.getEpicId());
            epic.getSubtasksIdListInEpic().clear();
            recalculateEpicStatus(subtask.getEpicId());
            subtaskStorage.remove(subtask.getId());
        }
    }

    public void deleteEpics() {
        epicStorage.clear();
        subtaskStorage.clear();
    }

    public Integer deleteTaskById(int taskId) {
        if (taskStorage.containsKey(taskId)) {
            taskStorage.remove(taskId);
            historyManager.remove(taskId);
            return taskId;
        } else {
            return 404;
        }
    }

    public Integer deleteSubtaskById(int subtaskId) {
        if (subtaskStorage.containsKey(subtaskId)) {
            Integer epicId = subtaskStorage.get(subtaskId).getEpicId();
            Epic epic = epicStorage.get(epicId);
            List<Integer> subtaskIdList = epic.getSubtasksIdListInEpic();
            for (Integer subtaskIdFromList : subtaskIdList) {
                if (subtaskIdFromList == subtaskId) {
                    subtaskIdList.remove(subtaskIdFromList);
                    subtaskStorage.remove(subtaskId);
                    historyManager.remove(subtaskId);
                    break;
                }
            }
            recalculateEpicStatus(epicId);
            return subtaskId;
        } else {
            return 404;
        }
    }

    public Integer deleteEpicById(Integer epicId) {
        if (epicStorage.containsKey(epicId)) {
            Epic epic = epicStorage.remove(epicId);
            historyManager.remove(epicId);
            List<Integer> subtasksIdInEpic = epic.getSubtasksIdListInEpic();
            for (Integer subtaskId : subtasksIdInEpic) {
                subtaskStorage.remove(subtaskId);
                historyManager.remove(subtaskId);
            }
            return epicId;
        } else {
            return 404;
        }
    }

    public Task getTaskById(Integer taskId) {
        Task task = taskStorage.get(taskId);
        if (task != null) {
            historyManager.addTask(task);
        }
        return task;
    }

    public Subtask getSubtaskById(Integer subtaskId) {
        Subtask subtask = subtaskStorage.get(subtaskId);
        if (subtask != null) {
            historyManager.addTask(subtask);
        }
        return subtask;
    }

    public Epic getEpicById(Integer epicId) {
        Epic epic = epicStorage.get(epicId);
        if (epic != null) {
            historyManager.addTask(epic);
        }
        return epic;
    }

    public List<Subtask> getSubtaskListByEpicId(Integer epicId) {
        Epic epic = epicStorage.get(epicId);
        List<Subtask> subtasksList = new ArrayList<>();
        for (Integer subtasksId : epic.getSubtasksIdListInEpic()) {
            subtasksList.add(subtaskStorage.get(subtasksId));
        }
        return subtasksList;
    }

    public Task updateTask(Task task) {
        taskStorage.put(task.getId(), task);
        return taskStorage.get(task.getId());
    }

    public Task updateEpic(Epic epic) {
        if (epicStorage.containsKey(epic.getId())) {
            epicStorage.put(epic.getId(), epic);
            return epicStorage.get(epic.getId());
        } else return null;
    }

    public Subtask updateSubtask(Subtask subtask) {
        if (subtaskStorage.containsKey(subtask.getId())
                && epicStorage.containsKey(subtask.getEpicId())) {
            subtaskStorage.put(subtask.getId(), subtask);
            recalculateEpicStatus(subtask.getEpicId());
            return subtaskStorage.get(subtask.getId());
        } else return null;
    }

    private void recalculateEpicStatus(Integer epicId) {
        int cntNew = 0;
        int cntDone = 0;
        Epic epic = epicStorage.get(epicId);
        List<Integer> subTasksIdList = epic.getSubtasksIdListInEpic();
        for (Integer subTaskId : subTasksIdList) {
            TaskStatus taskStatus = subtaskStorage.get(subTaskId).getTaskStatus();
            if (taskStatus == TaskStatus.NEW) {
                cntNew++;
            } else if (taskStatus == TaskStatus.DONE) {
                cntDone++;
            }
        }
        if (cntNew == subTasksIdList.size()) {
            epic.setTaskStatus(TaskStatus.NEW);
        } else if (cntDone == subTasksIdList.size()) {
            epic.setTaskStatus(TaskStatus.DONE);
        } else epic.setTaskStatus(TaskStatus.IN_PROGRESS);
    }

    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
