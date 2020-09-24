package edc.projects.status.managing.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edc.projects.status.managing.entities.Swieta;
import edc.projects.status.managing.repository.SwietaRepository;
import edc.projects.status.managing.service.SwietaService;


@Service
public class SwietaServiceimpl implements SwietaService {

	
	
	private SwietaRepository swietaRepository;
	
	@Autowired
	public SwietaServiceimpl(SwietaRepository swietaRepository)
	{
		this.swietaRepository = swietaRepository;
	}
	
	
	
	
	
	@Override
	public List<Swieta> getAll() {

		return swietaRepository.findAll();
	}

}
