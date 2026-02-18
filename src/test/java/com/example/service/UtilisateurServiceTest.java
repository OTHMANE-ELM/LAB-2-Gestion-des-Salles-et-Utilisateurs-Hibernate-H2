package com.example.service;

import com.example.model.Utilisateur;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class UtilisateurServiceTest {

    private EntityManagerFactory emf;
    private UtilisateurService service;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("gestion-salles");
        service = new UtilisateurService(emf);
    }

    @After
    public void tearDown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @Test
    public void testCrudOperations() {
        // Create
        Utilisateur utilisateur = new Utilisateur("Tester", "Utilisateur", "UtilisateurMoi888.@example.com");
        utilisateur.setDateNaissance(LocalDate.of(1990, 1, 1));
        utilisateur.setTelephone("+212771434249");

        Utilisateur savedUtilisateur = service.save(utilisateur);
        assertNotNull(savedUtilisateur.getId());

        // Read
        Optional<Utilisateur> foundUtilisateur = service.findById(savedUtilisateur.getId());
        assertTrue(foundUtilisateur.isPresent());
        assertEquals("Tester", foundUtilisateur.get().getNom());

        // Update
        Utilisateur toUpdate = foundUtilisateur.get();
        toUpdate.setNom("Modifié");
        service.update(toUpdate);

        Optional<Utilisateur> updatedUtilisateur = service.findById(savedUtilisateur.getId());
        assertTrue(updatedUtilisateur.isPresent());
        assertEquals("Modifié", updatedUtilisateur.get().getNom());

        // Delete
        service.delete(updatedUtilisateur.get());
        Optional<Utilisateur> deletedUtilisateur = service.findById(savedUtilisateur.getId());
        assertFalse(deletedUtilisateur.isPresent());
    }

    @Test
    public void testFindByEmail() {
        // Create test user
        Utilisateur utilisateur = new Utilisateur("Othmane", "ELM", "OTHMANEELM777@example.com");
        service.save(utilisateur);

        // Test find by email
        Optional<Utilisateur> foundUtilisateur = service.findByEmail("OTHMANEELM777@example.com");
        assertTrue(foundUtilisateur.isPresent());
        assertEquals("Othmane", foundUtilisateur.get().getNom());

        // Test with non-existent email
        Optional<Utilisateur> notFound = service.findByEmail("Pasdemail@example.com");
        assertFalse(notFound.isPresent());

        // Clean up
        service.delete(foundUtilisateur.get());
    }

    @Test
    public void testFindAll() {
        // Create test users
        Utilisateur u1 = new Utilisateur("AMINE", "VRAI", "AVRAI@example.com");
        Utilisateur u2 = new Utilisateur("AMINE", "Copie", "COPIE@example.com");
        service.save(u1);
        service.save(u2);

        // Test find all
        List<Utilisateur> utilisateurs = service.findAll();
        assertTrue(utilisateurs.size() >= 2);

        // Clean up
        service.delete(u1);
        service.delete(u2);
    }
}