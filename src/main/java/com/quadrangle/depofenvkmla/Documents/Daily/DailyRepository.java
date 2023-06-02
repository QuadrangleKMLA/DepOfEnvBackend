package com.quadrangle.depofenvkmla.Documents.Daily;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyRepository extends MongoRepository<Daily, ObjectId> {
    Daily getRoomStatusByNumber(int roomNumber);
}
