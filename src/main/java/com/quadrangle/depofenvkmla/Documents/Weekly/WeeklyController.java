package com.quadrangle.depofenvkmla.Documents.Weekly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/v1/weekly")
@CrossOrigin(origins = "http://localhost:8080")
public class WeeklyController {
    @Autowired
    private WeeklyServices weeklyServices;

    @GetMapping("/get")
    public ResponseEntity<List<Weekly>> listAllRooms() {
        return new ResponseEntity<>(weeklyServices.listAllRooms(), HttpStatus.OK);
    }

    @GetMapping("/get/{roomNumber}")
    public ResponseEntity<Map<String, LocalDate>> getRoomStatusByNumber(@PathVariable int roomNumber) {
        return new ResponseEntity<>(weeklyServices.getRoomStatus(roomNumber), HttpStatus.OK);
    }

    @PutMapping("/put/{roomNumber}")
    public ResponseEntity<Map<String, LocalDate>> addDateToRoom(@PathVariable int roomNumber) {
        return new ResponseEntity<>(weeklyServices.addDate(roomNumber), HttpStatus.OK);
    }

    @PutMapping("/put/reset")
    public ResponseEntity<String> resetAllRoomStatus() {
        return new ResponseEntity<>(weeklyServices.resetAllRooms(),HttpStatus.OK);
    }
}
