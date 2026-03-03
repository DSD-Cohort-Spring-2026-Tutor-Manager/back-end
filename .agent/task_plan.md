# Tutortoise — Task Plan

> **Storage Rule Applied:** Relevant to both repositories → defaults to `/back-end/.agent/`

---

## Phase 0: Foundation (Auth, RBAC, DB Schema, Project Structure Cleanup)

**Goal:** Establish secure authentication, role-based access control, proper database schema management, and clean up existing anti-patterns before building new features.

**Status:** Not Started

**Depends On:** Owner decisions on auth strategy (JWT vs session), migration tooling (Flyway vs Liquibase), and Supabase usage scope.

**Repos Affected:** Both

**Acceptance Criteria:**
- [ ] Spring Security configured with JWT authentication and refresh tokens
- [ ] Login/register endpoints on backend (`POST /api/auth/login`, `POST /api/auth/register`)
- [ ] Frontend AuthContext refactored to use JWT tokens instead of localStorage user object
- [ ] Protected route middleware in Next.js redirecting unauthenticated users to login
- [ ] Role-based endpoint protection (Parent, Tutor, Manager) enforced on all existing API endpoints
- [ ] Passwords hashed with BCrypt in database seed data and all new user creation
- [ ] Database credentials moved to environment variables (remove from `application.properties`)
- [ ] Flyway or Liquibase integrated for schema migrations
- [ ] `ddl-auto` set to `validate` in production
- [ ] Consistent API response envelope (`ApiResponse<T>`) applied to all endpoints
- [ ] Hardcoded IDs removed from frontend (parentId=1, studentId=7, tutorName="Tutor1 No1")
- [ ] TypeScript `any` types replaced with proper interfaces in frontend
- [ ] Frontend error handling improved (no more silent `console.error` swallowing)
- [ ] React Testing Library added to frontend `package.json`

**Known Gaps to Resolve:**
- No `spring-boot-starter-security` dependency in `pom.xml` — must be added
- No `TutorController` or `TutorService` — tutor entity exists but has no endpoints
- `proxy.ts` middleware is buggy — `/api/base` path unreachable, function name should be `middleware`
- `HttpRestResponse` only covers error cases, not success — needs `ApiResponse<T>` wrapper
- Session status enum in DB is `session_status` with values including `'upcoming'` but Java enum `SessionStatus` values may not match

---

## Phase 1: Credit Block System

**Goal:** Complete the credit purchase and deduction lifecycle so parents can buy credit blocks and have them auto-deducted when sessions complete.

**Status:** In Progress

**Depends On:** Phase 0 (auth required to identify which parent is purchasing)

**Repos Affected:** Both

**Acceptance Criteria:**
- [ ] Credit block packages defined (e.g., 5-hr, 10-hr, 20-hr bundles with pricing)
- [ ] `POST /api/credits/buy` validates parent has required payment info (or mock payment)
- [ ] Credits auto-deduct when a session is marked as completed
- [ ] Parent can view real-time credit balance on dashboard
- [ ] Full transaction history page with purchase and deduction entries
- [ ] Low-balance alerts (notification when credits drop below threshold)
- [ ] Manager can view all parents' credit balances and purchase history

**Known Gaps to Resolve:**
- `CreditRequest` DTO accepts raw `parentId` — should come from auth context
- No auto-deduction logic exists — `bookSession` deducts credits at booking time, not completion
- Frontend credit purchase flow exists but uses hardcoded `parentId: "1"`

---

## Phase 2: Session Management

**Goal:** Full session lifecycle — create, schedule, assign tutors, track status transitions, and mark complete with duration tracking.

**Status:** In Progress

**Depends On:** Phase 0 (auth), Phase 1 (credit deduction on completion)

**Repos Affected:** Both

**Acceptance Criteria:**
- [ ] Manager can create new sessions (tutor, subject, date/time, duration)
- [ ] Parents can browse and book open sessions for their students
- [ ] Session status transitions enforced: `open → scheduled → in-progress → completed/cancelled`
- [ ] Tutor can mark sessions as completed and log actual duration
- [ ] Completion triggers credit deduction from parent's balance
- [ ] Session calendar/list view for each role
- [ ] Cancellation policy enforced (credit refund if cancelled X hours before)

**Known Gaps to Resolve:**
- Session entity has `parent_id_fk` but initial sessions in seed data have parent assigned — open sessions should have `null` parent until booked
- No endpoint to create new sessions (only GET and book)
- No session status transition logic in `SessionService`
- Frontend tutor page filters by hardcoded tutor name

---

## Phase 3: Session Continuity Notes

**Goal:** Tutors log structured notes after each session. The next tutor can see the full note history for a student, enabling seamless handoffs.

**Status:** In Progress

**Depends On:** Phase 2 (sessions must exist and be completable)

**Repos Affected:** Both

**Acceptance Criteria:**
- [ ] Note model is per-session (not per-student) — each completed session has its own note entry
- [ ] Tutor can create/update notes linked to a specific session
- [ ] Notes include structured fields: topic covered, student engagement, areas of difficulty, next steps
- [ ] Note history visible to any tutor assigned to the same student
- [ ] Parent can view a summary of session notes (filtered view — no internal tutor commentary)
- [ ] Notes are searchable and sortable by date

**Known Gaps to Resolve:**
- Current `notes` field is on `Student` entity — a single text field, not per-session
- `StudentController` has `GET /{studentId}/note` and `PUT /{studentId}/note` but they read/write the Student.notes field
- No `SessionNote` entity exists — must be created
- Frontend has no notes UI for tutors

---

## Phase 4: ROI Progress Dashboard

**Goal:** Visual progress tracker showing parents their child's score trends, goal achievements, and measurable improvement over time.

**Status:** Partially Started

**Depends On:** Phase 2 (session data with scores), Phase 3 (notes for context)

**Repos Affected:** Both

**Acceptance Criteria:**
- [ ] Line chart showing assessment scores over time per subject
- [ ] Goal vs. actual score comparison per session
- [ ] Overall progress percentage (composite across subjects)
- [ ] Subject-level breakdown view
- [ ] Parent can set/update goals for their student
- [ ] Progress snapshots saved for historical comparison
- [ ] Dashboard loads performantly with pagination/lazy loading

**Known Gaps to Resolve:**
- Backend has `student-scores` and `students-subject-progress` endpoints — partially functional
- Frontend shows last-two-session scores but no charts or trend visualization
- No charting library in frontend `package.json` — need to add (recharts, chart.js, etc.)
- Assessment points schema supports earned/goal/max but no aggregate progress calculation exists

---

## Phase 5: Manager Reporting & LTV

**Goal:** Comprehensive reporting dashboard for center managers — revenue tracking, unbilled hours, per-student lifetime value, and operational metrics.

**Status:** Partially Started

**Depends On:** Phase 1 (credit data), Phase 2 (session data)

**Repos Affected:** Both

**Acceptance Criteria:**
- [ ] Revenue dashboard: total revenue, revenue by period, revenue by tutor
- [ ] Unbilled hours report: sessions completed but not yet deducted
- [ ] Per-student LTV calculation and display
- [ ] Per-tutor utilization metrics (sessions taught, hours logged)
- [ ] Credit block sellthrough rate
- [ ] Exportable reports (CSV/PDF)
- [ ] Date range filtering on all reports

**Known Gaps to Resolve:**
- `AdminDashboardService` exists but only provides `weeklySessionsBooked` and `weeklyCreditSold`
- No LTV calculation logic exists
- No per-tutor reporting endpoints
- Admin frontend shows only 4 stat boxes and a session table

---

## Phase 6: Additional Features (Phase 2+)

**Goal:** Features beyond MVP scope flagged for future implementation.

**Status:** Not Started

**Depends On:** Phases 0–5

**Repos Affected:** Both

**Acceptance Criteria:**
- [ ] Owner to define additional features list
- [ ] Each feature scoped with acceptance criteria
- [ ] Prioritization decision made

**Known Gaps to Resolve:**
- No additional features have been specified in the prompt — awaiting owner input

---

## Phase 7: QA, Performance, and Deployment

**Goal:** Comprehensive quality assurance, performance optimization, and production-ready deployment pipeline.

**Status:** Not Started

**Depends On:** Phases 0–5 (all MVP features complete)

**Repos Affected:** Both

**Acceptance Criteria:**
- [ ] Unit test coverage ≥ 80% for backend service layer
- [ ] Integration tests for all API endpoints
- [ ] Frontend component tests for critical flows (auth, credits, booking)
- [ ] Performance testing: API response times < 200ms for common operations
- [ ] N+1 query detection and elimination (JPA `FetchType.LAZY` audited)
- [ ] CI/CD pipeline: build → test → deploy for both repos
- [ ] Environment-specific configuration (dev, staging, prod)
- [ ] Database backup and recovery plan
- [ ] Error monitoring and logging (Sentry or equivalent)
- [ ] API documentation complete (Swagger/OpenAPI — already partially in place via SpringDoc)

**Known Gaps to Resolve:**
- No CI/CD exists (`.github/` directories exist but haven't been audited for workflow content)
- Dockerfile exists but deployment process is unclear
- No test infrastructure in place for either repo
