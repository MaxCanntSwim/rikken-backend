package com.MaxEradus.rikken.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naam; // Optional name for the game

    @ManyToMany
    @JoinTable(
            name = "game_player", // Join table for many-to-many relationship
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private List<Player> players; // List of players participating in the game

    @OneToMany
    private List<Takkie> takkies; // List of Takkies associated with the game

    // Constructors
    public Game() {
    }

    public Game(String naam, List<Player> players, List<Takkie> takkies) {
        this.naam = naam;
        this.players = players;
        this.takkies = takkies;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Takkie> getTakkies() {
        return takkies;
    }

    public void setTakkies(List<Takkie> takkies) {
        this.takkies = takkies;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void addTakkie(Takkie takkie) {
        takkies.add(takkie);
    }

    public void removeTakkie(Takkie takkie) {
        takkies.remove(takkie);
    }

    // toString Method
    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", naam='" + naam + '\'' +
                ", players=" + (players != null ? players.size() : 0) + // Display number of players
                ", takkies=" + (takkies != null ? takkies.size() : 0) + // Display number of takkies
                '}';
    }
}
