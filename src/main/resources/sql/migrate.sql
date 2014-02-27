-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: db/changelog.xml
-- Ran at: 21/02/14 18:08
-- Against: dev@jdbc:postgresql://localhost:5432/metarepo
-- Liquibase version: 3.1.1
-- *********************************************************************

-- Lock Database
-- Changeset db/changelog.xml::1::oleksiy
CREATE TABLE basic_entity (
  id          SERIAL NOT NULL,
  context     VARCHAR,
  parent_id   INT,
  path        VARCHAR,
  entity_type VARCHAR,
  name        VARCHAR,
  CONSTRAINT basic_entity_pk PRIMARY KEY (id),
  CONSTRAINT basic_entity_parent_fk FOREIGN KEY (parent_id) REFERENCES basic_entity (id));

CREATE INDEX basic_entity_parent_idx ON basic_entity (parent_id);

CREATE TABLE columns (
  id          INT NOT NULL,
  column_type VARCHAR,
  formula     VARCHAR,
  CONSTRAINT columns_pk PRIMARY KEY (id),
  CONSTRAINT columns_basic_entity_fk FOREIGN KEY (id) REFERENCES basic_entity (id));

CREATE TABLE tables (id INT NOT NULL,
  CONSTRAINT tables_pk PRIMARY KEY (id),
  CONSTRAINT tables_basic_entity_fk FOREIGN KEY (id) REFERENCES basic_entity (id));

CREATE TABLE models (id   INT NOT NULL,
                     type VARCHAR,
  CONSTRAINT models_pk PRIMARY KEY (id),
  CONSTRAINT models_basic_entity_fk FOREIGN KEY (id) REFERENCES basic_entity (id));

CREATE TABLE schemas (
  id INT NOT NULL,
  CONSTRAINT schemas_pk PRIMARY KEY (id),
  CONSTRAINT schemas_basic_entity_fk FOREIGN KEY (id) REFERENCES basic_entity (id));

CREATE TABLE views (
  id INT NOT NULL,
  CONSTRAINT views_pk PRIMARY KEY (id),
  CONSTRAINT views_basic_entity_fk FOREIGN KEY (id) REFERENCES basic_entity (id));

-- Changeset db/changelog.xml::2::oleksiy
CREATE TABLE connections (
  id          SERIAL NOT NULL,
  source      INT,
  destination INT,
  CONSTRAINT connections_pk PRIMARY KEY (id),
  CONSTRAINT connections_basic_entity_source_fk FOREIGN KEY (source) REFERENCES basic_entity (id),
  CONSTRAINT connections_basic_entity_destination_fk FOREIGN KEY (destination) REFERENCES basic_entity (id));

CREATE TABLE closure_connection (
  source      INT,
  destination INT,
  CONSTRAINT closure_connection_basic_entity_source_fk FOREIGN KEY (source) REFERENCES basic_entity (id),
  CONSTRAINT closure_connection_basic_entity_destination_fk FOREIGN KEY (destination) REFERENCES basic_entity (id));