package ru.afso.projectzero.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.afso.projectzero.entities.ResearchEntity;
import ru.afso.projectzero.repositories.ResearchRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResearchService {

    private final ResearchRepository researchRepository;

    @Autowired
    public ResearchService(ResearchRepository researchRepository) {
        this.researchRepository = researchRepository;
    }

    public List<ResearchEntity> getResearches(int offset, int count) {
        return researchRepository.findAll()
                .stream().skip(offset).limit(count).collect(Collectors.toList());
    }

    public ResearchEntity getResearchById(String id) {
        return researchRepository.findById(id).orElse(null);
    }

    public ResearchEntity createResearch(ResearchEntity survey) {
        researchRepository.save(survey);
        return survey;
    }

    public ResearchEntity updateResearch(ResearchEntity survey) {
        return researchRepository.save(survey);
    }

    public void deleteResearchById(String id) {
        researchRepository.deleteById(id);
    }

    public long getTotalResearchCount() {
        return researchRepository.count();
    }

}
