package com.example;

import com.example.model.Salle;
import com.example.model.Utilisateur;
import com.example.service.SalleService;
import com.example.service.UtilisateurService;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class App {
    public static void main(String[] args) {
        // Création de l'EntityManagerFactory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestion-salles");

        // Création des services
        UtilisateurService utilisateurService = new UtilisateurService(emf);
        SalleService salleService = new SalleService(emf);

        try {
            // Test des opérations CRUD pour Utilisateur
            System.out.println("\n**** Test de CRUD Utilisateur ****");
            testCrudUtilisateur(utilisateurService);

            // Test des opérations CRUD pour Salle
            System.out.println("\n**** Test de CRUD Salle ****");
            testCrudSalle(salleService);

        } finally {
            // Fermeture de l'EntityManagerFactory
            emf.close();
        }
    }

    private static void testCrudUtilisateur(UtilisateurService service) {
        // Création (Create)
        System.out.println("Ajout des utilisateurs...");
        Utilisateur u1 = new Utilisateur("AMINE", "RAGHIB", "AMINE.RAGHIB@example.com");
        u1.setDateNaissance(LocalDate.of(1999, 6, 9));
        u1.setTelephone("+2121233443");

        Utilisateur u2 = new Utilisateur("ADEMOLA", "LOCKMAN", "ADEMOLA.ATM@example.com");
        u2.setDateNaissance(LocalDate.of(1945, 10, 1));
        u2.setTelephone("+3365546874");

        service.save(u1);
        service.save(u2);

        // Lecture (Read)
        System.out.println("\nListe des  utilisateurs :");
        List<Utilisateur> utilisateurs = service.findAll();
        utilisateurs.forEach(System.out::println);

        System.out.println("\nRecherche par ID (1) :");
        Optional<Utilisateur> utilisateurOpt = service.findById(1L);
        utilisateurOpt.ifPresent(System.out::println);

        System.out.println("\nRecherche par email :");
        Optional<Utilisateur> utilisateurParEmail = service.findByEmail("ADEMOLA.ATM@example.com");
        utilisateurParEmail.ifPresent(System.out::println);

        // Mise à jour (Update)
        System.out.println("\nModification du téléphone de l'utilisateur 1");
        utilisateurOpt.ifPresent(utilisateur -> {
            utilisateur.setTelephone("+33611223321");
            service.update(utilisateur);
            System.out.println("Utilisateur modifié : " + utilisateur);
        });

        // Suppression (Delete) *****
        System.out.println("\nSuppression utilisateur ID=2");
        service.deleteById(2L);
        System.out.println("supprimé");

        System.out.println("\nAprès suppression :");
        service.findAll().forEach(System.out::println);
    }

    private static void testCrudSalle(SalleService service) {
        // Création (Create)
        System.out.println("Ajout des salles...");
        Salle s1 = new Salle("Salle Info 1", 20);
        s1.setDescription("Salle pour travaux pratiques");
        s1.setEtage(1);

        Salle s2 = new Salle("Amphithéâtre A2", 100);
        s2.setDescription("Grand amphithéâtre pour les cours");
        s2.setEtage(2);

        Salle s3 = new Salle("Salle de sport", 30);
        s3.setDescription("Petite salle pour musculation");
        s3.setEtage(3);
        s3.setDisponible(false);

        service.save(s1);
        service.save(s2);
        service.save(s3);

        // Lecture (Read)
        System.out.println("\nListe des salles :");
        List<Salle> salles = service.findAll();
        salles.forEach(System.out::println);

        System.out.println("\nRecherche salle ID=2 :");
        Optional<Salle> salleOpt = service.findById(2L);
        salleOpt.ifPresent(System.out::println);

        System.out.println("\nSalles disponibles :");
        List<Salle> sallesDisponibles = service.findByDisponible(true);
        sallesDisponibles.forEach(System.out::println);

        System.out.println("\nSalles avec capacité minimum 50 :");
        List<Salle> sallesGrandes = service.findByCapaciteMinimum(50);
        sallesGrandes.forEach(System.out::println);

        // Mise à jour (Update)
        System.out.println("\nModification capacité salle 2");
        salleOpt.ifPresent(salle -> {
            salle.setCapacite(200);
            service.update(salle);
            System.out.println("Salle modifiée : " + salle);
        });

        // Suppression (Delete)
        System.out.println("\nSuppression salle ID=3");
        service.deleteById(3L);
        System.out.println("supprimée");

        System.out.println("\nListe finale des salles :");
        service.findAll().forEach(System.out::println);
    }
}