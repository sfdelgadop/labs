package co.edu.unal.software_engineering.labs.service;

import co.edu.unal.software_engineering.labs.model.Association;
import co.edu.unal.software_engineering.labs.repository.AssociationRepository;
import org.springframework.stereotype.Service;


@Service
public class AssociationService{

    private final AssociationRepository associationRepository;

    public AssociationService(AssociationRepository associationRepository){
        this.associationRepository = associationRepository;
    }

    public  void save(Association association){
        associationRepository.save(association);
    }

}
