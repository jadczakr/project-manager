package edc.projects.status.managing.service;

import java.time.LocalDate;
import java.util.List;

import edc.projects.status.managing.entities.Pracownicy;

public interface PracownicyService {

	List<Pracownicy> getAllPracownicy();
	
	Pracownicy getPracownikByLogin(String login);
	
	void save(Pracownicy pracownik);
	
	void urlopy();

	boolean jestNaUrlopie(String login);

	void setUrlop(String login, LocalDate od, LocalDate doo);
}
