package hw7;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Сравнение производительности ArrayList и LinkedList")
public class CollectionPerformanceTest {

    private static final int ELEMENT_COUNT = 10000;
    private static final StringBuilder results = new StringBuilder();

    @BeforeAll
    static void setup() {
        results.append("=".repeat(60)).append("\n");
        results.append(String.format("%-25s %-15s %-15s%n",
                "Операция", "ArrayList (мс)", "LinkedList (мс)"));
        results.append("-".repeat(60)).append("\n");
    }

    @Test
    @DisplayName("1. Добавление в конец")
    void testAddToEnd() {
        List<Integer> arrayList = new ArrayList<>();
        long startTime = System.nanoTime();
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            arrayList.add(i);
        }
        long arrayListTime = System.nanoTime() - startTime;

        List<Integer> linkedList = new LinkedList<>();
        startTime = System.nanoTime();
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            linkedList.add(i);
        }
        long linkedListTime = System.nanoTime() - startTime;

        assertEquals(ELEMENT_COUNT, arrayList.size());
        assertEquals(ELEMENT_COUNT, linkedList.size());

        saveResult("Добавление в конец", arrayListTime, linkedListTime);
    }

    @Test
    @DisplayName("2. Добавление в начало")
    void testAddToBeginning() {
        List<Integer> arrayList = new ArrayList<>();
        long startTime = System.nanoTime();
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            arrayList.add(0, i);
        }
        long arrayListTime = System.nanoTime() - startTime;

        List<Integer> linkedList = new LinkedList<>();
        startTime = System.nanoTime();
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            linkedList.add(0, i);
        }
        long linkedListTime = System.nanoTime() - startTime;

        assertEquals(ELEMENT_COUNT, arrayList.size());
        assertEquals(ELEMENT_COUNT, linkedList.size());

        saveResult("Добавление в начало", arrayListTime, linkedListTime);
    }

    @Test
    @DisplayName("3. Вставка в середину")
    void testInsertInMiddle() {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        for (int i = 0; i < ELEMENT_COUNT; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }

        long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            arrayList.add(ELEMENT_COUNT / 2, i);
        }
        long arrayListTime = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            linkedList.add(ELEMENT_COUNT / 2, i);
        }
        long linkedListTime = System.nanoTime() - startTime;

        assertEquals(ELEMENT_COUNT + 1000, arrayList.size());
        assertEquals(ELEMENT_COUNT + 1000, linkedList.size());

        saveResult("Вставка в середину", arrayListTime, linkedListTime);
    }

    @Test
    @DisplayName("4. Доступ по индексу")
    void testAccessByIndex() {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        for (int i = 0; i < ELEMENT_COUNT; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }

        long sum = 0;
        long startTime = System.nanoTime();
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            sum += arrayList.get(i);
        }
        long arrayListTime = System.nanoTime() - startTime;

        sum = 0;
        startTime = System.nanoTime();
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            sum += linkedList.get(i);
        }
        long linkedListTime = System.nanoTime() - startTime;

        long expectedSum = (long) ELEMENT_COUNT * (ELEMENT_COUNT - 1) / 2;
        assertEquals(expectedSum, sum);

        saveResult("Доступ по индексу", arrayListTime, linkedListTime);
    }

    @Test
    @DisplayName("5. Удаление из начала")
    void testRemoveFromBeginning() {
        List<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            arrayList.add(i);
        }

        long startTime = System.nanoTime();
        while (!arrayList.isEmpty()) {
            arrayList.remove(0);
        }
        long arrayListTime = System.nanoTime() - startTime;

        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            linkedList.add(i);
        }

        startTime = System.nanoTime();
        while (!linkedList.isEmpty()) {
            linkedList.remove(0);
        }
        long linkedListTime = System.nanoTime() - startTime;

        assertTrue(arrayList.isEmpty());
        assertTrue(linkedList.isEmpty());

        saveResult("Удаление из начала", arrayListTime, linkedListTime);
    }

    @Test
    @DisplayName("6. Удаление из конца")
    void testRemoveFromEnd() {
        List<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            arrayList.add(i);
        }

        long startTime = System.nanoTime();
        while (!arrayList.isEmpty()) {
            arrayList.remove(arrayList.size() - 1);
        }
        long arrayListTime = System.nanoTime() - startTime;

        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            linkedList.add(i);
        }

        startTime = System.nanoTime();
        while (!linkedList.isEmpty()) {
            linkedList.remove(linkedList.size() - 1);
        }
        long linkedListTime = System.nanoTime() - startTime;

        assertTrue(arrayList.isEmpty());
        assertTrue(linkedList.isEmpty());

        saveResult("Удаление из конца", arrayListTime, linkedListTime);
    }

    private void saveResult(String operation, long arrayListNano, long linkedListNano) {
        double arrayListMs = arrayListNano / 1_000_000.0;
        double linkedListMs = linkedListNano / 1_000_000.0;

        results.append(String.format("%-25s %-15.2f %-15.2f%n",
                operation, arrayListMs, linkedListMs));
    }

    @AfterAll
    static void printResults() {
        results.append("=".repeat(60)).append("\n");

        System.out.println(results.toString());
    }
}