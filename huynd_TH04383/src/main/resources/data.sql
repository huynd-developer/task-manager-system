INSERT INTO tac_gia (ma_tac_gia, ten_tac_gia)
VALUES
    ( 'TG001', 'J.K. Rowling'),
    ( 'TG002', 'George Orwell'),
    ( 'TG003', 'Haruki Murakami'),
    ( 'TG004', 'Paulo Coelho'),
    ( 'TG005', 'Stephen King'),
    ('TG006', 'Agatha Christie'),
    ( 'TG007', 'Dan Brown'),
    ( 'TG008', 'F. Scott Fitzgerald'),
    ( 'TG009', 'Victor Hugo'),
    ( 'TG010', 'Nguyễn Nhật Ánh');

INSERT INTO sach ( ma_sach, ten_sach, the_loai, ngay_xuat_ban, gia, id_tac_gia)
VALUES
    ( 'S001', 'Harry Potter', 'Fantasy', '1997-06-26', 250000, 1),
    ( 'S002', '1984', 'Dystopia', '1949-06-08', 180000, 2),
    ( 'S003', 'Rừng Na Uy', 'Văn học Nhật', '1987-01-01', 200000, 3),
    ( 'S004', 'Nhà Giả Kim', 'Tâm lý', '1988-04-15', 220000, 4),
    ( 'S005', 'IT', 'Kinh dị', '1986-09-15', 300000, 5),
    ( 'S006', 'Án Mạng Trên Sông Nile', 'Trinh thám', '1937-01-01', 190000, 6),
    ( 'S007', 'Mật Mã Da Vinci', 'Trinh thám', '2003-03-18', 280000, 7),
    ( 'S008', 'Gatsby Vĩ Đại', 'Văn học cổ điển', '1925-04-10', 210000, 8),
    ( 'S009', 'Những Người Khốn Khổ', 'Văn học Pháp', '1862-01-01', 350000, 9),
    ( 'S010', 'Tôi Thấy Hoa Vàng Trên Cỏ Xanh', 'Văn học thiếu nhi', '2010-03-20', 150000, 10);