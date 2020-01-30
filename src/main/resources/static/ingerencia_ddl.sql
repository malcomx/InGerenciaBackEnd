
-- Se conecta como usuario postgres
\c postgres postgres

-- Configura la salida al archivo ingerencia_ddl.log
\o ingerencia_ddl.log

-- Elimina la base de datos
DROP DATABASE IF EXISTS ingerencia_db;

-- Elimina el role o usuario
DROP ROLE IF EXISTS ingerencia_user;

-- Crea el rol / usuario propietario de la db
CREATE ROLE ingerencia_user LOGIN PASSWORD 'ingerencia_user' NOSUPERUSER INHERIT CREATEDB NOCREATEROLE;

COMMENT ON ROLE ingerencia_user IS 'Usuario ingerencia_user, que se encarga de conectarse a la base de datos INGERENCIA_DB para las pruebas de la Empresa InGerencia.';

-- Crea la base de datos
CREATE DATABASE ingerencia_db WITH OWNER = ingerencia_user ENCODING = 'UTF8' TABLESPACE = pg_default;

-- Asigna todos los permisos de la db al usuario
GRANT ALL ON DATABASE ingerencia_db TO ingerencia_user;

\c ingerencia_db

-- Crea el esquema dentro de la base de datos ingerencia_db
CREATE SCHEMA test_schema AUTHORIZATION ingerencia_user;

-- Se setea al nuevo esquema
SET search_path TO test_schema;

-- 
ALTER ROLE ingerencia_user SET search_path = test_schema;

--
ALTER USER ingerencia_user SET search_path = test_schema;

ALTER USER ingerencia_user WITH SUPERUSER;

-- Configura el esquema por defecto en la base de datos
ALTER DATABASE ingerencia_db SET search_path TO test_schema;

-- Crea la tabla hacker_news
CREATE TABLE hacker_news (
    id VARCHAR(30) NOT NULL, -- Asociado a la propiedad ObjectID del JSON de Hacker News
    title TEXT,
    created_at TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE NOT NULL,
    CONSTRAINT pk_hacker_news PRIMARY KEY (id)
);

ALTER TABLE hacker_news OWNER TO ingerencia_user;

\q
