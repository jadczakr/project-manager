package edc.projects.status.managing.service.implementation;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edc.projects.status.managing.entities.Klienci;
import edc.projects.status.managing.repository.KlienciRepository;
import edc.projects.status.managing.service.KlienciService;

@Service
public class KlienciServiceImpl implements KlienciService {

	
	private KlienciRepository klienciRepository;
	
	
	@Autowired
	public KlienciServiceImpl(KlienciRepository klienciRepository)
	{
		this.klienciRepository = klienciRepository;
	}
	
	@Override
	public List<Klienci> getAllKlienci() {
		
		return klienciRepository.findAll(Sort.by(Sort.Direction.ASC,"nazwa"));
	}

	@Override
	public void dodaj(Klienci klient) {
		
		klienciRepository.save(klient);
		
	}



}
