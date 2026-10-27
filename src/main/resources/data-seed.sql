-- Database Creation Script for College Event Registration System
CREATE DATABASE IF NOT EXISTS college_event_db;
USE college_event_db;

-- Initial Seed Data (Optional for testing)
INSERT INTO events (name, event_date, venue, description, created_at) VALUES 
('Annual Tech Symposium 2026', '2026-11-15', 'Main Auditorium, Block A', 'Flagship university technical conference featuring paper presentations, coding contests, and guest lectures from industry leaders.', NOW()),
('AI & Robotics Workshop', '2026-11-20', 'Seminar Hall 2, Dept of CSE', 'Hands-on training session covering autonomous robotics, computer vision algorithms, and edge AI deployment.', NOW()),
('Inter-College Hackathon (CodeCraft)', '2026-11-28', 'Central Computing Facility (Lab 4)', '24-hour continuous hackathon solving real-world challenges in smart campus infrastructure and healthcare.', NOW());
