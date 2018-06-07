package com.ing.repository;

import com.ing.model.entity.CustomerOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {

	/**
	 * Sums up WOOL fields in customer_order table. returns 0 if no record present
	 * @return total wool order present
	 */
	@Query(value = "select coalesce(sum(WOOL),0) from CUSTOMER_ORDER", nativeQuery = true)
	int findTotalWoolOrder();
	/**
	 * Sums up MILK fields in customer_order table. returns 0 if no record present
	 * @return total milk order present
	 */
	@Query(value = "select coalesce(sum(MILK),0) from CUSTOMER_ORDER", nativeQuery = true)
	int findTotalMilkOrder();

}