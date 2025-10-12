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
EOF

echo "✅ Klar til udvikling!"
