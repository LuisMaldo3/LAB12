package com.tecsup.petclinic.repositories;
import com.tecsup.petclinic.entities.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@RepositoryRestResource(exported = false)
public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {}
