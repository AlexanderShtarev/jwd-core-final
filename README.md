drop schema if exists space_program;

create schema if not exists space_program;

create table space_program.rank
(
    id   INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    rank VARCHAR(20)     NOT NULL UNIQUE
);

INSERT INTO space_program.rank(id, rank)
VALUES (1, 'NEWBEE'),
       (2, 'OLD'),
       (3, 'CAPTAIN');

create table space_program.role
(
    id   INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    role VARCHAR(20)     NOT NULL UNIQUE
);

INSERT INTO space_program.role(role)
VALUES ('PILOT'),
       ('GRINDER'),
       ('SOMEONE ELSE');

create table space_program.mission_result
(
    id     INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    result VARCHAR(20)     NOT NULL UNIQUE
);

INSERT INTO space_program.mission_result(id, result)
VALUES (1, 'PLANNED'),
       (2, 'IN_PROGRESS'),
       (3, 'COMPLETED');

create table if not exists space_program.flight_mission
(
    id             INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name           VARCHAR(20)     NOT NULL,
    distance       BIGINT          NOT NULL CHECK (distance > 0 ),
    start          DATETIME,
    end            DATETIME,
    mission_result INT             NOT NULL DEFAULT 1,
    CONSTRAINT fk_mission_result FOREIGN KEY (mission_result) REFERENCES space_program.mission_result (id)
);

create table if not exists space_program.spaceship
(
    id                        INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name                      VARCHAR(20)     NOT NULL UNIQUE,
    flight_distance           BIGINT          NOT NULL CHECK ( flight_distance > 0 ),
    is_ready_for_next_mission BOOL DEFAULT TRUE,
    flight_mission_id         INT UNIQUE,
    FOREIGN KEY (flight_mission_id) REFERENCES space_program.flight_mission (id)
);

create table if not exists space_program.crew_member
(
    id                        INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name                      VARCHAR(20)     NOT NULL UNIQUE,
    role                      INT             NOT NULL,
    rank                      INT             NOT NULL,
    is_ready_for_next_mission BOOL DEFAULT TRUE,
    spaceship_id              INT,
    FOREIGN KEY (spaceship_id) REFERENCES space_program.spaceship (id),
    CONSTRAINT fk_rank FOREIGN KEY (rank) REFERENCES space_program.rank (id),
    CONSTRAINT fk_role FOREIGN KEY (role) REFERENCES space_program.role (id)
);


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
WHERE is_ready_for_next_mission = TRUE
  AND name = 'Alex Spaceship';

UPDATE space_program.flight_mission
SET flight_mission.end = localtime
WHERE id = 1
