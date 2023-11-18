CREATE TABLE IF NOT EXISTS users(
   id  int NOT NULL PRIMARY KEY AUTO_INCREMENT,
   email varchar(30) NOT NULL,
   login varchar(30) NOT NULL,
   name  varchar(30),
   birthday date
);

CREATE TABLE IF NOT EXISTS friends(
   user_id int NOT NULL REFERENCES users (id) ON DELETE CASCADE,
   friends_id int NOT NULL REFERENCES users (id) ON DELETE CASCADE,
   status boolean,
   PRIMARY KEY (user_id, friends_id)
);

CREATE TABLE IF NOT EXISTS mpa(
   id  int NOT NULL PRIMARY KEY AUTO_INCREMENT,
   name varchar(255)
);

CREATE TABLE IF NOT EXISTS films(
   id  int NOT NULL PRIMARY KEY AUTO_INCREMENT,
   name varchar(255),
   description varchar(255),
   release_date date,
   rate int default 0,
   duration int,
   mpa_id int REFERENCES mpa (id)
);

CREATE TABLE IF NOT EXISTS likes(
   user_id int NOT NULL REFERENCES users (id) ON DELETE CASCADE,
   films_id int NOT NULL REFERENCES films (id) ON DELETE CASCADE,
   PRIMARY KEY (user_id, films_id)
);

CREATE TABLE IF NOT EXISTS genre(
   id  int NOT NULL PRIMARY KEY AUTO_INCREMENT,
   name varchar(255)
);

CREATE TABLE IF NOT EXISTS film_genres(
  films_id int NOT NULL REFERENCES films (id) ON DELETE CASCADE,
  genre_id int NOT NULL REFERENCES genre (id) ON DELETE CASCADE
);



