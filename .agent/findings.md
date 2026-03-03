# Tutortoise — Findings & Research Log

> **Storage Rule Applied:** Relevant to both repositories → defaults to `/back-end/.agent/`

---

## Purpose

This file captures discoveries, research notes, technical decisions, and references gathered during development. Each entry identifies which repo it applies to and whether the finding has been acted upon.

---

## Findings from Initial Codebase Analysis (2026-03-02)

| Date | Repo | Topic | Source / Location | Key Takeaway | Applied? |
|------|------|-------|-------------------|--------------|----------|
| 2026-03-02 | Backend | No Spring Security | `pom.xml` — missing `spring-boot-starter-security` | All 12+ API endpoints are publicly accessible. Any user can call any endpoint without authentication. Critical security gap that blocks production readiness. | ❌ Not yet — Phase 0 |
| 2026-03-02 | Backend | Plain-text passwords in seed data | `database/db-2.sql` — `'password123'` stored directly | The field is named `password_encrypted` but values are plain text. BCrypt must be used for all password storage. | ❌ Not yet — Phase 0 |
| 2026-03-02 | Backend | Database credentials in source code | `application.properties` lines 4-6 | Supabase URL, username, and password pattern visible in the committed properties file. Must be moved to environment variables before any public deployment. | ❌ Not yet — Phase 0 |
| 2026-03-02 | Backend | `ddl-auto=update` in production config | `application.properties` line 12 | Hibernate auto-DDL can silently modify production schema. Should be `validate` with Flyway managing migrations. | ❌ Not yet — Phase 0 |
| 2026-03-02 | Frontend | Fake authentication | `app/page.tsx` lines 17-36 | Login page hardcodes user credentials and matches client-side. `AuthContext` stores user in localStorage with no token validation. Anyone can bypass by manually setting localStorage. | ❌ Not yet — Phase 0 |
| 2026-03-02 | Frontend | Hardcoded IDs throughout | `parent/page.tsx` (parentId=1), `context/StudentContext.tsx` (studentId=7), `tutor/page.tsx` ("Tutor1 No1") | Data is not scoped to the authenticated user. The app only works for the hardcoded demo user. | ❌ Not yet — Phase 0 |
| 2026-03-02 | Frontend | MUI + Tailwind v4 coexistence | `package.json`, `globals.css`, `Theme.ts` | Both systems are installed and used. The theme system is well-built with tokens bridging the two. Potential SSR hydration issues with MUI Emotion + Tailwind PostCSS, but no issues reported yet. | ⚠️ Monitor |
| 2026-03-02 | Frontend | Proxy middleware bug | `proxy.ts` lines 1-26 | Function exported as `proxy` but Next.js middleware requires export as `middleware`. Also, the `/api/base` handler on line 12 is unreachable because the `/api` handler on line 6 catches it first. | ❌ Not yet — Phase 0 |
| 2026-03-02 | Backend | No Tutor endpoints | `tutor/Tutor.java` — entity only | The `tutor` package has only the entity class. No controller, service, or repository. Tutors cannot view their own sessions or students through a dedicated API. The frontend works around this by filtering all sessions client-side by tutor name. | ❌ Not yet — Phase 2 |
| 2026-03-02 | Backend | Inconsistent response format | `HttpRestResponse.java`, all controllers | Error responses use `HttpRestResponse` (status, operationStatus, message) but success responses return raw DTOs/primitives. No consistent envelope pattern. | ❌ Not yet — Phase 0 |
| 2026-03-02 | Backend | Session status enum mismatch | `db-1.sql` line 2, `db-3.sql` line 17 | DB enum defines: `'scheduled', 'in-progress', 'cancelled', 'completed'` but seed data uses `'upcoming'` which is not in the enum. Session status lifecycle needs alignment. | ❌ Not yet — Phase 2 |
| 2026-03-02 | Frontend | Duplicate API methods | `tutortoiseClient.ts` lines 45-49, 100-106 | `getAllSessions()` and `getSessionHistory()` both call the same endpoint (`/api/sessions`). One should be removed. | ❌ Not yet — cleanup |
| 2026-03-02 | Backend | Student notes model is insufficient | `Student.java` — `notes` field (String), `StudentController` | Notes are a single text field on the Student entity, not per-session. The MVP requires structured, per-session notes linked to tutor + session + student. A new `SessionNote` entity is needed. | ❌ Not yet — Phase 3 |

---

## Technical Research Notes

### MUI v7 + Next.js 16 SSR Considerations
- MUI uses Emotion for CSS-in-JS. With Next.js App Router, Emotion styles must be injected server-side to avoid FOUC.
- The current `ThemeProvider.tsx` likely uses `"use client"` — verify that SSR hydration works correctly.
- If SSR issues arise, consider `@mui/material-nextjs` adapter package.
- Tailwind v4 uses PostCSS and should not conflict with Emotion, but monitor for specificity issues.

### Spring Boot 4.0.2 Notes
- Spring Boot 4 is a very recent major version. Verify that all dependencies (SpringDoc, Lombok) are compatible.
- Spring Boot 4 requires Jakarta EE 11 namespace — already in use (`jakarta.persistence.*`, `jakarta.validation.*`).
- Spring Security 7+ has different filter chain defaults vs. v6 — consult official migration guide.

### JWT Implementation Patterns
- **Access token:** Short-lived (15 min), stateless, sent in `Authorization: Bearer` header.
- **Refresh token:** Long-lived (7 days), stored securely, used to obtain new access tokens.
- **JJWT library** (`io.jsonwebtoken`) is the standard for Spring Boot JWT — confirm compatibility with Boot 4.
- Alternative: Spring Security OAuth2 Resource Server with `spring-boot-starter-oauth2-resource-server`.

### Credit Block Wallet Pattern
- Idempotent credit operations: use transaction IDs to prevent double-deduction.
- Optimistic locking on `Parent.currentCreditAmount` to prevent race conditions in concurrent bookings.
- Consider `@Version` annotation on Parent entity for JPA optimistic locking.
- Credit balance should never go negative — add `CHECK` constraint in PostgreSQL.

### Flyway Migration Strategy
- Flyway migrations are ordered by version prefix: `V1__`, `V2__`, etc.
- Existing manual SQL scripts (`db-1.sql` through `db-4.sql`) should be consolidated into initial Flyway migrations.
- Use `spring.flyway.baseline-on-migrate=true` for existing databases.
- Development can use `spring.flyway.clean-disabled=false` for clean rebuilds.

### Session Status State Machine
Current enum values are inconsistent across SQL files. Recommended state machine:
```
open → scheduled → in_progress → completed
                                → cancelled
open → cancelled (if cancelled before booking)
```
- `open`: Created by manager, available for booking
- `scheduled`: Booked by parent for a student
- `in_progress`: Tutor marks session as started
- `completed`: Tutor marks session as done → triggers credit deduction + note prompt
- `cancelled`: Session cancelled → credit refund if policy allows

---

## Storage Rule Decision Log

| File | Destination | Rule Applied | Reasoning |
|------|-------------|-------------|-----------|
| `project_overview.md` | `/back-end/.agent/` | Cross-cutting → backend default | Covers both repos, domain glossary, architecture — shared context |
| `rules.md` | `/back-end/.agent/` | Cross-cutting → backend default | Rules apply to both repos |
| `task_plan.md` | `/back-end/.agent/` | Cross-cutting → backend default | Phases affect both repos |
| `progress.md` | `/back-end/.agent/` | Cross-cutting → backend default | Tracks progress across both repos |
| `implementation_plan.md` (FE) | `/front-end/.agent/` | Frontend-specific | Only concerns frontend code |
| `implementation_plan.md` (BE) | `/back-end/.agent/` | Backend-specific | Only concerns backend code |
| `findings.md` | `/back-end/.agent/` | Cross-cutting → backend default | Findings span both repos |
