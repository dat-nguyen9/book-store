package com.book_store.service;

import com.book_store.entity.Category;
import com.book_store.entity.Product;
import com.book_store.repository.CategoryRepository;
import com.book_store.repository.ProductImageRepository;
import com.book_store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    public Page<Product> listAll(int currentPage, String sortField,
                                 String sortDirection, String keyword, int categoryId) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(currentPage - 1, 6, sort);
        if (categoryId == -1) {
            return productRepository.searchProductByName(keyword, pageable);
        }
        return productRepository.searchProductByNameAndCategory(keyword, categoryId, pageable);
    }

    public List<Category> getCategoryList() {
        return categoryRepository.findAll();
    }
}
