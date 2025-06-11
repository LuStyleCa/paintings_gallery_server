package com.toonborghuis.gallery.paintings_gallery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.toonborghuis.gallery.paintings_gallery.entities.AboutPage;

@Repository
public interface AboutPageRepository extends JpaRepository<AboutPage, Long> {
    
}
