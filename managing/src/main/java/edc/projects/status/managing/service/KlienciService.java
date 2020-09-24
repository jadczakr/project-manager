package edc.projects.status.managing.service;

import java.util.List;

import edc.projects.status.managing.entities.Klienci;


public interface KlienciService {

	List<Klienci> getAllKlienci();
	
	void dodaj(Klienci klient);

		

}
