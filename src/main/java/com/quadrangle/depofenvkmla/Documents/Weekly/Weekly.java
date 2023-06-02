package com.quadrangle.depofenvkmla.Documents.Weekly;

import com.quadrangle.depofenvkmla.Documents.Daily.Daily;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

@Document(collection = "WEEKLY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Weekly {
    @Id
    private ObjectId id;
    private int roomNumber;
    private ArrayList<Date> checkList = new ArrayList<>();

    public void addDate(Daily daily) {
        checkList.add(daily.getToday());
    }

    public void resetDateList() {
        checkList.clear();
    }
}
