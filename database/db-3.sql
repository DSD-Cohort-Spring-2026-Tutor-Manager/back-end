insert into "Student"
(parent_id_fk, first_name, last_name, notes) values
(1, 'John', 'Smith', 'This is a note about John Smith.'),
(2, 'Jane', 'Doe', 'This is a note about Jane Doe.'),
(1, 'Michael', 'Johnson', 'This is a note about Michael Johnson.'),
(1, 'Emily', 'Davis', 'This is a note about Emily Davis.'),
(2, 'David', 'Wilson', 'This is a note about David Wilson.'),
(2, 'Sarah', 'Miller', 'This is a note about Sarah Miller.'),
(1, 'Robert', 'Brown', 'This is a note about Robert Brown.'),
(2, 'Jessica', 'Taylor', 'This is a note about Jessica Taylor.'),
(3, 'Daniel', 'Anderson', 'This is a note about Daniel Anderson.'),
(1, 'Laura', 'Thomas', 'This is a note about Laura Thomas.');

insert into "Session"
(parent_id_fk, student_id_fk, tutor_id_fk, duration_hours, session_status, datetime_started, assessment_points_earned, assessment_points_goal, assessment_points_max)
values
(1, 1, 1, 2.0, 'upcoming', '2026-03-15 10:00:00', 55, 80, 100),
(2, 2, 2, 1.5, 'upcoming', '2026-03-16 14:00:00', 75, 100, 100),
(1, 3, 1, 2.5, 'upcoming', '2026-03-17 09:00:00', 80, 90, 100),
(1, 4, 3, 1.0, 'upcoming', '2026-03-18 11:00:00', 40, 70, 100),
(2, 5, 2, 3.0, 'upcoming', '2026-03-19 13:00:00', 80, 85, 100),
(2, 6, 3, 1.5, 'upcoming', '2026-04-20 15:00:00', 75, 95, 100),
(1, 7, 1, 2.0, 'upcoming', '2026-04-21 10:00:00', 85, 85, 100),
(2, 8, 2, 1.0, 'upcoming', '2026-04-22 14:00:00', 85, 80, 100),
(3, 9, 1 , 1 , 'upcoming' , '2026-04-22 14:00:00' , 80 , 95 , 100 ),
(1 ,10 ,2 ,2.5 , 'upcoming' , '2026-04-24T09:30:00' ,90 ,90 ,100 );

insert into "CreditTransaction"
(session_id_fk, tutor_id_fk, parent_id_fk, datetime_transaction, number_of_credits, transaction_total_usd, transaction_type)
values
(1, 1, 1, '2026-03-15 10:00:00', 1, 100.00, 'redeem'),
(2, 2, 2, '2026-03-16 14:00:00', 1, 75.00, 'redeem'),
(3, 1, 1, '2026-03-17 09:00:00', 1, 125.00, 'redeem'),
(4, 3, 1, '2026-03-18 11:00:00', 1, 50.00, 'redeem'),
(5, 2, 2, '2026-03-19 13:00:00', 1, 150.00, 'redeem'),
(6, 3, 2, '2026-04-20 15:00:00', 1, 75.00, 'redeem'),
(7, 1, 1, '2026-04-21 10:00:00', 1, 100.00, 'redeem'),
(8, 2, 2, '2026-04-22 14:00:00', 1, 50.00, 'redeem'),
(9 ,1 ,3 , '2026-04-22 14:30:00' ,1 ,50.00 , 'redeem' ),
(10 ,2 ,1 , '2026-04-24 09:30:00' ,1 ,125.00 , 'redeem' ),
(1, 1, 1, '2026-03-15 11:00:00', 1, 100.00, 'redeem'),
(2, 2, 2, '2026-03-16 15:00:00', 1, 75.00, 'redeem'),
(3, 1, 1, '2026-03-17 10:00:00', 1, 125.00, 'redeem'),
(4, 3, 1, '2026-03-18 12:00:00', 1, 50.00, 'redeem'),
(5, 2, 2, '2026-03-19 14:00:00', 1, 150.00, 'redeem'),
(6, 3, 2, '2026-04-20 16:00:00', 1, 75.00, 'redeem'),
(7, 1, 1, '2026-04-21 11:00:00', 1, 100.00, 'redeem'),
(8, 2, 2, '2026-04-22 15:00:00', 1, 50.00, 'redeem'),
(9 ,1 ,3 , '2026-04-22 15:30:00' ,1 ,50.00 , 'redeem' ),
(10 ,2 ,1 , '2026-04-24 10:30:00' ,1 ,125.00 , 'redeem' );