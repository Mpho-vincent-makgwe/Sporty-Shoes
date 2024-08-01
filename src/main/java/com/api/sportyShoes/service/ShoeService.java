package com.api.sportyShoes.service;

import java.util.Date;
import java.util.List;
import com.api.sportyShoes.exceptionHandler.BusinessException;
import com.api.sportyShoes.model.PurchaseReport;
import com.api.sportyShoes.model.Shoe;

public interface ShoeService {
    Shoe createShoe(Shoe shoe) throws BusinessException;
    Shoe getShoeById(int id) throws BusinessException;
    Shoe updateShoe(Shoe shoe);
    void deleteShoeById(int id) throws BusinessException;
    List<Shoe> getAllShoes();

    PurchaseReport createPurchaseReport(PurchaseReport pr) throws BusinessException;
    PurchaseReport getPurchaseReportById(int id) throws BusinessException;
    PurchaseReport updatePurchaseReport(PurchaseReport pr);
    void deletePurchaseReportById(int id) throws BusinessException;
    List<PurchaseReport> getAllPurchaseReports();
    List<PurchaseReport> getAllPurchaseReportsByCategory(String category);
    List<PurchaseReport> getAllPurchaseReportsByDop(Date purchaseDate);

    List<Shoe> searchShoesByKeyword(String keyword);
    List<PurchaseReport> searchPurchaseReportsByKeyword(String keyword);
}
