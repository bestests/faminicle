CREATE TABLE EVENTDAY(
EV_NO INT AUTO_INCREMENT PRIMARY KEY, 
MEM_NO INT NOT NULL, 
EV_TITLE VARCHAR(100) NOT NULL,
EV_START DATE NOT NULL,
EV_END DATE,
EV_TYPE VARCHAR(10)
);