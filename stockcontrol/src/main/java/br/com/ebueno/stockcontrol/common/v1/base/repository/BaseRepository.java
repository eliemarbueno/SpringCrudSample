package br.com.ebueno.stockcontrol.common.v1.base.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<E, ID> extends JpaRepository<E, ID> {
	Page<E> findAll(Pageable pageable);
    List<E> findAll();   
}
