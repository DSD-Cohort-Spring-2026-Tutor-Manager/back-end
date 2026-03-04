# Tutortoise — Progress Log

> **Storage Rule Applied:** Relevant to both repositories → defaults to `/back-end/.agent/`

---

## Current Overall Project Status

**Phase:** Pre-MVP — Foundation work (Phase 0) has not started. Phases 1–5 are partially in progress with significant gaps.

**Summary:** Both repositories have a functional skeleton. The backend serves data through REST APIs and the frontend renders role-based dashboards. However, there is **no authentication**, **no security**, **no testing**, and several **hardcoded values** that prevent the app from being usable beyond demo mode.

---

## Confirmed Complete

| Item | Repo | Evidence |
|------|------|----------|
| Project scaffolding (Spring Boot 4, Next.js 16) | Both | `pom.xml`, `package.json` |
| PostgreSQL database setup on Supabase | Backend | `application.properties` |
| JPA entity mapping for 7 domain models | Backend | `Admin.java`, `Parent.java`, `Tutor.java`, `Student.java`, `Session.java`, `CreditTransaction.java`, `Subject.java` |
| REST API for credit operations (buy, balance, history) | Backend | `CreditController.java`, `CreditService.java` |
| REST API for session listing (all, by tutor, open) | Backend | `SessionController.java`, `SessionService.java` |
| REST API for parent info and session booking | Backend | `ParentController.java`, `ParentService.java` |
| REST API for student CRUD and notes | Backend | `StudentController.java`, `StudentService.java` |
| Admin dashboard endpoint | Backend | `AdminController.java`, `AdminDashboardService.java` |
| Global exception handling | Backend | `RestExceptionHandler.java` |
| MUI theme system with design tokens | Frontend | `Theme.ts`, `Tokens.ts`, `ThemeProvider.tsx` |
| Role-based routing (parent, tutor, admin) | Frontend | `app/parent/`, `app/tutor/`, `app/admin/` |
| API client with 10 methods | Frontend | `tutortoiseClient.ts` |
| Login page UI | Frontend | `app/page.tsx` |
| Parent dashboard with credit view and student management | Frontend | `app/parent/page.tsx` |
| Admin dashboard with stats and session table | Frontend | `app/admin/page.tsx` |
| Tutor dashboard with session overview | Frontend | `app/tutor/page.tsx` |
| Proxy middleware for API routing | Frontend | `proxy.ts` |
| Database seed data (SQL scripts) | Backend | `database/db-1.sql` through `db-4.sql` |

---

## In Progress (What Remains)

| Item | Repo | What's Done | What Remains |
|------|------|-------------|--------------|
| Authentication | Both | Login page UI exists, AuthContext stores user in localStorage | Backend auth endpoints, JWT, Spring Security, frontend token management |
| Credit Block System | Both | Buy/balance/history endpoints, frontend credit bar | Credit packages, auto-deduction on session completion, low-balance alerts |
| Session Management | Both | List/book endpoints, open sessions | Create session, status transitions, completion flow, calendar view |
| Session Notes | Backend | Student.notes field, get/put endpoints | Per-session note model, structured fields, note history UI |
| ROI Dashboard | Both | Assessment points on Session entity, basic score display | Charts, trend visualization, goal setting, progress snapshots |
| Manager Reporting | Both | Weekly stats endpoint, basic admin page | LTV, revenue reports, tutor utilization, export |

---

## Active Blockers

1. **No authentication system** — Cannot scope data to the logged-in user. All features are blocked on Phase 0.
2. **Hardcoded IDs** — Frontend assumes `parentId=1`, `studentId=7`, `tutorName="Tutor1 No1"`.
3. **No Spring Security dependency** — Must add `spring-boot-starter-security` to `pom.xml`.
4. **Plain-text passwords** — Seed data and entity field name (`password_encrypted`) is misleading — passwords are not actually encrypted.
5. **Owner decisions needed** — Auth strategy, migration tooling, additional features list.

---

## Running Log

| Date | Repo | Phase | Task | Status | Notes / Errors |
|------|------|-------|------|--------|----------------|
| 2026-03-02 | Both | Analysis | Deep codebase analysis of both repos | ✅ Complete | Scanned 43 Java files, 42 TS/TSX files, 4 SQL scripts, all config files |
| 2026-03-02 | Backend | Analysis | Entity/model inventory | ✅ Complete | 7 entities: Admin, Parent, Tutor, Student, Session, CreditTransaction, Subject |
| 2026-03-02 | Backend | Analysis | API endpoint inventory | ✅ Complete | 5 controllers, ~12 endpoints total, all unprotected |
| 2026-03-02 | Backend | Analysis | Security audit | ✅ Complete | No Spring Security, no JWT, plain-text passwords, DB creds in source |
| 2026-03-02 | Frontend | Analysis | Component inventory | ✅ Complete | 10 shared components, 3 role dashboards, fake auth, 2 contexts |
| 2026-03-02 | Frontend | Analysis | Styling audit | ✅ Complete | MUI 7 + Tailwind 4 coexisting, theme system well-built, some inline styles |
| 2026-03-02 | Frontend | Analysis | API client audit | ✅ Complete | 10 fetch-based methods, no auth headers, no error state management |
| 2026-03-02 | Both | Analysis | Cross-cutting gap analysis | ✅ Complete | Major gaps: auth, security, testing, hardcoded IDs, missing response envelope |
| 2026-03-02 | Both | Phase 0 | Generated context files (FILES 1–7) | ✅ Complete | project_overview, rules, task_plan, progress, implementation_plans, findings |
