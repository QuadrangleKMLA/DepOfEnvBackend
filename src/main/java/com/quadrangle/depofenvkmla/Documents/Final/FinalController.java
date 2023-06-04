package com.quadrangle.depofenvkmla.Documents.Final;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/final")
@CrossOrigin(origins = "http://localhost:3000")
public class FinalController {
    @Autowired
    private FinalServices finalServices;

    @GetMapping("/get")
    public ResponseEntity<Map<String, Boolean>> listAllRooms() {
        return new ResponseEntity<>(finalServices.listAwardPointsStatus(), HttpStatus.OK);
    }

    @PutMapping("/put")
    public ResponseEntity<String> evaluateAllRooms() {
        return new ResponseEntity<>(finalServices.evaluateAllRooms(),HttpStatus.OK);
    }

    @PutMapping("put/reset")
    public ResponseEntity<String> resetAllRooms() {
        return new ResponseEntity<>(finalServices.resetAllRooms(), HttpStatus.OK);
    }
}
