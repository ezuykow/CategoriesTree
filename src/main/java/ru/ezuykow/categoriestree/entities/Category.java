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

    public Category(String name) {
        this.name = name;
    }

    public Category(Category parent, String name) {
        this.parent = parent;
        this.name = name;
    }
}
