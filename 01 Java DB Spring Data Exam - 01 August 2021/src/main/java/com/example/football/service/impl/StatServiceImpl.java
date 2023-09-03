package com.example.football.service.impl;

import com.example.football.models.dto.ImportStatDto;
import com.example.football.models.dto.ImportStatRootDto;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class StatServiceImpl implements StatService {
    private final Validator validation;
    private final ModelMapper modelMapper;
    private Path path = Path.of("E:\\Programs\\SpringAngularProject\\01 Java DB Spring Data Exam - 01 August 2021\\src\\main\\resources\\files\\xml\\stats.xml");
    private final StatRepository statRepository;
    private final Unmarshaller unmarshaller;

    @Autowired
    public StatServiceImpl(StatRepository statRepository) throws JAXBException {
        this.statRepository = statRepository;
        this.validation = Validation.buildDefaultValidatorFactory().getValidator();
        this.modelMapper = new ModelMapper();

        JAXBContext context = JAXBContext.newInstance(ImportStatRootDto.class);
        this.unmarshaller = context.createUnmarshaller();
    }

    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {

        return String.join("\n", Files.readAllLines(path));
    }

    @Override
    public String importStats() throws JAXBException, IOException {

        ImportStatRootDto importStatRootDto = (ImportStatRootDto) this.unmarshaller.unmarshal(new FileReader(path.toAbsolutePath().toString()));

        String result = importStatRootDto.getStats()
                .stream()
                .map(this::importStatDto)
                .collect(Collectors.joining("\n"));
        return result;
    }

    private String importStatDto(ImportStatDto importStatDto){
        Set<ConstraintViolation<ImportStatDto>> validate = this.validation.validate(importStatDto);

        if(!validate.isEmpty()){
            return "Invalid stats";
        }

        Optional<Stat> stat = this.statRepository.findByShootingAndPassingAndEndurance(importStatDto.getShooting(), importStatDto.getPassing(), importStatDto.getEndurance());

        if(stat.isPresent()){
            return "Invalid stat";
        }

        Stat statMap = this.modelMapper.map(importStatDto, Stat.class);

        this.statRepository.save(statMap);

        return "The stat is: "+stat;
    }

    @Override
    public Optional<Stat> getStatById(long id) {
        return Optional.empty();
    }
}
