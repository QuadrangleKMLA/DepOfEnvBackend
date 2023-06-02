package com.quadrangle.depofenvkmla.Documents.Biweekly;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BiweeklyRepository extends MongoRepository<Biweekly, ObjectId> {
    Biweekly getRoomStatusByRoomNumber(int roomNumber);
}
