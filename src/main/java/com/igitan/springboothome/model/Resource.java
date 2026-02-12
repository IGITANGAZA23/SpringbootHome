package com.igitan.springboothome.model;

import jakarta.persistence.*;

@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    public Resource() {
    }

    public Resource(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static class ResourceBuilder {
        private Long id;
        private String name;
        private String description;

        ResourceBuilder() {
        }

        public ResourceBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ResourceBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ResourceBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Resource build() {
            return new Resource(id, name, description);
        }
    }

    public static ResourceBuilder builder() {
        return new ResourceBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
