package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

        // Tìm tất cả sản phẩm theo danh mục con
        List<Product> findByCategoryCategoryId(Long categoryId);

        List<Product> findTop4ByCategory_NameIgnoreCase(String categoryName, Sort sort);

        // Tìm sản phẩm theo danh mục cha (bao gồm cả danh mục con)
        @Query("SELECT p FROM Product p WHERE p.category.parent.categoryId = :parentId OR p.category.categoryId = :parentId")
        List<Product> findByCategoryParentCategoryId(@Param("parentId") Long parentId);

        // Tìm sản phẩm cùng danh mục nhưng khác sản phẩm hiện tại
        @Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId AND p.productId != :productId")
        List<Product> findByCategoryCategoryIdAndProductIdNot(@Param("categoryId") Long categoryId,
                        @Param("productId") Long productId);

        // Tìm kiếm nâng cao
        @Query("SELECT p FROM Product p WHERE " +
                        "(:query IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))) AND "
                        +
                        "(:categoryId IS NULL OR p.category.categoryId = :categoryId) AND " +
                        "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
                        "(:maxPrice IS NULL OR p.price <= :maxPrice) " +
                        "ORDER BY " +
                        "CASE WHEN :sortBy = 'name' AND :sortOrder = 'asc' THEN p.name END ASC, " +
                        "CASE WHEN :sortBy = 'name' AND :sortOrder = 'desc' THEN p.name END DESC, " +
                        "CASE WHEN :sortBy = 'price' AND :sortOrder = 'asc' THEN p.price END ASC, " +
                        "CASE WHEN :sortBy = 'price' AND :sortOrder = 'desc' THEN p.price END DESC, " +
                        "CASE WHEN :sortBy = 'createdAt' AND :sortOrder = 'asc' THEN p.createdAt END ASC, " +
                        "CASE WHEN :sortBy = 'createdAt' AND :sortOrder = 'desc' THEN p.createdAt END DESC")
        List<Product> searchProducts(@Param("query") String query,
                        @Param("categoryId") Long categoryId,
                        @Param("minPrice") BigDecimal minPrice,
                        @Param("maxPrice") BigDecimal maxPrice,
                        @Param("sortBy") String sortBy,
                        @Param("sortOrder") String sortOrder);

        // Gợi ý sản phẩm cùng danh mục (trừ sản phẩm hiện tại)
        @Query("SELECT p FROM Product p WHERE p.category.categoryId = (SELECT p2.category.categoryId FROM Product p2 WHERE p2.productId = :productId) AND p.productId != :productId")
        List<Product> findRelatedProducts(@Param("productId") Long productId);

        // Gợi ý tên sản phẩm (tối đa 5 sản phẩm)
        List<Product> findTop4ByNameContainingIgnoreCase(String name);

        // Lấy 10 sản phẩm mới nhất theo categoryId
        List<Product> findTop9ByCategory_CategoryIdOrderByProductIdDesc(Long categoryId);

        // Lấy 10 sản phẩm mới nhất theo parent category id (bao gồm cả parent và con)
        List<Product> findTop9ByCategory_Parent_CategoryIdOrderByProductIdDesc(Long parentId);
}
