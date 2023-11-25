package ru.kata.spring.boot_security.demo.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.*;
import java.util.stream.IntStream;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "positions")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToMany(mappedBy = "positions")
    private Set<User> user;

//    @Transient
//    public final static PositionsType[] allPositionsTypeTypes = PositionsType.values();
//
//    @Transient
//    public final static PositionsType[] allPositionsTypes = PositionsType.values();

//    @Transient
//    private final static List<Position> allPositions = new ArrayList<>();

//    static {
//        Arrays.stream(allPositionsTypes).map(r -> new Position(r.name())).forEach(allPositions::add);
//    }

//    public static List<Position> allPositions() {
//        return allPositions;
//    }

    public Position(String name) {
        this.name = name;
//        setName(name);
    }

//    public void setName(String name) {
//        String nameInUpperCase = name.toUpperCase();
//        boolean correctName = Arrays.stream((allPositionsTypes))
//                .anyMatch(r -> r.name().equals(nameInUpperCase));
//        this.name = correctName ? nameInUpperCase : allPositionsTypes[0].name();
//        this.name = name;
//    }

//    public enum PositionsType {
//        TRAINEE("ученик"),
//        REPAIR_EMPLOYEE("ремонтник"),
//        TV_MANUFACTURE_EMPLOYEE("сотр отд произв тв"),
//        PHONE_MANUFACTURE_EMPLOYEE("сотр отд произв тел"),
//
//        TV_MANUFACTURE_MASTER("нач отд произв тв"),
//        PHONE_MANUFACTURE_MASTER("нач отд произв тел"),
//
//        REPAIR_MASTER("нач ремонт отд"),
//
//        MANUFACTURE_BOSS("нач произв цеха"),
//
//        HEAD("глава фирмы");
//
//        private final String name;
//
//        PositionsType(String name) {
//            this.name = name;
//        }
//
//        public String getName() {
//            return name;
//        }
//    }

//    public static List<String> listOfAllPositions() {
//        return Arrays.stream(allPositionsTypes).map(Enum::name).toList();
//    }

    // todo delete

//    public static List<Position> getListOfPositions(int numberOfPositions) {
//        List<Position> Positions = new ArrayList<>();
//        IntStream.range(0, numberOfPositions).mapToObj(n -> new Position(allPositionsTypes[n].name())).forEach(Positions::add);
//        return Positions;
//    }

    // todo delete

//    public static Comparator<Position> PositionComparator =
//            Comparator.comparingInt(r -> PositionsType.valueOf(r.getName()).ordinal());

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return name.equals(position.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
