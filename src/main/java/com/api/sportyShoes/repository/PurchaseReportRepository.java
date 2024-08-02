package com.api.sportyShoes.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.sportyShoes.model.PurchaseReport;

@Repository
public interface PurchaseReportRepository extends JpaRepository<PurchaseReport, Integer> {
    List<PurchaseReport> findByPurchaseDate(Date purchaseDate);
    List<PurchaseReport> findByCategory(String category);
    List<PurchaseReport> findByCategoryContainingOrPurchasedByContaining(String category, String purchasedBy);
    PurchaseReport findById(int id);
}
