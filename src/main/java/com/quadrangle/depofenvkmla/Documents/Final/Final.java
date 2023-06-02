package com.quadrangle.depofenvkmla.Documents.Final;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "FINAL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Final {
    @Id
    private ObjectId id;
    private Map<String, Boolean> awardPointList;

    public void editAwardPointList(int roomNumber) {
        awardPointList.put(Integer.toString(roomNumber), true);
    }

    public void resetAllToDefault() {
        awardPointList.replaceAll((s, v) -> false);
    }
}
