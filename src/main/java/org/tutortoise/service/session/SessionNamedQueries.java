package org.tutortoise.service.session;

public interface SessionNamedQueries {

    String FIND_STUDENT_INFO_BY_PARENT = "WITH StudentDateTime AS ("
            + "       SELECT "
            + "        *, ROW_NUMBER() OVER ( "
            + "            PARTITION BY session.parent_id_fk, session.student_id_fk, session.subject_id_fk "
            + "            ORDER BY session.datetime_started DESC "
            + "            ) as rowNum "
            + "    FROM "
            + "        session "
            + "    WHERE session.session_status = 'completed' AND session.parent_id_fk = :parentId "
            + ")"
            + "SELECT "
            + "stu.first_name as firstName,"
            + "stu.last_name as lastName,"
            + "sdt.student_id_fk as studentId, "
            + "sdt.rowNum as rowNum, "
            + "sum(sdt.assessment_points_earned) as assessmentPointsEarned, "
            + "sum(sdt.assessment_points_max) as assessmentPointsMax"
            + " FROM "
            + "    StudentDateTime sdt, "
            + "    student stu "
            + "WHERE "
            + "    rowNum <= 2 "
            + "AND sdt.student_id_fk = stu.student_id "
            + "GROUP BY stu.first_name, stu.last_name, sdt.student_id_fk, sdt.rowNum "
            + "order by sdt.rowNum, sdt.student_id_fk";

    String FIND_STUDENT_PROGRESS_BY_SUBJECT = "SELECT sess.student_id_fk as studentId, " +
            "sess.subject_id_fk as subjectId, " +
            "stu.first_name || ' ' || stu.last_name as studentName," +
            "sub.subject as subject," +
            "sub.total_sessions_hours as totalSessionsHours," +
            "sum(sess.duration_hours) as totalSessionsHoursCompleted " +
            "FROM session as sess," +
            "student as stu," +
            "subject as sub " +
            "WHERE session_status = 'completed' " +
            "AND sess.parent_id_fk = :parentId " +
            "AND sess.student_id_fk = stu.student_id " +
            "AND sess.subject_id_fk = sub.subject_id " +
            "GROUP BY sess.student_id_fk, sess.subject_id_fk, stu.first_name, stu.last_name, sub.subject, sub.total_sessions_hours";

}

