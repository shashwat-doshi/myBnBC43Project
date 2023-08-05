
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
