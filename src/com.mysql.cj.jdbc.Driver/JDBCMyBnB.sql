
create table User (
    userID int(50) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    SIN int(10) NOT NULL UNIQUE,
    userAddress varchar(100) NOT NULL,
    DOB DATE NOT NULL,
    firstName varchar(30) NOT NULL,
    lastName varchar(30) NOT NULL,
    age int(10) NOT NULL,
    isAdmin Boolean NOT NULL DEFAULT 0,
    occupation varchar(50) NOT NULL
);

INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, age, isAdmin, occupation) VALUES (5665424957, '221B Baker St', '1970-11-30', 'Sherlock', 'Holmes', 52, 0, 'Detective');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, age, isAdmin, occupation) VALUES (3105037991, 'St 1 Calgary', '1972-12-31', 'James', 'Doah', 33, 0, 'Engineer');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, age, isAdmin, occupation) VALUES (3811452384, 'St 2 Vancouver', '1980-09-02', 'Mahesh', 'Dalle', 32, 0, 'Professor');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, age, isAdmin, occupation) VALUES (6562433252, 'St 3 Toronto', '2000-02-14', 'Kamlesh', 'Singh', 32, 0, 'Artist');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, age, isAdmin, occupation) VALUES (6317190891, 'St 4 Dubai', '2002-05-26', 'William', 'Root', 32, 0, 'Lawyer');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, age, isAdmin, occupation) VALUES (1398192688, 'St 5 Mumbai', '1990-08-13', 'Peter', 'Parker', 32, 0, 'Manager');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, age, isAdmin, occupation) VALUES (4054121868, 'St 6 Delhi', '2003-04-26', 'Walt', 'Whitman', 32, 0, 'Sportsman');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, age, isAdmin, occupation) VALUES (4243858298, 'St 7 Singapore', '1970-09-29', 'Stephen', 'Strange', 32, 0, 'Gym Trainee');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, age, isAdmin, occupation) VALUES (7174630225, 'St 8 Sydney', '2000-11-30', 'Tony', 'Stark', 32, 0, 'UI Designer');
INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, age, isAdmin, occupation) VALUES (6800269404, 'St 9 Geneva', '1994-12-08', 'James', 'Trickington', 32, 1, 'Aircraft Pilot');


/* add query about updating age w.r.t DOB */


create table Property(
    propertyID int(50) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    street varchar(100) NOT NULL,
    country varchar(50) NOT NULL,
    city varchar(50) NOT NULL,
    postalCode varchar(10) NOT NULL,
    coordinates POINT NOT NULL
);

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

create table Amenity(
    amenityID int(50) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    amenityType varchar(100) NOT NULL UNIQUE,
    details varchar(100)
);

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

create table PaymentType(
    paymentTypeID int(50) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    paymentName varchar(16) NOT NULL UNIQUE
);

create table Comment(
    commentID int(50) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    details varchar(50) NOT NULL
);

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

create table Currency (
    currencyID int(50) NOT NULL AUTO_INCREMENT PRIMARY KEY, 
    currencyName varchar(20) NOT NULL,
    symbol varchar(2) NOT NULL
);

create table Listing (
    listingID int(50) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    listingStatus ENUM('available', 'unavailable') DEFAULT 'available',
    startDate DATETIME NOT NULL,
    endDate DATETIME NOT NULL,
    pricePerNight FLOAT(7,2) NOT NULL,
    propertyID int(50) NOT NULL,
    posterID int(50) NOT NULL, 
    currencyID int(50) NOT NULL,
    FOREIGN KEY (propertyID)
        REFERENCES Property(propertyID),
    FOREIGN KEY (posterID)
        REFERENCES User(userID),
    FOREIGN KEY (currencyID)
        REFERENCES Currency(currencyID)
);



create table Payment(
    paymentID int(50) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    cardNumber int(16) NOT NULL UNIQUE,
    expiryDate DATE NOT NULL,
    billingAddress varchar(100) NOT NULL,
    paymentTypeID int(50) NOT NULL,
    FOREIGN KEY (paymentTypeID)
        REFERENCES PaymentType(paymentTypeID)
);


create table PaymentInfo(
    paymentInfoID int(50) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    cardNumber int(16) NOT NULL UNIQUE,
    expiryDate DATE NOT NULL,
    billingAddress varchar(100) NOT NULL,
    userID int(50) NOT NULL,
    paymentTypeID int(50) NOT NULL,
    FOREIGN KEY (userID)
        REFERENCES User(userID),
    FOREIGN KEY (paymentTypeID)
        REFERENCES PaymentType(paymentTypeID)
);

create table Review(
    reviewID int(50) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    rating int(1) NOT NULL CONSTRAINT ratingCheck CHECK (rating BETWEEN 1 AND 5),
    commentID int(50) NULL,
    FOREIGN KEY (commentID)
        REFERENCES Comment(commentID)
);

create table Booking(
    bookingID int(50) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    paymentName varchar(16) NOT NULL UNIQUE,
    startDate DATETIME NOT NULL,
    endDate DATETIME NOT NULL,
    accommodations varchar(100),
    listingID int(50) NOT NULL,
    paymentID int(50) NOT NULL,
    renterID int(50) NOT NULL,
    reviewForRenter int(50) NULL,
    reviewForOwner int(50) NULL, /* allows NULL -- check */
    reviewForProperty int(50) NULL,

    FOREIGN KEY (listingID)
        REFERENCES Listing(listingID),
    FOREIGN KEY (paymentID)
        REFERENCES Payment(paymentID),
    FOREIGN KEY (renterID)
        REFERENCES User(userID),
    FOREIGN KEY (reviewForRenter)
        REFERENCES Review(reviewID),
    FOREIGN KEY (reviewForOwner)
        REFERENCES Review(reviewID),
    FOREIGN KEY (reviewForProperty)
        REFERENCES Review(reviewID)
);

create table House (
    propertyID int(50) NOT NULL PRIMARY KEY, 
    capacity int(10) NOT NULL,
    FOREIGN KEY (propertyID)
        REFERENCES Property(propertyID)
);

create table HotelApartment(
    propertyID int(50) NOT NULL PRIMARY KEY, 
    capacity int(10) NOT NULL,
    FOREIGN KEY (propertyID)
        REFERENCES Property(propertyID)
);

create table Room(
    propertyID int(50) NOT NULL PRIMARY KEY, 
    isShared BOOLEAN,
    capacity int(10) NOT NULL,
    FOREIGN KEY (propertyID)
        REFERENCES Property(propertyID)
);

create table Offers (
    amenityID int(50) NOT NULL, 
    propertyID int(50) NOT NULL,
    isAvailable Boolean NOT NULL,
    cost int(10) NOT NULL DEFAULT 0,
    PRIMARY KEY(amenityID, propertyID),
    FOREIGN KEY (amenityID)
        REFERENCES Amenity(amenityID),
    FOREIGN KEY (propertyID)
        REFERENCES Property(propertyID)
);