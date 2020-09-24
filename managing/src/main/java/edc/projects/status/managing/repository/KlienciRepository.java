package edc.projects.status.managing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edc.projects.status.managing.entities.Klienci;

public interface KlienciRepository extends JpaRepository<Klienci,Integer>{

}
