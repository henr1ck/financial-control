package br.edu.ifpi.financialcontrol.repository;

import br.edu.ifpi.financialcontrol.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {

}
