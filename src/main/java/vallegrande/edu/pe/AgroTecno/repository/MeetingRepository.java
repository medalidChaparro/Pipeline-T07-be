package vallegrande.edu.pe.AgroTecno.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vallegrande.edu.pe.AgroTecno.model.Meeting;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {

    @Override
    @EntityGraph(attributePaths = {"client"})
    List<Meeting> findAll();

    @Override
    @EntityGraph(attributePaths = {"client"})
    Optional<Meeting> findById(Integer id);
}
