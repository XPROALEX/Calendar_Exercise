package it.Ale.Calendar.entity;

import it.Ale.Calendar.dto.EventDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private User owner;
    private List<EventDto> events;


    public Calendar() {
    }
}
