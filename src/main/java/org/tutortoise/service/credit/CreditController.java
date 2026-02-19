package org.tutortoise.service.credit;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credits")
public class CreditController {

    private final CreditService creditService;

    private final CreditTransactionRepository transactionRepository;

    public CreditController(CreditService creditService, CreditTransactionRepository transactionRepository) {
        this.creditService = creditService;
        this.transactionRepository = transactionRepository;
    }


    @Operation(summary = "Parents buy credit", description = "1 credit = 1 hour of tutoring")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/buy")
    public ResponseEntity<CreditResponseDTO> buyCredits
            (@RequestBody CreditRequest creditRequest) {
        CreditResponseDTO response =  creditService.buyCredits(creditRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Parents can check their current remaining credit balance", description = "Parents cannot schedule sessions if they do not have enough credits")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Show remaining credit balance"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/balance/{parentId}")
    public ResponseEntity<Double> getBalance(@PathVariable int parentId){
        return ResponseEntity.ok(creditService.getBalance(parentId));
    }

    @Operation(summary = "Parents transaction history", description = "Parents can check their transaction history to see when they bought credits and how many credits they have used for sessions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Show successful transaction history"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/history/{parentId}")
    public ResponseEntity<List<CreditHistoryDTO>> getHistory(@PathVariable int parentId){
        return ResponseEntity.ok(creditService.getHistory(parentId));
    }


}
