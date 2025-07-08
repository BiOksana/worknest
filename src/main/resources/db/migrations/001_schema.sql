-- liquibase formatted sql
-- changeset oksana.bibikova:001

CREATE TABLE IF NOT EXISTS project (
 deadline date NULL,
 id int NOT NULL AUTO_INCREMENT,
 description text,
 experience varchar(255) NULL,
 field varchar(255) NULL,
 name varchar(255) UNIQUE NOT NULL,
 status VARCHAR(150) NULL,
 PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS vacancy (
 id int NOT NULL AUTO_INCREMENT,
 project_id int NOT NULL,
 country varchar(255) NULL,
 description text,
 experience varchar(255) NULL,
 field varchar(255) NULL,
 name varchar(255) NULL,
 PRIMARY KEY (id),
 CONSTRAINT fk_vacancy_project1
 FOREIGN KEY (project_id)
 REFERENCES project (id));