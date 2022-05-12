create database Ecompany;
use Ecompany;
create table Employee(
FirstName varchar(255),
LastName varchar(255),
Email varchar(255),
Profession varchar(255),
Gender varchar(255),
PhoneNumber varchar(255),
Password varchar(255)

);

DELIMITER $$
USE `ecompany`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_employee`(firstName varchar(255),lastName varchar(255),email varchar(255),profession varchar(255),gender varchar(255),phoneNumber varchar(255))
BEGIN
insert into employee(FirsName,LastName,Email,Profession,Gender,PhoneNumber)
values(firstName,lastName,email,profession,gender,phoneNumber);
END;$$

DELIMITER ;


DELIMITER $$
USE `ecompany`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_employee`(firstName varchar(255))
BEGIN
delete from employee where FirsName=firstName;
END;$$

DELIMITER ;

DELIMITER $$
USE `ecompany`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_all_employee`()
BEGIN
select * from Employee;
END;$$

DELIMITER ;


DELIMITER $$
USE `ecompany`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `register_employee`(firstName varchar(255),lastName varchar(255),email varchar(255),profession varchar(255),gender varchar(255),phoneNumber char(10),password varchar(255))
BEGIN
insert into employee(FirsName,LastName,Email,Profession,Gender,PhoneNumber,Password)
values(firstName,lastName,email,profession,gender,phoneNumber,password);
END;$$



DELIMITER ;
