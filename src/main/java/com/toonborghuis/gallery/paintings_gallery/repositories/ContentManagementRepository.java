package com.toonborghuis.gallery.paintings_gallery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toonborghuis.gallery.paintings_gallery.entities.ContentManagement;

@Repository
public interface ContentManagementRepository extends JpaRepository<ContentManagement, Long>{
    
}
