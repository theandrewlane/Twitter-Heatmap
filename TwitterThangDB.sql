DROP DATABASE IF EXISTS TwitterThangDB;


CREATE DATABASE TwitterThangDB;

USE TwitterThangDB;

CREATE TABLE Person (
	PersonID	int				NOT NULL	PRIMARY KEY	AUTO_INCREMENT,
	FirstName	nvarchar(50)	NOT NULL,
    LastName	nvarchar(50)	NOT NULL,
    Address		nvarchar(200)	NOT NULL,
    City		varchar(50)		NOT NULL,
    State		char(2)			NULL,
    PostalCode	varchar(10)		NOT NULL,
	Country		varchar(20)		NOT NULL,
    Phone		varchar(20)		NOT NULL,
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

CREATE TABLE KeywordLink (
	KeyLinkID	int				NOT NULL	PRIMARY KEY AUTO_INCREMENT,
    KeywordID	int				NOT NULL,
    PersonID		int				NOT NULL
);

ALTER TABLE UserInfo
ADD CONSTRAINT fk_userLogin
FOREIGN KEY (PersonID)
REFERENCES Person(PersonId);

ALTER TABLE KeywordLink
ADD CONSTRAINT fk_linkKeyword
FOREIGN KEY (KeywordId)
REFERENCES Keyword(KeywordId);

ALTER TABLE KeywordLink
ADD CONSTRAINT fk_linkPerson
FOREIGN KEY (PersonID)
REFERENCES Person(PersonId);

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

INSERT INTO Person (FirstName, LastName, Address, City, State, PostalCode, Country, Phone, Email)
VALUES ('TestFirst', 'TestLast', '1234 TestStreet', 'SomeCity', 'UT', '85632', 'Test Country', '18002658542', 'NotReal@Email.com');




		