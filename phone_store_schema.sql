-- Database: Phone_Store

-- 1. BẢNG NGƯỜI DÙNG (quản trị và khách hàng)
CREATE TABLE users (
                       user_id      BIGINT PRIMARY KEY AUTO_INCREMENT,
                       username     VARCHAR(50)  NOT NULL UNIQUE,
                       password     VARCHAR(255) NOT NULL,
                       email        VARCHAR(100) NOT NULL UNIQUE,
                       full_name    VARCHAR(100),
                       phone        VARCHAR(20),
                       address      VARCHAR(255),
                       avatar       VARCHAR(255),
                       role         VARCHAR(20)  NOT NULL DEFAULT 'USER', -- USER, ADMIN
                       created_at   DATETIME     DEFAULT CURRENT_TIMESTAMP,
                       status       TINYINT      DEFAULT 1
);

-- 2. BẢNG DANH MỤC SẢN PHẨM (hỗ trợ danh mục cha-con)
CREATE TABLE categories (
                            category_id  BIGINT PRIMARY KEY AUTO_INCREMENT,
                            name         VARCHAR(100) NOT NULL,
                            parent_id    BIGINT NULL,
                            description  VARCHAR(255),
                            FOREIGN KEY (parent_id) REFERENCES categories(category_id) ON DELETE SET NULL
);

-- 3. BẢNG SẢN PHẨM
CREATE TABLE products (
                          product_id   BIGINT PRIMARY KEY AUTO_INCREMENT,
                          name         VARCHAR(200) NOT NULL,
                          category_id  BIGINT NOT NULL,
                          brand        VARCHAR(100),
                          price        DECIMAL(15,2) NOT NULL,
                          discount     DECIMAL(5,2) DEFAULT 0,
                          stock        INT DEFAULT 0,
                          description  TEXT,
                          image_url    VARCHAR(255),
                          created_at   DATETIME DEFAULT CURRENT_TIMESTAMP,
                          updated_at   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          status       TINYINT DEFAULT 1,
                          FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

-- 4. BẢNG HÌNH ẢNH PHỤ SẢN PHẨM
CREATE TABLE product_images (
                                image_id     BIGINT PRIMARY KEY AUTO_INCREMENT,
                                product_id   BIGINT NOT NULL,
                                url          VARCHAR(255) NOT NULL,
                                caption      VARCHAR(100),
                                FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

-- 5. BẢNG GIỎ HÀNG
CREATE TABLE carts (
                       cart_id      BIGINT PRIMARY KEY AUTO_INCREMENT,
                       user_id      BIGINT NOT NULL,
                       created_at   DATETIME DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- 6. SẢN PHẨM TRONG GIỎ HÀNG
CREATE TABLE cart_items (
                            cart_item_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            cart_id      BIGINT NOT NULL,
                            product_id   BIGINT NOT NULL,
                            quantity     INT NOT NULL DEFAULT 1,
                            price        DECIMAL(15,2) NOT NULL,
                            added_at     DATETIME DEFAULT CURRENT_TIMESTAMP,
                            FOREIGN KEY (cart_id) REFERENCES carts(cart_id) ON DELETE CASCADE,
                            FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- 7. ĐƠN HÀNG
CREATE TABLE orders (
                        order_id         BIGINT PRIMARY KEY AUTO_INCREMENT,
                        user_id          BIGINT NOT NULL,
                        shipping_address VARCHAR(255),
                        shipping_method  VARCHAR(100),
                        shipping_fee     DECIMAL(15,2) DEFAULT 0,
                        total_amount     DECIMAL(15,2) NOT NULL,
                        status           VARCHAR(50) DEFAULT 'pending',
                        payment_method   VARCHAR(50),
                        payment_status   VARCHAR(50) DEFAULT 'unpaid',
                        note             VARCHAR(255),
                        created_at       DATETIME DEFAULT CURRENT_TIMESTAMP,
                        updated_at       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- 8. CHI TIẾT ĐƠN HÀNG
CREATE TABLE order_items (
                             order_item_id  BIGINT PRIMARY KEY AUTO_INCREMENT,
                             order_id       BIGINT NOT NULL,
                             product_id     BIGINT NOT NULL,
                             quantity       INT NOT NULL,
                             price          DECIMAL(15,2) NOT NULL,
                             discount       DECIMAL(5,2) DEFAULT 0,
                             FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
                             FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- 9. LỊCH SỬ TRẠNG THÁI ĐƠN HÀNG
CREATE TABLE order_status_logs (
                                   log_id      BIGINT PRIMARY KEY AUTO_INCREMENT,
                                   order_id    BIGINT NOT NULL,
                                   old_status  VARCHAR(50),
                                   new_status  VARCHAR(50),
                                   changed_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
                                   changed_by  BIGINT,
                                   FOREIGN KEY (order_id) REFERENCES orders(order_id),
                                   FOREIGN KEY (changed_by) REFERENCES users(user_id)
);

-- 10. PHIẾU GIẢM GIÁ / MÃ KHUYẾN MÃI
CREATE TABLE coupons (
                         coupon_id      BIGINT PRIMARY KEY AUTO_INCREMENT,
                         code           VARCHAR(30) NOT NULL UNIQUE,
                         description    VARCHAR(255),
                         discount_percent DECIMAL(5,2) DEFAULT 0,
                         max_discount   DECIMAL(15,2) DEFAULT 0,
                         min_order_value DECIMAL(15,2) DEFAULT 0,
                         start_date     DATETIME,
                         end_date       DATETIME,
                         usage_limit    INT DEFAULT 0,
                         used_count     INT DEFAULT 0,
                         status         TINYINT DEFAULT 1
);

-- 11. MÃ GIẢM GIÁ ĐƯỢC ÁP DỤNG TRÊN ĐƠN HÀNG
CREATE TABLE order_coupons (
                               id           BIGINT PRIMARY KEY AUTO_INCREMENT,
                               order_id     BIGINT NOT NULL,
                               coupon_id    BIGINT NOT NULL,
                               discount_value DECIMAL(15,2) DEFAULT 0,
                               FOREIGN KEY (order_id) REFERENCES orders(order_id),
                               FOREIGN KEY (coupon_id) REFERENCES coupons(coupon_id)
);

-- 12. LOG DOANH THU ĐƠN HÀNG
CREATE TABLE revenue_logs (
                              log_id     BIGINT PRIMARY KEY AUTO_INCREMENT,
                              order_id   BIGINT,
                              revenue    DECIMAL(15,2),
                              profit     DECIMAL(15,2),
                              log_date   DATETIME DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (order_id) REFERENCES orders(order_id)
);

-- 13. ĐÁNH GIÁ SẢN PHẨM
CREATE TABLE reviews (
                         review_id   BIGINT PRIMARY KEY AUTO_INCREMENT,
                         product_id  BIGINT NOT NULL,
                         user_id     BIGINT NOT NULL,
                         rating      INT CHECK (rating BETWEEN 1 AND 5),
                         content     TEXT,
                         created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
                         status      TINYINT DEFAULT 1,
                         FOREIGN KEY (product_id) REFERENCES products(product_id),
                         FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- 14. PHẢN HỒI ĐÁNH GIÁ (ADMIN HOẶC USER)
CREATE TABLE review_replies (
                                reply_id    BIGINT PRIMARY KEY AUTO_INCREMENT,
                                review_id   BIGINT NOT NULL,
                                user_id     BIGINT NOT NULL,
                                content     TEXT,
                                created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (review_id) REFERENCES reviews(review_id),
                                FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- 15. PHẢN HỒI / LIÊN HỆ TỪ KHÁCH HÀNG
CREATE TABLE feedbacks (
                           feedback_id  BIGINT PRIMARY KEY AUTO_INCREMENT,
                           user_id      BIGINT,
                           name         VARCHAR(100),
                           email        VARCHAR(100),
                           subject      VARCHAR(200),
                           message      TEXT NOT NULL,
                           status       VARCHAR(50) DEFAULT 'pending',
                           created_at   DATETIME DEFAULT CURRENT_TIMESTAMP,
                           FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- 16. THÔNG BÁO CHO USER
CREATE TABLE notifications (
                               notification_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               user_id         BIGINT,
                               title           VARCHAR(200),
                               message         TEXT,
                               is_read         TINYINT DEFAULT 0,
                               created_at      DATETIME DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- 17. ĐỊA CHỈ GIAO HÀNG CỦA USER
CREATE TABLE shipping_addresses (
                                    address_id     BIGINT PRIMARY KEY AUTO_INCREMENT,
                                    user_id        BIGINT NOT NULL,
                                    address        VARCHAR(255) NOT NULL,
                                    phone          VARCHAR(20),
                                    receiver_name  VARCHAR(100),
                                    is_default     TINYINT DEFAULT 0,
                                    created_at     DATETIME DEFAULT CURRENT_TIMESTAMP,
                                    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- 18. HOẠT ĐỘNG QUẢN TRỊ (LOG ADMIN)
CREATE TABLE admin_logs (
                            log_id     BIGINT PRIMARY KEY AUTO_INCREMENT,
                            admin_id   BIGINT NOT NULL,
                            action     VARCHAR(255),
                            description TEXT,
                            created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                            FOREIGN KEY (admin_id) REFERENCES users(user_id)
);

-- 19. DANH SÁCH YÊU THÍCH SẢN PHẨM
CREATE TABLE wishlists (
                           wishlist_id  BIGINT PRIMARY KEY AUTO_INCREMENT,
                           user_id      BIGINT NOT NULL,
                           product_id   BIGINT NOT NULL,
                           created_at   DATETIME DEFAULT CURRENT_TIMESTAMP,
                           UNIQUE KEY unique_wishlist (user_id, product_id),
                           FOREIGN KEY (user_id) REFERENCES users(user_id),
                           FOREIGN KEY (product_id) REFERENCES products(product_id)
);