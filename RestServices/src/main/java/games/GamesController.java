package games;

import model.Game;
import model.GameDTO;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repos.GameRepo;
import repos.UserRepo;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/games/")
public class GamesController {
    private static final String template="TemplatePaper";

    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private UserRepo userRepo;

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public Iterable<GameDTO> getGames(@PathVariable String username){
        List<Game> games=gameRepo.getAll();
        List<User> users=new ArrayList<>();
        for(User u:userRepo.getAll()){
            users.add(u);
        }
        List<GameDTO> rez=new ArrayList<>();
        for(Game g: games){
            List<String> userspergame=g.getUsers();
            if(userspergame.contains(username)) {
                //rez.add(g);
                List<Integer> positions = new ArrayList<>();
                for (String player : g.getUsers()) {
                        User p=userRepo.findOneByUsername(player);
                        positions.add(p.getPlanePoz());
                }
                GameDTO gameDTO = new GameDTO(g, positions);
                rez.add(gameDTO);
            }
        }
        return rez;
    }

}

