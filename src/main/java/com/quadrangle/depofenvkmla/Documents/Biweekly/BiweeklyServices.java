package com.quadrangle.depofenvkmla.Documents.Biweekly;

import com.quadrangle.depofenvkmla.Documents.Weekly.Weekly;
import com.quadrangle.depofenvkmla.Documents.Weekly.WeeklyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.quadrangle.depofenvkmla.Documents.Biweekly.Biweekly.getDateList;

@Service
public class BiweeklyServices {
    @Autowired
    private BiweeklyRepository biweeklyRepository;

    @Autowired
    private WeeklyRepository weeklyRepository;

    public List<Biweekly> listAllRooms() {
        return biweeklyRepository.findAll();
    }

    public Map<String, LocalDate[]> getRoomStatusByNumber(int roomNumber) {
        return biweeklyRepository.getRoomStatusByRoomNumber(roomNumber).getCheckList();
    }

    public Map<String, LocalDate[]> addWeekList(int roomNumber) {
        Biweekly target = biweeklyRepository.getRoomStatusByRoomNumber(roomNumber);

        Weekly weekly = weeklyRepository.findByRoomNumber(roomNumber);
        LocalDate[] localDates = getDateList(weekly.getDateMap());
        Arrays.sort(localDates);
        if (target.getCheckList().size() == 0) {
            target.addDateSet(weekly.getDateMap());
        } else {
            for (LocalDate[] ld : target.getCheckList().values()) {
                Arrays.sort(ld);
                if (!Arrays.equals(ld, localDates)) {
                    target.addDateSet(weekly.getDateMap());
                }
            }
        }

        biweeklyRepository.save(target);

        return target.getCheckList();
    }
}
