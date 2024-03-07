package ru.practicum.kanbanApp.test;

import org.junit.jupiter.api.Test;
import ru.practicum.kanbanApp.service.Managers;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    @Test
    public void objectsShouldNotNull() {
        assertNotNull(Managers.getDefault(), "getDefault() is null");
        assertNotNull(Managers.getDefaultHistory(), "getDefaultHistory() is null");
    }
}