package ru.ezuykow.categoriestree.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ezuykow
 */
@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer Id;

    @Column(name = "category_name")
    private String name;

    @OneToOne
    @JoinColumn(name = "parent")
    private Category parent;

    @Column(name = "owner_id")
    private Long ownerId;

    public Category(String name, Long ownerId) {
        this.name = name;
        this.ownerId = ownerId;
    }

    public Category(Category parent, String name, Long ownerId) {
        this.parent = parent;
        this.name = name;
        this.ownerId = ownerId;
    }
}
