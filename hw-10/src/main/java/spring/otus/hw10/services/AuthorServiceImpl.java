package spring.otus.hw10.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.otus.hw10.dto.AuthorCreateDto;
import spring.otus.hw10.dto.AuthorDto;
import spring.otus.hw10.exceptions.NotFoundException;
import spring.otus.hw10.mapper.AuthorMapper;
import spring.otus.hw10.repositories.AuthorRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorMapper authorMapper;

    private final AuthorRepository authorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> findAll() {
        return authorRepository.findAll().stream().map(authorMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDto findById(long id) {
        return authorMapper.toDto(authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author with id %d not found".formatted(id))
        ));
    }

    @Override
    @Transactional
    public AuthorDto create(AuthorCreateDto author) {
        return authorMapper.toDto(authorRepository.save(authorMapper.toModel(author)));
    }

    @Override
    @Transactional
    public AuthorDto update(AuthorDto author) {
        authorRepository.findById(author.id())
                .orElseThrow(() -> new NotFoundException("Author with id %d not found".formatted(author.id())));
        return authorMapper.toDto(authorRepository.save(authorMapper.toModel(author)));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
       authorRepository.deleteById(id);
    }
}
