package com.api.sportyShoes.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.api.sportyShoes.exceptionHandler.BusinessException;
import com.api.sportyShoes.model.PurchaseReport;
import com.api.sportyShoes.model.Shoe;
import com.api.sportyShoes.repository.PurchaseReportRepository;
import com.api.sportyShoes.repository.ShoesRepository;

@Service
public class ShoeServiceImpl implements ShoeService {

    @Autowired
    private ShoesRepository shoesRepo;

    @Autowired
    private PurchaseReportRepository prRepo;

    @Override
    public Shoe createShoe(Shoe shoe) throws BusinessException {
        int id = shoe.getId();
        Shoe oldShoe = null;
        try {
            oldShoe = shoesRepo.findById(id).get();
        } catch (NoSuchElementException e) {
            // Ignore if not found
        }
        if (oldShoe != null) {
            throw new BusinessException("Shoe already exists with id: " + id);
        }
        return shoesRepo.save(shoe);
    }

    @Override
    public Shoe getShoeById(int id) throws BusinessException {
        Shoe shoe = null;
        try {
            if (id <= 0) {
                throw new BusinessException("Shoe Id can not be negative or zero");
            }
            shoe = shoesRepo.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new BusinessException("Shoe not found with Id: " + id);
        }
        return shoe;
    }

    @Override
    public Shoe updateShoe(Shoe shoe) {
        return shoesRepo.save(shoe);
    }

    @Override
    public void deleteShoeById(int id) throws BusinessException {
        try {
            shoesRepo.deleteById(id);
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Invalid id: " + id);
        } catch (EmptyResultDataAccessException e) {
            throw new BusinessException("Shoe does not exist with id: " + id);
        }
    }

    @Override
    public List<Shoe> getAllShoes() {
        return shoesRepo.findAll();
    }

    @Override
    public PurchaseReport createPurchaseReport(PurchaseReport pr) throws BusinessException {
        int id = pr.getId();
        PurchaseReport oldPr = null;
        try {
            oldPr = prRepo.findById(id).get();
        } catch (NoSuchElementException e) {
            // Ignore if not found
        }
        if (oldPr != null) {
            throw new BusinessException("Purchase report already exists with id: " + id);
        }
        return prRepo.save(pr);
    }

    @Override
    public PurchaseReport getPurchaseReportById(int id) throws BusinessException {
        PurchaseReport pr = null;
        try {
            if (id <= 0) {
                throw new BusinessException("Purchase Report Id can not be negative or zero");
            }
            pr = prRepo.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new BusinessException("Purchase Report not found with Id: " + id);
        }
        return pr;
    }

    @Override
    public PurchaseReport updatePurchaseReport(PurchaseReport pr) {
        return prRepo.save(pr);
    }

    @Override
    public void deletePurchaseReportById(int id) throws BusinessException {
        try {
            prRepo.deleteById(id);
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Invalid id: " + id);
        } catch (EmptyResultDataAccessException e) {
            throw new BusinessException("Purchase Report does not exist with Id: " + id);
        }
    }

    @Override
    public List<PurchaseReport> getAllPurchaseReports() {
        return prRepo.findAll();
    }

    @Override
    public List<PurchaseReport> getAllPurchaseReportsByCategory(String category) {
        return prRepo.findByCategory(category);
    }

    @Override
    public List<PurchaseReport> getAllPurchaseReportsByDop(Date dop) {
        return prRepo.findByDop(dop);
    }
}
