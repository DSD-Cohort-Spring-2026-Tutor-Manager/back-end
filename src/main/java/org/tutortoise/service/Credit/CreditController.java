package org.tutortoise.service.Credit;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credits")
public class CreditController {

    private final CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buyCredits
            (@RequestParam int parentId,
             @RequestParam int credits,
             @RequestParam double amount) {
        creditService.buyCredits(parentId,credits, amount);
        return ResponseEntity.ok("Credit purchased successfully");
    }

    @GetMapping("/balance/{parentId}")
    public ResponseEntity<Double> getBalance(@PathVariable int parentId){
        return ResponseEntity.ok(creditService.getBalance(parentId));
    }

    @GetMapping("/history/{parentId}")
    public ResponseEntity<List<CreditTransaction>> getHistory(@PathVariable int parentId){
        return ResponseEntity.ok(creditService.getHistory(parentId));
    }
}
