package com.example.football.service.impl;


import com.example.football.models.dto.ImportTownDto;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


//ToDo - Implement all methods
@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public TownServiceImpl(TownRepository townRepository) {
        this.townRepository = townRepository;
        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder().create();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        String path = "E:\\Programs\\SpringAngularProject\\01 Java DB Spring Data Exam - 01 August 2021\\src\\main\\resources\\files\\json\\towns.json";

        Path pathToTownFile = Path.of(path);
        return String.join("\n",Files.readAllLines(pathToTownFile));
    }

    @Override
    public String importTowns() throws IOException {
        String jsonData = this.readTownsFileContent();

        ImportTownDto[] importTownDtoList = this.gson.fromJson(jsonData, ImportTownDto[].class);
        List<String> result = new ArrayList<>();
        for(ImportTownDto importTownDto: importTownDtoList){
            Set<ConstraintViolation<ImportTownDto>> validationErrors = validator.validate(importTownDto);

            if(validationErrors.isEmpty()){
                Optional<Town> byName = this.townRepository.findByName(importTownDto.getName());
                if(byName.isEmpty()){

                    Town town = modelMapper.map(importTownDto, Town.class);

                    this.townRepository.save(town);
                    result.add(String.format("Successfully imported Town - %s - %d",town.getName(),town.getPopulation()));
                }else{
                    result.add("The town is already exists");
                }

            }else{
                result.add("There are errors");
            }
        }
        return String.join("\n",result);
    }

    @Override
    public Optional<Town> getTownByName(String townName) {
        return Optional.empty();
    }
}
