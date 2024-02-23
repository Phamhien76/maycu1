package ra.project5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ra.project5.model.dto.response.CategoriesSales;
import ra.project5.model.dto.response.ProductBestSellerProducts;
import ra.project5.service.OrderService;
import ra.project5.serviceImp.CommonService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/dash-board")

public class AdminDashBoardController {
    @Autowired
    private CommonService commonService;

    @Autowired
    OrderService orderService;
    @GetMapping("/sales")
    public ResponseEntity<?> sealTotalPrice(
            @RequestParam String to,
            @RequestParam String from) throws ParseException {
        Date dateFrom = new  SimpleDateFormat("yyyy-MM-dd").parse(from);
        Date dateTo = new SimpleDateFormat("yyyy-MM-dd").parse(to);
        java.sql.Date dateFromSQL=commonService.convertToSqlDate(dateFrom);
        java.sql.Date dateToSQL=commonService.convertToSqlDate(dateTo);
        return new ResponseEntity<>(orderService.salesTotalPrice(dateFromSQL, dateToSQL), HttpStatus.OK);
    }

    @GetMapping("/sales/bestSellerProducts")
    public ResponseEntity<List<ProductBestSellerProducts>>getBestSellerProductsOfMonth(){
        return new ResponseEntity<>(orderService.getBestSellerProductsOfMonth(), HttpStatus.OK) ;

    }

    @GetMapping("/sales/categories")
    public ResponseEntity<List<CategoriesSales>>getCategoriesSeller(){
        return new ResponseEntity<>(orderService.getCategoriesSale(), HttpStatus.OK) ;

    }




}
