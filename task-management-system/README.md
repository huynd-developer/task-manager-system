# Task Management System

Hệ thống quản lý công việc (Task Management System) được xây dựng bằng Java Spring Boot, hỗ trợ quản lý công việc theo dự án, phân quyền người dùng chặt chẽ và theo dõi trạng thái công việc. Dự án áp dụng các tiêu chuẩn thiết kế RESTful API và bảo mật hiện đại.

## Công nghệ sử dụng

- Ngôn ngữ: Java 17
- Framework: Spring Boot 3.4.1 (Spring Web, Spring Data JPA, Spring Security)
- Bảo mật: JWT (JSON Web Token), BCrypt Password Encoder
- Cơ sở dữ liệu: Microsoft SQL Server
- Build Tool: Maven
- Thư viện hỗ trợ: Lombok
- Tài liệu API: Swagger UI (OpenAPI 3) có tích hợp xác thực Bearer Token.

## Chức năng nổi bật

- Bảo mật & Phân quyền (Security & Authorization):
  - Xác thực người dùng bằng JWT Token.
  - Phân quyền theo Role: ADMIN (toàn quyền) và USER (quyền hạn chế).
  - Tự động khởi tạo tài khoản Admin mặc định khi hệ thống chạy lần đầu.
- Quản lý công việc (Task):
  - Thêm, sửa, xóa, và xem danh sách công việc có hỗ trợ Phân trang (Pagination).
  - Cập nhật trạng thái công việc (TODO, IN_PROGRESS, DONE, CANCELED).
  - Ràng buộc logic chặt chẽ: Chỉ gán task cho thành viên thuộc dự án, chặn sửa task đã hoàn thành.
- Quản lý dự án (Project):
  - Tạo và theo dõi tiến độ dự án.
  - Xóa dự án an toàn với cơ chế tự động dọn dẹp dữ liệu liên quan (Cascade Delete).
- Kiến trúc & Best Practices:
  - JPA Auditing: Tự động ghi nhận thời gian tạo (createdAt) và cập nhật (updatedAt).
  - Tách biệt DTO & Entity: Bảo mật cấu trúc database và dễ dàng mở rộng.
  - Xử lý lỗi tập trung (Global Exception Handler): Bắt và trả về lỗi chuẩn format JSON (400, 404, 500) và lỗi Validation.

## Hướng dẫn cài đặt và chạy dự án

### 1. Cấu hình Cơ sở dữ liệu (Database)

1. Mở SQL Server Management Studio (SSMS).
2. Chạy file script SQL đính kèm trong thư mục dự án để tạo database task_management_db, các bảng và dữ liệu mẫu.
3. Mở file src/main/resources/application.properties và kiểm tra lại thông tin kết nối:

spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=task_management_db;encrypt=true;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=mật_khẩu_của_bạn

### 2. Chạy ứng dụng

1. Mở terminal tại thư mục gốc của dự án.
2. Build và chạy ứng dụng bằng Maven:

mvn spring-boot:run

3. Ứng dụng sẽ khởi chạy mặc định tại cổng 8080.

### 3. Kiểm thử API (Swagger UI)

Hệ thống đã tích hợp sẵn Swagger để test API trực tiếp trên trình duyệt.

- URL truy cập: http://localhost:8080/swagger-ui/index.html (hoặc /swagger tùy cấu hình properties).
- Tài khoản mặc định để test:
  - Username: admin
  - Password: admin123

Cách test API yêu cầu bảo mật:
1. Gọi API POST /api/auth/login với tài khoản admin ở trên.
2. Copy chuỗi Token trả về.
3. Kéo lên đầu trang Swagger, bấm vào nút Authorize (hình ổ khóa).
4. Dán token vào và bấm Authorize. Bây giờ bạn có thể gọi mọi API trong hệ thống.