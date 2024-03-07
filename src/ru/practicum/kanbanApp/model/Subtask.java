package ru.practicum.kanbanApp.model;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String title, String description, Integer epicId) {
        super(title, description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return ("ID: " + id + " Наименование: " + title + " Описание: " + description
                + " Статус: " + taskStatus + " ID эпика: " + epicId);
    }

}
