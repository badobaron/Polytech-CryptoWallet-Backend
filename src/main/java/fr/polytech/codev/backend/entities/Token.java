package fr.polytech.codev.backend.entities;

import fr.polytech.codev.backend.adapters.UserIdJsonbAdapter;
import lombok.Data;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tokens")
public class Token implements fr.polytech.codev.backend.entities.Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "The value can't be blank!")
    @Size(message = "The value can't exceed 36 characters!", max = 36)
    @Column(name = "value")
    private String value;

    @JsonbDateFormat("dd/MM/yyyy hh:mm:ss")
    @NotNull(message = "The begin date can't be null!")
    @PastOrPresent(message = "The begin date can't be in the future!")
    @Column(name = "begin_date")
    private LocalDateTime beginDate;

    @JsonbDateFormat("dd/MM/yyyy hh:mm:ss")
    @NotNull(message = "The end date can't be null!")
    @FutureOrPresent(message = "The end date can't be in the past!")
    @Column(name = "end_date")
    private LocalDateTime endDate;

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