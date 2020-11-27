drop schema if exists space_program;

create schema if not exists space_program;

create table if not exists space_program.flight_mission
(
    id                INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name              VARCHAR(20)     NOT NULL,
    distance          BIGINT          NOT NULL CHECK (distance > 0 ),
    start             DATETIME,
    end               DATETIME,
    mission_result_id INT
);

create table if not exists space_program.spaceship
(
    id                        INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name                      VARCHAR(20)     NOT NULL UNIQUE,
    flight_distance           BIGINT          NOT NULL CHECK ( flight_distance > 0 ),
    is_ready_for_next_mission BOOL DEFAULT TRUE,
    flight_mission_id         INT
);

create table if not exists space_program.crew_member
(
    id                        INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name                      VARCHAR(20)     NOT NULL UNIQUE,
    role_id                   INT CHECK ( role_id > 0),
    rank_id                   INT CHECK ( rank_id > 0),
    is_ready_for_next_mission BOOL DEFAULT TRUE,
    spaceship_id              INT
);

create table if not exists space_program.role
(
    id   INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(25)     NOT NULL
);

create table if not exists space_program.rank
(
    id   INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(25)     NOT NULL
);

create table if not exists space_program.mission_result
(
    id   INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(25)     NOT NULL
);

INSERT INTO space_program.rank
VALUES (1, 'CAPTAIN'),
       (2, 'NEWBIE'),
       (3, 'OLD GRIND MASTER');

INSERT INTO space_program.role
VALUES (1, 'PILOT'),
       (2, 'YANG'),
       (3, 'MOICHIK');

INSERT INTO space_program.mission_result
VALUES (1, 'PLANNED'),
       (2, 'COMPLETED'),
       (3, 'CANCELED');

INSERT INTO space_program.crew_member
    VALUE
    (1, 'Alex', 1, 1, 1, NULL);

INSERT INTO space_program.spaceship
    VALUE
    (1, 'Alex spaceship', 99999, default, NULL);

INSERT INTO space_program.flight_mission
    VALUE
    (1, 'First mission', 9999, localtime, null, 1);

UPDATE space_program.crew_member
SET crew_member.spaceship_id = 1
WHERE crew_member.name = 'Alex';

UPDATE space_program.spaceship
SET space_program.spaceship.flight_mission_id = 1
WHERE is_ready_for_next_mission = TRUE;

UPDATE space_program.flight_mission
SET flight_mission.end               = localtime,
    flight_mission.mission_result_id = 2
WHERE mission_result_id = 1
