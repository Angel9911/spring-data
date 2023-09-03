package com.example.football.service.impl;

import com.example.football.models.dto.ImportTeamDto;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final Validator validator;
    private final TownRepository townRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, TownRepository townRepository) {
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
        this.gson = new GsonBuilder().create();
        this.modelMapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        String path = "E:\\Programs\\SpringAngularProject\\01 Java DB Spring Data Exam - 01 August 2021\\src\\main\\resources\\files\\json\\teams.json";

        Path pathToTownFile = Path.of(path);
        return String.join("\n", Files.readAllLines(pathToTownFile));
    }

    @Override
    public String importTeams() throws IOException {
        String json = this.readTeamsFileContent();

        ImportTeamDto[] importTeamDtos = this.gson.fromJson(json, ImportTeamDto[].class);

        return Arrays.stream(importTeamDtos)
                .map(this::importTeam)
                .collect(Collectors.joining("\n"));
    }

    private String importTeam(ImportTeamDto importTeamDto){
        List<String> result = new ArrayList<>();
        Set<ConstraintViolation<ImportTeamDto>> validate = validator.validate(importTeamDto);
        if(validate.isEmpty()){

            Optional<Team> byName = this.teamRepository.findByName(importTeamDto.getName());

            if(byName.isEmpty()){
                Team team = modelMapper.map(importTeamDto, Team.class);

                Optional<Town> town = this.townRepository.findByName(importTeamDto.getTownName());

                if(town.isPresent()){

                    team.setTown(town.get());
                    this.teamRepository.save(team);

                    result.add(String.format("Successfully imported Team -  %s and %d",team.getName(),team.getFanBase()));
                }else{
                    result.add("Town isn't exists");
                }

            }else{
                result.add("Team is already exists");
            }
        }else{
            result.add("There are some errors");
        }
        return String.join("\n",result);
    }
    @Override
    public Optional<Team> getTeamByName(String name) {
        return Optional.empty();
    }
}
