package ru.kata.spring.boot_security.demo.helper;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PositionsForView {
    String firstPosition;
    List<String> otherPositions;

    public PositionsForView(List<String> positions) {
        firstPosition = positions.isEmpty() ? "-" : positions.get(0);
        otherPositions = positions.isEmpty() ? new ArrayList<>() : positions.stream().skip(1).toList();
    }
}
