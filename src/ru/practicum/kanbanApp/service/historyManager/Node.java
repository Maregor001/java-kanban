package ru.practicum.kanbanApp.service.historyManager;

import ru.practicum.kanbanApp.model.Task;

public class Node {
    protected Node prev;
    protected Task task;
    protected Node next;

    public Node(Node prev, Task task, Node next) {
        this.prev = prev;
        this.task = task;
        this.next = next;
    }
}
