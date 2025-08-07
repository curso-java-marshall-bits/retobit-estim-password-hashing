package dev.marshallBits.estim.services;

import dev.marshallBits.estim.models.Game;
import dev.marshallBits.estim.models.ReviewType;
import dev.marshallBits.estim.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public Game findById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + id));
    }

    @Override
    public List<Game> searchByName(String name) {
        return gameRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Game> findByReviewType(ReviewType reviewType) {
        return gameRepository.findByReviewType(reviewType);
    }

    @Override
    public List<Game> findByGenre(String genre) {
        return gameRepository.findByGenre(genre);
    }

    @Override
    public List<Game> findByDeveloper(String developer) {
        return gameRepository.findByDeveloper(developer);
    }

    @Override
    public List<Game> findAllOrderByMetacriticScore() {
        return gameRepository.findAllOrderByMetacriticScoreDesc();
    }

    @Override
    public Game createGame(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public Game updateGame(Long id, Game gameDetails) {
        Game game = findById(id);
        game.setName(gameDetails.getName());
        game.setReviewType(gameDetails.getReviewType());
        game.setDescription(gameDetails.getDescription());
        game.setDeveloper(gameDetails.getDeveloper());
        game.setPublisher(gameDetails.getPublisher());
        game.setReleaseDate(gameDetails.getReleaseDate());
        game.setPrice(gameDetails.getPrice());
        game.setGenre(gameDetails.getGenre());
        game.setMetacriticScore(gameDetails.getMetacriticScore());
        game.setSteamReviews(gameDetails.getSteamReviews());
        return gameRepository.save(game);
    }

    @Override
    public void deleteGame(Long id) {
        Game game = findById(id);
        gameRepository.delete(game);
    }
}
