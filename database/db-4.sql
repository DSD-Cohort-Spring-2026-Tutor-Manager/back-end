create table subject (
                         subject_id serial primary key,
                         subject varchar(255) not null,
                         total_sessions_hours int default 0
);

insert into subject
(subject, total_sessions_hours)
values
    ('Calculus', 10),
    ('Physics', 15),
    ('History', 10),
    ('Literature', 10),
    ('Algebra II', 10),
    ('Algebra I', 10),
    ('Physical Education', 5),
    ('Computer Science', 20),
    ('Economics', 10),
    ('Psychology', 10);


alter table session add column subject_id_fk int;

update session
set subject_id_fk = s.subject_id
from subject s
where (session.parent_id_fk+session.tutor_id_fk) = s.subject_id;
