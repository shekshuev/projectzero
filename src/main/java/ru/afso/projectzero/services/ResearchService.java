package ru.afso.projectzero.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.afso.projectzero.dto.ResearchDTO;
import ru.afso.projectzero.entities.ResearchEntity;
import ru.afso.projectzero.models.ResearchModel;
import ru.afso.projectzero.repositories.ResearchRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ResearchService {

    private final ResearchRepository researchRepository;

    @Autowired
    public ResearchService(ResearchRepository researchRepository) {
        this.researchRepository = researchRepository;
    }

    public List<ResearchModel> getResearches(int offset, int count) {
        return StreamSupport.stream(researchRepository.findAll().spliterator(), false)
        		.map(ResearchEntity::toModel)
                .skip(offset).limit(count).collect(Collectors.toList());
    }

    public ResearchModel getResearchById(long id) {
        return researchRepository.findById(id).orElseThrow(NoSuchElementException::new).toModel();
    }

    public ResearchModel createResearch(ResearchDTO researchDTO) {
    	ResearchEntity research = new ResearchEntity(researchDTO);
        return researchRepository.save(research).toModel();
    }

    public ResearchModel updateResearch(long id, ResearchDTO researchDTO) {
    	ResearchEntity research = new ResearchEntity(researchDTO);
    	research.setId(id);
        return researchRepository.save(research).toModel();
    }

    public void deleteResearchById(long id) {
        researchRepository.deleteById(id);
    }

    public long getTotalResearchCount() {
        return researchRepository.count();
    }

}
