-- Create ENUM types for status and transaction type
CREATE TYPE session_status AS ENUM ('scheduled', 'in-progress', 'cancelled', 'completed');
CREATE TYPE transaction_type AS ENUM ('purchase', 'redeem');

-- Create Admin table first (no dependencies)
CREATE TABLE Admin (
    admin_id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    password_encrypted VARCHAR(1000) NOT NULL
);

-- Create Parent table
CREATE TABLE Parent (
    parent_id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    password_encrypted VARCHAR(1000) NOT NULL,
    current_credit_amount DECIMAL(10, 2) DEFAULT 0.00
);

-- Create Tutor table
CREATE TABLE Tutor (
    tutor_id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    password_encrypted VARCHAR(1000) NOT NULL
);

-- Create Student table
CREATE TABLE Student (
    student_id SERIAL PRIMARY KEY,
    parent_id_fk INTEGER NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    notes TEXT,
    CONSTRAINT fk_student_parent FOREIGN KEY (parent_id_fk) REFERENCES Parent(parent_id)
);

-- Create Session table
CREATE TABLE Session (
    session_id SERIAL PRIMARY KEY,
    parent_id_fk INTEGER NOT NULL,
    student_id_fk INTEGER NOT NULL,
    tutor_id_fk INTEGER NOT NULL,
    duration_hours DECIMAL(5, 2) NOT NULL,
    session_status session_status DEFAULT 'upcoming',
    datetime_started TIMESTAMP,
    assessment_points_earned DECIMAL(10, 2) DEFAULT 0,
    assessment_points_goal DECIMAL(10, 2),
    assessment_points_max DECIMAL(10, 2),
    CONSTRAINT fk_session_parent FOREIGN KEY (parent_id_fk) REFERENCES Parent(parent_id),
    CONSTRAINT fk_session_student FOREIGN KEY (student_id_fk) REFERENCES Student(student_id),
    CONSTRAINT fk_session_tutor FOREIGN KEY (tutor_id_fk) REFERENCES Tutor(tutor_id)
);

-- Create Credit Transaction table
CREATE TABLE CreditTransaction (
    transaction_id SERIAL PRIMARY KEY,
    session_id_fk INTEGER,
    tutor_id_fk INTEGER,
    parent_id_fk INTEGER NOT NULL,
    datetime_transaction TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    number_of_credits DECIMAL(10, 2) NOT NULL,
    transaction_total_usd DECIMAL(10, 2) NOT NULL,
    transaction_type transaction_type NOT NULL,
    CONSTRAINT fk_transaction_session FOREIGN KEY (session_id_fk) REFERENCES Session(session_id),
    CONSTRAINT fk_transaction_parent FOREIGN KEY (parent_id_fk) REFERENCES Parent(parent_id)
);