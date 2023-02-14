CREATE TABLE players (
                         id   INTEGER       NOT NULL AUTO_INCREMENT,
                         name TEXT          NOT NULL,
                         geography char(3)  NOT NULL,
                         PRIMARY KEY (id)
);

CREATE TABLE playerLevels (
                        id   INTEGER        NOT NULL AUTO_INCREMENT,
                        playerId INTEGER    NOT NULL,
                        level TEXT          NOT NULL,
                        game TEXT           NOT NULL,
                        PRIMARY KEY (id)
);
