package com.codewithisa.userservice.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(
        name = "Users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "emailAddress")
        }
)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Schema(example = "1")
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Schema(example = "isarndr")
    private String username;

    @NotBlank
    @Size(max = 20)
    @Schema(example = "isa@yahoo.com")
    private String emailAddress;

    @Schema(example = "123")
    private String password;

    public User(Long id, String email, String password) {
        this.id = id;
        this.emailAddress = email;
        this.password = password;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.emailAddress = email;
        this.password = password;
    }
}

