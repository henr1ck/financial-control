package br.edu.ifpi.financialcontrol.repository;

import br.edu.ifpi.financialcontrol.domain.Flow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FlowRepository extends JpaRepository<Flow, Long>, JpaSpecificationExecutor<Flow> {

    Optional<Flow> findByCode(String code);
}
