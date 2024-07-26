package com.movie.rock.movie.controller;

import com.movie.rock.member.service.CustomUserDetails;
import com.movie.rock.movie.data.request.MovieFavorRequestDTO;
import com.movie.rock.movie.data.response.MovieFavorResponseDTO;
import com.movie.rock.movie.service.MovieFavorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/movies/detail")
@RequiredArgsConstructor
public class MovieFavorController {

    private final MovieFavorService movieFavorService;

    private Long getMemNumFromAuthentication(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetails) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            return customUserDetails.getMemNum();
        } else {
            throw new IllegalStateException("Unexpected principal type in Authentication");
        }
    }

    @PostMapping("/{movieId}/favorites")
    public ResponseEntity<MovieFavorResponseDTO> addMovieFavor(@RequestBody MovieFavorRequestDTO movieFavorRequestDTO, Authentication authentication) {
        Long memNum = getMemNumFromAuthentication(authentication);
        MovieFavorResponseDTO responseDTO = movieFavorService.addFavorites(memNum, movieFavorRequestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{movieId}/favorites")
    public ResponseEntity<MovieFavorResponseDTO> removeFavorite(@PathVariable("movieId") Long movieId, Authentication authentication) {
        Long memNum = getMemNumFromAuthentication(authentication);
        MovieFavorResponseDTO responseDTO = movieFavorService.removeFavorites(memNum, movieId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{movieId}/favorites")
    public ResponseEntity<MovieFavorResponseDTO> getFavoriteStatus(@PathVariable("movieId") Long movieId, Authentication authentication) {
        Long memNum = getMemNumFromAuthentication(authentication);
        MovieFavorResponseDTO responseDTO = movieFavorService.getFavoritesStatus(memNum, movieId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<MovieFavorResponseDTO>> getFavoriteMovies(Authentication authentication) {
        Long memNum = getMemNumFromAuthentication(authentication);
        List<MovieFavorResponseDTO> favorites = movieFavorService.getFavoritesMovies(memNum);
        return ResponseEntity.ok(favorites);
    }
}
