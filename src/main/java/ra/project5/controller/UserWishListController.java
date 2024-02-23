package ra.project5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project5.advice.CustomException;
import ra.project5.model.dto.request.WishListRequest;
import ra.project5.model.dto.response.WishListResponse;
import ra.project5.service.WishListService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/wishList")
public class UserWishListController {
    @Autowired
    private WishListService wishListService;

    //WISHLIST

    @PostMapping
    public ResponseEntity<WishListResponse> save(@RequestBody WishListRequest wishListRequest) throws CustomException {
        return new ResponseEntity<>(wishListService.save(wishListRequest), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<WishListResponse>> findAllWishList(){
        return new ResponseEntity<>(wishListService.findAll(),HttpStatus.OK);
    }
    @DeleteMapping("/{wishListId}")
    public ResponseEntity<?> deleteWishList(@PathVariable Long wishListId) throws CustomException {
        if (wishListService.delete(wishListId)){
            return new ResponseEntity<>("Delete successful",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("WishListId not found",HttpStatus.NOT_FOUND);
        }
    }

//    Integer getUserId(){
//        String fileName = "E:\\IT\\STRING BOOK\\project5\\log.txt";
//        String line = null;
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(fileName));
//            line = br.readLine();
//            System.out.println(line);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        if (line==null){
//            return 0;
//        }
//        return Integer.parseInt(line);
//    }

}
