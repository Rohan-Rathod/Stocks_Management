package com.example.stocks.controller;

import com.example.stocks.model.Stock;
import com.example.stocks.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockRepository stockRepository;

    @GetMapping
    public String listStocks(Model model) {
        model.addAttribute("stocks", stockRepository.findAll());
        return "stock-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("stock", new Stock());
        return "add-stock";
    }

    @PostMapping("/add")
    public String addStock(@ModelAttribute Stock stock) {
        stockRepository.save(stock);
        return "redirect:/stocks";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Stock stock = stockRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid stock Id:" + id));
        model.addAttribute("stock", stock);
        return "update-stock";
    }

    @PostMapping("/update/{id}")
    public String updateStock(@PathVariable Long id, @ModelAttribute Stock stock) {
        stock.setId(id);
        stockRepository.save(stock);
        return "redirect:/stocks";
    }

    @GetMapping("/delete/{id}")
    public String deleteStock(@PathVariable Long id) {
        stockRepository.deleteById(id);
        return "redirect:/stocks";
    }
}
