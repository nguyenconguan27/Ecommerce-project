package com.example.ecommerceproject.apis;

import com.example.ecommerceproject.dto.ProductDTO;
import com.example.ecommerceproject.payload.response.ProductListResponse;
import com.example.ecommerceproject.payload.response.ResponseObject;
import com.example.ecommerceproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductApis {
    @Autowired
    private ProductService productService;

    @GetMapping("/detail")
    public ResponseEntity<ResponseObject> getOneproduct(@RequestParam String id) throws Exception {
        ProductDTO productDTO = productService.getOne(Integer.parseInt(id));
        return ResponseEntity.ok().body(ResponseObject.builder()
                .message("Get product successfully")
                .data(productDTO)
                .httpStatus(HttpStatus.OK)
                .build());
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseObject> updateproduct(@RequestBody ProductDTO productDTO) throws Exception {
        if(productDTO.getId() == null || !productService.existingProduct(productDTO.getId())) {
            throw new Exception("Update product failed");
        }
        ProductDTO newproductDTO = productService.updateOrAdd(productDTO);
        return ResponseEntity.ok().body(ResponseObject.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("Update product successfully")
                        .data(newproductDTO)
                .build());
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addproduct(@RequestBody ProductDTO productDTO) throws Exception {
        ProductDTO newproductDTO = productService.updateOrAdd(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseObject.builder()
                        .data(newproductDTO)
                        .message("Add new product successfully")
                        .httpStatus(HttpStatus.CREATED)
                        .build()
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseObject> deleteproduct(@RequestParam String id) throws Exception {
        productService.delete(Integer.parseInt(id));
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseObject.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("Delete product successfully")
                        .build()
        );
    }

    @GetMapping("/")
    public ResponseEntity<ResponseObject> getByTitle(@RequestParam(value = "page_num", required=false,defaultValue = "1") String pageNum,
                                         @RequestParam(value = "page_size", required=false,defaultValue = "10") String pageSize,
                                         @RequestParam(value = "sort_field", required=false,defaultValue = "") String sortField,
                                         @RequestParam(value = "sort_dir", required=false,defaultValue = "") String sortDir,
                                         @RequestParam(value = "title", required = false, defaultValue = "") String title,
                                         @RequestParam(value = "category_id", required = false, defaultValue = "0") String categoryId,
                                         @RequestParam(value = "min_price", required=false,defaultValue = "0") String priceMin,
                                         @RequestParam(value = "max_price", required=false,defaultValue = "1000000000") String priceMax) throws Exception {
        ProductListResponse productDTOList = productService.getByOptions(Integer.parseInt(pageNum),
                Integer.parseInt(pageSize),
                sortField,
                sortDir,
                title,
                Integer.parseInt(categoryId),
                Integer.parseInt(priceMin),
                Integer.parseInt(priceMax));
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message("Get product list successfully")
                        .data(productDTOList)
                        .httpStatus(HttpStatus.OK)
                        .build()
        );
    }
}
