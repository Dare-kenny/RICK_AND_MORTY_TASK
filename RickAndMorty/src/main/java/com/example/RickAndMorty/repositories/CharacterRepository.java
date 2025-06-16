package com.example.RickAndMorty.repositories;

import com.example.RickAndMorty.schemas.RickAndMortyCharacters;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<RickAndMortyCharacters,Integer> {
}
