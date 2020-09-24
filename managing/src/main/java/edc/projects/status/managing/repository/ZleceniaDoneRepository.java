package edc.projects.status.managing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edc.projects.status.managing.entities.ZleceniaDone;

public interface ZleceniaDoneRepository extends JpaRepository<ZleceniaDone,Integer>{

}
