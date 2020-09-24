package edc.projects.status.managing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edc.projects.status.managing.entities.Zlecenia;

public interface ZleceniaRepository extends JpaRepository<Zlecenia,Integer>{

}
