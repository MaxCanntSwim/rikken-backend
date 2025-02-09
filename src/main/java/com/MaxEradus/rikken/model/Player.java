package com.MaxEradus.rikken.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String passcodeHash; // Store hashed passcodes

    @ManyToMany(mappedBy = "players")
    private List<Game> games; // List of games this player has participated in

    // Constructors
    public Player() {
    }

    public Player(String name, String passcodeHash) {
        this.name = name;
        this.passcodeHash = passcodeHash;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasscodeHash() {
        return passcodeHash;
    }

    public void setPasscodeHash(String passcodeHash) {
        this.passcodeHash = passcodeHash;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public void removeGame(Game game) {
        games.remove(game);
    }

    // toString Method
    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", passcodeHash='" + passcodeHash + '\'' +
                ", games=" + (games != null ? games.size() : 0) + // Display number of games
                '}';
    }
}