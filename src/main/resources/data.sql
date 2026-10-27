-- ===================================================================
-- Initial Seed Data for College Event Registration System
-- Pre-populates sample events for testing and immediate demonstration
-- ===================================================================

INSERT INTO events (name, event_date, venue, description, created_at)
SELECT 'Annual Tech Symposium 2026', '2026-11-15', 'Main Auditorium, Block A', 'Flagship university technical conference featuring paper presentations, coding contests, and guest lectures from industry leaders.', CURRENT_TIMESTAMP()
WHERE NOT EXISTS (SELECT 1 FROM events WHERE name = 'Annual Tech Symposium 2026');

INSERT INTO events (name, event_date, venue, description, created_at)
SELECT 'AI & Robotics Workshop', '2026-11-20', 'Seminar Hall 2, Dept of CSE', 'Hands-on training session covering autonomous robotics, computer vision algorithms, and edge AI deployment.', CURRENT_TIMESTAMP()
WHERE NOT EXISTS (SELECT 1 FROM events WHERE name = 'AI & Robotics Workshop');

INSERT INTO events (name, event_date, venue, description, created_at)
SELECT 'Inter-College Hackathon (CodeCraft)', '2026-11-28', 'Central Computing Facility (Lab 4)', '24-hour continuous hackathon solving real-world challenges in smart campus infrastructure and healthcare.', CURRENT_TIMESTAMP()
WHERE NOT EXISTS (SELECT 1 FROM events WHERE name = 'Inter-College Hackathon (CodeCraft)');
