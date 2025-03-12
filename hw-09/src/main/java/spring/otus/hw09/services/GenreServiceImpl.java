package spring.otus.hw09.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.otus.hw09.dto.GenreDto;
import spring.otus.hw09.exceptions.NotFoundException;
import spring.otus.hw09.mapper.GenreMapper;
import spring.otus.hw09.repositories.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> findAll() {
        return genreRepository.findAll().stream().map(genreMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public GenreDto findById(long id) {
        return genreMapper.toDto(genreRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(id))
        ));
    }

    @Override
    @Transactional
    public GenreDto create(GenreDto genre) {
        if (genre.id() == null || genre.id() == 0) {
            throw new NotFoundException("id must be null");
        }
        return genreMapper.toDto(genreRepository.save(genreMapper.toModel(genre)));
    }

    @Override
    @Transactional
    public GenreDto update(GenreDto genre) {
        findById(genre.id()).id();
        return genreMapper.toDto(genreRepository.save(genreMapper.toModel(genre)));
    }


    @Override
    @Transactional
    public void deleteById(long id) {
        genreRepository.deleteById(id);
    }
}
