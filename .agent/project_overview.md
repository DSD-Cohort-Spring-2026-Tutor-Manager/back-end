# Tutortoise — Project Overview

> **Storage Rule Applied:** Relevant to both repositories → defaults to `/back-end/.agent/`

---

## Business Context

Tutortoise is a **Tutoring Management Center** application solving two core problems in private education:

1. **Churn** — Parents leave when they don't see measurable progress in their child's learning.
2. **Revenue Leakage** — Unbilled or untracked sessions cause learning centers to lose money.

The application gives parents visibility into their child's progress (reducing churn) and enforces a credit-based billing system where sessions auto-deduct from a prepaid balance (eliminating revenue leakage).

---

## MVP Feature Status

| # | Feature | Description | Status |
|---|---------|-------------|--------|
| 1 | Credit Block System | Parents purchase hour blocks. Credits auto-deduct per completed session. Balance always visible. | **In Progress** — Buy credits, balance, and transaction history endpoints exist. Frontend credit view partially built. No credit-block "packages" concept yet. |
| 2 | Session Management | Schedule sessions, assign tutors, mark complete, track duration. | **In Progress** — Session entity and CRUD endpoints exist. Open session listing and booking implemented. Session status transitions (complete/cancel) not fully wired. |
| 3 | Session Continuity Notes | Tutors log structured notes post-session. Next tutor sees full history. | **In Progress** — Student note GET/PUT endpoints exist on backend. Frontend tutor notes UI not built. Notes are a single text field on `Student`, not per-session structured notes. |
| 4 | ROI Progress Dashboard | Visual grade/goal tracker. Parents see measurable progress over time. | **Partially Started** — Assessment points (earned/goal/max) exist on Session entity. Backend has `student-scores` and `students-subject-progress` endpoints. Frontend shows last-two-session scores on parent dashboard. No charts or trend visualization. |
| 5 | Manager Reporting | Unbilled hours, revenue tracking, per-student LTV metrics. | **Partially Started** — Admin dashboard endpoint returns `weeklySessionsBooked` and `weeklyCreditSold`. Frontend admin page shows basic stats and session table. No LTV, unbilled hours, or deep reporting. |
| 6 | Role-Based Access | Three roles: Parent, Tutor, Manager. Each sees only their relevant views. | **Partially Started** — Frontend has `/parent`, `/tutor`, `/admin` routes with distinct layouts. Login page is **fake** (client-side credential matching, no backend authentication). No Spring Security, no JWT, no route guards. |

---

## Repository Structure (As-Is)

```
Cohort/                          ← Non-versioned parent folder (OFF-LIMITS for files)
├── front-end/                   ← Next.js 16.1.6 / React 19 / TypeScript
│   ├── app/
│   │   ├── _api/
│   │   │   ├── login/routes.ts
│   │   │   └── tutortoiseClient.ts    ← API client (fetch-based, 10 methods)
│   │   ├── _components/               ← Shared UI components (10 dirs)
│   │   │   ├── Alert/
│   │   │   ├── CreditContext/
│   │   │   ├── CreditOpts/
│   │   │   ├── CreditsViewbar/
│   │   │   ├── DataBox/
│   │   │   ├── DataBoxGrid/
│   │   │   ├── DataTable/             ← Has Admin/ and AvailableSessionsTable/ sub-variants
│   │   │   ├── Modal/
│   │   │   ├── SideNav/
│   │   │   └── TopNav/
│   │   ├── admin/                     ← Manager dashboard pages
│   │   │   ├── classes/ | credits/ | student/ | tutoring/
│   │   │   ├── layout.tsx | page.tsx
│   │   ├── parent/                    ← Parent dashboard pages
│   │   │   ├── credits/ | tutoring/
│   │   │   ├── layout.tsx | page.tsx
│   │   ├── tutor/                     ← Tutor dashboard pages
│   │   │   ├── classes/ | students/
│   │   │   ├── layout.tsx | page.tsx
│   │   ├── context/
│   │   │   ├── AuthContext.tsx        ← Fake auth (localStorage, no JWT)
│   │   │   └── StudentContext.tsx     ← Hardcoded default student (id=7, "Zayn")
│   │   ├── theme/
│   │   │   ├── Theme.ts              ← MUI createTheme (257 lines, comprehensive)
│   │   │   ├── ThemeProvider.tsx
│   │   │   └── Tokens.ts             ← Design tokens
│   │   ├── globals.css                ← Tailwind import + CSS custom properties
│   │   ├── layout.tsx                 ← Root layout (ThemeProvider → AuthProvider)
│   │   └── page.tsx                   ← Login page (fake client-side auth)
│   ├── public/                        ← Static assets (18 items)
│   ├── proxy.ts                       ← Next.js middleware rewrites /api/* to Render backend
│   ├── package.json
│   ├── next.config.ts
│   └── tsconfig.json
│
└── back-end/                    ← Spring Boot 4.0.2 / Java 21 / Maven
    ├── src/main/java/org/tutortoise/service/
    │   ├── TutortoiseApplication.java
    │   ├── admin/                     ← Admin entity, controller, dashboard service, DTO
    │   ├── advice/                    ← RestExceptionHandler, HttpRestResponse
    │   ├── credit/                    ← CreditTransaction entity, controller, service, DTOs
    │   ├── parent/                    ← Parent entity, controller, service, DTO
    │   ├── session/                   ← Session entity, controller, service, DTOs, NamedQueries
    │   ├── student/                   ← Student entity, controller, service, DTOs
    │   ├── subject/                   ← Subject entity, SubjectDTO
    │   └── tutor/                     ← Tutor entity (no controller/service)
    ├── src/main/resources/
    │   └── application.properties     ← PostgreSQL on Supabase, ddl-auto=update
    ├── database/                      ← Manual SQL migration scripts (db-1 through db-4)
    ├── pom.xml
    └── Dockerfile
```

---

## User Roles & Permission Matrix

| Capability | Parent | Tutor | Manager (Admin) |
|-----------|:------:|:-----:|:---------------:|
| View child's progress & scores | ✅ | — | ✅ |
| View credit balance & history | ✅ | — | ✅ |
| Buy credits | ✅ | — | ✅ |
| Book sessions | ✅ | — | ✅ |
| Add students | ✅ | — | ✅ |
| View assigned students | — | ✅ | ✅ |
| Log/update session notes | — | ✅ | ✅ |
| Mark sessions complete | — | ✅ | ✅ |
| View all sessions & reports | — | — | ✅ |
| Manage users & billing | — | — | ✅ |

> ⚠️ GAP: No backend enforcement exists. All endpoints are publicly accessible.  
> ✅ RECOMMENDATION: Implement Spring Security with JWT-based RBAC before any production deployment.

---

## Domain Glossary

| Term | Definition |
|------|-----------|
| **Credit Block** | A prepaid bundle of tutoring hours purchased by a parent. 1 credit = 1 hour. |
| **Session Note** | Text notes logged by a tutor after (or during) a session, providing continuity for the next tutor. Currently stored as a single `notes` field on the `Student` entity. |
| **LTV (Lifetime Value)** | Total revenue generated by a single student/parent over their entire relationship with the center. |
| **Continuity** | The ability for any tutor to pick up where the last tutor left off, powered by session notes and progress history. |
| **Churn** | When a parent stops using the service, typically due to perceived lack of value or progress. |
| **Credit Deduction** | The automatic subtraction of credits from a parent's balance when a session is completed. |
| **Goal** | A target score (`assessment_points_goal`) set per session, used to measure student progress. |
| **Progress Snapshot** | A point-in-time view of a student's scores vs. goals across sessions and subjects. |

---

## Key Architectural Decisions (Inferred from Codebase)

1. **Monolithic API** — Single Spring Boot app serving all REST endpoints. No microservices.
2. **Feature-based package structure** — Backend organizes by domain feature (`admin/`, `credit/`, `session/`, etc.) rather than by layer. Each package contains its own entity, controller, service, repository, and DTOs.
3. **JPA with `ddl-auto=update`** — Schema is managed by Hibernate auto-DDL. Manual SQL scripts in `/database/` exist for initial setup and seed data but are not integrated into the app lifecycle.
4. **Proxy-based API routing** — Frontend uses a Next.js middleware (`proxy.ts`) to rewrite `/api/*` requests to the deployed backend at `back-end-main.onrender.com`.
5. **MUI + Tailwind CSS coexistence** — Frontend uses both MUI (theme-driven) and Tailwind v4 utility classes. CSS custom properties in `globals.css` and design tokens in `Tokens.ts` bridge the two systems.
6. **No real authentication** — Login page performs client-side credential matching against hardcoded users. `AuthContext` stores user in `localStorage`. No tokens, no backend auth endpoints.

---

## Open Questions / Assumptions Needing Owner Input

1. **Authentication strategy** — Should we implement JWT with refresh tokens, or use session-based auth? Is OAuth/SSO required?
2. **Database migration tooling** — Should we adopt Flyway or Liquibase, or continue with manual SQL scripts?
3. **Deployment target** — Backend is deployed on Render. Is that the long-term plan? Should the frontend also deploy there?
4. **Multi-tenancy** — Is this a single-center app, or should it support multiple learning centers?
5. **Credit pricing** — Is the credit-to-USD rate fixed (1 credit = X dollars), or does it vary by package?
6. **Session notes model** — Should notes be per-session (linked to Session) or per-student-per-tutor (current model)?
7. **Supabase usage** — PostgreSQL is hosted on Supabase. Are we using any Supabase features (Auth, Realtime, Storage) or just the database?
