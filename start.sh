#!/bin/bash
echo "🚀 Starter PostgreSQL..."
sudo service postgresql start

echo "📦 Sikrer at databasen findes..."
sudo -u postgres psql <<EOF
CREATE DATABASE opskrifter;
\c opskrifter
CREATE TABLE IF NOT EXISTS ingrediens (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    parent_id INTEGER REFERENCES ingrediens(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS opskrift (
    id SERIAL PRIMARY KEY, 
    name VARCHAR(100) NOT NULL,
    link_to_page TEXT,
    time_to_make INTEGER,
    instructions TEXT,
    has_made BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS opskrift_ingredient (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    opskrift_id INTEGER NOT NULL,
    ingredient_id INTEGER NOT NULL,
    amount TEXT,
    FOREIGN KEY(opskrift_id) REFERENCES opskrift(id) ON DELETE CASCADE,
    FOREIGN KEY(ingredient_id) REFERENCES ingrediens(id) ON DELETE CASCADE
    );
EOF

echo "✅ Klar til udvikling!"
