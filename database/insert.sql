use fruit;
INSERT INTO `fruit`.`role` (`name`, `status`) VALUES ('admin', '1');
INSERT INTO `fruit`.`role` (`name`, `status`) VALUES ('client', '1');
INSERT INTO `fruit`.`role` (`name`, `status`) VALUES ('manager', '1');
-- category
INSERT INTO `fruit`.`category` (`name`, `status`,`slug`) VALUES ('Trái cây xuất khẩu', '1','trai-cay-xuat-khau');
INSERT INTO `fruit`.`category` (`name`, `status`,`slug`) VALUES ('Trái cây nhập khẩu', '1','trai-cay-nhap-khau');
INSERT INTO `fruit`.`category` (`name`, `status`,`slug`) VALUES ('Trái cây nội địa', '1','trai-cay-noi-dia');

INSERT INTO `fruit`.`user` (`email`, `password`,`enabled`) VALUES ('root@gmail.com', '$2a$10$vKa8QA3QDni7s7vm2CDRYeZHymc8zyGOSzQqr5fUL.rsFJj9QrJWi',1);
insert into `fruit`.`user_role` (`user_id`, `role_id`) values (1, 1);

-- nhap khau
INSERT INTO `fruit`.`product`
(`slug`,`name`,`price`,`description`,`discount`,`quantity`,`status`,`category_id`,`created_by`,`created_at`, `update_by`,`update_at`)
VALUES ('kiwi-vang-newzealand','Kiwi vàng Newzealand (túi 300g)','100000',
        '<p>Kiwi của Newzealand ngon số 1 thế giới. Toàn bộ quy trình từ trồng, chăm bón đến thu, hái, vận chuyển đều được chính phủ Newzealand kiểm soát rất chặt chẽ để đảm bảo trái Kiwi Newzealand luôn đồng đều và đạt tiêu chuẩn cao nhất.<br>
<strong>Đặc điểm:</strong>&nbsp;Quả kiwi này có ruột bên trong màu vàng, có vỏ mượt, màu đồng, ở đầu quả có đài giống như vương miện. Quả kiwi vàng có thể ăn được ngay khi mua, vì vậy bạn có thể ngay lập tức thưởng thức được vị ngọt ngào của nó.<br>
<strong>Mùa vụ:&nbsp;</strong>từ tháng 5 đến tháng 1<br>
<strong>Giá trị dinh dưỡng:</strong><br>
- Kiwi cung cấp nguồn Vitamin C và E, kali và chất xơ.<br>
- Hương vị ngọt ngào vùng nhiệt đới, thơm mát đã khiến nó trở thành món giải khát mùa hè tuyệt vời. Bạn có thể cắt đôi quả kiwi vàng ra, dùng thìa xúc ruột ăn. Bạn cũng có thể trộn nó cùng với đồ uống mà bạn thích, bày trên món tráng miệng, cho vào món xa lát, hoặc dùng cả quả kiwi xanh và vàng cho bữa tiệc đầy màu sắc và hương vị.</p>
<h3><strong>Lợi ích bổ dưỡng từ trái kiwi có tác dụng gì đến sức khỏe ?</strong></h3>
<p><br>
Theo phân tích của các chuyên gia dinh dưỡng, trong 1 trái kiwi các vitamin E, A, C, B1, B2 cùng các khoáng chất: 0,26 mg niacin; 19 mcg folate;&nbsp; 237 mg kali và 26 mg&nbsp; photpho. Ngoài ra còn có một lượng nhỏ magie, sodium, canxi, sắt, đồng, kẽm, mangan. giúp ích có các bệnh như :&nbsp;ngừa các bệnh hô hấp,&nbsp;ngừa các bệnh tim mạch,&nbsp;chăm sóc nuôi dưỡng da,&nbsp;Tốt cho phụ nữ mang thai, ngăn ngừa bệnh ung thư,&nbsp;Kiểm soát huyết áp, giúp xương chắc khỏe,&nbsp;</p>
<p><strong>Kiwi xanh hay kiwi vàng tốt hơn?</strong><br>
&nbsp;</p>
<p>Trong kiwi xanh có hàm lượng vitamin C gấp đôi trái cam, hàm lượng vitamin K lớn hơn vitamin K trong 1 trái chuối. Ngoài ra, hàm lượng chất xơ có trong trái kiwi cũng lớn hơn hàm lượng chất xơ có trong một chén cơm.</p>
<p>Với kiwi vàng, ngoài những chất khoáng tương tự như kiwi xanh, nó còn cung cấp thêm cho cơ thể chất sắt là 4% và 15% vitamin E, 13% axit folate. Đặc biệt hàm lượng vitamin C trong kiwi vàng lên tới 270%.</p>
<p>Ngoài ra, trên thị trường, kiwi vàng luôn có giá bán cao hơn so với kiwi xanh.Từ những so sánh trên cho thấy, kiwi vàng tốt hơn so với kiwi xanh.</p>',
        '10','20','1','2','user','2023-10-01 11:40:44','user','2023-10-01 11:40:44');
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('kiwi-vang-newzealand-vinfruits-1-min-900x900.jpg', 1);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('kiwi-vang-newzealand-vinfruits-2-min-900x900.jpg', 1);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('kiwi-vang-newzealand-vinfruits-6-min-900x900.jpg', 1);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('kiwi-vang-newzealand-vinfruits-7-min-900x900.jpg', 1);

INSERT INTO `fruit`.`product`
(`slug`,`name`,`price`,`discount`,`quantity`,`status`,`category_id`,`created_by`,`created_at`, `update_by`,`update_at`)
VALUES ('le-han-quoc-iqa','Lê Hàn Quốc', '200000','10','20', '1','2','user','2023-10-01 11:40:44', 'user', '2023-10-01 11:40:44');
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('le-han-quoc.jpg', 2);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('le-han-quoc-vinfruits-5-min-900x900.jpg', 2);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('le-han-quoc-vinfruits-2-min-900x900.jpg', 2);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('le-han-quoc-vinfruits-4-min-900x900.jpg', 2);

INSERT INTO `fruit`.`product`
(`slug`,`name`,`price`,`discount`, `quantity`,`status`,`category_id`, `created_by`,`created_at`,`update_by`,`update_at`)
VALUES ('sau-rieng-musang-king-malaysia-400g','Sầu riêng Musang King Malaysia 400G','499000','10','20', '1','2','user','2023-10-01 11:40:44','user','2023-10-01 11:40:44');
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('sau-rieng-musang-king-malaysia.png', 3);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('sau-rieng-musang-king-malaysia-2.png', 3);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('sau-rieng-musang-king-malaysia-3.png', 3);

INSERT INTO `fruit`.`product`
(`slug`,`name`,`price`,`discount`, `quantity`,`status`,`category_id`, `created_by`,`created_at`,`update_by`,`update_at`)
VALUES ('nho-den-my-tui-1kg','Nho đen Mỹ túi 1kg','320000','10','20', '1','2','user','2023-10-01 11:40:44','user','2023-10-01 11:40:44');
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('nho-den-my-vinfruits-4-min-900x900.jpg', 4);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('nho-den-my-vinfruits-1-min-900x900.jpg', 4);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('nho-den-my-vinfruits-2-min-900x900.jpg', 4);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('nho-den-my-vinfruits-3-min-900x900.jpg', 4);

INSERT INTO `fruit`.`product`
(`slug`,`name`,`price`,`discount`, `quantity`,`status`,`category_id`, `created_by`,`created_at`,`update_by`,`update_at`)
VALUES ('na-dai-loan','Na Đài Loan túi 500g','59000','10','20', '1','2','user','2023-10-01 11:40:44','user','2023-10-01 11:40:44');
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('na-dai-dai-loan.jpg', 5);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('na-dai-dai-loan-2.jpg', 5);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('na-dai-dai-loan-3.jpg', 5);

INSERT INTO `fruit`.`product`
(`slug`,`name`,`price`,`discount`, `quantity`,`status`,`category_id`, `created_by`,`created_at`,`update_by`,`update_at`)
VALUES ('dua-luoi-dai-loan','Dưa lưới Đài Loan (túi 900g)','89000','10','20', '1','2','user','2023-10-01 11:40:44','user','2023-10-01 11:40:44');
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('dua-luoi-dai-loan.jpg', 6);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('dua-luoi-dai-loan-vinfruits-3-900x900.jpg', 6);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('dua-luoi-dai-loan-vinfruits-2-900x900.jpg', 6);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('dua-luoi-dai-loan-vinfruits-4-900x900.jpg', 6);

INSERT INTO `fruit`.`product`
(`slug`,`name`,`price`,`discount`, `quantity`,`status`,`category_id`, `created_by`,`created_at`,`update_by`,`update_at`)
VALUES ('tao-xanh-my','Táo xanh Mỹ (túi 700g)','65000','10','20', '1','2','user','2023-10-01 11:40:44','user','2023-10-01 11:40:44');
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('tao-xanh-my (1).jpg', 7);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('tao-xanh-my (2).jpg', 7);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('tao-xanh-my (3).jpg', 7);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('tao-xanh-my (4).jpg', 7);

-- noi dia
INSERT INTO `fruit`.`product`
(`slug`,`name`,`price`,`discount`, `quantity`,`status`,`category_id`, `created_by`,`created_at`,`update_by`,`update_at`)
VALUES ('dua-xiem-troc-ben-tre-tui-450-600g','Dừa xiêm trọc Bến Tre size M (450-600g/trái)','23000','10','20', '1','3','user','2023-10-01 11:40:44','user','2023-10-01 11:40:44');
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('dua-sim-doc-ben-tre-1.webp', 8);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('dua-sim-doc-ben-tre-2.webp', 8);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('dua-sim-doc-ben-tre-3.webp', 8);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('dua-sim-doc-ben-tre-4.webp', 8);

INSERT INTO `fruit`.`product`
(`slug`,`name`,`price`,`discount`, `quantity`,`status`,`category_id`, `created_by`,`created_at`,`update_by`,`update_at`)
VALUES ('thanh-long-ruot-do-tui-600gr','Thanh Long Đỏ - trái 600gr','23000','10','20', '1','3','user','2023-10-01 11:40:44','user','2023-10-01 11:40:44');
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('thanh-long-ruot-do (1).jpg',9);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('thanh-long-ruot-do (2).jpg', 9);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('thanh-long-ruot-do (3).jpg', 9);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('thanh-long-ruot-do (4).jpg', 9);

INSERT INTO `fruit`.`product`
(`slug`,`name`,`price`,`discount`, `quantity`,`status`,`category_id`, `created_by`,`created_at`,`update_by`,`update_at`)
VALUES ('bo-sap-tui-1kg','Bơ sáp túi 1kg','52000','10','20', '1','3','user','2023-10-01 11:40:44','user','2023-10-01 11:40:44');
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('bo-sap1.webp',10);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('bo-sap2.webp', 10);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('bo-sap3.webp', 10);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('bo-sap4.webp', 10);

INSERT INTO `fruit`.`product`
(`slug`,`name`,`price`,`discount`, `quantity`,`status`,`category_id`, `created_by`,`created_at`,`update_by`,`update_at`)
VALUES ('vu-sua-lo-ren-tui-1kg','Vú Sữa Lò Rèn (Kg)','152000','10','20', '1','3','user','2023-10-01 11:40:44','user','2023-10-01 11:40:44');
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('vu-sua-lo-ren (1).webp', 11);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('vu-sua-lo-ren (2).webp', 11);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('vu-sua-lo-ren (3).webp', 11);

INSERT INTO `fruit`.`product`
(`slug`,`name`,`price`,`discount`, `quantity`,`status`,`category_id`, `created_by`,`created_at`,`update_by`,`update_at`)
VALUES ('luu-bi-mat','Lựu bí mật túi 500gram','42000','10','20', '1','3','user','2023-10-01 11:40:44','user','2023-10-01 11:40:44');
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('luu-bi-mat (1).jpg', 12);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('luu-bi-mat (2).jpg', 12);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('luu-bi-mat (3).jpg', 12);

INSERT INTO `fruit`.`product`
(`slug`,`name`,`price`,`discount`, `quantity`,`status`,`category_id`, `created_by`,`created_at`,`update_by`,`update_at`)
VALUES ('man-hong-rat-ngon','Mận hồng túi 600gram','42000','10','20', '1','3','user','2023-10-01 11:40:44','user','2023-10-01 11:40:44');
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('man-hong (1).jpg', 13);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('man-hong (2).jpg', 13);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('man-hong (3).jpg', 13);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('man-hong (4).jpg', 13);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('man-hong (5).jpg', 13);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('man-hong (6).jpg', 13);

INSERT INTO `fruit`.`product`
(`slug`,`name`,`price`,`discount`, `quantity`,`status`,`category_id`, `created_by`,`created_at`,`update_by`,`update_at`)
VALUES ('chom-chom-thai-ngon','Chôm chôm Thái 600gram','34000','10','20', '1','3','user','2023-10-01 11:40:44','user','2023-10-01 11:40:44');
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('chom-chom (1).webp', 14);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('chom-chom (2).webp', 14);
INSERT INTO `fruit`.`image` (`link`, `product_id`) VALUES ('chom-chom (3).webp', 14);

-- shipping status
INSERT INTO `fruit`.`shipping_status` (`name`) VALUES ('VERIFIED');
INSERT INTO `fruit`.`shipping_status` (`name`) VALUES ('UNVERIFIED');
INSERT INTO `fruit`.`shipping_status` (`name`) VALUES ('DELIVERING');
INSERT INTO `fruit`.`shipping_status` (`name`) VALUES ('DELIVERED');
INSERT INTO `fruit`.`shipping_status` (`name`) VALUES ('CANCELED');
INSERT INTO `fruit`.`shipping_status` (`name`) VALUES ('CANCELING');
