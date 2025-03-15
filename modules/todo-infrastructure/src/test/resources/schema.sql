-- Таблица для ProjectAgregate
CREATE TABLE IF NOT EXISTS project (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE,
    end_date DATE
);

-- Таблица для TaskEntity
CREATE TABLE IF NOT EXISTS task (
    id UUID PRIMARY KEY,
    project_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    finish_date DATE,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE
);
