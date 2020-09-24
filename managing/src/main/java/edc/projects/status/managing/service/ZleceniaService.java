package edc.projects.status.managing.service;

import java.util.List;
import java.util.Set;


import edc.projects.status.managing.entities.Zlecenia;

public interface ZleceniaService {

	void addZlecenie(Zlecenia tempZlecenie);

	void deleteZlecenie(Integer id);

	List<Zlecenia> getFinalAllZlecenia(String login);

	Zlecenia getZlecenie(Integer id);

	void saveOrUpdate(Zlecenia tempZlecenie);

	Set<Zlecenia> getZleceniaZZastepstewm(String login);

	List<Zlecenia> getDzienneZlecenia(String login);

	List<Zlecenia> getZakresoweZlecenia(String login);

	
}

