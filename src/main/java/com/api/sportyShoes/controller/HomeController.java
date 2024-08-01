package com.api.sportyShoes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.api.sportyShoes.exceptionHandler.BusinessException;
import com.api.sportyShoes.model.PurchaseReport;
import com.api.sportyShoes.model.Shoe;
import com.api.sportyShoes.repository.PurchaseReportRepository;
import com.api.sportyShoes.repository.ShoesRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private ShoesRepository shoesRepo;

    @Autowired
    private PurchaseReportRepository prRepo;

    @GetMapping
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @GetMapping("/shoes")
    public ModelAndView getAllShoes() {
        List<Shoe> shoes = shoesRepo.findAll();
        ModelAndView mav = new ModelAndView("shoes");
        mav.addObject("shoes", shoes);
        return mav;
    }

    @GetMapping("/shoes/new")
    public ModelAndView createShoeForm() {
        return new ModelAndView("create_shoe");
    }

    @PostMapping("/shoes")
    public ModelAndView createShoe(Shoe shoe, Model model) {
        try {
            int id = shoe.getId();
            if (shoesRepo.findById(id).isPresent()) {
                throw new BusinessException("Shoe already exists with id: " + id);
            }
            shoesRepo.save(shoe);
            model.addAttribute("successMessage", "Shoe created successfully");
        } catch (BusinessException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return new ModelAndView("create_shoe");
    }

    @GetMapping("/shoes/edit/{id}")
    public ModelAndView updateShoeForm(@PathVariable int id, Model model) throws BusinessException {
        Shoe shoe;
        try {
            shoe = shoesRepo.findById(id).orElseThrow(() -> new BusinessException("Shoe not found with Id: " + id));
        } catch (NoSuchElementException e) {
            throw new BusinessException("Shoe not found with Id: " + id);
        }
        model.addAttribute("shoe", shoe);
        return new ModelAndView("update_shoe");
    }

    @PostMapping("/shoes/update")
    public ModelAndView updateShoe(Shoe shoe, Model model) {
        shoesRepo.save(shoe);
        model.addAttribute("successMessage", "Shoe updated successfully");
        return new ModelAndView("update_shoe");
    }

    @GetMapping("/shoes/delete/{id}")
    public ModelAndView deleteShoeForm(@PathVariable int id, Model model) throws BusinessException {
        Shoe shoe;
        try {
            shoe = shoesRepo.findById(id).orElseThrow(() -> new BusinessException("Shoe not found with Id: " + id));
        } catch (NoSuchElementException e) {
            throw new BusinessException("Shoe not found with Id: " + id);
        }
        model.addAttribute("shoe", shoe);
        return new ModelAndView("delete_shoe");
    }

    @PostMapping("/shoes/delete")
    public String deleteShoeById(@RequestParam int id, Model model) {
        try {
            shoesRepo.deleteById(id);
            model.addAttribute("successMessage", "Successfully deleted shoe with id: " + id);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Invalid id: " + id);
        } catch (NoSuchElementException e) {
            model.addAttribute("errorMessage", "Shoe does not exist with id: " + id);
        }
        return "redirect:/shoes"; // Redirect to shoes page
    }

    @GetMapping("/purchaseReports")
    public ModelAndView getAllPurchaseReports() {
        List<PurchaseReport> purchaseReports = prRepo.findAll();
        ModelAndView mav = new ModelAndView("purchase_reports");
        mav.addObject("purchaseReports", purchaseReports);
        return mav;
    }

    @GetMapping("/purchaseReports/new")
    public ModelAndView createPurchaseReportForm() {
        return new ModelAndView("create_purchase_report");
    }

    @PostMapping("/purchaseReports")
    public ModelAndView createPurchaseReport(PurchaseReport pr, Model model) {
        try {
            int id = pr.getId();
            if (prRepo.findById(id).isPresent()) {
                throw new BusinessException("Purchase report already exists with id: " + id);
            }
            prRepo.save(pr);
            model.addAttribute("successMessage", "Purchase report created successfully");
        } catch (BusinessException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return new ModelAndView("create_purchase_report");
    }

    @GetMapping("/purchaseReports/edit/{id}")
    public ModelAndView updatePurchaseReportForm(@PathVariable int id, Model model) throws BusinessException {
        PurchaseReport pr;
        try {
            pr = prRepo.findById(id).orElseThrow(() -> new BusinessException("Purchase Report not found with Id: " + id));
        } catch (NoSuchElementException e) {
            throw new BusinessException("Purchase Report not found with Id: " + id);
        }
        model.addAttribute("purchaseReport", pr);
        return new ModelAndView("update_purchase_report");
    }

    @PostMapping("/purchaseReports/update")
    public ModelAndView updatePurchaseReport(PurchaseReport pr, Model model) {
        prRepo.save(pr);
        model.addAttribute("successMessage", "Purchase report updated successfully");
        return new ModelAndView("update_purchase_report");
    }

    @GetMapping("/purchaseReports/delete/{id}")
    public ModelAndView deletePurchaseReportForm(@PathVariable int id, Model model) throws BusinessException {
        PurchaseReport pr;
        try {
            pr = prRepo.findById(id).orElseThrow(() -> new BusinessException("Purchase Report not found with Id: " + id));
        } catch (NoSuchElementException e) {
            throw new BusinessException("Purchase Report not found with Id: " + id);
        }
        model.addAttribute("purchaseReport", pr);
        return new ModelAndView("delete_purchase_report");
    }

    @PostMapping("/purchaseReports/delete")
    public String deletePurchaseReportById(@RequestParam int id, Model model) {
        try {
            prRepo.deleteById(id);
            model.addAttribute("successMessage", "Successfully deleted purchase report with id: " + id);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "Invalid id: " + id);
        } catch (NoSuchElementException e) {
            model.addAttribute("errorMessage", "Purchase Report does not exist with id: " + id);
        }
        return "redirect:/purchaseReports"; // Redirect to purchaseReports page
    }
}

