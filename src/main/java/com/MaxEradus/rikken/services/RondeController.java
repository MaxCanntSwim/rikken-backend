package com.MaxEradus.rikken.services;

import com.MaxEradus.rikken.Repos.RondeRepository;
import com.MaxEradus.rikken.model.Player;
import com.MaxEradus.rikken.model.Ronde;
import com.MaxEradus.rikken.model.RondeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api/ronde")
public class RondeController {

    @Autowired
    private final RondeRepository rondeRepository;
    private final PlayerController playerController;

    public RondeController(RondeRepository rondeRepository, PlayerController playerController) {
        this.rondeRepository = rondeRepository;
        this.playerController = playerController;
    }

    @GetMapping(path = {"","/"})
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(rondeRepository.findAll());
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createRonde(@RequestBody Map<String,String> body) {
        String rondeType = body.get("rondeType");
        String voor = body.get("voor");
        String tegen = body.get("tegen");
        String punten = body.get("punten");
        if ( isNullOrEmpty(rondeType) || isNullOrEmpty(voor) || tegen == null || isNullOrEmpty(punten) || !isNumeric(punten)) return ResponseEntity.badRequest().build(); // Check for empty fields

        String[] voorb = voor.split(",\\s*");
        String[] tegenb = tegen.split(",\\s*");
        if (voorb.length + tegenb.length != 5 && (voor.isEmpty()  || tegen.isEmpty())) return ResponseEntity.badRequest().build(); // Check if there are 4 players
        int puntenb = Integer.parseInt(punten);
        RondeType rondeType1 = null;
        try {
            rondeType1 = RondeType.valueOf(rondeType);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        ArrayList<Player> pvoor = new ArrayList<>();
        ArrayList<Player> ptegen = new ArrayList<>();

        for (String s : voorb) {
            Player p = null;
            try {
                p = (Player) playerController.getPlayerById(Long.parseLong(s)).getBody();
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
            if (p == null) return ResponseEntity.badRequest().build();
            pvoor.add(p);
        }
        if (!tegen.isEmpty()){
            for (String s : tegenb) {
                Player p = null;
                try {
                    p = (Player) playerController.getPlayerById(Long.parseLong(s)).getBody();
                } catch (Exception e) {
                    return ResponseEntity.badRequest().build();
                }
                if (p == null) return ResponseEntity.badRequest().build();
                ptegen.add(p);
            }
        }
        Ronde ronde = new Ronde(rondeType1, pvoor, ptegen, puntenb);
        return ResponseEntity.ok(rondeRepository.save(ronde));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteRondeById(Long id) {
        if (!rondeRepository.existsById(id)) return ResponseEntity.badRequest().build();
        rondeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<?> getRondeById(Long id) {
        if (!rondeRepository.existsById(id)) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(rondeRepository.findById(id).orElse(null));
    }

    static Boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+");  // Matches positive or negative integers
    }
}
