package ru.afso.projectzero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.afso.projectzero.models.Research;
import ru.afso.projectzero.repositories.ResearchRepository;
import ru.afso.projectzero.utils.ApiResponse;
import ru.afso.projectzero.utils.SuccessResponse;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1.0/research")
public class ResearchController {

    private final ResearchRepository researchRepository;

    @Autowired
    public ResearchController(ResearchRepository researchRepository) {
        this.researchRepository = researchRepository;
    }

    @GetMapping
    public ApiResponse<HashMap<String, Object>> getResearches(
            @RequestParam(name="count") Optional<Integer> optionalCount,
            @RequestParam(name="offset") Optional<Integer> optionalOffset
    ) {
        int count = optionalCount.orElse(5);
        int offset = optionalOffset.orElse(0);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", researchRepository.count());
        map.put("researches", researchRepository.findAll()
                .stream().skip(offset).limit(count).collect(Collectors.toList()));
        return new SuccessResponse<>(map);
    }

    @GetMapping("/{id}")
    public ApiResponse<Research> getResearch(@PathVariable String id) {
        return new SuccessResponse<>(researchRepository.findById(id).orElse(null));
    }

    @PostMapping(consumes = {"application/json"})
    public ApiResponse<Research> createResearch(@RequestBody Research research) {
        researchRepository.save(research);
        return new SuccessResponse<>(research);
    }

    @PutMapping(value = "/{id}", consumes = {"application/json"})
    public ApiResponse<Research> updateResearch(@RequestBody Research research, @PathVariable String id) {
        research.setId(id);
        researchRepository.save(research);
        return new SuccessResponse<>(research);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteResearch(@PathVariable String id) {
        researchRepository.deleteById(id);
        return new SuccessResponse<>(true);
    }

}
