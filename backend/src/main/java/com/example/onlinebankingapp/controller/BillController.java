package com.example.onlinebankingapp.controller;

import com.example.onlinebankingapp.model.entities.Bill;
import com.example.onlinebankingapp.model.repositories.BillRepository;
import com.example.onlinebankingapp.model.requests.BillRequest;
import com.example.onlinebankingapp.service.BillService;
import com.example.onlinebankingapp.view.converter.BillDTOConverter;
import com.example.onlinebankingapp.view.dto.BillDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bills")
public class BillController {

    private final BillService billService;
    private final BillDTOConverter billDTOConverter;

    @Autowired
    public BillController(BillService billService,
                          BillDTOConverter billDTOConverter) {
        this.billService = billService;
        this.billDTOConverter = billDTOConverter;
    }

    @GetMapping
    public ResponseEntity<List<BillDTO>> getAll() {
        List<BillDTO> billDTOS = billService.getAll()
                .stream().map(billDTOConverter::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(billDTOS, HttpStatus.OK);
    }

    @GetMapping("/{bill_id}")
    public ResponseEntity<BillDTO> getBillById(@PathVariable("bill_id") Long billId) {
        BillDTO billDTO = billDTOConverter.convertToDto(billService.getBillById(billId));
        return new ResponseEntity<>(billDTO, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<BillDTO> insertBill(@RequestBody BillRequest billRequest) {
        BillDTO billDTO = billService.insertBill(billRequest);
        return new ResponseEntity<>(billDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{bill_id}")
    public ResponseEntity<BillDTO> updateBill(@PathVariable("bill_id") Long billId, @RequestBody BillRequest billRequest) {
        BillDTO billDTO = billService.updateBill(billId, billRequest);
        return new ResponseEntity<>(billDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{bill_id}")
    public ResponseEntity<Void> deleteBill(@PathVariable("bill_id") Long billId) {
        billService.deleteBill(billId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/pay/{bill_id}")
    public ResponseEntity<BillDTO> payBill(@PathVariable("bill_id") Long billId) {
        BillDTO billDTO = billService.payBill(billId);
        return new ResponseEntity<>(billDTO, HttpStatus.OK);
    }
}
