package com.quadrangle.depofenvkmla.Documents.Biweekly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

@Document(collection = "BIWEEKLY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Biweekly {
    @Id
    private ObjectId id;
    private int roomNumber;
    private ArrayList<Date[]> checkList = new ArrayList<>();

    public void addDateSet(ArrayList<Date> weeklyCheckList) {
        Date[] dateList = new Date[weeklyCheckList.size()];
        int i = 0;
        for (Date d : weeklyCheckList) {
            dateList[i] = d;
        }

        checkList.add(dateList);

        if (checkList.size() > 2) {
            checkList.remove(0);
        }
    }

    public boolean evaluateValidity() {
        for (Date[] dL : checkList) {
            if (dL.length < 2) {
                return false;
            }
        }

        return true;
    }
}
