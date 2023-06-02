package com.quadrangle.depofenvkmla.Documents.Daily;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DailyServices {
    @Autowired
    private DailyRepository dailyRepository;

    public List<Daily> listAllRooms() {
        return dailyRepository.findAll();
    }

    public Map<String, Boolean> getRoomStatusByNumber(int roomNumber) {
        return dailyRepository.getRoomStatusByNumber(roomNumber).getTrashStatus();
    }

    public String registerCheck(int roomNumber, String key) {
        Daily daily = dailyRepository.getRoomStatusByNumber(roomNumber);

        daily.updateTrashStatus(key);

        if (daily.getTrashStatus().get(key)) {
            daily.setToday(new Date());
            return "Successfully Updated Room "+roomNumber+"'s Trash Type "+key+" to True";
        } else {
            return "Error Occurred";
        }
    }

    public String resetAllRooms() {
        List<Daily> roomList = dailyRepository.findAll();

        for (Daily daily : roomList) {
            daily.resetTrashStatus();
        }

        return "Reset All Room Status";
    }
}
