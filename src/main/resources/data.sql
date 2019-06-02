DROP TABLE IF EXISTS jokes;

CREATE TABLE jokes (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  joke VARCHAR(250) NOT NULL
);

INSERT INTO jokes (joke) VALUES
('If at first you don’t succeed; call it version 1.0.'),
('My software never has bugs. It just develops random features.'),
('I would love to change the world, but they won’t give me the source code.'),
('The code that is the hardest to debug is the code that you know cannot possibly be wrong.'),
('Beware of programmers that carry screwdrivers.'),
('Hey! It compiles! Ship it!'),
('Programmers are tools for converting caffeine into code.'),
('My attitude isn’t bad. It’s in beta.');
