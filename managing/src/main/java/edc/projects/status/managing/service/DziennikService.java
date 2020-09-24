package edc.projects.status.managing.service;

import java.util.List;

import edc.projects.status.managing.entities.Dziennik;

public interface DziennikService {

	void addNotatke(Dziennik dziennik);

	void usunPoTerminie();

	void deleteNotatka(Dziennik dziennik);

	Dziennik getNotatkaById(Integer theId);

	void deleteNotatkaById(Integer theId);
	
	List<Dziennik> getAllOgolne();

	List<Dziennik> getAll2(String login);

}
