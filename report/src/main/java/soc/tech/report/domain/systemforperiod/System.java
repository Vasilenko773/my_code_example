package soc.tech.report.domain.systemforperiod;

import lombok.AllArgsConstructor;
import lombok.Data;
import soc.tech.report.domain.systemforperiod.day.Day;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class System {

    private int id;
    private String name;
    private double hours;
    private List<Day> days = new ArrayList<>();
}
