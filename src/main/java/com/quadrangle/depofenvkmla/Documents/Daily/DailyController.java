package com.quadrangle.depofenvkmla.Documents.Daily;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/daily")
public class DailyController {
    @Autowired
    private DailyServices dailyServices;

    @GetMapping("/get")
    public ResponseEntity<List<Daily>> listAllRoomStatus() {
        return new ResponseEntity<>(dailyServices.listAllRooms(), HttpStatus.OK);
    }

    @GetMapping("/get/{number}")
    public ResponseEntity<Map<String, Boolean>> getRoomStatus(@PathVariable int number) {
        return new ResponseEntity<>(dailyServices.getRoomStatusByNumber(number), HttpStatus.OK);
    }

    @PutMapping("/put/{number}")
    public ResponseEntity<String> registerCheck(@PathVariable int number, @RequestBody Map<String, String> payload) {
        return new ResponseEntity<>(dailyServices.registerCheck(number, payload.get("type")), HttpStatus.OK);
    }

    @PutMapping("/put/reset")
    public ResponseEntity<String> resetRoomStatus() {
        return new ResponseEntity<>(dailyServices.resetAllRooms(), HttpStatus.OK);
    }
}
