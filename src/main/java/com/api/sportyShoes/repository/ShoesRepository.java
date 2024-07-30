package com.api.sportyShoes.repository;


import com.api.sportyShoes.model.Shoe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoesRepository extends JpaRepository<Shoe, Integer>{
	
}
