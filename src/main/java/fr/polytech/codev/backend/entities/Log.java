package fr.polytech.codev.backend.entities;

import fr.polytech.codev.backend.adapters.UserIdJsonbAdapter;
import lombok.Data;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "logs")
public class Log implements fr.polytech.codev.backend.entities.Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "The ip address can't be blank!")
    @Size(message = "The ip address can't exceed 20 characters!", max = 20)
    @Column(name = "ip_address")
    private String ipAddress;

    @NotBlank(message = "The platform can't be blank!")
    @Size(message = "The platform can't exceed 250 characters!", max = 250)
    @Column(name = "platform")
    private String platform;

    @JsonbDateFormat("dd/MM/yyyy hh:mm:ss")
    @NotNull(message = "The creation date can't be null!")
    @PastOrPresent(message = "The creation date can't be in the future!")
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @JsonbDateFormat("dd/MM/yyyy hh:mm:ss")
    @NotNull(message = "The last update can't be null!")
    @PastOrPresent(message = "The last update can't be in the future!")
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @JsonbProperty("userId")
    @JsonbTypeAdapter(UserIdJsonbAdapter.class)
    @NotNull(message = "The user can't be null!")
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
}