package com.quadrangle.depofenvkmla.Documents.Biweekly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.*;

@Document(collection = "BIWEEKLY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Biweekly {
    @Id
    private ObjectId id;
    private int roomNumber;
    private Map<String, LocalDate[]> checkList;
    private static boolean flag;

    public void addDateSet(Map<String, LocalDate> weeklyCheckList) {
        LocalDate[] dateList = getDateList(weeklyCheckList);

        if (flag) {
            checkList.put("Week1", dateList);
            flag = false;
        } else {
            checkList.put("Week2", dateList);
            flag = true;
        }

        if (checkList.size() > 2) {
            checkList.remove(!flag ? "Week2" : "Week1");
        }
    }

    public boolean evaluateValidity() {
        for (LocalDate[] c : checkList.values()) {
            if (c.length < 2) {
                return false;
            }
        }

        return true;
    }

    public static LocalDate[] getDateList(Map<String, LocalDate> localDateMap) {
        LocalDate[] localDates = new LocalDate[localDateMap.size()];
        int i=0;
        for (LocalDate localDate : localDateMap.values()) {
            localDates[i] = localDate;
            i++;
        }

        return localDates;
    }
}
