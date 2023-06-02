package com.quadrangle.depofenvkmla.Documents.Final;

import com.quadrangle.depofenvkmla.Documents.Biweekly.Biweekly;
import com.quadrangle.depofenvkmla.Documents.Biweekly.BiweeklyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class FinalServices {
    @Autowired
    private FinalRepository finalRepository;

    @Autowired
    private BiweeklyRepository biweeklyRepository;

    public Map<String, Boolean> listAwardPointsStatus() {
        Final awardList = finalRepository.findAll().get(0);

        return awardList.getAwardPointList();
    }

    public String evaluateAllRooms() {
        Final awardList = finalRepository.findAll().get(0);

        List<Biweekly> biweeklies = biweeklyRepository.findAll();

        for (Biweekly b : biweeklies) {
            if (b.evaluateValidity()) {
                awardList.editAwardPointList(b.getRoomNumber());
                Map<String, LocalDate[]> input = b.getCheckList();
                b.getCheckList().clear();

                b.setCheckList(input);
            }
        }

        return "Successfully Evaluated!";
    }

    public String resetAllRooms() {
        Final awardList = finalRepository.findAll().get(0);

        awardList.resetAllToDefault();
        return "Successfully Reset";
    }
}
