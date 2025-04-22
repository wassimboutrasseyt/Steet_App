package org.demo.core_app.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PubRoom extends Room {

    @OneToMany(mappedBy = "pubRoom")
    private List<Participation> participation;
}