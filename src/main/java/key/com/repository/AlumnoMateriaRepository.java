package key.com.repository;

import key.com.entity.AlumnoMateria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlumnoMateriaRepository extends JpaRepository<AlumnoMateria, Long> {
    List<AlumnoMateria> findByAlumnoId(Long alumnoId);

    @Query("SELECT am FROM AlumnoMateria am WHERE am.materia.id = :materiaId AND am.alumno.direccion = :email")
    Optional<AlumnoMateria> findByMateriaIdAndAlumnoEmail(@Param("materiaId") Long materiaId, @Param("email") String email);
}
