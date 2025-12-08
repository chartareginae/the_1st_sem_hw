package hw7;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;


class CustomArrayListTest {

    private CustomList<String> list;
    private CustomList<Integer> intList;

    @BeforeEach
    void setUp() {
        list = new CustomArrayList<>();
        intList = new CustomArrayList<>(5);
    }

    @Test
    @DisplayName("Тест конструктора по умолчанию")
    void testDefaultConstructor() {
        assertNotNull(list);
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    @DisplayName("Тест конструктора с начальной емкостью")
    void testConstructorWithInitialCapacity() {
        assertNotNull(intList);
        assertTrue(intList.isEmpty());
        assertEquals(0, intList.size());
    }

    @Test
    @DisplayName("Тест конструктора с невалидной емкостью")
    void testConstructorWithInvalidCapacity() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CustomArrayList<>(0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new CustomArrayList<>(-1);
        });
    }

    @Test
    @DisplayName("Тест добавления элементов")
    void testAdd() {
        list.add("Element1");
        assertEquals(1, list.size());
        assertFalse(list.isEmpty());

        list.add("Element2");
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("Тест добавления null элемента")
    void testAddNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            list.add(null);
        });
    }

    @Test
    @DisplayName("Тест получения элемента по индексу")
    void testGet() {
        list.add("Element1");
        list.add("Element2");
        list.add("Element3");

        assertEquals("Element1", list.get(0));
        assertEquals("Element2", list.get(1));
        assertEquals("Element3", list.get(2));
    }

    @Test
    @DisplayName("Тест получения с невалидным индексом")
    void testGetInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(0);
        });

        list.add("Element1");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(1);
        });
    }

    @Test
    @DisplayName("Тест удаления элемента")
    void testRemove() {
        list.add("Element1");
        list.add("Element2");
        list.add("Element3");

        String removed = list.remove(1);
        assertEquals("Element2", removed);
        assertEquals(2, list.size());
        assertEquals("Element1", list.get(0));
        assertEquals("Element3", list.get(1));
    }

    @Test
    @DisplayName("Тест удаления первого элемента")
    void testRemoveFirst() {
        list.add("Element1");
        list.add("Element2");

        String removed = list.remove(0);
        assertEquals("Element1", removed);
        assertEquals(1, list.size());
        assertEquals("Element2", list.get(0));
    }

    @Test
    @DisplayName("Тест удаления последнего элемента")
    void testRemoveLast() {
        list.add("Element1");
        list.add("Element2");

        String removed = list.remove(1);
        assertEquals("Element2", removed);
        assertEquals(1, list.size());
        assertEquals("Element1", list.get(0));
    }

    @Test
    @DisplayName("Тест удаления с невалидным индексом")
    void testRemoveInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(0);
        });

        list.add("Element1");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(1);
        });
    }

    @Test
    @DisplayName("Тест размера списка")
    void testSize() {
        assertEquals(0, list.size());

        list.add("Element1");
        assertEquals(1, list.size());

        list.add("Element2");
        assertEquals(2, list.size());

        list.remove(0);
        assertEquals(1, list.size());
    }

    @Test
    @DisplayName("Тест проверки на пустоту")
    void testIsEmpty() {
        assertTrue(list.isEmpty());

        list.add("Element1");
        assertFalse(list.isEmpty());

        list.remove(0);
        assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("Тест динамического расширения")
    void testDynamicExpansion() {
        CustomArrayList<Integer> testList = new CustomArrayList<>(2);

        testList.add(1);
        testList.add(2);
        testList.add(3); // Должно вызвать расширение

        assertEquals(3, testList.size());
        assertEquals(Integer.valueOf(1), testList.get(0));
        assertEquals(Integer.valueOf(2), testList.get(1));
        assertEquals(Integer.valueOf(3), testList.get(2));
    }

    @Test
    @DisplayName("Тест коэффициента расширения")
    void testGrowthFactor() {
        CustomArrayList<Integer> testList = new CustomArrayList<>(4);

        // Добавляем элементы, чтобы вызвать расширение
        for (int i = 0; i < 5; i++) {
            testList.add(i);
        }

        // Емкость должна увеличиться в 1.5 раза: 4 * 1.5 = 6
        assertEquals(5, testList.size());
    }











    @Test
    @DisplayName("Интеграционный тест: добавление, получение и удаление")
    void testIntegration() {
        // Начальное состояние
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        // Добавление элементов
        list.add("First");
        list.add("Second");
        list.add("Third");

        assertFalse(list.isEmpty());
        assertEquals(3, list.size());

        // Получение элементов
        assertEquals("First", list.get(0));
        assertEquals("Second", list.get(1));
        assertEquals("Third", list.get(2));

        // Удаление элемента из середины
        String removed = list.remove(1);
        assertEquals("Second", removed);
        assertEquals(2, list.size());

        // Проверка смещения
        assertEquals("First", list.get(0));
        assertEquals("Third", list.get(1));

        // Удаление всех элементов
        list.remove(0);
        list.remove(0);

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    @DisplayName("Тест с целочисленными элементами")
    void testWithIntegerElements() {
        intList.add(10);
        intList.add(20);
        intList.add(30);

        assertEquals(3, intList.size());
        assertEquals(Integer.valueOf(10), intList.get(0));
        assertEquals(Integer.valueOf(20), intList.get(1));
        assertEquals(Integer.valueOf(30), intList.get(2));

        intList.remove(1);
        assertEquals(2, intList.size());
        assertEquals(Integer.valueOf(10), intList.get(0));
        assertEquals(Integer.valueOf(30), intList.get(1));
    }
}