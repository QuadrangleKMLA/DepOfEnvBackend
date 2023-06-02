package com.quadrangle.depofenvkmla.Documents.Weekly;

import com.quadrangle.depofenvkmla.Documents.Daily.Daily;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Map;

@Document(collection = "WEEKLY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Weekly {
    @Id
    private ObjectId id;
    private int roomNumber;
    private Map<String, LocalDate> dateMap;

    public void addDate(Daily daily) {
        dateMap.put(Integer.toString(dateMap.size()+1), daily.getToday());
    }

    public void resetDateList() {
        dateMap.clear();
    }
}
