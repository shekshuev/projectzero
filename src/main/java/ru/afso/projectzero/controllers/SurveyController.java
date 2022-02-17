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
import ru.afso.projectzero.dto.FilledSurveyDTO;
import ru.afso.projectzero.dto.QuestionDTO;
import ru.afso.projectzero.dto.SurveyDTO;
import ru.afso.projectzero.entities.FilledSurveyEntity;
import ru.afso.projectzero.entities.QuestionEntity;
import ru.afso.projectzero.entities.SurveyEntity;
import ru.afso.projectzero.models.ResponseListModel;
import ru.afso.projectzero.models.SurveyModel;
import ru.afso.projectzero.services.SurveyService;
import ru.afso.projectzero.utils.BaseResponse;
import ru.afso.projectzero.utils.SuccessResponse;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1.0/surveys")
@Tag(name = "Surveys", description = "Surveys management API")
@SecurityScheme(name = "Administrator", scheme = "bearer", bearerFormat = "JWT", type = SecuritySchemeType.HTTP)
public class SurveyController {

    private final SurveyService surveyService;

    @Autowired
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }



    @Operation(summary = "Get surveys",
            description = "Returns all surveys with pagination",
            security = @SecurityRequirement(name = "Administrator"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns total surveys count and surveys list"),
            @ApiResponse(responseCode = "401", description = "Not authenticated",
                    content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @GetMapping(produces = {"application/json"})
    public ResponseEntity<ResponseListModel<SurveyModel>> getSurveys(
            @Parameter(name = "count", in = ParameterIn.QUERY,
                    description = "Surveys count to return, default is 5")
            @RequestParam(name="count") Optional<Integer> optionalCount,
            @Parameter(name = "offset", in = ParameterIn.QUERY,
                    description = "From which survey return, default is 0")
            @RequestParam(name="offset") Optional<Integer> optionalOffset
    ) {
        int count = optionalCount.orElse(5);
        int offset = optionalOffset.orElse(0);
        return new ResponseEntity<>(new ResponseListModel<>(
                surveyService.getTotalSurveysCount(),
                surveyService.getSurveys(offset, count).stream()
                        .map(SurveyEntity::toModel).collect(Collectors.toList())), HttpStatus.OK);
    }



    @Operation(summary = "Get survey by ID",
            description = "Return survey by its ID",
            security = @SecurityRequirement(name = "Administrator"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return survey by its ID"),
            @ApiResponse(responseCode = "401", description = "Not authenticated",
                    content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "Survey not found",
                    content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @GetMapping(value="/{id}", produces = {"application/json"})
    public ResponseEntity<SurveyModel> getSurvey(
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Survey id")
            @PathVariable long id
    ) {
        return new ResponseEntity<>(surveyService.getSurveyById(id).toModel(), HttpStatus.OK);
    }



    @Operation(summary = "Create survey",
            description = "Creates new survey",
            security = @SecurityRequirement(name = "Administrator"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns empty body if survey was created", headers = {
                    @Header(name = "Location", description = "Contains survey location URI")
            }),
            @ApiResponse(responseCode = "400", description = "Other error",
                    content = @Content(schema = @Schema(implementation = String.class, description = "Error message"))),
            @ApiResponse(responseCode = "401", description = "Not authenticated",
                    content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @PostMapping(consumes = { "application/json" })
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> createSurvey(
            @Parameter(in = ParameterIn.DEFAULT, description = "Survey to add. Cannot be null or empty",
                    required = true, schema = @Schema(implementation = SurveyDTO.class))
            @Valid @RequestBody SurveyDTO surveyDTO
    ) {
        SurveyModel model = surveyService.createSurvey(new SurveyEntity(surveyDTO)).toModel();
        URI location = URI.create(String.format("/surveys/%d", model.getId()));
        return ResponseEntity.created(location).build();
    }



    @Operation(summary = "Update survey",
            description = "Updates existing survey",
            security = @SecurityRequirement(name = "Administrator"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Returns empty body if survey was updated"),
            @ApiResponse(responseCode = "400", description = "Other error",
                    content = @Content(schema = @Schema(implementation = String.class, description = "Error message"))),
            @ApiResponse(responseCode = "401", description = "Not authenticated",
                    content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "Survey not found",
                    content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @PutMapping(value = "/{id}", consumes = { "application/json" }, produces = {"application/json"})
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> updateSurvey(
            @Parameter(in = ParameterIn.DEFAULT, description = "Survey data to update",
                    required = true, schema = @Schema(implementation = SurveyDTO.class))
            @Valid @RequestBody SurveyDTO surveyDTO,
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Survey id")
            @PathVariable long id
    ) {
        surveyService.updateSurvey(surveyService.getSurveyById(id), surveyDTO);
        return ResponseEntity.noContent().build();
    }



    @Operation(summary = "Add question to survey",
            description = "Adds question to an existing survey",
            security = @SecurityRequirement(name = "Administrator"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Returns empty body if question was added"),
            @ApiResponse(responseCode = "400", description = "Other error",
                    content = @Content(schema = @Schema(implementation = String.class, description = "Error message"))),
            @ApiResponse(responseCode = "401", description = "Not authenticated",
                    content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "Survey not found",
                    content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @PostMapping(value = "/{id}/questions", consumes = { "application/json" })
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> addQuestionToSurvey(
            @Parameter(in = ParameterIn.DEFAULT, description = "Question data to update",
                    required = true, schema = @Schema(implementation = QuestionDTO.class))
            @Valid @RequestBody QuestionDTO questionDTO,
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Survey id")
            @PathVariable long id
    ) {
        surveyService.addQuestion(surveyService.getSurveyById(id), new QuestionEntity(questionDTO));
        return ResponseEntity.noContent().build();
    }



    @Operation(summary = "Delete question from survey",
            description = "Deletes existing question of survey",
            security = @SecurityRequirement(name = "Administrator"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Returns empty body if question was deleted"),
            @ApiResponse(responseCode = "401", description = "Not authenticated",
                    content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "Question not found",
                    content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @DeleteMapping("/questions/{questionId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> deleteQuestionFromSurvey(
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Question id")
            @PathVariable long questionId
    ) {
        surveyService.deleteQuestionById(questionId);
        return ResponseEntity.noContent().build();
    }



    @Operation(summary = "Delete survey",
            description = "Deletes existing survey",
            security = @SecurityRequirement(name = "Administrator"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Returns empty body if survey was deleted"),
            @ApiResponse(responseCode = "401", description = "Not authenticated",
                    content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "Survey not found",
                    content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> deleteSurvey(
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Survey id")
            @PathVariable long id
    ) {
        surveyService.deleteSurveyById(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/filled")
    @PreAuthorize("hasAuthority('admin')")
    public BaseResponse<HashMap<String, Object>> getFilledSurveys(
            @RequestParam(name="count") Optional<Integer> optionalCount,
            @RequestParam(name="offset") Optional<Integer> optionalOffset
    ) {
        int count = optionalCount.orElse(5);
        int offset = optionalOffset.orElse(0);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", surveyService.getTotalFilledSurveysCount());
        map.put("filledSurveys", surveyService.getFilledSurveys(offset, count).stream().map(FilledSurveyEntity::toModel).collect(Collectors.toList()));
        return new SuccessResponse<>(map);
    }

    @GetMapping("/filled/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public BaseResponse<?> getFilledSurvey(@PathVariable long id) {
        return new SuccessResponse<>(surveyService.getFilledSurveyById(id).toModel());
    }

    @PostMapping(value="/filled", consumes = { "application/json" })
    public BaseResponse<?> createFilledSurvey(@Valid @RequestBody FilledSurveyDTO filledSurveyDTO) {
        return new SuccessResponse<>(surveyService.createFilledSurvey(new FilledSurveyEntity(filledSurveyDTO)).toModel());
    }
}
