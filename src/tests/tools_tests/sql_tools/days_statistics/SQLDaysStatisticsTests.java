package tests.tools_tests.sql_tools.days_statistics;

import org.junit.jupiter.api.Test;
import tools.sql_tools.days_statistics.SelectFromDaysStatistics;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLDaysStatisticsTests {
    @Test
    public void getAverageMacroForMonth_11_2025() {
        float resultMacro;
        float toMinusResultMacro;

        for (int i = 1; i < 12; i++) {
            resultMacro = SelectFromDaysStatistics.getAverageMacroForMonth(2025, 11).getKcal();
            toMinusResultMacro = (resultMacro - (resultMacro % 0.01f));
            assertEquals(4810.63, toMinusResultMacro);
        }
    }

    @Test
    public void getAverageMacroForMonth_2_2025() {
        System.out.println(String.valueOf(SelectFromDaysStatistics.getAverageMacroForMonth(2026, 2).getKcal()));
    }
}
