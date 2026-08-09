package com.example.paymentservice.controller;

import com.example.paymentservice.enums.Status;
import com.example.paymentservice.mapper.TransferMapper;
import com.example.paymentservice.model.TransferCreationModel;
import com.example.paymentservice.model.TransferModel;
import com.example.paymentservice.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.paymentservice.resource.TransferCreationResource;
import com.example.paymentservice.resource.TransferResource;

@Slf4j
@RestController
@RequestMapping("/trasnfer")
public class TransferController {

    private final TransferMapper transferMapper;
    private final TransferService transferService;

    public TransferController(TransferMapper transferMapper , TransferService transferService) {
        this.transferMapper = transferMapper;
        this.transferService = transferService;
    }

    @Operation(description = "Creates a New Transfer , requires Idempotency key with Headers")
    @PostMapping("/")
    public ResponseEntity<TransferResource> createTransfer(@RequestBody @Valid TransferCreationResource transferCreationResource ,
                                                           @RequestHeader("x-Idemptoency-key") Long key) {

        log.info("Create Transfer Endpoint Invoked");
        TransferCreationModel transferCreationModel = transferMapper.toTransferCreationModel(transferCreationResource);

        TransferModel createdTransferModel = transferService.createTransfer(transferCreationModel , key);

        return ResponseEntity.ok(transferMapper.toTransferResource(createdTransferModel));
    }

    @Operation(description = "Get Transfer Status")
    @GetMapping("/status")
    public ResponseEntity<Status> getTransferStatus(Long id) {
        return ResponseEntity.ok(transferService.getTransferStatus(id));
    }

    @Operation(description = "Get Transfer Details")
    @GetMapping("/details")
    public ResponseEntity<TransferResource> getTransferDetails(Long id) {

        TransferModel transferModel = transferService.getTransferDetails(id);
        return ResponseEntity.ok(transferMapper.toTransferResource(transferModel));
    }


}
