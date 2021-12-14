package ru.afso.projectzero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;
import ru.afso.projectzero.dto.ResearchDTO;
import ru.afso.projectzero.entities.ResearchEntity;
import ru.afso.projectzero.services.ResearchService;
import ru.afso.projectzero.utils.ApiResponse;
import ru.afso.projectzero.utils.ErrorResponse;
import ru.afso.projectzero.utils.SuccessResponse;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1.0/research")
public class ResearchController {

    private final ResearchService researchService;

    @Autowired
    public ResearchController(ResearchService researchService) {
        this.researchService = researchService;
    }

    @GetMapping
    public ApiResponse<HashMap<String, Object>> getResearches(
            @RequestParam(name="count") Optional<Integer> optionalCount,
            @RequestParam(name="offset") Optional<Integer> optionalOffset
    ) {
        int count = optionalCount.orElse(5);
        int offset = optionalOffset.orElse(0);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", researchService.getTotalResearchCount());
        map.put("researches", researchService.getResearches(offset, count).stream().map(ResearchEntity::toModel).collect(Collectors.toList()));
        return new SuccessResponse<>(map);
    }

    @GetMapping("/{id}")
    public ApiResponse<?> getResearch(@PathVariable long id) {
        return new SuccessResponse<>(researchService.getResearchById(id).toModel());
    }

    @PostMapping(consumes = {"application/json"})
    public ApiResponse<?> createResearch(@RequestBody ResearchDTO researchDTO) {
        try {
            return new SuccessResponse<>(researchService.createResearch(new ResearchEntity(researchDTO)).toModel());
        } catch (DataAccessException e) {
            return new ErrorResponse<>(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = {"application/json"})
    public ApiResponse<?> updateResearch(@RequestBody ResearchDTO researchDTO, @PathVariable long id) {
        ResearchEntity research = new ResearchEntity(researchDTO);
        research.setId(id);
        try {
            return new SuccessResponse<>(researchService.updateResearch(research).toModel());
        } catch (DataAccessException e) {
            return new ErrorResponse<>(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteResearch(@PathVariable long id) {
        try {
            researchService.deleteResearchById(id);
            return new SuccessResponse<>(true);
        } catch (DataAccessException e) {
            return new ErrorResponse<>(e.getMessage());
        }
    }

}
