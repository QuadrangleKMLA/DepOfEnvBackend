package com.quadrangle.depofenvkmla.Documents.Daily;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Document(collection = "DAILY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Daily {
    @Id
    private ObjectId id;
    private int number;
    private Map<String, Boolean> trashStatus;
    private LocalDate today;

    public void updateTrashStatus(String key) {
        trashStatus.replace(key, true);
    }

    public void resetTrashStatus() {
        trashStatus.replace("Waste", false);
        trashStatus.replace("Recycle", false);
        trashStatus.replace("Box", false);
    }

    public void setToday(Date date) {
        this.today = dateConverter(date);
    }

    public static LocalDate dateConverter(Date date) {

        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
