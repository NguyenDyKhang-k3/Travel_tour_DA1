CREATE DATABASE TravelTour
USE TravelTour
GO

CREATE TABLE NHANVIEN(
MaNV varchar(20) primary key not null,
MatKhau varchar(20) null,
HoTen nvarchar(50) not null,
NgayVaoLam date not null,
NgaySinh date not null,
SDT char(12) not null,
ChucVu nvarchar(50) not null,
Hinh varchar(50) not null,
TrangThai nvarchar(50) not null
)

CREATE TABLE TOUR(
MaTour varchar(6) primary key not null,
TenTour nvarchar(50) not null,
DiemDi nvarchar(50) not null,
DiemDen nvarchar(50) not null,
Gia float not null,
GhiChu nvarchar(100) null,
NgayTao date default getdate()
)

CREATE TABLE KHACHHANG(
MaKH varchar(6) primary key not null,
TenKH nvarchar(50) not null,
GioiTinh bit default 0,
QueQuan nvarchar(50) not null,
NgaySinh date,
GTTT char(15) not null,--căn cước công dân, số định danh,....
SDT char(12) not null,--có thể trùng
Email varchar(50) null,
GhiChu nvarchar(100) null
)

CREATE TABLE LICHTRINH(
MaLT varchar(6) primary key not null,
MaNV varchar(20) not null references NHANVIEN(MaNV),
MaKH varchar(6) not null references KHACHHANG(MaKH) ON DELETE NO ACTION ON UPDATE CASCADE,
MaTour varchar(6) not null references TOUR(matour),
NgayBD date not null,
GioDi time(0) not null,
NgayKT date not null,
GioDen time(0) not null,
HuongDV nvarchar(100) not null,
PhuThu float default 0 not null,
SoKhach int not null,
DiaDiemDD nvarchar(50) not null,
TrangThai nvarchar(50) not null
)

CREATE TABLE LICHTRINH_KHACHHANG(
MaLT varchar(6) not null references LICHTRINH(MaLT) ON DELETE NO ACTION ON UPDATE CASCADE,
MaKH varchar(6) not null references KHACHHANG(MaKH)
)

CREATE TABLE KHACHSAN(
MaKS varchar(6) primary key not null,
TenKS nvarchar(50) not null,
SDT char(12) not null,
GiaThue float not null,
DiaChi nvarchar(50) not null,
GhiChu nvarchar(100) null
)

CREATE TABLE LICHTRINH_KHACHSAN(
MaLT varchar(6) not null references LICHTRINH(MaLT ) ON DELETE NO ACTION ON UPDATE CASCADE,
MaKS varchar(6) not null references KHACHSAN(MaKS) ON DELETE NO ACTION ON UPDATE CASCADE
)

CREATE TABLE LOAIPHUONGTIEN(
MaLoaiPT varchar(6) primary key not null,
TenLoaiPT nvarchar(50) not null
)

CREATE TABLE PHUONGTIEN(
MaPT varchar(6) primary key not null,
TenPT nvarchar(50) not null,
MaLoaiPT varchar(6)  not null references LOAIPHUONGTIEN(MaLoaiPT) ON DELETE NO ACTION ON UPDATE CASCADE,
SDT char(12) not null,
Gia float not null,
DiaChi nvarchar(50) not null,
BienSo nvarchar(50) not null,
GhiChu nvarchar(100) null
)

CREATE TABLE LICHTRINH_PHUONGTIEN(
MaLT varchar(6) not null references LICHTRINH(MaLT) ON DELETE NO ACTION ON UPDATE CASCADE,
MaPT varchar(6) not null references PHUONGTIEN(MaPT) ON DELETE NO ACTION ON UPDATE CASCADE
)

CREATE TABLE THAMQUAN(
MaDD varchar(6) primary key not null,
TenDD nvarchar(50) not null,
LoaiDV nvarchar(50) not null,
SDT char(12) not null,
Gia float not null,
DiaChi nvarchar(50) not null,
GhiChu nvarchar(100) null
)

CREATE TABLE LICHTRINH_THAMQUAN(
MaLT varchar(6) not null references LICHTRINH(MaLT) ON DELETE NO ACTION ON UPDATE CASCADE,
MaDD varchar(6) not null references THAMQUAN(MaDD) ON DELETE NO ACTION ON UPDATE CASCADE
)

CREATE TABLE HOPDONG(--hóa đơn đầu tiên
MaHopDong varchar(6) primary key not null,
MaLT varchar(6) not null references LICHTRINH(MaLT) ON DELETE NO ACTION ON UPDATE CASCADE,
LoaiThanhToan nvarchar(50) not null,
TongTien float not null,
TienCoc float not null,
NgayLapHopDong date default getdate(),
MaNV varchar(20) not null references NHANVIEN(MaNV),
TrangThai nvarchar(50) not null
)

CREATE TABLE HOADON(--hóa đơn thứ hai (trả tiền còn lại)
MaHoaDon varchar(6) primary key not null,
MaHopDong varchar(6) references HOPDONG(mahopdong) ON DELETE NO ACTION ON UPDATE CASCADE,
TrangThai nvarchar(50) not null,
NgayLapHoaDon date default getdate(),
GhiChu nvarchar(50) null
)

CREATE TABLE LICHSUHD(
MaLS INT IDENTITY(1,1) PRIMARY KEY not null,
MaNV varchar(20) not null references NHANVIEN(MaNV) ON DELETE NO ACTION ON UPDATE CASCADE,
NgayTruyCap DATE not null,
ThoiGianTruyCap TIME(0) not null,
HoatDong nvarchar(50) not null
)

SELECT COUNT(lt.MaTour), SUM(tr.Gia*lt.SoKhach) TongDthuTour
FROM LICHTRINH lt 
JOIN TOUR tr ON lt.MaTour = tr.MaTour
JOIN dbo.HOPDONG hdong ON hdong.MaLT = lt.MaLT
JOIN dbo.HOADON hdon ON hdon.MaHopDong = hdong.MaHopDong
WHERE MONTH(hdon.NgayLapHoaDon) IN ('1', '2', '3')  
AND YEAR(hdon.NgayLapHoaDon) = '2023'

SELECT COUNT(lt.MaTour), SUM(tr.Gia*lt.SoKhach) TongDthuTour
FROM LICHTRINH lt 
JOIN TOUR tr ON lt.MaTour = tr.MaTour
JOIN dbo.HOPDONG hdong ON hdong.MaLT = lt.MaLT
JOIN dbo.HOADON hdon ON hdon.MaHopDong = hdong.MaHopDong
WHERE MONTH(hdon.NgayLapHoaDon) = '1'

SELECT ROW_NUMBER() OVER (ORDER BY tr.MaTour) STT,tr.MaTour, tr.TenTour, hdon.NgayLapHoaDon, COUNT(lt.MaTour) as 'So Tour Da Mo', 
SUM(lt.SoKhach) LuongKhach, SUM(tr.Gia*lt.SoKhach) TongDthuTour
FROM LICHTRINH lt 
JOIN TOUR tr ON lt.MaTour = tr.MaTour
JOIN dbo.HOPDONG hdong ON hdong.MaLT = lt.MaLT
JOIN dbo.HOADON hdon ON hdon.MaHopDong = hdong.MaHopDong
 WHERE MONTH(hdon.NgayLapHoaDon) IN ('1', '2', '3')  
AND YEAR(hdon.NgayLapHoaDon) = '2023'
GROUP BY tr.MaTour, tr.TenTour, hdon.NgayLapHoaDon

SELECT * FROM dbo.LICHTRINH WHERE MaLT = 'LT0043'

SELECT Gia FROM dbo.TOUR t
JOIN dbo.LICHTRINH lt
ON lt.MaTour = t.MaTour
WHERE lt.MaLT = 'LT0043'

SELECT ROW_NUMBER() OVER (ORDER BY MaKH), MaKH , TenKH, GioiTinh, NgaySinh, GTTT, SDT, Email FROM dbo.KHACHHANG 
WHERE MaKH LIKE N'KH0001'
OR TenKH LIKE N'Lê Quang Nhựt'
OR GTTT LIKE N''
OR SDT LIKE ''
OR Email LIKE ''
AND MaKH NOT IN(SELECT MaKH FROM dbo.LICHTRINH_KHACHHANG WHERE MaLT = 'LT0002') 

SELECT ROW_NUMBER() OVER (ORDER BY MaHoaDon), 
MaHoaDon, CONCAT(hd.MaHopDong, ' - ', t.TenTour, ' - ', lt.MaLT), GioLapHoaDon,
NgayLapHoaDon, hd.TrangThai, hd.GhiChu, NguoiTao 
FROM dbo.HOADON hd
JOIN dbo.HOPDONG hdong ON hdong.MaHopDong = hd.MaHopDong
JOIN dbo.LICHTRINH lt ON lt.MaLT = hdong.MaLT
JOIN dbo.TOUR t ON t.MaTour = lt.MaTour
WHERE hd.MaHoaDon LIKE ''
OR CONCAT(hd.MaHopDong, ' - ', t.TenTour, ' - ', lt.MaLT) LIKE ''
OR hd.NgayLapHoaDon LIKE ''

SELECT CONCAT(kh.TenKH, ' - ', kh.GTTT) FROM dbo.KHACHHANG kh
JOIN dbo.LICHTRINH lt ON lt.MaKH = kh.MaKH
WHERE CONCAT(kh.TenKH, ' - ', kh.GTTT) 
NOT IN(SELECT CONCAT(kh.TenKH, ' - ', kh.GTTT) FROM dbo.LICHTRINH_KHACHHANG
WHERE CONCAT(kh.TenKH, ' - ', kh.GTTT) = N'Hồ Thị Hà - 089784522512')

SELECT SUM(HOPDONG.TongTien) AS dt
FROM HOADON inner join HOPDONG on HOADON.MaHopDong = HOPDONG.MaHopDong
where YEAR(NgayLapHoaDon) = '2023'
GROUP BY YEAR(NgayLapHoaDon)

SELECT ROW_NUMBER() OVER (ORDER BY kh.MaKH) STT, CONCAT(ltkh.MaLT, ' - ', t.TenTour), CONCAT(ltkh.MaKH, ' - ', kh.TenKH)
FROM LICHTRINH_KHACHHANG ltkh
JOIN KHACHHANG kh ON ltkh.MaKH = kh.MaKH 
JOIN dbo.LICHTRINH lt ON lt.MaLT = ltkh.MaLT
JOIN dbo.TOUR t ON t.MaTour = lt.MaTour
WHERE kh.MaKH = 'KH0001'
GROUP BY kh.MaKH, CONCAT(ltkh.MaLT, ' - ', t.TenTour),  CONCAT(ltkh.MaKH, ' - ', kh.TenKH)

SELECT COUNT(lt.MaTour) SoTour, SUM(tr.Gia*lt.SoKhach) TongDthuTour, sum(lt.SoKhach) SoKhach
FROM LICHTRINH lt 
JOIN TOUR tr ON lt.MaTour = tr.MaTour
JOIN dbo.HOPDONG hdong ON hdong.MaLT = lt.MaLT
JOIN dbo.HOADON hdon ON hdon.MaHopDong = hdong.MaHopDong
WHERE DATEPART(quarter,NgayLapHoaDon) = '1' --quý nè yêu quái
AND YEAR(hdon.NgayLapHoaDon) = '2022'

SELECT CONCAT(lt.MaLT, ' - ', t.TenTour, ' - ', kh.TenKH, ' - ', lt.TrangThai) FROM LICHTRINH lt INNER JOIN KHACHHANG kh ON kh.MaKH = lt.MaKH 
INNER JOIN dbo.TOUR t
ON t.MaTour = lt.MaTour

SELECT ROW_NUMBER() OVER (ORDER BY MaKH), MaKH , TenKH, GioiTinh, NgaySinh, GTTT, SDT, Email FROM dbo.KHACHHANG 
WHERE MaKH LIKE 'KH0001'
OR TenKH LIKE ''
OR GTTT LIKE ''
OR SDT LIKE ''
OR Email LIKE ''
AND MaKH NOT IN(SELECT MaKH FROM dbo.LICHTRINH_KHACHHANG WHERE MaLT = 'LT0001')

SELECT ROW_NUMBER() OVER (ORDER BY MaKH), MaKH , TenKH, GioiTinh, NgaySinh, GTTT, SDT, Email 
FROM dbo.KHACHHANG 
WHERE (MaKH LIKE 'KH0001' OR TenKH LIKE '' OR GTTT LIKE '' OR SDT LIKE '' OR Email LIKE '') 
AND MaKH NOT IN(SELECT MaKH FROM dbo.LICHTRINH_KHACHHANG WHERE MaLT = 'LT0001')

SELECT COUNT(MaLT) FROM dbo.LICHTRINH