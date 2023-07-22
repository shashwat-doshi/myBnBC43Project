/* seed user table */
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, age, isAdmin, occupation) VALUES ('5665424957', '221B Baker St', '1970-11-30', 'Sherlock', 'Holmes', 52, 0, 'Detective');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, age, isAdmin, occupation) VALUES ('3105037991', 'St 1 Calgary', '1972-12-31', 'James', 'Doah', 33, 0, 'Engineer');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, age, isAdmin, occupation) VALUES ('3811452384', 'St 2 Vancouver', '1980-09-02', 'Mahesh', 'Dalle', 22, 0, 'Professor');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, age, isAdmin, occupation) VALUES ('6562433252', 'St 3 Toronto', '2000-02-14', 'Kamlesh', 'Singh', 29, 0, 'Artist');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, age, isAdmin, occupation) VALUES ('6317190891', 'St 4 Dubai', '2002-05-26', 'William', 'Root', 34, 0, 'Lawyer');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, age, isAdmin, occupation) VALUES ('1398192688', 'St 5 Mumbai', '1990-08-13', 'Peter', 'Parker', 28, 0, 'Manager');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, age, isAdmin, occupation) VALUES ('4054121868', 'St 6 Delhi', '2003-04-26', 'Walt', 'Whitman', 27, 0, 'Sportsman');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, age, isAdmin, occupation) VALUES ('4243858298', 'St 7 Singapore', '1970-09-29', 'Stephen', 'Strange', 26, 0, 'Gym Trainee');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, age, isAdmin, occupation) VALUES ('7174630225', 'St 8 Sydney', '2000-11-30', 'Tony', 'Stark', 30, 0, 'UI Designer');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, age, isAdmin, occupation) VALUES ('6800269404', 'St 9 Geneva', '1994-12-08', 'James', 'Trickington', 41, 1, 'Aircraft Pilot');

/* seed property table */
insert into Property (street, country, city, postalCode, coordinates) values ('8354 Walton Way', 'Poland', 'Płońsk', '09-101', POINT(52.931452, 101.9214852));
insert into Property (street, country, city, postalCode, coordinates) values ('67 Glacier Hill Plaza', 'Philippines', 'Tubod', '9209', POINT(7.9632405, 123.8751554));
insert into Property (street, country, city, postalCode, coordinates) values ('023 Bayside Circle', 'Indonesia', 'Palamadu', 'M1A 5J2', POINT(-10.0396, 120.754));
insert into Property (street, country, city, postalCode, coordinates) values ('76 Brentwood Place', 'Iran', 'Semīrom', 'M1X 4D1', POINT(31.4151366, 51.5683274));
insert into Property (street, country, city, postalCode, coordinates) values ('644 Luster Circle', 'Japan', 'Setaka', '834-0122', POINT(32.5539412, 130.4103077));
insert into Property (street, country, city, postalCode, coordinates) values ('66 Talmadge Center', 'China', 'Erdao', '772-9234', POINT(43.865595, 125.374217));
insert into Property (street, country, city, postalCode, coordinates) values ('71310 Havey Terrace', 'New Caledonia', 'Koumac', '98850', POINT(-20.5805385, 164.2740515));
insert into Property (street, country, city, postalCode, coordinates) values ('95 Graedel Lane', 'Ireland', 'Coolock', 'D17', POINT(53.3916434, -6.1920933));
insert into Property (street, country, city, postalCode, coordinates) values ('007 Green Ridge Place', 'China', 'Yudong', 'M1D LS2', POINT(28.705864, 109.276796));
insert into Property (street, country, city, postalCode, coordinates) values ('45738 3rd Center', 'Japan', 'Toyonaka', '771-0217', POINT(34.1228075, 134.5990156));

/* seed amenity table */
insert into Amenity (amenityType, details) values ('A washer or dryer', 'Includes washer/dryer in the property');
insert into Amenity (amenityType, details) values ('Heating', 'Includes Heating in the property');
insert into Amenity (amenityType, details) values ('pool', 'Includes a swimming pool in the property');
insert into Amenity (amenityType, details) values ('a kitchen', 'Includes a kitchen in the property');
insert into Amenity (amenityType, details) values ('mini bar', 'Includes a mini bar in the property');
insert into Amenity (amenityType, details) values ('Pets allowed', 'Pets are allowed at the property');
insert into Amenity (amenityType, details) values ('Wifi', 'Includes wifi access at the property');
insert into Amenity (amenityType, details) values ('Jacuzzi', 'Includes Jacuzzi at the property');
insert into Amenity (amenityType, details) values ('Free parking', 'Property includes free parking at the premises');
insert into Amenity (amenityType, details) values ('Laptop-friendly workspace', 'The property has a comfortable workspace for guests');

/* seed payment type table */
insert into PaymentType (paymentName) values ('Debit Card');
insert into PaymentType (paymentName) values ('Credit Card');
insert into PaymentType (paymentName) values ('Cheque');
insert into PaymentType (paymentName) values ('Bank Transfer');

/* Seed comment table*/
insert into Comment (details) values ('Great stay!');
insert into Comment (details) values ('Highly recommended!');
insert into Comment (details) values ('Amazing experience!');
insert into Comment (details) values ('Top-notch service and impeccable cleanliness.');
insert into Comment (details) values ('Clean, neat and tidy!');
insert into Comment (details) values ('Clean and cozy accommodation.');
insert into Comment (details) values ('Disappointing.');
insert into Comment (details) values ('Would definitely come back.');
insert into Comment (details) values ('I feel this property is a bit overrated!');
insert into Comment (details) values ('Overpriced!');

/* Seed currency table */
insert into Currency (currencyName, symbol) values ('Ruble', 'BYR');
insert into Currency (currencyName, symbol) values ('Franc', 'XOF');
insert into Currency (currencyName, symbol) values ('Yuan Renminbi', 'CNY');
insert into Currency (currencyName, symbol) values ('UAE Dirham', 'AED');
insert into Currency (currencyName, symbol) values ('Krona', 'SEK');
insert into Currency (currencyName, symbol) values ('Real', 'BRL');
insert into Currency (currencyName, symbol) values ('Manat', 'AZN');
insert into Currency (currencyName, symbol) values ('Indian Rupee', 'INR');
insert into Currency (currencyName, symbol) values ('Euro', 'EUR');
insert into Currency (currencyName, symbol) values ('Peso', 'PHP');

/* Seed Listing table */
insert into Listing (listingStatus, startDate, endDate, pricePerNight, propertyID, posterID, currencyID) values ('unavailable', '2023-09-26 11:00:00', '2023-10-01 15:00:00', 198.51, 9, 9, 7);
insert into Listing (listingStatus, startDate, endDate, pricePerNight, propertyID, posterID, currencyID) values ('available', '2023-09-07 18:14:54', '2023-09-12 11:48:37', 301.12, 6, 10, 4);
insert into Listing (listingStatus, startDate, endDate, pricePerNight, propertyID, posterID, currencyID) values ('available', '2023-09-08 05:45:05', '2023-09-28 08:34:18', 319.36, 8, 2, 7);
insert into Listing (listingStatus, startDate, endDate, pricePerNight, propertyID, posterID, currencyID) values ('available', '2023-09-15 11:28:12', '2023-09-08 01:34:43', 230.56, 4, 4, 3);
insert into Listing (listingStatus, startDate, endDate, pricePerNight, propertyID, posterID, currencyID) values ('unavailable', '2023-09-07 16:49:39', '2023-09-15 12:00:32', 168.06, 2, 3, 7);
insert into Listing (listingStatus, startDate, endDate, pricePerNight, propertyID, posterID, currencyID) values ('available', '2023-09-22 14:55:06', '2023-09-20 15:42:48', 261.96, 10, 10, 7);
insert into Listing (listingStatus, startDate, endDate, pricePerNight, propertyID, posterID, currencyID) values ('available', '2023-09-11 12:10:08', '2023-09-20 18:30:24', 42.95, 8, 8, 4);
insert into Listing (listingStatus, startDate, endDate, pricePerNight, propertyID, posterID, currencyID) values ('available', '2023-09-22 13:18:24', '2023-09-03 17:05:21', 298.88, 4, 3, 1);
insert into Listing (listingStatus, startDate, endDate, pricePerNight, propertyID, posterID, currencyID) values ('available', '2023-09-11 23:14:45', '2023-09-22 11:28:54', 109.63, 4, 4, 1);
insert into Listing (listingStatus, startDate, endDate, pricePerNight, propertyID, posterID, currencyID) values ('unavailable', '2023-09-26 22:23:17', '2023-09-16 02:59:06', 251.42, 7, 8, 2);