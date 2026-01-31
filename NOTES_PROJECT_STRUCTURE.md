# Ghi chú chức năng từng file/thư mục

## src/main/java/com/example/phoneshop/

### config/
- **SecurityConfig.java**  
  Cấu hình bảo mật (Spring Security): phân quyền, xác thực, cấu hình đường dẫn public/private.

### controller/
- **AdminLogController.java**  
  Xử lý các API liên quan đến log của admin (xem, xóa, ghi log thao tác quản trị).
- **CartController.java**  
  Xử lý thao tác giỏ hàng: thêm/xóa/sửa sản phẩm trong giỏ, xem giỏ hàng.
- **CategoryController.java**  
  Quản lý danh mục sản phẩm: thêm/sửa/xóa/xem danh mục.
- **OrderController.java**  
  Xử lý đơn hàng: tạo, xem chi tiết, hủy, liệt kê đơn hàng của user hoặc admin.
- **ProductController.java**  
  Xử lý sản phẩm: danh sách, chi tiết, thêm/sửa/xóa sản phẩm.
- **ReviewController.java**  
  Quản lý đánh giá sản phẩm: thêm/xóa/sửa/xem đánh giá.
- **ShippingInfoController.java**  
  Xử lý thông tin giao hàng: thêm/sửa/xem địa chỉ giao hàng của user, quản lý trạng thái giao hàng.
- **UserController.java**  
  Quản lý thông tin người dùng: đăng ký, đăng nhập, cập nhật thông tin cá nhân, đổi mật khẩu.

### model/
- **AdminLog.java**  
  Entity ánh xạ bảng admin_logs, lưu lịch sử hoạt động của admin.
- **Cart.java**  
  Entity ánh xạ bảng cart, lưu thông tin giỏ hàng của từng user.
- **CartItem.java**  
  Entity ánh xạ bảng cart_items, lưu các sản phẩm trong từng giỏ hàng.
- **Category.java**  
  Entity ánh xạ bảng categories, lưu loại/danh mục sản phẩm.
- **Order.java**  
  Entity ánh xạ bảng orders, lưu thông tin đơn hàng của user.
- **OrderItem.java**  
  Entity ánh xạ bảng order_items, các sản phẩm trong từng đơn hàng.
- **Product.java**  
  Entity ánh xạ bảng products, lưu sản phẩm bán trong hệ thống.
- **ProductCategory.java**  
  Entity ánh xạ bảng product_categories, liên kết sản phẩm với danh mục.
- **Review.java**  
  Entity ánh xạ bảng reviews, lưu đánh giá của user về sản phẩm.
- **ReviewReply.java**  
  Entity ánh xạ bảng review_replies, phản hồi của admin/user cho đánh giá.
- **ShippingInfo.java**  
  Entity ánh xạ bảng shipping_info, lưu thông tin giao nhận/địa chỉ giao hàng.
- **User.java**  
  Entity ánh xạ bảng user (nếu có, cho các tính năng riêng biệt).
- **Users.java**  
  Entity ánh xạ bảng users (thường chỉ dùng 1 bảng users, có thể là do phân chia logic).

### repository/
- **...Repository.java**  
  Các interface extends JpaRepository cho thao tác CRUD với từng entity tương ứng (ví dụ: ProductRepository thao tác với Product, OrderRepository thao tác với Order...)

### service/
- **...Service.java**  
  Chứa business logic (nghiệp vụ) cho từng module: xử lý các thao tác phức tạp, kiểm tra điều kiện trước khi ghi database, gửi mail, xử lý thanh toán, kiểm tra tồn kho,...

### dto/
- **...DTO.java**  
  Các lớp trung gian (Data Transfer Object): dùng để giao tiếp giữa backend và frontend, gom dữ liệu từ nhiều entity hoặc loại bỏ các trường nhạy cảm khi trả về client.

### exception/
- **NotFoundException.java**  
  Ngoại lệ khi không tìm thấy resource (sản phẩm, user, đơn hàng, ...).
- **CustomException.java**  
  Ngoại lệ tùy chỉnh cho các lỗi khác (ví dụ: thao tác không hợp lệ, lỗi logic nghiệp vụ).

### security/
- **UserDetailsServiceImpl.java**  
  Custom UserDetailsService để xác thực người dùng với Spring Security.

---

## src/main/resources/

### static/
- **css/**  
  Chứa file CSS (ví dụ: style chung cho toàn bộ web, style cho từng module)
- **js/**  
  Chứa file JS (ví dụ: xử lý thêm vào giỏ hàng, ajax, hiệu ứng giao diện)
- **images/**  
  Chứa ảnh tĩnh, logo, banner...

### templates/
- **admin_logs/**  
  Giao diện trang quản lý log admin.
- **cart/**  
  Giao diện giỏ hàng (xem, sửa, xóa sản phẩm trong giỏ).
- **categories/**  
  Giao diện danh mục sản phẩm.
- **orders/**  
  Giao diện đơn hàng (tạo mới, xem chi tiết, quản lý đơn hàng).
- **products/**  
  Giao diện sản phẩm (danh sách, chi tiết, thêm/sửa/xóa sản phẩm).
- **product_categories/**  
  Giao diện liên kết sản phẩm với danh mục.
- **reviews/**  
  Giao diện đánh giá sản phẩm.
- **review_replies/**  
  Giao diện phản hồi đánh giá.
- **shipping_info/**  
  Giao diện quản lý địa chỉ giao hàng.
- **users/**  
  Giao diện thông tin người dùng, đăng ký, đăng nhập, profile.
- **layout.html**  
  Layout chung (header, footer, navbar) kế thừa cho các trang khác.

### application.properties
File cấu hình ứng dụng: thông tin kết nối database, port, các cấu hình cho upload file, mail server, ...

---

## Các file gốc
- **pom.xml**  
  Quản lý dependencies và cấu hình build của dự án Maven.
- **README.md**  
  Hướng dẫn sử dụng, cài đặt, mô tả dự án.
