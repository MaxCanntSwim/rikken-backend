package com.MaxEradus.rikken.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Ronde {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RondeType rondeType; // Enum to represent the type of the round

    @ManyToMany
    private List<Player> playersVoor;

    @ManyToMany
    private List<Player> playersTegen;

    private int punten; // Points gained or lost (positive = gained, negative = lost)

    // Constructors
    public Ronde() {
    }

    public Ronde(RondeType rondeType, List<Player> voor, List<Player> tegen, int punten) {
        this.rondeType = rondeType;
        this.playersVoor = voor;
        this.playersTegen = tegen;
        this.punten = punten;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RondeType getRondeType() {
        return rondeType;
    }

    public void setRondeType(RondeType rondeType) {
        this.rondeType = rondeType;
    }

    public List<Player> getPlayersVoor() {
        return playersVoor;
    }

    public void setPlayersVoor(List<Player> playersVoor) {
        this.playersVoor = playersVoor;
    }

    public List<Player> getPlayersTegen() {
        return playersTegen;
    }

    public void setPlayersTegen(List<Player> playersTegen) {
        this.playersTegen = playersTegen;
    }

    public int getPunten() {
        return punten;
    }

    public void setPunten(int punten) {
        this.punten = punten;
    }

    @Override
    public String toString() {
        return "Ronde{" +
            "id=" + id +
            ", rondeType=" + rondeType +
            ", playersVoor=" + playersVoor +
            ", playersTegen=" + playersTegen +
            ", punten=" + punten +
            '}';
    }
}
