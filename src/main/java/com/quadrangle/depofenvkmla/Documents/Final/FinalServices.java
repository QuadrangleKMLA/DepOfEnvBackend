package com.quadrangle.depofenvkmla.Documents.Final;

import com.quadrangle.depofenvkmla.Documents.Biweekly.Biweekly;
import com.quadrangle.depofenvkmla.Documents.Biweekly.BiweeklyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinalServices {
    @Autowired
    private FinalRepository finalRepository;

    @Autowired
    private BiweeklyRepository biweeklyRepository;

    public List<Final> listAwardPointsStatus() {
        return finalRepository.findAll();
    }

    public String evaluateAllRooms() {
        List<Biweekly> biweeklies = biweeklyRepository.findAll();

        for (Biweekly b : biweeklies) {
            if (b.evaluateValidity()) {
                Final roomAwards = finalRepository.findRoomByRoomNumber(b.getRoomNumber());
                roomAwards.satisfiesConditions();
                Map<String, LocalDate[]> input = b.getCheckList();
                input.clear();
                b.setCheckList(input);
            }
        }

        return "Successfully Evaluated!";
    }

    public String resetAllRooms() {
        List<Final> awardList = finalRepository.findAll();

        for (Final f : awardList) {
            f.resetConditions();
            finalRepository.save(f);
        }

        return "Successfully Reset";
    }
}
