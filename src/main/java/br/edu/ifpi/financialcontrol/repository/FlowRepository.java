package br.edu.ifpi.financialcontrol.repository;

import br.edu.ifpi.financialcontrol.domain.Flow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowRepository extends JpaRepository<Flow, Long> {

}
