package com.MaxEradus.rikken.model;

import jakarta.persistence.*;

@Entity
public class Takkie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Ronde ronde1;

    @OneToOne
    private Ronde ronde2;

    @OneToOne
    private Ronde ronde3;

    @OneToOne
    private Ronde ronde4;

    public Takkie() {
    }

    public Takkie(Ronde ronde1, Ronde ronde2, Ronde ronde3, Ronde ronde4) {
        this.ronde1 = ronde1;
        this.ronde2 = ronde2;
        this.ronde3 = ronde3;
        this.ronde4 = ronde4;
    }

    public Ronde getRonde1() {
        return ronde1;
    }

    public void setRonde1(Ronde ronde1) {
        this.ronde1 = ronde1;
    }

    public Ronde getRonde2() {
        return ronde2;
    }

    public void setRonde2(Ronde ronde2) {
        this.ronde2 = ronde2;
    }

    public Ronde getRonde3() {
        return ronde3;
    }

    public void setRonde3(Ronde ronde3) {
        this.ronde3 = ronde3;
    }

    public Ronde getRonde4() {
        return ronde4;
    }

    public void setRonde4(Ronde ronde4) {
        this.ronde4 = ronde4;
    }

    @Override
    public String toString() {
        return "Takkie{" +
                "id=" + id +
                ", ronde1=" + (ronde1 != null ? ronde1.toString() : "null") +
                ", ronde2=" + (ronde2 != null ? ronde2.toString() : "null") +
                ", ronde3=" + (ronde3 != null ? ronde3.toString() : "null") +
                ", ronde4=" + (ronde4 != null ? ronde4.toString() : "null") +
                '}';
    }
}
