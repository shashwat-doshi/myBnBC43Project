create table User(
    userID int(50) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    SIN varchar(10) NOT NULL UNIQUE,
    userAddress varchar(100) NOT NULL,
    DOB DATE NOT NULL,
    firstName varchar(30) NOT NULL,
    lastName varchar(30) NOT NULL,
    age int(10) NULL,
    occupation varchar(50) NOT NULL
);


create table Property(
    propertyID int(50) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    street varchar(100) NOT NULL,
    country varchar(50) NOT NULL,
    city varchar(50) NOT NULL,
    postalCode varchar(10) NOT NULL,
    coordinates POINT NOT NULL
);


create table Amenity(
    amenityID int(50) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    amenityType varchar(100) NOT NULL UNIQUE,
    details varchar(100)
);


create table PaymentType(
    paymentTypeID int(50) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    paymentName varchar(16) NOT NULL UNIQUE
);

create table Comment(
    commentID int(50) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    details varchar(50) NOT NULL
);

create table Currency (
    currencyID int(50) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    currencyName varchar(20) NOT NULL,
    symbol varchar(3) NOT NULL
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
    cardNumber varchar(16) NOT NULL UNIQUE,
    expiryDate DATE NOT NULL,
    billingAddress varchar(100) NOT NULL,
    paymentTypeID int(50) NOT NULL,
    FOREIGN KEY (paymentTypeID)
        REFERENCES PaymentType(paymentTypeID)
);


create table PaymentInfo(
    paymentInfoID int(50) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    cardNumber varchar(16) NOT NULL UNIQUE,
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
    bookingStatus ENUM('confirmed', 'canceled by guest', 'canceled by host') DEFAULT 'confirmed',
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
