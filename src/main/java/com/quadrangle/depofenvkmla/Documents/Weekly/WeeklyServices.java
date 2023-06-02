package com.quadrangle.depofenvkmla.Documents.Weekly;

import com.quadrangle.depofenvkmla.Documents.Daily.Daily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class WeeklyServices {
    @Autowired
    private WeeklyRepository weeklyRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Weekly> listAllRooms() {
        return weeklyRepository.findAll();
    }

    public Map<String, Date> getRoomStatus(int roomNumber) {
        Weekly weekly = weeklyRepository.findByRoomNumber(roomNumber);

        return weekly.getDateMap();
    }

    public Date addDate(int roomNumber) {
        List<Daily> insert = mongoTemplate.find(new Query(Criteria.where("roomNumber").is(roomNumber)), Daily.class, "DAILY");
        Weekly target = weeklyRepository.findByRoomNumber(roomNumber);
        target.addDate(insert.get(0));
        weeklyRepository.save(target);

        return target.getDateMap().get("date"+(target.getDateMap().size()-1));
    }

    public String resetAllRooms() {
        List<Weekly> list = weeklyRepository.findAll();

        for (Weekly w : list) {
            w.resetDateList();
        }

        weeklyRepository.saveAll(list);
        return "true";
    }
}
