package com.quadrangle.depofenvkmla.Documents.Weekly;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeeklyRepository extends MongoRepository<Weekly, ObjectId> {
    Weekly findByRoomNumber(int roomNumber);
}
