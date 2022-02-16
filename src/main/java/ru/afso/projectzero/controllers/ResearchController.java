package ru.afso.projectzero.controllers;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.afso.projectzero.dto.ResearchDTO;
import ru.afso.projectzero.entities.ResearchEntity;
import ru.afso.projectzero.models.ResearchModel;
import ru.afso.projectzero.models.ResponseListModel;
import ru.afso.projectzero.services.ResearchService;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1.0/researches")
@Tag(name = "Researches", description = "Researches management API")
@SecurityScheme(name = "Administrator", scheme = "bearer", bearerFormat = "JWT", type = SecuritySchemeType.HTTP)
public class ResearchController {

    private final ResearchService researchService;

    @Autowired
    public ResearchController(ResearchService researchService) {
        this.researchService = researchService;
    }


    @Operation(summary = "Get researches",
            description = "Returns all researches with pagination",
            security = @SecurityRequirement(name = "Administrator"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns total researches count and researches list"),
            @ApiResponse(responseCode = "401", description = "Not authenticated",
                    content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @GetMapping(produces = {"application/json"})
    public ResponseEntity<ResponseListModel<ResearchModel>> getResearches(
            @Parameter(name = "count", in = ParameterIn.QUERY,
                    description = "Researches count to return, default is 5")
            @RequestParam(name="count") Optional<Integer> optionalCount,
            @Parameter(name = "offset", in = ParameterIn.QUERY,
                    description = "From which research return, default is 0")
            @RequestParam(name="offset") Optional<Integer> optionalOffset
    ) {
        int count = optionalCount.orElse(5);
        int offset = optionalOffset.orElse(0);
        return new ResponseEntity<>(new ResponseListModel<>(
                researchService.getTotalResearchCount(),
                researchService.getResearches(offset, count).stream()
                        .map(ResearchEntity::toModel).collect(Collectors.toList())), HttpStatus.OK);
    }



    @Operation(summary = "Get research by ID",
            description = "Return research by its ID",
            security = @SecurityRequirement(name = "Administrator"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return research by its ID"),
            @ApiResponse(responseCode = "401", description = "Not authenticated",
                    content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "Research not found",
                    content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @GetMapping(value="/{id}", produces = {"application/json"})
    public ResponseEntity<ResearchModel> getResearch(
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Research id")
            @PathVariable long id
    ) {
        return new ResponseEntity<>(researchService.getResearchById(id).toModel(), HttpStatus.OK);
    }



    @Operation(summary = "Create research",
            description = "Creates new research",
            security = @SecurityRequirement(name = "Administrator"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns empty body if research was created", headers = {
                    @Header(name = "Location", description = "Contains research location URI")
            }),
            @ApiResponse(responseCode = "400", description = "Other error",
                    content = @Content(schema = @Schema(implementation = String.class, description = "Error message"))),
            @ApiResponse(responseCode = "401", description = "Not authenticated",
                    content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @PostMapping(consumes = { "application/json" })
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> createResearch(
            @Parameter(in = ParameterIn.DEFAULT, description = "Research to add. Cannot be null or empty",
                    required = true, schema = @Schema(implementation = ResearchDTO.class))
            @Valid @RequestBody ResearchDTO researchDTO
    ) {
        ResearchModel model = researchService.createResearch(new ResearchEntity(researchDTO)).toModel();
        URI location = URI.create(String.format("/researches/%d", model.getId()));
        return ResponseEntity.created(location).build();
    }



    @Operation(summary = "Update research",
            description = "Updates existing research",
            security = @SecurityRequirement(name = "Administrator"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Returns empty body if research was updated"),
            @ApiResponse(responseCode = "400", description = "Other error",
                    content = @Content(schema = @Schema(implementation = String.class, description = "Error message"))),
            @ApiResponse(responseCode = "401", description = "Not authenticated",
                    content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "Research not found",
                    content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @PutMapping(value = "/{id}", consumes = { "application/json" }, produces = {"application/json"})
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> updateResearch(
            @Parameter(in = ParameterIn.DEFAULT, description = "Research data to update",
                    required = true, schema = @Schema(implementation = ResearchDTO.class))
            @Valid @RequestBody ResearchDTO researchDTO,
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Research id")
            @PathVariable long id
    ) {
        ResearchEntity research = new ResearchEntity(researchDTO);
        research.setId(id);
        researchService.updateResearch(research);
        return ResponseEntity.noContent().build();
    }



    @Operation(summary = "Delete research",
            description = "Deletes existing research",
            security = @SecurityRequirement(name = "Administrator"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Returns empty body if research was deleted"),
            @ApiResponse(responseCode = "401", description = "Not authenticated",
                    content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "Research not found",
                    content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> deleteResearch(
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Research id")
            @PathVariable long id
    ) {
        researchService.deleteResearchById(id);
        return ResponseEntity.noContent().build();
    }

}
