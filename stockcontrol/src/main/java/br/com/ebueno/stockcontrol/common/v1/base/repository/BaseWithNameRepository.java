package br.com.ebueno.stockcontrol.common.v1.base.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseWithNameRepository<E, ID> extends BaseRepository<E, ID> {
	Page<E> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
    List<E> findAllByNameContainingIgnoreCase(String name);
    Page<E> findAllByName(String name, Pageable pageable);
    List<E> findAllByName(String name);
    Optional<E> findByName(String name);
}
