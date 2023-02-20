CREATE TABLE players (
                         id   INTEGER       NOT NULL UNIQUE,
                         name TEXT          NOT NULL,
                         geography char(3)  NOT NULL,
                         PRIMARY KEY (id)
);

CREATE TABLE playerLevels (
                        id   INTEGER        NOT NULL,
                        playerId INTEGER    NOT NULL,
                        level TEXT          NOT NULL,
                        game TEXT           NOT NULL,
                        PRIMARY KEY (id)
);
