package com.toonborghuis.gallery.paintings_gallery.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contact_page")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String phoneNumber;

    private String location;

    @JsonIgnore
    @OneToOne(mappedBy = "contactPage")
    private ContentManagement contentManagement;

}
