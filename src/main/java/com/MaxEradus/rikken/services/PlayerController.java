package com.MaxEradus.rikken.services;

import com.MaxEradus.rikken.Repos.PlayerRepository;
import com.MaxEradus.rikken.model.Player;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    private final PlayerRepository playerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping(path = {"","/"})
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(playerRepository.findAll());
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createPlayer(@RequestBody Map<String,String> body) {
        String name = body.get("name");
        String passcode = body.get("passcode");
        if (isNullOrEmpty(name) || isNullOrEmpty(passcode) || passcode.length() != 4) return ResponseEntity.badRequest().build(); // Check for empty fields

        String passcodeHash = passwordEncoder.encode(passcode); // Hash the passcode
        Player player = new Player(name, passcodeHash);
        return ResponseEntity.ok(playerRepository.save(player));
    }

    @GetMapping(path = "/{id}/validate")
    public ResponseEntity<?> validatePasscode(@PathVariable("id") Long id, @RequestBody Map<String,String> body) {
        String passcode = body.get("passcode");
        Player player = playerRepository.findById(id).orElse(null);
        if (player == null || isNullOrEmpty(passcode) || passcode.length() != 4) return ResponseEntity.badRequest().build(); // Check if player exists

        return ResponseEntity.ok(passwordEncoder.matches(passcode, player.getPasscodeHash())); // Check match
    }

    @DeleteMapping(path = "/{id}/delete")
    public ResponseEntity<?> deletePlayerById(@PathVariable("id") Long id, @RequestBody Map<String,String> body) {
        ResponseEntity<?> response = validatePasscode(id, body);
        if (response.getStatusCode() != HttpStatus.OK || !(Boolean) response.getBody()) return ResponseEntity.badRequest().build(); // Check if passcode is correct
        Player player = playerRepository.findById(id).orElse(null);
        playerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{id}/get")
    public ResponseEntity<?> getPlayerById(@PathVariable("id") Long id) {
        if(id == null) return ResponseEntity.badRequest().build();
        if (playerRepository.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok( playerRepository.findById(id).orElse(null));
    }

    static Boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
