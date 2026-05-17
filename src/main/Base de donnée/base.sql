CREATE EXTENSION IF NOT EXISTS pgcrypto;

DROP TABLE IF EXISTS INSCRIT;
DROP TABLE IF EXISTS JOUE;
DROP TABLE IF EXISTS PARTICIPE;
DROP TABLE IF EXISTS EVENEMENT;
DROP TABLE IF EXISTS COMISSION;
DROP TABLE IF EXISTS FANFARON;
DROP TABLE IF EXISTS PUPITRE;


CREATE TABLE COMISSION (
                           PRIMARY KEY (nom),
                           nom VARCHAR(80) NOT NULL
);

CREATE TABLE EVENEMENT (
                           PRIMARY KEY (nom),
                           nom         VARCHAR(80) NOT NULL,
                           type        VARCHAR(80),
                           date        DATE,
                           duree       VARCHAR(80),
                           lieu        VARCHAR(80),
                           description VARCHAR(80),
                           pseudo      VARCHAR(80) NOT NULL
);

CREATE TABLE FANFARON (
                          PRIMARY KEY (pseudo),
                          pseudo         VARCHAR(80) NOT NULL,
                          email          VARCHAR(80),
                          mdp            BYTEA NOT NULL,
                          prenom         VARCHAR(80),
                          nom            VARCHAR(80),
                          genre          VARCHAR(80),
                          regime         VARCHAR(80),
                          date_creation  DATE,
                          date_connexion DATE,
                          role           VARCHAR(80) NOT NULL
);

CREATE TABLE INSCRIT (
                         PRIMARY KEY (pseudo, nom),
                         pseudo     VARCHAR(80) NOT NULL,
                         nom        VARCHAR(80) NOT NULL,
                         instrument VARCHAR(80) NOT NULL,
                         status     VARCHAR(80)
);

CREATE TABLE JOUE (
                      PRIMARY KEY (pseudo, instrument),
                      pseudo     VARCHAR(80) NOT NULL,
                      instrument VARCHAR(80) NOT NULL
);

CREATE TABLE PARTICIPE (
                           PRIMARY KEY (pseudo, nom),
                           pseudo VARCHAR(80) NOT NULL,
                           nom    VARCHAR(80) NOT NULL
);

CREATE TABLE PUPITRE (
                         PRIMARY KEY (instrument),
                         instrument VARCHAR(80) NOT NULL
);

ALTER TABLE EVENEMENT ADD FOREIGN KEY (pseudo) REFERENCES FANFARON (pseudo);

ALTER TABLE INSCRIT ADD FOREIGN KEY (instrument) REFERENCES PUPITRE (instrument);
ALTER TABLE INSCRIT ADD FOREIGN KEY (nom) REFERENCES EVENEMENT (nom);
ALTER TABLE INSCRIT ADD FOREIGN KEY (pseudo) REFERENCES FANFARON (pseudo);

ALTER TABLE JOUE ADD FOREIGN KEY (instrument) REFERENCES PUPITRE (instrument);
ALTER TABLE JOUE ADD FOREIGN KEY (pseudo) REFERENCES FANFARON (pseudo);

ALTER TABLE PARTICIPE ADD FOREIGN KEY (nom) REFERENCES COMISSION (nom);
ALTER TABLE PARTICIPE ADD FOREIGN KEY (pseudo) REFERENCES FANFARON (pseudo);


















-- COMISSION
INSERT INTO COMISSION (nom) VALUES
                                ('Prestation'),
                                ('Artistique'),
                                ('Logistique'),
                                ('Communication interne');

-- PUPITRE
INSERT INTO PUPITRE (instrument) VALUES
                                     ('Trompette'),
                                     ('Saxophone'),
                                     ('Trombone'),
                                     ('Percussion'),
                                     ('Clarinette'),
                                     ('Tuba');

-- FANFARON
INSERT INTO FANFARON (pseudo, email, mdp, prenom, nom, genre, regime, date_creation, date_connexion, role) VALUES
                                                                                                               ('admin', 'admin@mail.com', digest('admin', 'sha256'), 'Admin', 'Strateur', 'autre', 'aucune', '2026-01-01', '2026-04-15', 'admin'),
                                                                                                               ('leo38', 'leo@mail.com', digest('password123', 'sha256'), 'Léo', 'Martin', 'homme', 'aucune', '2026-01-10', '2026-03-01', 'fanfaron'),
                                                                                                               ('anna69', 'anna@mail.com', digest('secret456', 'sha256'), 'Anna', 'Durand', 'femme', 'vegetarien', '2026-02-15', '2026-03-02', 'fanfaron'),
                                                                                                               ('max42', 'max@mail.com', digest('pass789', 'sha256'), 'Max', 'Bernard', 'autre', 'vegan', '2026-02-20', '2026-03-03', 'fanfaron'),
                                                                                                               ('zoe01', 'zoe@mail.com', digest('zoepass', 'sha256'), 'Zoé', 'Petit', 'femme', 'sans porc', '2026-03-01', '2026-03-04', 'fanfaron');

-- EVENEMENT
INSERT INTO EVENEMENT (nom, type, date, duree, lieu, description, pseudo) VALUES
                                                                              ('Répétition Carnaval Lyon', 'Répétition', '2026-03-08', '2h', 'Lyon', 'Répétition générale', 'leo38'),
                                                                              ('Carnaval Lyon', 'Prestation', '2026-04-10', '3h', 'Lyon', 'Grand défilé festif', 'leo38'),
                                                                              ('Festival Jazz', 'Prestation', '2026-05-20', '2h', 'Villeurbanne', 'Soirée jazz', 'leo38'),
                                                                              ('Fête Musique', 'Atelier', '2026-06-21', '5h', 'Paris', 'Concert plein air', 'leo38');

-- JOUE
INSERT INTO JOUE (pseudo, instrument) VALUES
                                          ('leo38', 'Trompette'),
                                          ('anna69', 'Saxophone'),
                                          ('max42', 'Percussion'),
                                          ('zoe01', 'Clarinette'),
                                          ('leo38', 'Trombone');

-- INSCRIT
INSERT INTO INSCRIT (pseudo, nom, instrument, status) VALUES
                                                          ('leo38', 'Carnaval Lyon', 'Trompette', 'présent'),
                                                          ('zoe01', 'Carnaval Lyon', 'Clarinette', 'absent'),
                                                          ('anna69', 'Festival Jazz', 'Saxophone', 'présent'),
                                                          ('max42', 'Fête Musique', 'Percussion', 'incertain');

-- PARTICIPE
INSERT INTO PARTICIPE (pseudo, nom) VALUES
                                        ('leo38', 'Prestation'),
                                        ('anna69', 'Artistique'),
                                        ('max42', 'Logistique'),
                                        ('zoe01', 'Communication interne');