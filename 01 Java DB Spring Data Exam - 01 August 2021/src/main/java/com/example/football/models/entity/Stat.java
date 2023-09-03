package com.example.football.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "stats")
public class Stat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private float shooting;

    @Column( nullable = false)
    private float passing;

    @Column( nullable = false)
    private float endurance;

    public Stat() {
    }

    public Stat(float shooting, float passing, float endurance) {
        this.shooting = shooting;
        this.passing = passing;
        this.endurance = endurance;
    }

    public float getShooting() {
        return shooting;
    }

    public void setShooting(float shooting) {
        this.shooting = shooting;
    }

    public float getPassing() {
        return passing;
    }

    public void setPassing(float passing) {
        this.passing = passing;
    }

    public float getEndurance() {
        return endurance;
    }

    public void setEndurance(float endurance) {
        this.endurance = endurance;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Stat{" +
                "passing=" + passing +
                ", shooting=" + shooting +
                ", endurance=" + endurance +
                '}';
    }
}
