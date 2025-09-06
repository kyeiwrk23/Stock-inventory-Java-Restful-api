insert into role(role_name) values('ROLE_ADMIN');
insert into role(role_name) values('ROLE_USER');

## Category Data

insert into category(category_name,description,parent_category,active) values('Electronics','Devices and electronic equipment','Electronics','true');
insert into category(category_name,description,parent_category,active) values('Mobile Phones','Smartphones and related accessories','Electronics','true');
insert into category(category_name,description,parent_category,active) values('Laptops','All brands of laptops and notebooks','Electronics','true');
insert into category(category_name,description,parent_category,active) values('Kitchen Appliances','Small appliances used in the kitchen','Home Appliances','true');
insert into category(category_name,description,parent_category,active) values('Clothing','All types of wearable garments','Clothing','true');
insert into category(category_name,description,parent_category,active) values('Men Clothing','Clothing items designed for men','Clothing','true');
insert into category(category_name,description,parent_category,active) values('Womens Clothing','Clothing items designed for women','Clothing','true');
insert into category(category_name,description,parent_category,active) values('Furniture','Household and office furniture','None','true');
insert into category(category_name,description,parent_category,active) values('Office Supplies','Stationery and office consumables','None','true');
insert into category(category_name,description,parent_category,active) values('Sports Equipment','Equipment and gear for sports and fitness','None','true');
insert into category(category_name,description,parent_category,active) values('Automotive Parts','Car accessories and replacement parts','Automotive','true');
insert into category(category_name,description,parent_category,active) values('Toys & Games','Items for children and adults for play and entertainment','None','true');
insert into category(category_name,description,parent_category,active) values('Health & Personal Care','Health products, grooming, and personal care items','None','true');
insert into category(category_name,description,parent_category,active) values('Books & Media','Books,DVDs, and digital media','None','true');

##Supplier and Address Data

insert into supplier(supplier_name,email,phone,tax_code,contact_person,website,payment_terms,notes,created_at,last_updated,is_active)
values('Global Appliances Co.','john.smith@globalappliances.com','+1-800-555-9876','98-7654321','John Smith','https://globalappliances.com','Net 45','Handles bulk appliance orders','2023-03-20 10:00:00','2025-07-01 12:00:00',true);

insert into address (supplier_id,street_name,city,state,zip,country) values (1,'789 Kitchen St.','Chicago','IL','60616','USA');

insert into supplier(supplier_name,email,phone,tax_code,contact_person,website,payment_terms,notes,created_at,last_updated,is_active)
values('BrightTech Solutions','alicia@brighttech.io','+44 20 7946 0000','GB123456789','Alicia Brown','https://brighttech.io','Net 60','Supplies IT hardware','2023-05-10 09:15:00','2025-07-01 12:00:00',true);

insert into address (supplier_id,street_name,city,state,zip,country) values (2,'745 Silicon Ave','London','London','SW1A 1AA','UK');

insert into supplier(supplier_name,email,phone,tax_code,contact_person,website,payment_terms,notes,created_at,last_updated,is_active)
values('StyleWear Inc.','m.green@stylewear.com','+1-212-555-2020','11-9876543','Michael Green','https://stylewear.com','Prepaid','Supplier of fashion and clothing','2022-11-01 11:00:00','2025-01-12 10:30:00',true);

insert into address (supplier_id,street_name,city,state,zip,country) values (3,'500 Fashion Blvd','New York','NY','10001','USA');


insert into supplier(supplier_name,email,phone,tax_code,contact_person,website,payment_terms,notes,created_at,last_updated,is_active)
values('FitGear Distributors','emma@fitgear.com','+1-888-555-7777','88-1122334','Emma White','https://fitgear.com','Net 30','Gym equipment supplier','2023-02-18 08:45:00','2023-02-18 08:45:00',true);

insert into address (supplier_id,street_name,city,state,zip,country) values (4,'350 Fitness Road','Denver','CO','80202','USA');

insert into supplier(supplier_name,email,phone,tax_code,contact_person,website,payment_terms,notes,created_at,last_updated,is_active)
values('EcoHouse Ltd.','sarah@ecohouse.uk','+44 161 555 8080','GB556677889','Sarah Johnson','https://ecohouse.uk','Net 60','Inactive: no recent orders','2021-08-30 15:10:00','2023-12-01 17:00:00',true);

insert into address (supplier_id,street_name,city,state,zip,country) values (5,'22 Green Lane','Manchester','Greater MCR','M1 1AE','UK');

insert into supplier(supplier_name,email,phone,tax_code,contact_person,website,payment_terms,notes,created_at,last_updated,is_active)
values('AutoParts Inc.','r.king@autoparts.com','+1-310-555-3210','55-6677889','Robert King','https://autoparts.com','Net 30','Car and bike spare parts','2023-04-07 13:00:00','2025-08-10 11:40:00',true);

insert into address (supplier_id,street_name,city,state,zip,country) values (6,'987 Motorway Blvd','Los Angeles','CA','90001','USA');

insert into supplier(supplier_name,email,phone,tax_code,contact_person,website,payment_terms,notes,created_at,last_updated,is_active)
values('BookHive Publishing','natalie@bookhive.com','+1-646-555-3030','22-3344556','Natalie Fisher','https://bookhive.com','Net 15','Supplies educational and fiction books','2022-09-19 10:25:00','2025-04-22 09:30:00',true);

insert into address (supplier_id,street_name,city,state,zip,country) values (7,'10 Reading Ave','Boston','MA','02110','USA');

insert into supplier(supplier_name,email,phone,tax_code,contact_person,website,payment_terms,notes,created_at,last_updated,is_active)
values('MedSupply Global','alan.wu@medsupply.com','+1-202-555-9090','22-3344556','Dr. Alan Wu','https://medsupply.com','Net 45','Hospital and clinic supplies','2023-06-01 12:00:00','2025-07-30 15:15:00',true);

insert into address (supplier_id,street_name,city,state,zip,country) values (8,'150 Wellness Street','Washington','DC','20001','USA');

insert into supplier(supplier_name,email,phone,tax_code,contact_person,website,payment_terms,notes,created_at,last_updated,is_active)
values('Orion Furniture','carlos@orionfurniture.com','+1-702-555-6161','33-2211333','Carlos Rivera','https://orionfurniture.com','Net 60','Office and home furniture provider','2023-07-12 11:30:00','2025-08-20 17:45:00',true);

insert into address (supplier_id,street_name,city,state,zip,country) values (9,'789 Home Decor Ave','Las Vegas','NV','89101','USA');


## Items Data

insert into inventory_item(item_name,stock,unit_price,re_order_level,description,status,category_id,supplier_id) values('iPhone 14 Pro',20,900.00,10,'Apple smartphone, 128GB, Space Black','In stock',2,3);
insert into inventory_item(item_name,stock,unit_price,re_order_level,description,status,category_id,supplier_id) values('Samsung Galaxy S23',15,899.00,10,'Samsung smartphone, 256GB, Phantom Black','In Stock',2,3);
insert into inventory_item(item_name,stock,unit_price,re_order_level,description,status,category_id,supplier_id) values('Dell XPS 13',10,1299.00,5,'Dell 13" Laptop, i7, 16GB RAM, 512GB SSD','Low Stock',3,3);
insert into inventory_item(item_name,stock,unit_price,re_order_level,description,status,category_id,supplier_id) values('Sony 65" 4K TV',5,1199.00,10,'UHD Smart TV with HDR','Low Stock',1,1);
insert into inventory_item(item_name,stock,unit_price,re_order_level,description,status,category_id,supplier_id) values('Microwave Oven 1000W',20,149.00,8,'Compact microwave with digital controls','In Stock',4,2);
insert into inventory_item(item_name,stock,unit_price,re_order_level,description,status,category_id,supplier_id) values('Mens T-Shirt (Large)',100,12.99,30,'100% cotton, crew neck, black','In Stock',6,6);
insert into inventory_item(item_name,stock,unit_price,re_order_level,description,status,category_id,supplier_id) values('Office Desk',8,249.00,3,'Wooden desk with drawers, 120x60 cm','In Stock',9,8);
insert into inventory_item(item_name,stock,unit_price,re_order_level,description,status,category_id,supplier_id) values('Yoga Mat',50,25.00,20,'Non-slip yoga mat, 6mm thickness','In Stock',10,5);
insert into inventory_item(item_name,stock,unit_price,re_order_level,description,status,category_id,supplier_id) values('Motor Oil 5W-30 (1L)',60,7.50,15,'Synthetic engine oil, 5W-30, 1L bottle','In Stock',11,7);
insert into inventory_item(item_name,stock,unit_price,re_order_level,description,status,category_id,supplier_id) values('Electric Toothbrush',35,39.99,10,'Rechargeable, 3 brushing modes','In Stock',13,9);
insert into inventory_item(item_name,stock,unit_price,re_order_level,description,status,category_id,supplier_id) values('Microwave Oven 1000W',20,149.00,8,'Compact microwave with digital controls','In Stock',4,2);
insert into inventory_item(item_name,stock,unit_price,re_order_level,description,status,category_id,supplier_id) values('Laptop Charger',18,22.00,10,'65W universal laptop charger','In Stock',3,3);
insert into inventory_item(item_name,stock,unit_price,re_order_level,description,status,category_id,supplier_id) values('Binder Clip (Large)',500,0.15,300,'2-inch black binder clip','In Stock',9,8);
insert into inventory_item(item_name,stock,unit_price,re_order_level,description,status,category_id,supplier_id) values('Projector',2,230.00,2,'1080p HD projector','In Stock',3,3);
insert into inventory_item(item_name,stock,unit_price,re_order_level,description,status,category_id,supplier_id) values('External Hard Drive',4,59.00,5,'1TB USB 3.0 portable HDD','Low Stock',3,3);
insert into inventory_item(item_name,stock,unit_price,re_order_level,description,status,category_id,supplier_id) values('Sticky Notes',95,1.20,40,'Pack of 3x3 inch sticky notes','In Stock',9,8);
insert into inventory_item(item_name,stock,unit_price,re_order_level,description,status,category_id,supplier_id) values('Desk Organizer',9,7.50,10,'5-compartment plastic desk caddy','Low Stock',9,8);
insert into inventory_item(item_name,stock,unit_price,re_order_level,description,status,category_id,supplier_id) values('HDMI Cable',80,5.00,40,'6ft HDMI 2.0 cable','In Stock',3,3);
insert into inventory_item(item_name,stock,unit_price,re_order_level,description,status,category_id,supplier_id) values('Whiteboard Markers',14,3.60,10,'Set of 4 assorted markers','In Stock',9,8);
insert into inventory_item(item_name,stock,unit_price,re_order_level,description,status,category_id,supplier_id) values('Toner Cartridge',2,45.00,5,'Black toner for HP LaserJet','Reorder Needed',3,3);
insert into inventory_item(item_name,stock,unit_price,re_order_level,description,status,category_id,supplier_id) values('Ethernet Cable',45,6.00,20,'10m CAT6 network cable','In Stock',1,1);
