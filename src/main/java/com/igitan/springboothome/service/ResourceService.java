package com.igitan.springboothome.service;

import com.igitan.springboothome.dto.ResourceDTO;
import com.igitan.springboothome.model.Resource;
import com.igitan.springboothome.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    public Page<ResourceDTO> getAllResources(Pageable pageable) {
        return resourceRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    public ResourceDTO getResourceById(Long id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found with id: " + id));
        return convertToDTO(resource);
    }

    @Transactional
    public ResourceDTO createResource(ResourceDTO resourceDTO) {
        Resource resource = Resource.builder()
                .name(resourceDTO.getName())
                .description(resourceDTO.getDescription())
                .build();
        Resource savedResource = resourceRepository.save(resource);
        return convertToDTO(savedResource);
    }

    @Transactional
    public ResourceDTO updateResource(Long id, ResourceDTO resourceDTO) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found with id: " + id));

        resource.setName(resourceDTO.getName());
        resource.setDescription(resourceDTO.getDescription());

        Resource updatedResource = resourceRepository.save(resource);
        return convertToDTO(updatedResource);
    }

    @Transactional
    public void deleteResource(Long id) {
        if (!resourceRepository.existsById(id)) {
            throw new RuntimeException("Resource not found with id: " + id);
        }
        resourceRepository.deleteById(id);
    }

    private ResourceDTO convertToDTO(Resource resource) {
        return ResourceDTO.builder()
                .id(resource.getId())
                .name(resource.getName())
                .description(resource.getDescription())
                .build();
    }
}
