# Tutortoise — Backend Implementation Plan

> **Storage Rule Applied:** Backend-specific → `/back-end/.agent/`

---

## Actual Package Structure vs. Recommended Target

```
src/main/java/org/tutortoise/service/
├── TutortoiseApplication.java          ← Main class
├── admin/                              ← ✅ Keep
│   ├── Admin.java                      ← Entity
│   ├── AdminController.java            ← GET /api/admin/dashboard
│   ├── AdminDashboardDTO.java
│   ├── AdminDashboardService.java
│   ├── AdminRepository.java
│   └── DateUtility.java
├── advice/                             ← ✅ Keep
│   ├── HttpRestResponse.java           ← Error response DTO
│   └── RestExceptionHandler.java       ← Global exception handler
├── credit/                             ← ✅ Keep
│   ├── CreditController.java           ← POST /buy, GET /balance/{id}, GET /history/{id}
│   ├── CreditHistoryDTO.java
│   ├── CreditRequest.java
│   ├── CreditResponseDTO.java
│   ├── CreditService.java
│   ├── CreditTransaction.java          ← Entity
│   ├── CreditTransactionDTO.java
│   ├── CreditTransactionRepository.java
│   └── TransactionType.java            ← Enum (purchase, redeem)
├── parent/                             ← ✅ Keep
│   ├── Parent.java                     ← Entity
│   ├── ParentController.java           ← GET /{id}, GET /{id}/student-scores, etc.
│   ├── ParentDTO.java
│   ├── ParentRepository.java
│   └── ParentService.java
├── session/                            ← ✅ Keep
│   ├── Session.java                    ← Entity (with NamedNativeQueries)
│   ├── SessionController.java          ← GET /all, GET /tutor/{id}, GET /open
│   ├── SessionDTO.java
│   ├── SessionNamedQueries.java        ← Interface with query constants
│   ├── SessionRepository.java
│   ├── SessionService.java
│   ├── SessionStatus.java              ← Enum
│   ├── SessionStudentData.java         ← Native query result class
│   └── SessionStudentSubjectData.java  ← Native query result class
├── student/                            ← ✅ Keep
│   ├── Student.java                    ← Entity
│   ├── StudentController.java          ← POST /add, GET /{id}/note, PUT /{id}/note
│   ├── StudentDTO.java
│   ├── StudentNoteDTO.java
│   ├── StudentProgressDTO.java
│   ├── StudentRepository.java
│   ├── StudentRequest.java
│   └── StudentService.java
├── subject/                            ← ✅ Keep
│   ├── Subject.java                    ← Entity
│   └── SubjectDTO.java
└── tutor/                              ← ⚠️ INCOMPLETE — entity only, no controller/service
    └── Tutor.java                      ← Entity

RECOMMENDED ADDITIONS:
├── auth/                               ← NEW: Authentication package
│   ├── AuthController.java             ← POST /login, POST /register, POST /refresh
│   ├── AuthService.java
│   ├── JwtService.java                 ← Token generation/validation
│   ├── LoginRequest.java
│   ├── LoginResponse.java
│   ├── RegisterRequest.java
│   └── UserDetailsServiceImpl.java
├── config/                             ← NEW: Security & app configuration
│   ├── SecurityConfig.java             ← Spring Security filter chain
│   ├── JwtAuthenticationFilter.java
│   ├── CorsConfig.java
│   └── WebConfig.java
├── common/                             ← NEW: Shared utilities
│   ├── ApiResponse.java                ← Generic response envelope
│   └── ApiException.java               ← Custom exception hierarchy
├── tutor/                              ← ENHANCED
│   ├── TutorController.java            ← NEW
│   ├── TutorService.java               ← NEW
│   ├── TutorRepository.java            ← NEW
│   └── TutorDTO.java                   ← NEW
└── note/                               ← NEW: Per-session notes
    ├── SessionNote.java                ← Entity
    ├── SessionNoteController.java
    ├── SessionNoteService.java
    ├── SessionNoteRepository.java
    └── SessionNoteDTO.java
```

---

## Entity Relationship Summary

### Existing Entities

| Entity | Table | Key Fields | Relationships |
|--------|-------|-----------|---------------|
| `Admin` | `admin` | adminId, firstName, lastName, email, phone, passwordEncrypted | None |
| `Parent` | `parent` | parentId, firstName, lastName, email, phone, passwordEncrypted, currentCreditAmount | → Students (1:N), → Sessions (1:N), → CreditTransactions (1:N) |
| `Tutor` | `tutor` | tutorId, firstName, lastName, email, phone, passwordEncrypted | → Sessions (1:N), → CreditTransactions (1:N) |
| `Student` | `student` | studentId, firstName, lastName, notes | → Parent (N:1), → Sessions (1:N) |
| `Session` | `session` | sessionId, durationHours, sessionStatus, datetimeStarted, assessmentPoints* | → Parent (N:1), → Student (N:1), → Tutor (N:1), → Subject (N:1), → CreditTransactions (1:N) |
| `CreditTransaction` | `credittransaction` | transactionId, dateTime, numberOfCredits, transactionTotalUsd, transactionType | → Parent (N:1), → Tutor (N:1), → Session (N:1) |
| `Subject` | `subject` | id, subject, totalSessionsHours | → Sessions (1:N) |

### Missing Entities (Required for MVP)

| Entity | Purpose | Suggested Fields |
|--------|---------|-----------------|
| `SessionNote` | Per-session structured notes by tutors | noteId, sessionId (FK), tutorId (FK), studentId (FK), topicCovered, engagementLevel, difficultyAreas, nextSteps, createdAt, updatedAt |
| `ProgressSnapshot` | Point-in-time progress record | snapshotId, studentId (FK), subjectId (FK), score, goal, capturedAt |

> ⚠️ GAP: `Student.notes` is a single text field — not per-session and not structured. The MVP requires a dedicated `SessionNote` entity.

---

## Full API Endpoint Inventory

| Method | Route | Controller | Service | Status | Notes |
|--------|-------|------------|---------|--------|-------|
| `GET` | `/api/admin/dashboard` | `AdminController.getDashboard` | `AdminDashboardService.getDashboard` | ✅ Working | Returns weeklySessionsBooked, weeklyCreditSold. Supports pagination. |
| `POST` | `/api/credits/buy` | `CreditController.buyCredits` | `CreditService.buyCredits` | ✅ Working | Accepts `CreditRequest` (parentId, credits, amount). No auth check. |
| `GET` | `/api/credits/balance/{parentId}` | `CreditController.getBalance` | `CreditService.getBalance` | ✅ Working | Returns raw `Double`. Should return `ApiResponse<Double>`. |
| `GET` | `/api/credits/history/{parentId}` | `CreditController.getHistory` | `CreditService.getHistory` | ✅ Working | Returns `List<CreditHistoryDTO>`. |
| `GET` | `/api/parent/{parentId}` | `ParentController.getStudentInfoForParent` | `ParentService.getStudentInformation` | ✅ Working | Optional `?studentId=` filter. |
| `GET` | `/api/parent/{parentId}/student-scores` | `ParentController.getParentSessionInfo` | `ParentService.getStudentDetailsByParent` | ✅ Working | Uses native query for aggregation. |
| `GET` | `/api/parent/{parentId}/students-subject-progress` | `ParentController.getParentSessionInfo` (overloaded) | `ParentService.getStudentProgressBySubject` | ✅ Working | Optional `?studentId=` & `?subject=` filters. |
| `POST` | `/api/parent/book/{sessionId}/{parentId}/{studentId}` | `ParentController.bookSession` | `ParentService.bookSession` | ✅ Working | Deducts credits at booking time. |
| `GET` | `/api/sessions` | `SessionController.getAllSessions` | `SessionService.getSessions(null, null)` | ✅ Working | Returns all sessions unfiltered. |
| `GET` | `/api/sessions/tutor/{tutorId}?status=` | `SessionController.getSessions` | `SessionService.getSessions` | ✅ Working | Filters by tutor and status. |
| `GET` | `/api/sessions/open` | `SessionController.getOpenSessions` | `SessionService.getOpenSessions` | ✅ Working | Returns sessions available for booking. |
| `POST` | `/api/student/add` | `StudentController.addStudent` | `StudentService.addStudent` | ✅ Working | Creates student linked to parentId. |
| `GET` | `/api/student/{studentId}/note?tutorId=` | `StudentController.getStudentNote` | `StudentService.getStudentNote` | ✅ Working | Reads `Student.notes` field. |
| `PUT` | `/api/student/{studentId}/note?tutorId=` | `StudentController.updateStudentNote` | `StudentService.updateStudentNote` | ✅ Working | Updates `Student.notes` field. |

### Missing Endpoints (Required for MVP)

| Method | Route | Phase | Purpose |
|--------|-------|-------|---------|
| `POST` | `/api/auth/login` | 0 | Authenticate user, return JWT |
| `POST` | `/api/auth/register` | 0 | Create new user account |
| `POST` | `/api/auth/refresh` | 0 | Refresh access token |
| `GET` | `/api/auth/me` | 0 | Get current authenticated user profile |
| `POST` | `/api/sessions` | 2 | Create a new session (manager-only) |
| `PUT` | `/api/sessions/{id}/status` | 2 | Transition session status |
| `PUT` | `/api/sessions/{id}/complete` | 2 | Mark session complete + deduct credits |
| `GET` | `/api/tutor/{tutorId}/sessions` | 2 | Get tutor's assigned sessions |
| `GET` | `/api/tutor/{tutorId}/students` | 2 | Get tutor's assigned students |
| `POST` | `/api/sessions/{sessionId}/notes` | 3 | Create session note |
| `GET` | `/api/students/{studentId}/notes` | 3 | Get all session notes for a student |
| `GET` | `/api/students/{studentId}/progress` | 4 | Get progress snapshots |
| `GET` | `/api/admin/reports/revenue` | 5 | Revenue report data |
| `GET` | `/api/admin/reports/ltv` | 5 | Per-student LTV data |
| `GET` | `/api/admin/reports/tutor-utilization` | 5 | Tutor utilization metrics |

---

## Spring Security + JWT Implementation Plan

**Current state:** No security whatsoever. No `spring-boot-starter-security` dependency.

**Steps:**

1. **Add dependencies to `pom.xml`:**
   - `spring-boot-starter-security`
   - `io.jsonwebtoken:jjwt-api`, `jjwt-impl`, `jjwt-jackson` (JJWT library)

2. **Create `SecurityConfig.java`:**
   - Disable CSRF (REST API)
   - Configure stateless session management
   - Permit: `/api/auth/**`, `/api-docs/**`, `/swagger-ui/**`
   - Require authentication for all other `/api/**` routes
   - Add JWT authentication filter before `UsernamePasswordAuthenticationFilter`

3. **Create `JwtService.java`:**
   - Token generation with role claim
   - Token validation and parsing
   - Configurable expiry (access: 15min, refresh: 7days)
   - Secret key from environment variable

4. **Create `JwtAuthenticationFilter.java`:**
   - Extract Bearer token from `Authorization` header
   - Validate token and set `SecurityContext`

5. **Create `AuthController.java` + `AuthService.java`:**
   - Login: validate credentials → return access + refresh tokens
   - Register: hash password with BCrypt → create user → return tokens
   - Refresh: validate refresh token → return new access token

6. **Implement `UserDetailsService`:**
   - Query `Admin`, `Parent`, or `Tutor` table by email
   - Map to Spring Security `UserDetails` with roles

---

## Database Configuration

**Current state:**
- PostgreSQL on Supabase (`aws-1-us-east-1.pooler.supabase.com`)
- `ddl-auto=update` — Hibernate manages schema
- HikariCP connection pool (max 5, min idle 1)
- Manual SQL scripts in `/database/` for initial schema + seed data

**Recommended changes:**
1. **Move credentials to environment variables** — `application.properties` should use `${DB_URL}`, `${DB_USERNAME}`, `${DB_PASSWORD}`
2. **Adopt Flyway** for schema migrations:
   - Add `spring-boot-starter-data-jpa-flyway` or `flyway-core` + `flyway-database-postgresql`
   - Convert `database/db-*.sql` to Flyway migrations: `src/main/resources/db/migration/V1__initial_schema.sql`, etc.
3. **Set `ddl-auto=validate`** in production — Flyway handles schema, Hibernate only validates
4. **Add `application-dev.properties`** with `ddl-auto=update` for development convenience
5. **BCrypt all seed passwords** — current seed data uses plain text `'password123'`

---

## Per-Feature Implementation Notes

### Feature 1: Credit Block System (Phase 1)
- Add `CreditPackage` value object or config: `{ name: "Starter", hours: 5, priceUsd: 200 }`
- Modify `CreditService.buyCredits()` to validate against packages
- Add auto-deduction in `SessionService` when session status transitions to `completed`
- Add `GET /api/credits/packages` endpoint for frontend to fetch available bundles

### Feature 2: Session Management (Phase 2)
- Add `POST /api/sessions` — manager creates open sessions with tutor + subject + datetime
- Add `PUT /api/sessions/{id}/status` — enforce state machine: `open → scheduled → in_progress → completed|cancelled`
- Modify `bookSession()` to check credit balance before booking (already partially done)
- Add `TutorController` with `GET /api/tutor/{id}/sessions` and `GET /api/tutor/{id}/students`

### Feature 3: Session Continuity Notes (Phase 3)
- Create `SessionNote` entity with structured fields
- Create `SessionNoteRepository`, `SessionNoteService`, `SessionNoteController`
- Preserve backward compatibility: `Student.notes` remains as a general notes field
- New `SessionNote` entries are per-session and linked to tutor + student + session

### Feature 4: ROI Progress Dashboard (Phase 4)
- Create `ProgressSnapshot` entity for historical tracking
- Add service method to calculate composite progress across subjects
- Add `GET /api/students/{id}/progress?from=DATE&to=DATE` endpoint
- Progress = (assessmentPointsEarned / assessmentPointsGoal) × 100 aggregated over sessions

### Feature 5: Manager Reporting (Phase 5)
- Extend `AdminDashboardService` with revenue, LTV, and tutor utilization calculations
- LTV = total `transaction_total_usd` for all `purchase` transactions per parent
- Tutor utilization = total sessions hours / available hours
- Add CSV export via `@GetMapping(produces = "text/csv")` endpoints
