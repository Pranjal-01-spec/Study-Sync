INSERT INTO student (id, name, email, year_level, strong_subjects, weak_subjects, karma_points) VALUES
(1, 'Alice Smith', 'alice@college.edu', 'Senior', 'DSA,Java', 'DBMS', 100),
(2, 'Bob Jones', 'bob@college.edu', 'Junior', 'OS,DBMS', 'Java', 80),
(3, 'Charlie Brown', 'charlie@college.edu', 'Sophomore', 'Java,DSA', 'OS', 50),
(4, 'Diana Prince', 'diana@college.edu', 'Senior', 'DBMS,OS', 'DSA', 120),
(5, 'Evan Wright', 'evan@college.edu', 'Junior', 'DSA,DBMS', 'Java', 60);

INSERT INTO study_room (id, title, subject_tag, mode, start_time, end_time, max_capacity, host_id, status) VALUES
(1, 'Java Group Study', 'Java', 'Library Table 4', '2026-11-20T10:00:00', '2026-11-20T12:00:00', 5, 1, 'UPCOMING'),
(2, 'DSA Mastery', 'DSA', 'Google Meet', '2026-11-21T14:00:00', '2026-11-21T16:00:00', 5, 3, 'UPCOMING'),
(3, 'OS Concepts', 'OS', 'Library Table 2', '2026-11-22T09:00:00', '2026-11-22T11:00:00', 5, 2, 'UPCOMING'),
(4, 'DBMS Review', 'DBMS', 'Google Meet', '2026-11-23T15:00:00', '2026-11-23T17:00:00', 5, 4, 'UPCOMING');

INSERT INTO doubt_ticket (id, asker_id, mentor_id, subject_tag, title, description, code_snippet, preferred_slot, status) VALUES
(1, 2, NULL, 'Java', 'NullPointerException help', 'Getting NPE in my collection iterator.', '...', 'Morning', 'OPEN'),
(2, 3, NULL, 'DSA', 'QuickSort optimization', 'My QuickSort is hitting O(n^2).', '...', 'Afternoon', 'OPEN'),
(3, 1, NULL, 'OS', 'Mutex vs Semaphore', 'Need clarification on synchronization primitives.', NULL, 'Evening', 'OPEN'),
(4, 5, NULL, 'DBMS', 'Normal Forms', 'Struggling with 3NF decomposition.', NULL, 'Afternoon', 'OPEN'),
(5, 2, NULL, 'Java', 'Spring DI', 'How to handle circular dependency?', '...', 'Morning', 'OPEN');
