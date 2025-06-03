package br.com.ebueno.stockcontrol.domain.v1.category.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "categories")
@Entity
public class Category {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "category_id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "category_name", nullable = false, length = 255)
    private String name;
    @Column(name = "category_description", length = 765)
    private String description;
    @Column(name = "category_image_url", length = 765)
    private String imageUrl;
}
