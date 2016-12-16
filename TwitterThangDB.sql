DROP DATABASE IF EXISTS TwitterThangDB;


CREATE DATABASE TwitterThangDB;

USE TwitterThangDB;

CREATE TABLE Person (
	PersonID	int				NOT NULL	PRIMARY KEY	AUTO_INCREMENT,
	FirstName	nvarchar(50)	NOT NULL,
    LastName	nvarchar(50)	NOT NULL,
    Email		varchar(200)	NOT NULL
);

CREATE TABLE Keyword (
	KeywordID	int				NOT NULL	PRIMARY KEY	AUTO_INCREMENT,
    KeywordName	varchar(100)		NOT NULL
);

CREATE TABLE UserInfo (
	UserID		int				NOT NULL	PRIMARY KEY	AUTO_INCREMENT,
	PersonID	int				NOT NULL,
    UserName	varchar(50)		NOT NULL,
	Pword		varchar(50)		NOT NULL
);

ALTER TABLE UserInfo
ADD CONSTRAINT fk_userLogin
FOREIGN KEY (PersonID)
REFERENCES Person(PersonId);

ALTER TABLE UserInfo ADD UNIQUE(UserName);
ALTER TABLE Person ADD UNIQUE(Email);

INSERT INTO Keyword (KeywordName)
VALUES
	('afterparty')
	,('all-nighter')
	,('baby shower')
	,('bachelorette party')
	,('banquet')
	,('Bash')
	,('block party')
	,('bridal shower')
	,('bucks party')
	,('celebration')
	,('cocktail party')
	,('costume party')
	,('extravaganza')
	,('feast')
	,('festival')
	,('festivities')
	,('fiesta')
	,('gala')
	,('homecoming')
	,('house party')
	,('housewarming')
	,('jamboree')
	,('kegger')
	,('lawn party')
	,('mardi gras')
	,('masquerade')
	,('mixer')
	,('office party')
	,('pajama party')
	,('parade')
	,('reception')
	,('rejoicing')
	,('revel')
	,('revelry')
	,('roast')
	,('saturnalia')
	,('sendoff')
	,('shindig')
	,('Baby shower')
	,('sleepover')
	,('slumber party')
	,('stag night')
	,('surprise party')
	,('wingding');

INSERT INTO Person (FirstName, LastName, Email)
VALUES ('TestFirst', 'TestLast', 'NotReal@Email.com');

INSERT INTO Person (FirstName, LastName, Email)
VALUES ('Arthur', 'Brennan', 'THis@Email.com');

INSERT INTO UserInfo (PersonID, UserName, Pword)
VALUES (1, 'UserName', 'p4ssw0rd');

INSERT INTO UserInfo (PersonID, UserName, Pword)
VALUES (2, 'CouchN1nja', 'irondoor');



		