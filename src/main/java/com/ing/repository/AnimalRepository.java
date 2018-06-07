package com.ing.repository;

import com.ing.model.entity.Animal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

	/**
	 * Sums up WOOL fields in animal table. returns 0 if no record present
	 * @return total wool present in flock
	 */
	@Query(value = "select coalesce(sum(WOOL),0) from ANIMAL", nativeQuery = true)
	int findTotalWool();

	/**
	 * Sums up MILK fields in animal table. returns 0 if no record present
	 * @return total milk available in flock
	 */
	@Query(value = "select coalesce(sum(MILK),0) from ANIMAL", nativeQuery = true)
	int findTotalMilk();

}