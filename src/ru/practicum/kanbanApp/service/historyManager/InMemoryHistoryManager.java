package ru.practicum.kanbanApp.service.historyManager;

import ru.practicum.kanbanApp.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Integer, Node> historyStorage = new HashMap<>();
    private Node last;
    private Node firstNode;

    @Override
    public void addTask(Task task) {
        linkLast(task);

    }

    @Override
    public List<Task> getHistory() {
        List<Task> historyList = new ArrayList<>();
        if (!historyStorage.isEmpty()) {
            historyList.add(firstNode.task);
            Node nextNode = firstNode.next;
            while (nextNode != null) {
                historyList.add(nextNode.task);
                nextNode = nextNode.next;
            }
        }
        return historyList;
    }

    @Override
    public void remove(int id) {
        if (historyStorage.containsKey(id)) {
            removeNode(historyStorage.get(id));
        }
    }

    public void linkLast(Task task) {
        if (historyStorage.isEmpty()) {
            Node node = new Node(null, task, null);
            firstNode = node;
            last = node;
            historyStorage.put(task.getId(), node);
        } else {
            if (historyStorage.containsKey(task.getId())) {
                removeNode(historyStorage.get(task.getId()));
                if (historyStorage.isEmpty()) {
                    Node node = new Node(null, task, null);
                    firstNode = node;
                    last = node;
                    historyStorage.put(task.getId(), node);
                    return;
                }
            }
            Node secondLast = last; // предпоследний элемент
            last = new Node(secondLast, task, null);
            historyStorage.put(task.getId(), last);

            int prevTaskId = secondLast.task.getId(); // id предыдущего  (нода)
            Node prevNode = historyStorage.get(prevTaskId); // предыдущий нод
            prevNode.next = last; // в предыдущем ноде заполнили next
            historyStorage.put(prevTaskId, prevNode); // обновили предыдущий нод
        }
    }

    public void removeNode(Node removedNode) {
        Node nodeBeforeRemoved;
        Node nodeAfterRemoved;

        if (removedNode.prev == null) {
            if (removedNode.next != null) {
                nodeAfterRemoved = removedNode.next; // получаем из удаляемого нода следующий нод
                nodeAfterRemoved.prev = null;// после удаления первого следующий нод станет первым
                firstNode = nodeAfterRemoved;
                historyStorage.put(nodeAfterRemoved.task.getId(), nodeAfterRemoved);
            }
        } else if (removedNode.next == null) {
            nodeBeforeRemoved = removedNode.prev;
            nodeBeforeRemoved.next = null;
            historyStorage.put(nodeBeforeRemoved.task.getId(), nodeBeforeRemoved);
        } else {
            nodeBeforeRemoved = removedNode.prev;
            nodeAfterRemoved = removedNode.next;

            nodeBeforeRemoved.next = removedNode.next;
            nodeAfterRemoved.prev = removedNode.prev;

            historyStorage.put(nodeBeforeRemoved.task.getId(), nodeBeforeRemoved);
            historyStorage.put(nodeAfterRemoved.task.getId(), nodeAfterRemoved);
        }
        historyStorage.remove(removedNode.task.getId());
    }
}