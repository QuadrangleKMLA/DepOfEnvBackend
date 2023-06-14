package com.quadrangle.depofenvkmla.Documents.Final;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinalRepository extends MongoRepository<Final, ObjectId> {
    Final findRoomByRoomNumber(int roomNumber);
}
