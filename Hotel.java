package com.example.hotelbooking.model;

import jakarta.persistence.*;

@Entity
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String image;
    private String name;
    private String location;
    private int availableRooms;

    public Hotel() {
        // Default constructor required by JPA
    }

    public Hotel(Long id, String name, String location, int availableRooms) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.availableRooms = availableRooms;
    }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getAvailableRooms() { return availableRooms; }
    public void setAvailableRooms(int availableRooms) { this.availableRooms = availableRooms;
    }
}