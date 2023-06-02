package com.quadrangle.depofenvkmla.Documents.Biweekly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/biweekly")
public class BiweeklyController {
    @Autowired
    private BiweeklyServices biweeklyServices;

    @GetMapping("/get")
    public ResponseEntity<List<Biweekly>> listAllRooms() {
        return new ResponseEntity<>(biweeklyServices.listAllRooms(), HttpStatus.OK);
    }

    @GetMapping("/get/{roomNumber}")
    public ResponseEntity<Map<String, LocalDate[]>> getRoomStatusByNumber(@PathVariable int roomNumber) {
        return new ResponseEntity<>(biweeklyServices.getRoomStatusByNumber(roomNumber), HttpStatus.OK);
    }

    @PutMapping("/put/{roomNumber}")
    public ResponseEntity<Map<String, LocalDate[]>> addDateListToRoom(@PathVariable int roomNumber) {
        return new ResponseEntity<>(biweeklyServices.addWeekList(roomNumber), HttpStatus.OK);
    }
}
