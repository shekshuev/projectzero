package ru.afso.projectzero.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.afso.projectzero.entities.ResearchEntity;
import ru.afso.projectzero.repositories.ResearchRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ResearchService {

    private final ResearchRepository researchRepository;

    @Autowired
    public ResearchService(ResearchRepository researchRepository) {
        this.researchRepository = researchRepository;
    }

    public List<ResearchEntity> getResearches(int offset, int count) {
        return StreamSupport.stream(researchRepository.findAll().spliterator(), false)
                .skip(offset).limit(count).collect(Collectors.toList());
    }

    public ResearchEntity getResearchById(long id) {
        return researchRepository.findById(id).orElseThrow();
    }

    public ResearchEntity createResearch(ResearchEntity research) {
        return researchRepository.save(research);
    }

    public ResearchEntity updateResearch(ResearchEntity research) {
        return researchRepository.save(research);
    }

    public void deleteResearchById(long id) {
        researchRepository.deleteById(id);
    }

    public long getTotalResearchCount() {
        return researchRepository.count();
    }

}
