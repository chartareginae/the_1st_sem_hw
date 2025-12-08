package hw7;

import java.util.*;
import java.util.stream.Collectors;

public class MapOperations {
    public static List<Student> findStudentsByGradeRange(Map<Integer, Student> map,
                                                         double minGrade,
                                                         double maxGrade) {
        if (map == null || map.isEmpty()) {
            return Collections.emptyList();
        }

        if (minGrade > maxGrade) {
            throw new IllegalArgumentException("minGrade не может быть больше maxGrade");
        }

        List<Student> result = new ArrayList<>();

        for (Student student : map.values()) {
            double grade = student.getGrade();
            if (grade >= minGrade && grade <= maxGrade) {
                result.add(student);
            }
        }

        result.sort((s1, s2) -> Double.compare(s2.getGrade(), s1.getGrade()));

        return result;
    }

    public static List<Student> getTopNStudents(TreeMap<Integer, Student> treeMap, int n) {
        if (treeMap == null || treeMap.isEmpty() || n <= 0) {
            return Collections.emptyList();
        }

        List<Student> result = new ArrayList<>();
        int count = 0;

        for (Map.Entry<Integer, Student> entry : treeMap.descendingMap().entrySet()) {
            if (count >= n) {
                break;
            }
            result.add(entry.getValue());
            count++;
        }

        return result;
    }

    public static List<Student> findStudentsByGradeRangeStream(Map<Integer, Student> map,
                                                               double minGrade,
                                                               double maxGrade) {
        if (map == null) {
            return Collections.emptyList();
        }

        return map.values().stream()
                .filter(student -> student.getGrade() >= minGrade && student.getGrade() <= maxGrade)
                .sorted((s1, s2) -> Double.compare(s2.getGrade(), s1.getGrade()))
                .collect(Collectors.toList());
    }

    public static List<Student> getTopNStudentsStream(TreeMap<Integer, Student> treeMap, int n) {
        if (treeMap == null || n <= 0) {
            return Collections.emptyList();
        }

        return treeMap.descendingMap().values().stream()
                .limit(n)
                .collect(Collectors.toList());
    }

    public static HashMap<Integer, Student> createHashMap() {
        HashMap<Integer, Student> map = new HashMap<>();

        return map;
    }

    public static TreeMap<Integer, Student> createTreeMapDescending() {
        Comparator<Integer> descendingComparator = (id1, id2) -> id2.compareTo(id1);

        TreeMap<Integer, Student> treeMap = new TreeMap<>(descendingComparator);

        treeMap.putAll(createHashMap());

        return treeMap;
    }
}