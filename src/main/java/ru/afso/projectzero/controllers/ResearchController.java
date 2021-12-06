package ru.afso.projectzero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.afso.projectzero.entities.ResearchEntity;
import ru.afso.projectzero.services.ResearchService;
import ru.afso.projectzero.utils.ApiResponse;
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
        map.put("accounts", researchService.getResearches(offset, count));
        return new SuccessResponse<>(map);
    }

    @GetMapping("/{id}")
    public ApiResponse<ResearchEntity> getResearch(@PathVariable String id) {
        return new SuccessResponse<>(researchService.getResearchById(id));
    }

    @PostMapping(consumes = {"application/json"})
    public ApiResponse<ResearchEntity> createResearch(@RequestBody ResearchEntity research) {
        return new SuccessResponse<>(researchService.createResearch(research));
    }

    @PutMapping(value = "/{id}", consumes = {"application/json"})
    public ApiResponse<ResearchEntity> updateResearch(@RequestBody ResearchEntity research, @PathVariable String id) {
        research.setId(id);
        return new SuccessResponse<>(researchService.updateResearch(research));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteResearch(@PathVariable String id) {
        researchService.deleteResearchById(id);
        return new SuccessResponse<>(true);
    }

}
