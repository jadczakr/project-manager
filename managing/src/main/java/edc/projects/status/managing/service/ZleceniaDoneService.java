package edc.projects.status.managing.service;

import java.util.List;

import edc.projects.status.managing.entities.ZleceniaDone;

public interface ZleceniaDoneService {

	void addZlecenieDone(ZleceniaDone done);

	List<ZleceniaDone> getZleceniaDoneByLogin(String login);

	List<ZleceniaDone> getZleceniaDoneByLogin2(String login);

	ZleceniaDone getZlecenieDoneById(Integer id);

	void saveOrUpdate(ZleceniaDone zlecenie);

	List<ZleceniaDone> getZawieszone(String login);

	List<ZleceniaDone> getAnulowane(String login);

	ZleceniaDone getZlecenieById(Integer id);

	List<ZleceniaDone> getDoneNiestandardowe(String login);

	List<ZleceniaDone> getDoneByDetails(String query);
	
	void deleteZlecenieDone(ZleceniaDone zleconko);
}
