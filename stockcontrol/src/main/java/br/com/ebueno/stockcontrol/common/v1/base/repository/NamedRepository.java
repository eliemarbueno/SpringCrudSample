package br.com.ebueno.stockcontrol.common.v1.base.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface NamedRepository<E, ID> extends BaseRepository<E, ID> {
	Page<E> findByName(String name, Pageable pageable);
    List<E> listByName(String name);   
}
