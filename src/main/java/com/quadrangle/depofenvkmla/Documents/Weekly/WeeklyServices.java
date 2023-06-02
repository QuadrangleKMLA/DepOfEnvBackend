package com.quadrangle.depofenvkmla.Documents.Weekly;

import com.quadrangle.depofenvkmla.Documents.Daily.Daily;
import com.quadrangle.depofenvkmla.Documents.Daily.DailyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class WeeklyServices {
    @Autowired
    private WeeklyRepository weeklyRepository;

    @Autowired
    private DailyRepository dailyRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Weekly> listAllRooms() {
        return weeklyRepository.findAll();
    }

    public Map<String, LocalDate> getRoomStatus(int roomNumber) {
        Weekly weekly = weeklyRepository.findByRoomNumber(roomNumber);

        return weekly.getDateMap();
    }

    public Map<String,LocalDate> addDate(int roomNumber) {
        Weekly target = weeklyRepository.findByRoomNumber(roomNumber);
        Daily date = dailyRepository.getRoomStatusByNumber(roomNumber);
        if (target.getDateMap().size() == 0) {
                target.addDate(date);
        } else {
            for (int i=1;i<=target.getDateMap().size();i++) {
                if (!target.getDateMap().get(Integer.toString(i)).equals(date.getToday())) {
                    target.addDate(date);
                }
            }
        }
        weeklyRepository.save(target);

        return target.getDateMap();
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
