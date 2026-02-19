package org.tutortoise.service.credit;

import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tutortoise.service.parent.Parent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/credits")
public class CreditController {

    private final CreditService creditService;

    private final CreditTransactionRepository transactionRepository;

    public CreditController(CreditService creditService, CreditTransactionRepository transactionRepository) {
        this.creditService = creditService;
        this.transactionRepository = transactionRepository;
    }


    @PostMapping("/buy")
    public ResponseEntity<CreditResponseDTO> buyCredits
            (@RequestBody CreditRequest creditRequest) {
        CreditResponseDTO response =  creditService.buyCredits(creditRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/balance/{parentId}")
    public ResponseEntity<Double> getBalance(@PathVariable int parentId){
        return ResponseEntity.ok(creditService.getBalance(parentId));
    }

    @GetMapping("/history/{parentId}")
    public ResponseEntity<List<CreditHistoryDTO>> getHistory(@PathVariable int parentId){
        return ResponseEntity.ok(creditService.getHistory(parentId));
    }


}
