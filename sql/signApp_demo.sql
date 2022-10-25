 CREATE TABLE user (
  id int(3) NOT NULL,
  login varchar(25) NOT NULL,
  email varchar(25) NOT NULL,
  fullName varchar(25) NOT NULL,
  userStatus enum('ENABLED','DISABLED') NOT NULL,
  privilege enum('ADMIN','USER') NOT NULL,
  password varchar(25) NOT NULL,
  lastPasswordChange timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
  PRIMARY KEY (id, login, EMAIL)
) ;



CREATE TABLE signin (
  LastSignIn timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  ID int(11) NOT NULL,
  PRIMARY KEY (LastSignIn,ID) USING BTREE,
  KEY ID (ID),
  CONSTRAINT ID FOREIGN KEY (ID) REFERENCES user (ID) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



 insert into user values (5, 'nikolobo69', 'nico@gmail.com', 'Nicolito 21', ENABLED, ADMIN, 'abcd*1234');