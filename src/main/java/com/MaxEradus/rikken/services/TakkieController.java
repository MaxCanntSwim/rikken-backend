package com.MaxEradus.rikken.services;

import com.MaxEradus.rikken.Repos.TakkieRepository;
import com.MaxEradus.rikken.model.Ronde;
import com.MaxEradus.rikken.model.Takkie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/takkie")
public class TakkieController {

    @Autowired
    public final TakkieRepository takkieRepository;
    public final PlayerController playerController;
    public final RondeController rondeController;

    public TakkieController(TakkieRepository takkieRepository, PlayerController playerController, RondeController rondeController) {
        this.takkieRepository = takkieRepository;
        this.playerController = playerController;
        this.rondeController = rondeController;
    }

    @GetMapping(path = {"","/"})
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(takkieRepository.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTakkieWhole(@RequestBody Map<String,String> body) {
        String ronde1id = body.get("ronde1");
        String ronde2id = body.get("ronde2");
        String ronde3id = body.get("ronde3");
        String ronde4id = body.get("ronde4");
        if (isNullOrEmpty(ronde1id) || isNullOrEmpty(ronde2id) || isNullOrEmpty(ronde3id) || isNullOrEmpty(ronde4id)) {
            return ResponseEntity.badRequest().body(createMessage("One or more fields are empty"));
        }
        if (!isNumeric(ronde1id) || !isNumeric(ronde2id) || !isNumeric(ronde3id) || !isNumeric(ronde4id)) {
            return ResponseEntity.badRequest().body(createMessage("One or more fields are not numeric"));
        }
        Long ronde1 = Long.parseLong(ronde1id);
        Long ronde2 = Long.parseLong(ronde2id);
        Long ronde3 = Long.parseLong(ronde3id);
        Long ronde4 = Long.parseLong(ronde4id);
        Ronde ronde1a = null;
        Ronde ronde2a = null;
        Ronde ronde3a = null;
        Ronde ronde4a = null;

        try {
            ronde1a = (Ronde) rondeController.getRondeById(ronde1).getBody();
            ronde2a = (Ronde) rondeController.getRondeById(ronde2).getBody();
            ronde3a = (Ronde) rondeController.getRondeById(ronde3).getBody();
            ronde4a = (Ronde) rondeController.getRondeById(ronde4).getBody();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createMessage("Error retrieving ronde"));
        }
        Takkie takkie = new Takkie(ronde1a, ronde2a, ronde3a, ronde4a);
        return ResponseEntity.ok(createMessage("Takkie created successfully", takkieRepository.save(takkie)));
    }

    @PostMapping("/createEmpty")
    public ResponseEntity<?> createTakkieEmpty() {
        Takkie takkie = new Takkie();
        return ResponseEntity.ok(createMessage("Empty Takkie created successfully", takkieRepository.save(takkie)));
    }

    @PutMapping("/{id}/addRonde")
    public ResponseEntity<?> addRondeToTakkie(@PathVariable("id") Long id, @RequestBody Map<String,String> body) {
        if (!takkieRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(createMessage("Takkie not found"));
        }
        String rondeid = body.get("ronde");
        if (isNullOrEmpty(rondeid)) {
            return ResponseEntity.badRequest().body(createMessage("Ronde ID is empty"));
        }
        if (!isNumeric(rondeid)) {
            return ResponseEntity.badRequest().body(createMessage("Ronde ID is not numeric"));
        }
        Long ronde = Long.parseLong(rondeid);
        Ronde rondea;
        try {
            rondea = (Ronde) rondeController.getRondeById(ronde).getBody();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createMessage("Error retrieving ronde"));
        }
        Takkie takkie = takkieRepository.findById(id).get();
        if (takkie.getRonde1() == null) {
            takkie.setRonde1(rondea);
        } else if (takkie.getRonde2() == null) {
            takkie.setRonde2(rondea);
        } else if (takkie.getRonde3() == null) {
            takkie.setRonde3(rondea);
        } else if (takkie.getRonde4() == null) {
            takkie.setRonde4(rondea);
        } else {
            return ResponseEntity.badRequest().body(createMessage("All Ronde slots are filled"));
        }
        return ResponseEntity.ok(createMessage("Ronde added to Takkie successfully", takkieRepository.save(takkie)));
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<?> getTakkieById(@PathVariable("id") Long id) {
        if (!takkieRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(createMessage("Takkie not found"));
        }
        return ResponseEntity.ok(takkieRepository.findById(id).orElse(null));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteTakkieById(@PathVariable("id") Long id) {
        if (!takkieRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(createMessage("Takkie not found"));
        }
        takkieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    static Boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+");  // Matches positive or negative integers
    }

    private Map<String, Object> createMessage(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        return response;
    }

    private Map<String, Object> createMessage(String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("data", data);
        return response;
    }
}