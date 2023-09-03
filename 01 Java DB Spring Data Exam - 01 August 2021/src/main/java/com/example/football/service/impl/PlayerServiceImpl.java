package com.example.football.service.impl;

import com.example.football.models.dto.ImportPlayerRootDto;
import com.example.football.models.dto.PlayerDto;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


//ToDo - Implement all methods
@Service
public class PlayerServiceImpl  implements PlayerService {
    private final PlayerRepository playerRepository;
    private final TypeMap<PlayerDto, Player> typeMap;
    private Path path = Path.of("E:\\Programs\\SpringAngularProject\\01 Java DB Spring Data Exam - 01 August 2021\\src\\main\\resources\\files\\xml\\players.xml");
    private final Validator validation;
    private final ModelMapper modelMapper;
    private final Unmarshaller unmarshaller;
    private final TeamRepository teamRepository;
    private final StatRepository statRepository;
    private final TownRepository townRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository, StatRepository statRepository, TownRepository townRepository) throws JAXBException {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.statRepository = statRepository;
        this.townRepository = townRepository;
        this.validation = Validation.buildDefaultValidatorFactory().getValidator();

        Converter<String, LocalDate> converter = s-> s.getSource() == null ? null : LocalDate.parse(s.getSource(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        this.modelMapper = new ModelMapper();

        this.typeMap = modelMapper.createTypeMap(PlayerDto.class,Player.class);
        this.typeMap.addMappings(map ->
                map.using(converter).map(PlayerDto::getBirthDate,Player::setBirthDate));

        this.modelMapper.addConverter(converter);

        JAXBContext context = JAXBContext.newInstance(ImportPlayerRootDto.class);
        this.unmarshaller = context.createUnmarshaller();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return String.join("\n", Files.readAllLines(path));
    }

    @Override
    public String importPlayers() throws JAXBException, IOException {
        ImportPlayerRootDto importPlayerRootDto = (ImportPlayerRootDto) this.unmarshaller.unmarshal(new FileReader(path.toAbsolutePath().toString()));

        String result = importPlayerRootDto.getPlayers()
                .stream()
                .map(this::importPlayerDto)
                .collect(Collectors.joining("\n"));

        return result;
    }

    private String importPlayerDto(PlayerDto playerDto){
        Set<ConstraintViolation<PlayerDto>> validate = validation.validate(playerDto);

        if(!validate.isEmpty()){
            return "Invalid playerdto";
        }

        Optional<Player> byEmail = this.playerRepository.findByEmail(playerDto.getEmail());

        if(byEmail.isPresent()){
            return "The player is already exists";
        }

        Optional<Team> team = this.teamRepository.findByName(playerDto.getTeam().getName());
        Optional<Stat> stat = this.statRepository.findById(playerDto.getStat().getId());
        Optional<Town> town = this.townRepository.findByName(playerDto.getTown().getName());

        Player player = this.typeMap.map(playerDto);

        player.setStat(stat.get());
        player.setTeam(team.get());
        player.setTown(town.get());

        this.playerRepository.save(player);

        return "Successfully imported player: "+player.getFirstName() + " " + player.getLastName() + " " + player.getPosition().toString();
    }

    @Override
    public String exportBestPlayers() {
        return null;
    }
}
