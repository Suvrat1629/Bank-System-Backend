package com.example.Bank_System.controller;

import com.example.Bank_System.model.Card;
import com.example.Bank_System.request.BlockCardRequest;
import com.example.Bank_System.request.CardRequest;
import com.example.Bank_System.request.ChangePinRequest;
import com.example.Bank_System.request.SetLimitRequest;
import com.example.Bank_System.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    // Endpoint to request a new card
    @PostMapping("/request")
    public ResponseEntity<String> requestNewCard(@RequestBody CardRequest cardRequest) {
        String response = cardService.requestNewCard(
                cardRequest.getAccountId(),
                cardRequest.getCardType(),
                cardRequest.getDailyLimit(),
                cardRequest.getPin()
        );
        return ResponseEntity.ok(response);
    }

    // Endpoint to block a lost card
    @PostMapping("/block")
    public ResponseEntity<String> blockLostCard(@RequestBody BlockCardRequest blockCardRequest) {
        String response = cardService.blockLostCard(blockCardRequest.getCardNumber());
        return ResponseEntity.ok(response);
    }

    // Endpoint to set card limits
    @PostMapping("/set-limit")
    public ResponseEntity<String> setCardLimit(@RequestBody SetLimitRequest setLimitRequest) {
        String response = cardService.setCardLimits(
                setLimitRequest.getCardNumber(),
                setLimitRequest.getDailyLimit()
        );
        return ResponseEntity.ok(response);
    }

    // Endpoint to view card transactions
    @GetMapping("/transactions/{accountId}")
    public ResponseEntity<List<CardRequest>> viewCardTransactions(@PathVariable Long accountId) throws Exception {
            // Fetch card transactions from the service layer
            List<CardRequest> cardRequests = cardService.viewCardTransactions(accountId);

            // Return the response with a 200 OK status and the list of CardRequest DTOs
            return ResponseEntity.ok(cardRequests);
        }

    // Endpoint to change card PIN
    @PostMapping("/change-pin")
    public ResponseEntity<String> changeCardPin(@RequestBody ChangePinRequest changePinRequest) {
        String response = cardService.changePin(
                changePinRequest.getCardNumber(),
                changePinRequest.getCurrentPin(),
                changePinRequest.getNewPin()
        );
        return ResponseEntity.ok(response);
    }
}
