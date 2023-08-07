
DELIMITER //
CREATE TRIGGER posterIDTrigger
BEFORE INSERT
ON Booking FOR EACH ROW
BEGIN
	IF NEW.posterID IS NULL THEN
		SET NEW.posterID = (SELECT posterID FROM Listing WHERE listingID = NEW.listingID);
    END IF;
END;
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER ageTrigger
BEFORE INSERT
ON USER FOR EACH ROW
BEGIN
	IF NEW.age IS NULL THEN
	/* updating the User table to add an 'age' column which calculates the current age of the user using the user's date of birth */
    	SET NEW.age = DATE_FORMAT(FROM_DAYS(DATEDIFF(NOW(), NEW.DOB)), "%Y") + 0;
    END IF;
END;
//
DELIMITER ;

-- Create a check to assert endDate >= startDate + 1
DELIMITER //
CREATE TRIGGER ValidateBookingDates
BEFORE INSERT ON Booking
FOR EACH ROW
BEGIN
    IF (NEW.startDate >= NEW.endDate OR DATEDIFF(NEW.endDate, NEW.startDate) < 1)THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'StartDate of Booking has to be atleast one day after endDate';
    END IF;
END;
//
DELIMITER ;

-- Create a BEFORE INSERT to verify no duplicate Inserts in Booking table
DELIMITER //
CREATE TRIGGER ValidateNoOverlapingBookings
BEFORE INSERT ON Booking
FOR EACH ROW
BEGIN
    IF NEW.bookingStatus = 'confirmed' THEN
        IF EXISTS (
            SELECT *
            FROM Booking b
            WHERE bookingStatus = 'confirmed'
              AND listingID = NEW.listingID
              AND ((NEW.startDate BETWEEN b.startDate AND b.endDate) OR (NEW.endDate BETWEEN b.startDate AND b.endDate))
        ) THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Cannot insert duplicate confirmed booking for Listing already exists between dates';
        END IF;
    END IF;
END;
//
DELIMITER ;



-- Create a BEFORE INSERT to verify no INSERT if startDate/endDate is not in Listings availabilty
DELIMITER //
CREATE TRIGGER ValidateBookingWithinBounds
BEFORE INSERT ON Booking
FOR EACH ROW
BEGIN
    IF NEW.bookingStatus = 'confirmed' THEN
        IF EXISTS (
            SELECT *
			FROM Listing l
			WHERE NEW.listingID = l.listingID AND NOT ((NEW.startDate BETWEEN l.startDate AND l.endDate) OR (NEW.endDate BETWEEN l.startDate AND l.endDate))
        ) THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Cannot insert booking outside of Listing Bounds';
        END IF;
    END IF;
END;
//
DELIMITER ;

-- Create a check to assert endDate >= startDate + 1
DELIMITER //
CREATE TRIGGER ValidateListingDates
BEFORE INSERT ON Listing
FOR EACH ROW
BEGIN
    IF (NEW.startDate >= NEW.endDate OR DATEDIFF(NEW.endDate, NEW.startDate) < 1)THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'StartDate of Listing has to be atleast one day after endDate';
    END IF;
END;
//
DELIMITER ;

-- Create a BEFORE INSERT to verify no duplicate Inserts in Listing table
-- DELIMITER //
-- CREATE TRIGGER ValidateNoOverlapingListings
-- BEFORE INSERT ON Listing
-- FOR EACH ROW
-- BEGIN
--     IF EXISTS (
--         SELECT *
--         FROM Listing l
--         WHERE l.propertyID = NEW.propertyID
--             AND ((NEW.startDate BETWEEN l.startDate AND l.endDate) OR (NEW.endDate BETWEEN l.startDate AND l.endDate))
--         ) THEN
--             SIGNAL SQLSTATE '45000'
--             SET MESSAGE_TEXT = 'Property already has Listing on these days';
--         END IF;
-- END;
-- //
-- DELIMITER ;