CREATE TABLE player (
                         id   INTEGER       NOT NULL AUTO_INCREMENT,
                         name TEXT          NOT NULL,
                         geography char(3)  NOT NULL,
                         PRIMARY KEY (id)
);

CREATE TABLE playerLevels (
                        id   INTEGER        NOT NULL AUTO_INCREMENT,
                        playerId INTEGER    NOT NULL,
                        level TEXT          NOT NULL,
                        gameId INTEGER      NOT NULL,
                        PRIMARY KEY (id)
);
