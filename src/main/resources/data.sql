insert into users (login, password, email, phone, add_date) values ('admin', '$2a$10$lbAayA2.UfYySGZvt3x03OcLCpg5lJDnw1v..zqIkVRA/6FbvV50K', 'admin@gmail.com', '123123123', '2024-12-03 14:07:15.633000');
insert into users (login, password, email, phone, add_date) values ('rokan123', '$2a$10$DzQ4RyeO3OOxA68QByzM7uBrjPxQf6zH8BQatNoUEZolzibHMMO9O', 'rokan123@gmail.com', '123456789', '2024-12-03 17:36:04.198000');
insert into users (login, password, email, phone, add_date) values ('mikolaj', '$2a$10$KOdSXkaY.nnYZ1uL1XVlRONUBa/sAFpTRojNvo9LFQIgzdke4RRHm', 'mikolaj@onet.pl', '123123123', '2024-12-20 17:19:50.591000');
insert into users (login, password, email, phone, add_date) values ('alamakota', '$2a$10$VGEEy3LXPG94.iKNqkooZOR9GzJdpabCzgxhcS9hq1dbBS9Yo66Lu', 'alamakota@onet.pl', '123123123', '2024-12-20 18:49:15.224000');
insert into users (login, password, email, phone, add_date) values ( 'misiek123', '$2a$10$cAGm0VNatGrs90GXjkSG3uDjsHjLSVXYfYugu9niv70SJcy3HgCpy', 'misiek123', '123123123', '2024-12-30 15:00:14.757000');
insert into users (login, password, email, phone, add_date) values ( 'adam123', '$2a$10$u3vyuDLGAYhQim4mtCNsb.XK/h0uhV/pzwFxi7tLWI6fFTrDthuZi', 'adam123@onet.pl', '123123123', '2024-12-30 15:05:13.389000');


insert into brands (name) values ('Opel');
insert into brands (name) values ('Audi');
insert into brands (name) values ( 'BMW');
insert into brands (name) values ( 'Mercedes-Benz');
insert into brands (name) values ('Mazda');
insert into brands (name) values ( 'Volvo');
insert into brands (name) values ('Volkswagen');
insert into brands (name) values ( 'Porsche');
insert into brands (name) values ('Bentley');
insert into brands (name) values ( 'Renault');
insert into brands (name) values ( 'Peugeot');
insert into brands (name) values ( 'Nissan');
insert into brands (name) values ( 'Hyundai');
insert into brands (name) values ( 'Toyota');
insert into brands (name) values ( 'Skoda');


insert into fuels (name) values ( 'Benzyna');
insert into fuels (name) values ('Diesel');
insert into fuels (name) values ('Elektryk');
insert into fuels (name) values ('LPG');


insert into gear_boxes (name) values ('Automatyczna');
insert into gear_boxes (name) values ('Manualna');


insert into offers (title, description, mileage, year, price, add_date, user_id, fuel_id, gearbox_id, brand_id) values ( 'Audi Q3 w Dieslu', 'Garazowana, zadbana audica!', 93000, 2016, 85000, '2024-12-03 17:34:56.349000', 1, 2, 2, 2);
insert into offers (title, description, mileage, year, price, add_date, user_id, fuel_id, gearbox_id, brand_id) values ('BMW M3', 'Wyscigowa do zabawy jakich malo. Kontakt pod nr telefonu z profilu!', 45500, 2020, 125000, '2024-12-03 17:36:59.246000', 1, 1, 1, 3);
insert into offers (title, description, mileage, year, price, add_date, user_id, fuel_id, gearbox_id, brand_id) values ( 'Porsche 366km', 'Porobione Prosche ma 366koni, niebite.', 320000, 2013, 67000, '2024-12-03 17:38:07.962000', 2, 4, 1, 8);
insert into offers (title, description, mileage, year, price, add_date, user_id, fuel_id, gearbox_id, brand_id) values ( 'Bentley', 'Moj prywatny', 200104, 2017, 190000, '2024-12-16 14:00:30.906000', 3, 1, 1, 9);
insert into offers (title, description, mileage, year, price, add_date, user_id, fuel_id, gearbox_id, brand_id) values ('Porsche 911 Fast Edition', 'fast&furious', 34500, 2019, 235000, '2024-12-19 17:50:17.412000', 5, 1, 1, 8);
