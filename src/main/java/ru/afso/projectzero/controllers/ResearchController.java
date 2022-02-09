package ru.afso.projectzero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.afso.projectzero.dto.ResearchDTO;
import ru.afso.projectzero.entities.ResearchEntity;
import ru.afso.projectzero.services.ResearchService;
import ru.afso.projectzero.utils.BaseResponse;
import ru.afso.projectzero.utils.SuccessResponse;

import javax.validation.Valid;
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
    public BaseResponse<HashMap<String, Object>> getResearches(
            @RequestParam(name="count") Optional<Integer> optionalCount,
            @RequestParam(name="offset") Optional<Integer> optionalOffset
    ) {
        int count = optionalCount.orElse(5);
        int offset = optionalOffset.orElse(0);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", researchService.getTotalResearchCount());
        map.put("researches", researchService.getResearches(offset, count).stream()
                .map(ResearchEntity::toModel).collect(Collectors.toList()));
        return new SuccessResponse<>(map);
    }

    @GetMapping("/{id}")
    public BaseResponse<?> getResearch(@PathVariable long id) {
        return new SuccessResponse<>(researchService.getResearchById(id).toModel());
    }

    @PostMapping(consumes = {"application/json"})
    @PreAuthorize("hasAuthority('admin')")
    public BaseResponse<?> createResearch(@Valid @RequestBody ResearchDTO researchDTO) {
        return new SuccessResponse<>(researchService.createResearch(new ResearchEntity(researchDTO)).toModel());
    }

    @PutMapping(value = "/{id}", consumes = {"application/json"})
    @PreAuthorize("hasAuthority('admin')")
    public BaseResponse<?> updateResearch(@Valid @RequestBody ResearchDTO researchDTO, @PathVariable long id) {
        ResearchEntity research = new ResearchEntity(researchDTO);
        research.setId(id);
        return new SuccessResponse<>(researchService.updateResearch(research).toModel());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public BaseResponse<?> deleteResearch(@PathVariable long id) {
        researchService.deleteResearchById(id);
        return new SuccessResponse<>(true);
    }

}
