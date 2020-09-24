package edc.projects.status.managing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edc.projects.status.managing.entities.Dziennik;

public interface DziennikRepository extends JpaRepository<Dziennik,Integer>{

}
