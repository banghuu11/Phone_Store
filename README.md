# Phone_Store

DB 
-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jan 31, 2026 at 09:42 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `Phone_Store`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin_logs`
--

CREATE TABLE `admin_logs` (
  `log_id` bigint(20) NOT NULL,
  `admin_id` bigint(20) NOT NULL,
  `action` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `carts`
--

CREATE TABLE `carts` (
  `cart_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `cart_items`
--

CREATE TABLE `cart_items` (
  `cart_item_id` bigint(20) NOT NULL,
  `cart_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 1,
  `price` decimal(10,2) NOT NULL,
  `added_at` datetime DEFAULT current_timestamp(),
  `subtotal` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `category_id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`category_id`, `name`, `parent_id`, `description`) VALUES
(12, 'Điên Thoại', NULL, ''),
(13, 'Xiaomi', 12, ''),
(14, 'iphone', 12, ''),
(15, 'laptop ', NULL, ''),
(16, 'asus', 15, ''),
(17, 'dell', 15, ''),
(18, 'phụ kiện ', NULL, ''),
(19, 'tai nghe ', 18, ''),
(20, 'ban  phím ', 18, ''),
(21, 'chuột ', 18, ''),
(22, 'oppo', 12, ''),
(23, 'Màn hình', NULL, ''),
(24, 'Samsung', 12, '');

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
  `id` bigint(20) NOT NULL,
  `admin_reply` text DEFAULT NULL,
  `content` text NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `username` varchar(100) NOT NULL,
  `product_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`id`, `admin_reply`, `content`, `created_at`, `username`, `product_id`) VALUES
(3, 'sdsd', 'áuidfouahfihfjsf', '2025-07-02 03:07:00.000000', 'tran huu bang ', 31),
(4, 'con ạ ', 'sản phamcon hàng ko shop ', '2025-07-02 04:10:53.000000', 'anonymousUser', 28),
(5, 'cc ', 'sanasjdaisdjasid', '2025-07-02 04:12:34.000000', 'anonymousUser', 28),
(6, 'ss', 'sdsd', '2025-07-02 04:17:00.000000', 'anonymousUser', 29),
(9, 'sss', 'djhfshdf', '2025-07-15 00:40:56.000000', 'bang111', 28),
(10, NULL, 'sss', '2025-11-02 05:39:08.000000', 'anonymousUser', 37);

-- --------------------------------------------------------

--
-- Table structure for table `coupons`
--

CREATE TABLE `coupons` (
  `coupon_id` bigint(20) NOT NULL,
  `code` varchar(50) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `discount_percent` double NOT NULL,
  `max_discount` decimal(38,2) NOT NULL,
  `min_order_value` decimal(38,2) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `usage_limit` int(11) DEFAULT 0,
  `used_count` int(11) DEFAULT 0,
  `status` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `coupons`
--

INSERT INTO `coupons` (`coupon_id`, `code`, `description`, `discount_percent`, `max_discount`, `min_order_value`, `start_date`, `end_date`, `usage_limit`, `used_count`, `status`) VALUES
(3, 'vip999', 'thang0919823', 50, 100.00, 100.00, '2025-10-27', '2025-10-31', 1000, 0, b'0'),
(4, '6thang6', 'giẩm xâu ', 50, 20000000.00, 1000.00, '2025-11-03', '2026-10-24', 200, 0, b'1');

-- --------------------------------------------------------

--
-- Table structure for table `feedbacks`
--

CREATE TABLE `feedbacks` (
  `feedback_id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL,
  `subject` varchar(200) DEFAULT NULL,
  `message` text NOT NULL,
  `status` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `admin_reply` text DEFAULT NULL,
  `content` text NOT NULL,
  `username` varchar(100) NOT NULL,
  `product_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `feedbacks`
--

INSERT INTO `feedbacks` (`feedback_id`, `user_id`, `name`, `email`, `subject`, `message`, `status`, `created_at`, `admin_reply`, `content`, `username`, `product_id`) VALUES
(1, NULL, 'ádasd', 'fdgfg@gmail.com', 'sff', 'con ko shopj ', 0, '2025-07-01 07:43:05', NULL, '', '', 0),
(2, NULL, 'bang ', 'dfjghi@gmail.com', 'ịijf', 'igig', 0, '2025-07-02 03:09:20', NULL, '', '', 0);

-- --------------------------------------------------------

--
-- Table structure for table `images`
--

CREATE TABLE `images` (
  `image_id` bigint(20) NOT NULL,
  `caption` varchar(255) DEFAULT NULL,
  `url` varchar(500) NOT NULL,
  `product_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `images`
--

INSERT INTO `images` (`image_id`, `caption`, `url`, `product_id`) VALUES
(1, '', 'https://24hstore.vn/images/products/2024/11/26/large/iphone-16-pro-max-cu-titan-trang.jpg', 28),
(3, '', 'https://24hstore.vn/images/products/2024/11/26/large/iphone-16-pro-max-cu-titan-den.jpg', 28),
(4, '', 'https://cdn2.cellphones.com.vn/358x/media/catalog/product/i/p/iphone-16-pro-max-titan-trang.png', 28);

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE `notifications` (
  `notification_id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `message` text DEFAULT NULL,
  `is_read` tinyint(4) DEFAULT 0,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `shipping_address` varchar(255) NOT NULL,
  `shipping_method` varchar(100) DEFAULT NULL,
  `shipping_fee` decimal(10,2) NOT NULL,
  `total_amount` decimal(15,2) NOT NULL,
  `status` varchar(50) DEFAULT 'pending',
  `payment_method` varchar(50) DEFAULT NULL,
  `payment_status` varchar(50) DEFAULT 'unpaid',
  `note` varchar(1000) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `user_id`, `shipping_address`, `shipping_method`, `shipping_fee`, `total_amount`, `status`, `payment_method`, `payment_status`, `note`, `created_at`, `updated_at`) VALUES
(3, 64, 'qưeq, Nguyễn Thái Bình, Quận 1, HCM', 'STANDARD', 0.00, 129480.54, 'CONFIRMED', 'COD', 'UNPAID', 'ád', '2025-07-09 17:56:34', '2025-07-09 17:56:34'),
(4, 50, 'a, Trúc Bạch, Ba Đình, HN', 'STANDARD', 0.00, 108348522.48, 'CONFIRMED', 'COD', 'UNPAID', 'sad', '2025-07-15 22:47:09', '2025-07-15 22:47:09'),
(5, 66, 'â, Phường 2, Quận 3, HCM', 'STANDARD', 0.00, 21336.48, 'CONFIRMED', 'COD', 'UNPAID', 'qưeqwe', '2025-10-27 06:42:13', '2025-10-27 06:42:13'),
(6, 68, 'zhdjb, Phường 2, Quận 3, HCM', 'STANDARD', 0.00, 108348522.48, 'PENDING', 'COD', 'UNPAID', 'ád', '2025-11-03 05:04:33', '2025-11-03 05:04:33');

-- --------------------------------------------------------

--
-- Table structure for table `order_coupons`
--

CREATE TABLE `order_coupons` (
  `id` bigint(20) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `coupon_id` bigint(20) NOT NULL,
  `discount_value` decimal(15,2) DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `order_item_id` bigint(20) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `discount` decimal(38,2) DEFAULT NULL,
  `subtotal` decimal(38,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order_items`
--

INSERT INTO `order_items` (`order_item_id`, `order_id`, `product_id`, `quantity`, `price`, `discount`, `subtotal`) VALUES
(1, 3, 29, 1, 132123.00, 2.00, 129480.54),
(2, 4, 41, 1, 123123321.00, 12.00, 108348522.48),
(3, 5, 40, 2, 12123.00, 12.00, 21336.48),
(4, 6, 41, 1, 123123321.00, 12.00, 108348522.48);

-- --------------------------------------------------------

--
-- Table structure for table `order_status_logs`
--

CREATE TABLE `order_status_logs` (
  `log_id` bigint(20) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `old_status` varchar(50) DEFAULT NULL,
  `new_status` varchar(50) DEFAULT NULL,
  `changed_at` datetime DEFAULT current_timestamp(),
  `changed_by` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `product_id` bigint(20) NOT NULL,
  `name` varchar(200) NOT NULL,
  `category_id` bigint(20) NOT NULL,
  `brand` varchar(100) DEFAULT NULL,
  `price` decimal(15,2) NOT NULL,
  `discount` decimal(5,2) DEFAULT 0.00,
  `stock` int(11) DEFAULT 0,
  `description` text DEFAULT NULL,
  `image_url` varchar(500) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `status` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`product_id`, `name`, `category_id`, `brand`, `price`, `discount`, `stock`, `description`, `image_url`, `created_at`, `updated_at`, `status`) VALUES
(28, 'iPhone 16 Pro Max 256GB | Chính hãng VN/A', 14, 'Iphien', 1231231.00, 2.00, 12213, 'Camera chính: 48MP, f/1.78, 24mm, 2µm, chống rung quang học dịch chuyển cảm biến thế hệ thứ hai, Focus Pixels 100%\nTelephoto 2x 12MP: 52 mm, ƒ/1.6\nCamera góc siêu rộng: 48MP, 13 mm,ƒ/2.2 và trường ảnh 120°, Hybrid Focus Pixels, ảnh có độ phân giải', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone-16-pro-max.png', '2025-06-28 09:18:35', '2025-06-28 09:18:35', 1),
(29, 'Điện thoại Xiaomi 12 | Dienmayxiaomi.com - Điện máy XIAOMI', 13, 'Xiaomi ', 132123.00, 2.00, 1231320, 'AMOLED, 120Hz, Dolby Vision, DCI-P3, HDR10+, 1100 nits (tối đa)\n6.28 inches, Full HD+ (1080 x 2400 pixels), tỷ lệ 20:9\nHơn 68 tỷ màu\nCorning Gorilla Glass Victus\nCảm biến vân tay trong màn hình (quang học)', 'https://bizweb.dktcdn.net/thumb/large/100/375/502/products/xiaomi-12-xanh-duong-thumb-mau-600x600.jpg?v=1648980177303', '2025-06-28 09:20:01', '2025-06-28 09:20:01', 1),
(30, 'Asus ROG Strix SCAR 17 (G733PY-XS96) (2023) - AMD R9 7945HX / 32GB / 1TB SSD / RTX 4090 16GB / QHD 240Hz', 16, 'Asus ', 1212310.00, 10.00, 110, 'ROG Strix SCAR 17 năm 2023 là một trong những dòng laptop gaming hàng đầu của hãng ASUS. Với sức mạnh vượt trội và thiết kế đẳng cấp, sản phẩm này hứa hẹn sẽ mang lại trải nghiệm tuyệt vời cho các game thủ. Trong bài viết này, chúng ta sẽ điểm qua những tính năng nổi bật của ROG Strix SCAR 17 và xem liệu nó có xứng đáng là chiếc laptop gaming hiệu năng cao nhất hay không.', 'https://hungphatlaptop.com/wp-content/uploads/2023/08/Asus-ROG-Strix-SCAR-17-G733PY-XS96-2023-H1-1024x1024.jpeg', '2025-07-01 06:24:12', '2025-07-01 06:24:12', 1),
(31, 'DELL XPS 16 9640 (2024) - CORE ULTRA 9 185H / 64GB / 1TB SSD / RTX 4070 8GB / OLED 4K+ TOUCH / GRAPHITE', 17, 'DELL', 1203913.00, 10.00, 1203, 'Dell XPS 16 9640 (2024) là mẫu UltraBook cao cấp được thiết kế dành riêng cho doanh nhân và người dùng chuyên nghiệp cần một thiết bị mỏng nhẹ, sang trọng và hiệu năng mạnh mẽ. Với cấu hình cao cấp, màn hình sắc nét và khả năng xử lý đa nhiệm hiệu quả, Dell XPS 16 mang lại trải nghiệm làm việc linh hoạt, đồng thời khẳng định phong cách chuyên nghiệp trong mọi môi trường.', 'https://hungphatlaptop.com/wp-content/uploads/2024/01/DELL-XPS-16-9640-2024-Graphite-H1-1-1024x1024.jpeg', '2025-07-01 06:25:11', '2025-07-01 06:25:11', 1),
(33, 'Bàn phím cơ Low-Profile MelGeek O2 | Tri-mode', 20, ' MelGeek', 123123.00, 2.00, 11230, 'Lấy cảm hứng từ thiết kế trong suốt mang tính biểu tượng của Apple, MelGeek O2 được tạo ra để đảm bảo khả năng tương thích sâu và kết nối liền mạch trong hệ sinh thái Apple, như một ứng dụng gốc được cài sẵn trong macOS. Sản phẩm mang đến cho người dùng Mac một trải nghiệm nhẹ nhàng, mượt mà và dễ dàng – như không khí ta hít thở hằng ngày.', 'https://www.phongcachxanh.vn/cdn/shop/files/ban-phim-c-melgeek-o2-1155939124.jpg?v=1747710869&width=1200', '2025-07-02 18:53:32', '2025-07-02 18:53:32', 1),
(34, 'iPhone 15 128GB | Chính hãng VN/A', 14, 'Apple', 11122.00, 10.00, 11111, 'iPhone 15 là một trong những phiên bản nhận được nhiều sự săn đón từ các tín đồ công nghệ. Với thiết kế sang trọng thanh thoát, màu sắc ấn tượng với bảng màu pastel mới lạ, trẻ trung cùng hàng loạt những tiện ích công nghệ nổi bật được nâng cấp. iPhone 15 tiêu chuẩn 128GB được đánh giá là chiếc iPhone nhà Táo hứa hẹn sẽ mang đến những trải nghiệm công nghệ tuyệt vời nhất đến cho người dùng. Hãy cùng 24hStore tìm hiểu sâu hơn về siêu phẩm iPhone 15 tiêu chuẩn 128GB nhé!', 'https://24hstore.vn/images/products/2024/08/22/large/iphone-15-hinh-1.jpg', '2025-07-03 18:18:06', '2025-07-03 18:18:19', 1),
(35, 'Oppo Reno14 F 5G 8GB/256GB Chính Hãng', 22, 'Oppo', 1230192.00, 10.00, 111, 'Xóa phôngTự động lấy nét (AF)Chạm lấy nétHDRToàn cảnh (Panorama)Chống rung quang học (OIS)Ban đêm (Night Mode)Quay chậm (Slow Motion)Trôi nhanh thời gian (Time Lapse)Zoom kỹ thuật sốChụp siêu cậnAI CameraBộ lọc màuChụp chân dungAR StickerQuay video hiển thị képống kính GoogleLàm đẹp AISiêu cận (Macro)', 'https://dienthoaigiakho.vn/_next/image?url=https%3A%2F%2Fcdn.dienthoaigiakho.vn%2Fphotos%2F1751509941000-Oppo-Reno-14-F-5G-Xanh-duong-2.jpg&w=3840&q=75', '2025-07-08 18:12:20', '2025-07-08 18:13:04', 1),
(36, 'Xiaomi Redmi Note 14 5G 8GB 256GB', 13, 'Xiaomi ', 1123123123.00, 123.00, 123, 'asdasd', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/d/i/dien-thoai-xiaomi-redmi-note-14-5g.1.png', '2025-07-15 22:39:25', '2025-07-15 22:39:25', 1),
(37, 'iPhone 13 128GB | Chính hãng VN/A', 14, 'iPhone ', 12312313.00, 12.00, 123, 'iPhone 13 thường được trang bị chip A15 Bionic mạnh mẽ với 6 nhân CPU và 4 nhân GPU, cung cấp mức hiệu năng vượt trội, giúp xử lý nhanh chóng các tác vụ nặng. Màn hình Super Retina XDR 6.1 inch trên máy cũng được đánh giá cao khi mang tới hình ảnh sắc nét với độ sáng cao, tối ưu hóa trải nghiệm xem nội dung dưới mọi điều kiện ánh sáng. Chưa hết, iPhone13 còn sở hữu hệ thống camera kép 12MP với công nghệ ổn định hình ảnh quang học (OIS) cải thiện khả năng quay film, chụp hình, ngay cả khi đang ở trong môi trường ánh sáng yếu.\n\nCùng tìm hiểu chi tiết về thông số và giá bán iPhone 13 thường bao nhiêu qua nội dung chi tiết dưới đây!', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone_13_pdp_position-1a_color_midnight__vn.png', '2025-07-15 22:40:46', '2025-07-15 22:40:46', 1),
(38, 'Xiaomi Redmi Note 14 6GB 128GB', 13, 'Xiaomi', 23123.00, 1.00, 123230, 'Redmi note 14 chính hãng ra mắt vào ngày 10/01/2025 tại Việt Nam sở hữu AI mới nhất kèm chip MediaTek Helio G99-Ultra mạnh mẽ đi cùng camera 108MP.', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/d/i/dien-thoai-xiaomi-redmi-note-14_2__2.png', '2025-07-15 22:41:41', '2025-07-15 22:41:41', 1),
(39, 'Samsung Galaxy S25 256GB', 13, 'Samsung ', 2123.00, 1.00, 123, 'Samsung S25 thường 256GB trang bị vi xử lý Qualcomm Snapdragon 8 Elite for Galaxy , RAM 12GB, bộ nhớ trong 256GB cùng viên pin Li-ion 4000mAh. Máy sử dụng màn hình AMOLED 120Hz kích thước 6.2 inch với độ phân giải FHD+. Samsung S25 được trang bị 3 ống kính 50MP+10MP+12MP phía sau và camera selfie 12MP phía trước.', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/d/i/dien-thoai-samsung-galaxy-s25_1__2.png', '2025-07-15 22:42:55', '2025-07-15 22:43:22', 1),
(40, 'Samsung Galaxy S25 Ultra 12GB 256GB', 24, 'Samsung', 12123.00, 12.00, 12313, 'Samsung S25 Ultra mạnh mẽ với chip Snapdragon 8 Elite For Galaxy mới nhất, RAM 12GB và bộ nhớ trong 256GB-1TB. Hệ thống 3 camera sau chất lượng gồm camera chính 200MP, camera tele 50MP và camera góc siêu rộng 50MP. Thiết kế kính cường lực Corning Gorilla Armor 2 và khung viền Titanium, màn hình Dynamic AMOLED 6.9 inch. Điện thoại này còn có viên pin 5000mAh, hỗ trợ 5G và Galaxy AI ấn tượng, nâng cao trải nghiệm người dùng!', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/d/i/dien-thoai-samsung-galaxy-s25-ultra_3__3.png', '2025-07-15 22:44:08', '2025-07-15 22:44:08', 1),
(41, 'Samsung Galaxy A36 5G 8GB 128GB', 24, 'Samsung', 123123321.00, 12.00, 123123, 'Điện thoại Samsung A36 5G được trang bị chip Snapdragon® 6 Gen 3 Mobile Platform, RAM 8GB và bộ nhớ trong 128GB, kháng bụi, kháng nước đạt chuẩn IP67. Máy Samsung Galaxy A36 gồm 3 camera sau 50MP + 8MP + 5MP, camera trước 12MP, màn hình Super AMOLED 6.7 inch sắc nét độ phân giải FHD+. Máy sở hữu lớp kính Corning Gorilla Glass Victus+ ở màn hình và cả mặt lưng, kích thước sản phẩm 162.9 x 78.2 x 7.4mm.', 'https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/d/i/dien-thoai-samsung-galaxy-a36.2.png', '2025-07-15 22:45:26', '2025-07-15 22:45:26', 1);

-- --------------------------------------------------------

--
-- Table structure for table `product_images`
--

CREATE TABLE `product_images` (
  `image_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `url` varchar(255) NOT NULL,
  `caption` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `revenue_logs`
--

CREATE TABLE `revenue_logs` (
  `log_id` bigint(20) NOT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `revenue` decimal(15,2) DEFAULT NULL,
  `profit` decimal(15,2) DEFAULT NULL,
  `log_date` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `reviews`
--

CREATE TABLE `reviews` (
  `review_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `rating` int(11) DEFAULT NULL CHECK (`rating` between 1 and 5),
  `content` text DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `status` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `review_replies`
--

CREATE TABLE `review_replies` (
  `reply_id` bigint(20) NOT NULL,
  `review_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `content` text DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `shipping_addresses`
--

CREATE TABLE `shipping_addresses` (
  `address_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `receiver_name` varchar(100) DEFAULT NULL,
  `is_default` tinyint(4) DEFAULT 0,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` bigint(20) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `status` int(11) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `email`, `full_name`, `phone`, `address`, `avatar`, `role`, `created_at`, `status`, `updated_at`) VALUES
(1, 'user1', '123456', 'user1@example.com', 'Nguyễn Văn A', '0911111111', 'Hà Nội', NULL, 'ADMIN', '2025-06-21 03:13:38', 1, NULL),
(2, 'user2ádasdasd', 'abcdef', 'user2@example.com', 'Trần Thị B', '0922222222', 'Sài Gòn', '', 'ADMIN', '2025-06-21 03:13:38', 1, '2025-06-25 10:16:19.000000'),
(32, 'tran huu bang111', '123123', '23123213e@gmail.com', '123123ád', '123ấdadá', '123', 'dádasd', 'ADMIN', NULL, 0, '2025-06-25 10:19:01.000000'),
(40, 'sadmaowg', 'ksdjf', 'huhuysdgf@gmail.com', 'iủhe', 'sdfuu', 'ad', '', 'ADMIN', '2025-06-25 10:33:06', 1, '2025-06-25 10:33:06.000000'),
(45, 'foiughthoyifgjh', 'hukjhk', 'fghjkl@gmail.com', 'hjkhjk', 'hjkhjkhj', 'khjk', 'hjk', 'USER', '2025-06-25 21:06:24', 1, '2025-06-25 21:06:24.000000'),
(49, '123456a', '123456a', '123456a@gmail.com', 'oigfjhigj', '9048958', 'flkgjh', '', 'ADMIN', '2025-06-26 07:45:48', 1, '2025-06-26 07:45:48.000000'),
(50, 'bang111', '$2a$10$xug6gtc5qWFHhKJARU20Z.0CR/c4yx5gsQQUQnF7GcR4TSsTreiYO', '111@Gmail.com', 'admin', '', '', 'https://i.pinimg.com/736x/34/60/3c/34603ce8a80b1ce9a768cad7ebf63c56.jpg', 'ADMIN', '2025-06-26 02:56:48', 1, '2025-06-26 02:56:48.000000'),
(55, '1234', '$2a$10$syV1JjcLvhqKwAGqjvzEQuvyrxSydwU7SrpN00U1iYjsbCqJkaXzC', '1234@gmail.com', 'tranh huu bnag ', '092390817398', '', '', 'USER', '2025-06-26 16:11:04', 1, '2025-06-26 16:11:04.000000'),
(56, 'uuu1', '$2a$10$.RjXnz8FAu.uxZMVvDaFCu0W..cym7rgBlwVeKqF7CSRePPI5jZu2', 'uuu1@gmail.com', 'tran huu bang ', '09524351', 'hcm', 'https://lumiere-a.akamaihd.net/v1/images/a_avatarpandorapedia_jakesully_16x9_1098_02_b13c4171.jpeg?region=0%2C60%2C1920%2C960', 'USER', '2025-06-27 17:43:26', 1, '2025-06-27 17:43:26.000000'),
(62, 'bang1234', '$2a$10$p9id00B8SHX.aK.ybWKUAemYenRyKkoKYDuE.TCwEOpWPhHUTgkFe', 'bang1324@gmail.com', 'bang12345', '0944526553', 'hcm', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT8hUz-P7Cz2ZKn2DEN_EvFhjwmRiURX7r9TA&s', 'USER', '2025-06-27 18:47:25', 1, '2025-06-27 18:47:25.000000'),
(63, '9090', '$2a$10$u0ya1gd9q01FWmKpaQ9tzOyC6l84yOIUg.E83H33CxEZqkyRCGf7i', '9090@gmail.com', NULL, NULL, NULL, NULL, 'USER', '2025-06-27 18:57:50', 1, '2025-06-27 18:57:50.000000'),
(64, 'huu', '$2a$10$maidXDKNENJacRtQeL6YDuPhwff1dCQrrDbaSrPfaEzRmXIBMXRfO', 'huu@gmail.com', 'tran huu bang', '09445265531', 'hcm', 'https://hoseiki.vn/wp-content/uploads/2025/03/avatar-vo-tri-24.jpg', 'USER', '2025-06-27 19:06:30', 1, '2025-06-27 19:06:30.000000'),
(65, 'bangbang', '$2a$10$PZ/XwB3PINioYBsFlWup1.kPJQt.4pNzN08XTZmL6AfBZlTKa9h6C', 'bang11@gmail.com', NULL, NULL, NULL, NULL, 'ADMIN', '2025-10-22 16:39:23', 1, '2025-10-22 16:39:23.000000'),
(66, 'banghuu', '$2a$10$5CnGiv.klyqLjBf0vf9Dzu/prdEv6WUbDYNUeJq7yPnK0csplcwAO', 'banghuu11@gmail.com', 'trân huu bằng ', '', '', '', 'ADMIN', '2025-10-27 06:10:49', 1, '2025-10-27 06:10:49.000000'),
(67, 'bangbang123', '$2a$10$DDNHOoq7hPTX8Yi8g.QfJe0A1iy9hIilCymmjkmGnSCRIBkaX4as2', 'bangbang123@gmail.com', NULL, NULL, NULL, NULL, 'ADMIN', '2025-11-02 09:27:45', 1, '2025-11-02 09:27:45.000000'),
(68, 'huu123', '$2a$10$D1uzgD2Z4XO255j4m721b.LlJAe2mvGmi5UAMrH9U9hhuzbUrVDde', 'huu123@gmail.com', '', '', '', '', 'USER', '2025-11-03 05:03:18', 1, '2025-11-03 05:03:18.000000'),
(69, 'bang', '$2a$10$xmH/wp3fepPbEgHYRf4dz.XbKZpb4b7t4ho4vpdaspm/XEARSxZ9y', 'bang@gmail.com', NULL, NULL, NULL, NULL, 'USER', '2025-11-27 18:38:46', 1, '2025-11-27 18:38:46.000000'),
(73, 'bang100', '$2a$10$nPwJFnLl2LSTj7JcPyeKq.qCzeSPCQ7pWNt3NzNCPliowSNsutHSy', 'bang100@gmail.com', NULL, NULL, NULL, NULL, 'ADMIN', '2025-11-28 06:28:01', 1, '2025-11-28 06:28:01.000000');

-- --------------------------------------------------------

--
-- Table structure for table `wishlists`
--

CREATE TABLE `wishlists` (
  `wishlist_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `wishlists`
--

INSERT INTO `wishlists` (`wishlist_id`, `user_id`, `product_id`, `created_at`) VALUES
(8, 64, 28, '2025-07-15 22:37:25'),
(10, 66, 40, '2025-10-27 06:25:00'),
(11, 66, 36, '2025-10-27 06:25:10'),
(12, 66, 35, '2025-10-27 06:25:13');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin_logs`
--
ALTER TABLE `admin_logs`
  ADD PRIMARY KEY (`log_id`),
  ADD KEY `admin_id` (`admin_id`);

--
-- Indexes for table `carts`
--
ALTER TABLE `carts`
  ADD PRIMARY KEY (`cart_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `cart_items`
--
ALTER TABLE `cart_items`
  ADD PRIMARY KEY (`cart_item_id`),
  ADD KEY `cart_id` (`cart_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_id`),
  ADD KEY `parent_id` (`parent_id`);

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK6uv0qku8gsu6x1r2jkrtqwjtn` (`product_id`);

--
-- Indexes for table `coupons`
--
ALTER TABLE `coupons`
  ADD PRIMARY KEY (`coupon_id`),
  ADD UNIQUE KEY `code` (`code`);

--
-- Indexes for table `feedbacks`
--
ALTER TABLE `feedbacks`
  ADD PRIMARY KEY (`feedback_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `images`
--
ALTER TABLE `images`
  ADD PRIMARY KEY (`image_id`),
  ADD KEY `FKghwsjbjo7mg3iufxruvq6iu3q` (`product_id`);

--
-- Indexes for table `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`notification_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `order_coupons`
--
ALTER TABLE `order_coupons`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `coupon_id` (`coupon_id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`order_item_id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `order_status_logs`
--
ALTER TABLE `order_status_logs`
  ADD PRIMARY KEY (`log_id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `changed_by` (`changed_by`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`product_id`),
  ADD KEY `FKr638shrnkkh3wy5llr9cwyi4t` (`category_id`) USING BTREE;

--
-- Indexes for table `product_images`
--
ALTER TABLE `product_images`
  ADD PRIMARY KEY (`image_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `revenue_logs`
--
ALTER TABLE `revenue_logs`
  ADD PRIMARY KEY (`log_id`),
  ADD KEY `order_id` (`order_id`);

--
-- Indexes for table `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`review_id`),
  ADD KEY `product_id` (`product_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `review_replies`
--
ALTER TABLE `review_replies`
  ADD PRIMARY KEY (`reply_id`),
  ADD KEY `review_id` (`review_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `shipping_addresses`
--
ALTER TABLE `shipping_addresses`
  ADD PRIMARY KEY (`address_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `wishlists`
--
ALTER TABLE `wishlists`
  ADD PRIMARY KEY (`wishlist_id`),
  ADD UNIQUE KEY `unique_wishlist` (`user_id`,`product_id`),
  ADD KEY `product_id` (`product_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin_logs`
--
ALTER TABLE `admin_logs`
  MODIFY `log_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `carts`
--
ALTER TABLE `carts`
  MODIFY `cart_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `cart_items`
--
ALTER TABLE `cart_items`
  MODIFY `cart_item_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `category_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `comments`
--
ALTER TABLE `comments`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `coupons`
--
ALTER TABLE `coupons`
  MODIFY `coupon_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `feedbacks`
--
ALTER TABLE `feedbacks`
  MODIFY `feedback_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `images`
--
ALTER TABLE `images`
  MODIFY `image_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `notifications`
--
ALTER TABLE `notifications`
  MODIFY `notification_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `order_coupons`
--
ALTER TABLE `order_coupons`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `order_item_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `order_status_logs`
--
ALTER TABLE `order_status_logs`
  MODIFY `log_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `product_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT for table `product_images`
--
ALTER TABLE `product_images`
  MODIFY `image_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `revenue_logs`
--
ALTER TABLE `revenue_logs`
  MODIFY `log_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `reviews`
--
ALTER TABLE `reviews`
  MODIFY `review_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `review_replies`
--
ALTER TABLE `review_replies`
  MODIFY `reply_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `shipping_addresses`
--
ALTER TABLE `shipping_addresses`
  MODIFY `address_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=74;

--
-- AUTO_INCREMENT for table `wishlists`
--
ALTER TABLE `wishlists`
  MODIFY `wishlist_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admin_logs`
--
ALTER TABLE `admin_logs`
  ADD CONSTRAINT `admin_logs_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `carts`
--
ALTER TABLE `carts`
  ADD CONSTRAINT `carts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `cart_items`
--
ALTER TABLE `cart_items`
  ADD CONSTRAINT `cart_items_ibfk_1` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`cart_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `cart_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`);

--
-- Constraints for table `categories`
--
ALTER TABLE `categories`
  ADD CONSTRAINT `categories_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `categories` (`category_id`) ON DELETE SET NULL;

--
-- Constraints for table `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `FK6uv0qku8gsu6x1r2jkrtqwjtn` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`);

--
-- Constraints for table `feedbacks`
--
ALTER TABLE `feedbacks`
  ADD CONSTRAINT `feedbacks_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `images`
--
ALTER TABLE `images`
  ADD CONSTRAINT `FKghwsjbjo7mg3iufxruvq6iu3q` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`);

--
-- Constraints for table `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `order_coupons`
--
ALTER TABLE `order_coupons`
  ADD CONSTRAINT `order_coupons_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  ADD CONSTRAINT `order_coupons_ibfk_2` FOREIGN KEY (`coupon_id`) REFERENCES `coupons` (`coupon_id`);

--
-- Constraints for table `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`);

--
-- Constraints for table `order_status_logs`
--
ALTER TABLE `order_status_logs`
  ADD CONSTRAINT `order_status_logs_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  ADD CONSTRAINT `order_status_logs_ibfk_2` FOREIGN KEY (`changed_by`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`);

--
-- Constraints for table `product_images`
--
ALTER TABLE `product_images`
  ADD CONSTRAINT `product_images_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE;

--
-- Constraints for table `revenue_logs`
--
ALTER TABLE `revenue_logs`
  ADD CONSTRAINT `revenue_logs_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`);

--
-- Constraints for table `reviews`
--
ALTER TABLE `reviews`
  ADD CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  ADD CONSTRAINT `reviews_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `review_replies`
--
ALTER TABLE `review_replies`
  ADD CONSTRAINT `review_replies_ibfk_1` FOREIGN KEY (`review_id`) REFERENCES `reviews` (`review_id`),
  ADD CONSTRAINT `review_replies_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `shipping_addresses`
--
ALTER TABLE `shipping_addresses`
  ADD CONSTRAINT `shipping_addresses_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `wishlists`
--
ALTER TABLE `wishlists`
  ADD CONSTRAINT `wishlists_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `wishlists_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
