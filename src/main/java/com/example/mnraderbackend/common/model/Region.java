package com.example.mnraderbackend.common.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Region {

    @Id
    private Long id;

    @Column(nullable = false, length = 200)
    private String city;

}