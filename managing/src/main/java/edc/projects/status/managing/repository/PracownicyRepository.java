package edc.projects.status.managing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edc.projects.status.managing.entities.Pracownicy;

public interface PracownicyRepository extends JpaRepository<Pracownicy,Integer>{

}
