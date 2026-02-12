package com.igitan.springboothome.dto;

import jakarta.validation.constraints.NotBlank;

public class ResourceDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    public ResourceDTO() {
    }

    public ResourceDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static class ResourceDTOBuilder {
        private Long id;
        private String name;
        private String description;

        ResourceDTOBuilder() {
        }

        public ResourceDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ResourceDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ResourceDTOBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ResourceDTO build() {
            return new ResourceDTO(id, name, description);
        }
    }

    public static ResourceDTOBuilder builder() {
        return new ResourceDTOBuilder();
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
