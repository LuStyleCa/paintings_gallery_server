package com.toonborghuis.gallery.paintings_gallery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toonborghuis.gallery.paintings_gallery.entities.Gallery;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    
}
