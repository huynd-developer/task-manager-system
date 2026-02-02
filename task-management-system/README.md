# Task Management System

Hệ thống quản lý công việc được xây dựng bằng Java Spring Boot, hỗ trợ quản lý công việc theo dự án, phân quyền người dùng và theo dõi trạng thái công việc.

---

##  Công nghệ sử dụng

- **Ngôn ngữ:** Java 17
- **Framework:** Spring Boot 3.4.1
- **ORM:** Hibernate (JPA)
- **Cơ sở dữ liệu:** Microsoft SQL Server
- **Build Tool:** Maven
- **Thư viện hỗ trợ:** Lombok
- **Tài liệu API:** Swagger UI (OpenAPI 3)

---

## Chức năng chính

- **Quản lý công việc (Task)**
    - Thêm, sửa, xóa, xem danh sách công việc
    - Cập nhật trạng thái công việc (TODO, IN_PROGRESS, DONE)
    - Gán công việc cho người dùng và dự án

- **Quản lý người dùng (User)**
    - Lưu thông tin người dùng
    - Phân quyền theo vai trò (ROLE)

- **Quản lý dự án (Project)**
    - Tạo và theo dõi tiến độ dự án
    - Liên kết nhiều công việc trong cùng một dự án

- **Xử lý lỗi tập trung**
    - Sử dụng Global Exception Handler
    - Trả về lỗi dạng JSON thống nhất (HTTP Status, message, timestamp)

- **Tách DTO – Entity**
    - Không trả Entity trực tiếp ra API
    - Đảm bảo bảo mật và dễ mở rộng hệ thống

- **Tài liệu hóa API**
    - Tích hợp Swagger UI để test và xem API trực tiếp trên trình duyệt

---

## Hướng dẫn chạy và kiểm thử

### Cấu hình Database

Mở file:
