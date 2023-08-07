/* seed user table */
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, occupation) VALUES ('5665424957', '221B Baker St', '1970-11-30', 'Sherlock', 'Holmes', 'Detective');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, occupation) VALUES ('3105037991', 'St 1 Calgary', '1972-12-31', 'James', 'Doah', 'Engineer');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, occupation) VALUES ('3811452384', 'St 2 Vancouver', '1980-09-02', 'Mahesh', 'Dalle', 'Professor');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, occupation) VALUES ('6562433252', 'St 3 Toronto', '2000-02-14', 'Kamlesh', 'Singh', 'Artist');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, occupation) VALUES ('6317190891', 'St 4 Dubai', '2002-05-26', 'William', 'Root', 'Lawyer');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, occupation) VALUES ('1398192688', 'St 5 Mumbai', '1990-08-13', 'Peter', 'Parker', 'Manager');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, occupation) VALUES ('4054121868', 'St 6 Delhi', '2003-04-26', 'Walt', 'Whitman', 'Sportsman');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, occupation) VALUES ('4243858298', 'St 7 Singapore', '1970-09-29', 'Stephen', 'Strange', 'Gym Trainee');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, occupation) VALUES ('7174630225', 'St 8 Sydney', '2000-11-30', 'Tony', 'Stark', 'UI Designer');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, occupation) VALUES ('6800269404', 'St 9 Geneva', '1994-12-08', 'James', 'Trickington', 'Aircraft Pilot');

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
insert into Listing (listingStatus, startDate, endDate, pricePerNight, propertyID, posterID, currencyID) values ('unavailable', '2023-09-26 15:00:00', '2023-10-01 11:00:00', 198.51, 9, 9, 7);
insert into Listing (listingStatus, startDate, endDate, pricePerNight, propertyID, posterID, currencyID) values ('available', '2023-09-07 15:00:00', '2023-09-12 11:00:00', 301.12, 6, 10, 4);
insert into Listing (listingStatus, startDate, endDate, pricePerNight, propertyID, posterID, currencyID) values ('available', '2023-09-08 15:00:00', '2023-09-28 11:00:00', 319.36, 8, 2, 7);
insert into Listing (listingStatus, startDate, endDate, pricePerNight, propertyID, posterID, currencyID) values ('available', '2023-09-15 15:00:00', '2023-09-19 11:00:00', 230.56, 4, 4, 3);
insert into Listing (listingStatus, startDate, endDate, pricePerNight, propertyID, posterID, currencyID) values ('unavailable', '2023-09-07 15:00:00', '2023-09-20 11:00:00', 168.06, 2, 3, 7);
insert into Listing (listingStatus, startDate, endDate, pricePerNight, propertyID, posterID, currencyID) values ('available', '2023-09-22 15:00:00', '2023-10-01 11:00:00', 261.96, 10, 10, 7);
insert into Listing (listingStatus, startDate, endDate, pricePerNight, propertyID, posterID, currencyID) values ('available', '2023-09-11 15:00:00', '2023-09-20 11:00:00', 42.95, 8, 8, 4);
insert into Listing (listingStatus, startDate, endDate, pricePerNight, propertyID, posterID, currencyID) values ('available', '2023-09-22 15:00:00', '2023-09-29 11:00:00', 298.88, 4, 3, 1);
insert into Listing (listingStatus, startDate, endDate, pricePerNight, propertyID, posterID, currencyID) values ('available', '2023-09-11 15:00:00', '2023-09-22 11:00:00', 109.63, 4, 4, 1);
insert into Listing (listingStatus, startDate, endDate, pricePerNight, propertyID, posterID, currencyID) values ('unavailable', '2023-09-26 15:00:00', '2023-09-27 11:00:00', 251.42, 7, 8, 2);

/* Updated seed data for Payment table with cardNumber as a string of length 16 */
insert into Payment (cardNumber, expiryDate, billingAddress, paymentTypeID) values ('6334222255934911', '2026-01-06', '6902 Briar Crest Junction', 1);
insert into Payment (cardNumber, expiryDate, billingAddress, paymentTypeID) values ('4405641406297816', '2025-04-18', '45440 Harper Avenue', 2);
insert into Payment (cardNumber, expiryDate, billingAddress, paymentTypeID) values ('3561548840374782', '2025-11-16', '7 Texas Avenue', 3);
insert into Payment (cardNumber, expiryDate, billingAddress, paymentTypeID) values ('6706531481225272', '2026-03-07', '6449 Magdeline Drive', 4);
insert into Payment (cardNumber, expiryDate, billingAddress, paymentTypeID) values ('3564902688567659', '2025-12-30', '77406 Holmberg Circle', 4);
insert into Payment (cardNumber, expiryDate, billingAddress, paymentTypeID) values ('5602227679766824', '2025-11-05', '468 Texas Avenue', 4);
insert into Payment (cardNumber, expiryDate, billingAddress, paymentTypeID) values ('4041377840962352', '2025-07-12', '52437 Banding Center', 1);
insert into Payment (cardNumber, expiryDate, billingAddress, paymentTypeID) values ('3557228239376245', '2025-08-14', '94268 Red Cloud Circle', 2);
insert into Payment (cardNumber, expiryDate, billingAddress, paymentTypeID) values ('4913346333896756', '2026-02-10', '6 Fordem Avenue', 1);
insert into Payment (cardNumber, expiryDate, billingAddress, paymentTypeID) values ('5108752749363939', '2025-04-27', '28289 Birchwood Drive', 2);


/* Updated seed data for PaymentInfo table with cardNumber as a string of length 16 */
insert into PaymentInfo (cardNumber, expiryDate, billingAddress, userID, paymentTypeID) values ('6334222255934911', '2026-01-06', '6902 Briar Crest Junction', 3, 1);
insert into PaymentInfo (cardNumber, expiryDate, billingAddress, userID, paymentTypeID) values ('4405641406297816', '2025-04-18', '45440 Harper Avenue', 1, 2);
insert into PaymentInfo (cardNumber, expiryDate, billingAddress, userID, paymentTypeID) values ('3561548840374782', '2025-11-16', '7 Texas Avenue', 5, 3);
insert into PaymentInfo (cardNumber, expiryDate, billingAddress, userID, paymentTypeID) values ('6706531481225272', '2026-03-07', '6449 Magdeline Drive', 2, 4);
insert into PaymentInfo (cardNumber, expiryDate, billingAddress, userID, paymentTypeID) values ('3564902688567659', '2025-12-30', '77406 Holmberg Circle', 4, 4);
insert into PaymentInfo (cardNumber, expiryDate, billingAddress, userID, paymentTypeID) values ('5602227679766824', '2025-11-05', '468 Texas Avenue', 8, 1);
insert into PaymentInfo (cardNumber, expiryDate, billingAddress, userID, paymentTypeID) values ('4041377840962352', '2025-07-12', '52437 Banding Center', 6, 3);
insert into PaymentInfo (cardNumber, expiryDate, billingAddress, userID, paymentTypeID) values ('3557228239376245', '2025-08-14', '94268 Red Cloud Circle', 9, 2);
insert into PaymentInfo (cardNumber, expiryDate, billingAddress, userID, paymentTypeID) values ('4913346333896756', '2026-02-10', '6 Fordem Avenue', 10, 2);
insert into PaymentInfo (cardNumber, expiryDate, billingAddress, userID, paymentTypeID) values ('5108752749363939', '2025-04-27', '28289 Birchwood Drive', 7, 1);


/* Seed Review table */
insert into Review (rating, commentID) values (4, 1);
insert into Review (rating, commentID) values (5, 2);
insert into Review (rating, commentID) values (3, 3);
insert into Review (rating, commentID) values (5, 4);
insert into Review (rating, commentID) values (4, 5);
insert into Review (rating, commentID) values (2, 6);
insert into Review (rating, commentID) values (5, 7);
insert into Review (rating, commentID) values (3, 8);
insert into Review (rating, commentID) values (4, 9);
insert into Review (rating, commentID) values (1, 10);

/* Updated seed data for Booking table with consistent start and end times and valid date ranges */
insert into Booking (startDate, endDate, accommodations, listingID, paymentID, renterID, reviewForRenter, reviewForOwner, reviewForProperty)
values ('2023-09-26 15:00:00', '2023-10-01 11:00:00', 'I like the place neat and tidy', 1, 1, 3, 2, null, null);
insert into Booking (startDate, endDate, accommodations, listingID, paymentID, renterID, reviewForRenter, reviewForOwner, reviewForProperty)
values ('2023-09-08 15:00:00', '2023-09-10 11:00:00', 'Have a disability, can I get help moving my luggage?', 2, 2, 1, 4, null, 3);
insert into Booking (startDate, endDate, accommodations, listingID, paymentID, renterID, reviewForRenter, reviewForOwner, reviewForProperty)
values ('2023-09-18 15:00:00', '2023-09-21 11:00:00', 'I will have some friends over between 8:00-10:00 pm is that ok?', 3, 3, 5, null, null, null);
insert into Booking (startDate, endDate, accommodations, listingID, paymentID, renterID, reviewForRenter, reviewForOwner, reviewForProperty)
values ('2023-09-16 15:00:00', '2023-09-18 11:00:00', 'Can we please keep the noise to a minimum', 4, 4, 2, null, 1, 5);
insert into Booking (startDate, endDate, accommodations, listingID, paymentID, renterID, reviewForRenter, reviewForOwner, reviewForProperty)
values ('2023-09-26 15:00:00', '2023-09-27 11:00:00', 'I will have a pet with me can I get the access to the backyard?', 10, 5, 4, 10, 9, 8);


/* Seed House table */
insert into House (propertyID, capacity) values (1, 6);
insert into House (propertyID, capacity) values (5, 8);
insert into House (propertyID, capacity) values (8, 4);

/* Seed HotelApartment table */
insert into HotelApartment (propertyID, capacity) values (2, 4);
insert into HotelApartment (propertyID, capacity) values (4, 2);
insert into HotelApartment (propertyID, capacity) values (9, 1);

/* Seed Room table */
insert into Room (propertyID, isShared, capacity) values (3, true, 4);
insert into Room (propertyID, isShared, capacity) values (6, true, 3);
insert into Room (propertyID, isShared, capacity) values (7, false, 2);
insert into Room (propertyID, isShared, capacity) values (10, false, 1);

/* Seed Offers table */
insert into Offers (amenityID, propertyID, isAvailable, cost) values (1, 1, true, 20);
insert into Offers (amenityID, propertyID, isAvailable, cost) values (1, 2, true, 15);
insert into Offers (amenityID, propertyID, isAvailable, cost) values (3, 3, false, 0);
insert into Offers (amenityID, propertyID, isAvailable, cost) values (5, 4, true, 10);
insert into Offers (amenityID, propertyID, isAvailable, cost) values (5, 5, true, 5);
insert into Offers (amenityID, propertyID, isAvailable, cost) values (5, 6, true, 8);
insert into Offers (amenityID, propertyID, isAvailable, cost) values (5, 1, true, 12);
insert into Offers (amenityID, propertyID, isAvailable, cost) values (8, 2, false, 0);
insert into Offers (amenityID, propertyID, isAvailable, cost) values (8, 9, true, 18);
insert into Offers (amenityID, propertyID, isAvailable, cost) values (8, 10, true, 25);
insert into Offers (amenityID, propertyID, isAvailable, cost) values (1, 7, true, 0);
insert into Offers (amenityID, propertyID, isAvailable, cost) values (1, 8, true, 0);


/* FOR REPORTS */

insert into Property (street, country, city, postalCode, coordinates) values ('28 Mallory Point', 'Canada', 'Toronto', 'J6O 7K1', POINT(-100.93262, 49.8508));
insert into Property (street, country, city, postalCode, coordinates) values ('8 8th Trail', 'Canada', 'Toronto', 'U5Q 8D0', POINT(-71.58275, 46.69823));
insert into Property (street, country, city, postalCode, coordinates) values ('971 Steensland Center', 'Canada', 'Toronto', 'G6G 7D6', POINT(-113.6463436, 53.7925065));
insert into Property (street, country, city, postalCode, coordinates) values ('616 Waywood Road', 'Canada', 'Toronto', 'R3D 5Z5', POINT(-73.6072341, 45.7055658));
insert into Property (street, country, city, postalCode, coordinates) values ('43116 Kipling Hill', 'Canada', 'Toronto', 'Z4T 5H5', POINT(-114.4678603, 51.1909883));
insert into Property (street, country, city, postalCode, coordinates) values ('90 Garrison Trail', 'Canada', 'Toronto', 'M1N 5B9', POINT(-63.7716391, 46.4027492));
insert into Property (street, country, city, postalCode, coordinates) values ('1784 Westend Street', 'Canada', 'Toronto', 'P2K 9T8', POINT(-122.9057947, 49.1536517));
insert into Property (street, country, city, postalCode, coordinates) values ('06 Rowland Parkway', 'Canada', 'Toronto', 'M1N 5F9', POINT(-119.5937077, 49.4991381));
insert into Property (street, country, city, postalCode, coordinates) values ('5870 Rigney Pass', 'Canada', 'Toronto', 'C5R 8E6', POINT(-122.7535881, 49.2815819));
insert into Property (street, country, city, postalCode, coordinates) values ('6 Melrose Circle', 'Canada', 'Toronto', 'M1N 592', POINT(-81.41646, 42.91679));