# Tutortoise — Rules

> **Storage Rule Applied:** Relevant to both repositories → defaults to `/back-end/.agent/`

These rules are **non-negotiable**. Every agent session and every contributor must respect them.

---

## 1. Boundary Rule

All changes must be scoped to `/front-end/` or `/back-end/` only.

- The parent folder (`Cohort/`) is **strictly off-limits** for files, configs, commits, or `.agent/` directories of any kind.
- Never create `Cohort/.agent/`, `Cohort/.env`, `Cohort/docker-compose.yml`, or any other file at the parent level.
- Each repository has its own `.git` — treat them as independent projects that communicate only via REST API.

---

## 2. Storage Rule (Context Files)

| Scope | Location |
|-------|----------|
| Frontend-specific context | `/front-end/.agent/` |
| Backend-specific context | `/back-end/.agent/` |
| Shared / cross-cutting context | `/back-end/.agent/` (default) |

When in doubt, default to `/back-end/.agent/` and document the reasoning in `findings.md`.

---

## 3. Frontend Rules

### Framework & Routing
- **Next.js 16 with App Router** — all pages use `app/` directory routing. Do NOT create a `pages/` directory.
- File-based routing: `app/[role]/[feature]/page.tsx` pattern is established. Maintain it.
- All page components must be `"use client"` unless explicitly designed for server-side rendering.
- Root layout (`app/layout.tsx`) wraps `ThemeProvider` → `AuthProvider`. Do not alter this hierarchy without discussion.

### MUI Usage
- MUI theme is defined in `app/theme/Theme.ts` using tokens from `app/theme/Tokens.ts`.
- **All MUI customizations** go through the theme object — never override MUI styles with inline `sx` props for structural changes.
- Use MUI components for interactive elements (buttons, tables, modals, inputs).
- The theme already defines extensive component overrides for `MuiTable*`, `MuiTab`, `MuiButton`, `MuiCard`, etc. Extend these rather than creating ad-hoc styles.

### CSS & Styling
- **Tailwind v4** is installed and imported via `globals.css`. Use it for layout utilities (`flex`, `grid`, spacing, responsive).
- **CSS custom properties** are defined in `globals.css` `:root` and also injected from `Tokens.ts`. Prefer token-based variables.
- **CSS Modules** may be used for component-scoped styles where needed. Follow `ComponentName.module.css` naming.
- **No inline styles** — the codebase has some `style={{}}` usage (e.g., `admin/page.tsx`). These must be migrated to CSS classes or Tailwind utilities.
- CSS files for dashboards follow the pattern `[role]/dashboard.css`. Keep this convention.

### Component Standards
- Shared components live in `app/_components/[ComponentName]/`.
- Each component directory contains at minimum the `.tsx` file and optionally a CSS Module.
- **Maximum component size: 200 lines.** If a component exceeds this, extract sub-components.
- Type all props with TypeScript interfaces — avoid `any` types (the codebase has several `any` usages that need cleanup).

---

## 4. Backend Rules

### Architecture
- **Feature-based package structure** — each domain feature (`admin/`, `credit/`, `session/`, etc.) contains its own Entity, Controller, Service, Repository, and DTOs.
- Do NOT introduce a flat layer-based structure (e.g., `controllers/`, `services/`). Keep feature packages.
- Every new feature area must follow this pattern: `org.tutortoise.service.[feature]/`.

### Java Standards
- **Java 21** with preview features enabled (`--enable-preview`).
- Use **Lombok** annotations (`@Data`, `@Builder`, `@RequiredArgsConstructor`) where established. The codebase mixes Lombok and manual getters/setters — new code should prefer Lombok.
- Class names: `PascalCase`. Methods/fields: `camelCase`. Constants: `UPPER_SNAKE_CASE`.
- **DTOs required for all API responses** — never expose raw JPA entities in controller return types. Every entity field that leaves the API boundary must go through a DTO.

### Persistence
- Use JPA annotations for entity mapping. Table and column names use `snake_case` in PostgreSQL.
- Repository interfaces extend `JpaRepository` or `CrudRepository`.
- Named native queries are acceptable for complex reporting queries (see `SessionNamedQueries`).
- **Never use `ddl-auto=create` or `ddl-auto=create-drop`**. Current setting is `update` — migrate to Flyway when ready.

---

## 5. API Contract

### URL Conventions
- Base path: `/api/`
- Resource naming: plural nouns, lowercase (`/api/sessions`, `/api/credits`)
- Nested resources: `/api/parent/{parentId}/students`
- Actions: use descriptive verbs only when CRUD doesn't apply (`/api/credits/buy`)

### Response Envelope
All API responses should use a consistent JSON envelope:

```json
{
  "success": true,
  "data": { },
  "error": null
}
```

> ⚠️ GAP: The current codebase does NOT use a consistent envelope. Controllers return raw DTOs or primitives directly. The `HttpRestResponse` class exists for errors but is not used for success responses.  
> ✅ RECOMMENDATION: Introduce a generic `ApiResponse<T>` wrapper and apply it to all endpoints.

### HTTP Status Codes
| Scenario | Code |
|----------|------|
| Successful GET/PUT | `200 OK` |
| Successful POST (created) | `201 Created` |
| Successful DELETE | `204 No Content` |
| Validation error | `400 Bad Request` |
| Unauthenticated | `401 Unauthorized` |
| Insufficient permissions | `403 Forbidden` |
| Resource not found | `404 Not Found` |
| Server error | `500 Internal Server Error` |

---

## 6. Security Rules

- **Spring Security** is required on all non-public routes. Currently missing — must be added in Phase 0.
- **JWT validation** must protect every `/api/*` endpoint except `/api/auth/login` and `/api/auth/register`.
- Input validation using **Bean Validation annotations** (`@NotNull`, `@Positive`, `@Size`, etc.) — some is already in place on `ParentController`, extend to all controllers.
- **No secrets in source code** — `application.properties` currently contains the Supabase connection string with username. Passwords must use environment variables or a secrets manager.
- Password storage must use **BCrypt** or equivalent. Current seed data stores plain-text passwords — this must be fixed.

---

## 7. Git Rules

### Commit Messages (Conventional Commits)
```
feat: add credit purchase endpoint
fix: correct session status enum mapping
chore: update MUI to 7.4
refactor: extract session booking logic to service
docs: update API endpoint inventory
test: add unit tests for CreditService
```

### Branch Naming
```
feature/credit-block-system
fix/session-status-enum
chore/upgrade-spring-boot
refactor/auth-context-jwt
```

### Pull Requests
- PRs require passing tests before merge.
- PRs must include a description of what changed and why.
- Breaking API changes must be flagged in the PR title with `[BREAKING]`.

---

## 8. Testing Rules

### Backend
- **Unit tests required** for all Service layer classes. Use JUnit 5 + Mockito (both dependencies exist in `pom.xml`).
- **Integration tests required** for all API endpoints. Use `@SpringBootTest` with `@WebMvcTest` or `MockMvc`.
- Test files follow the package structure: `src/test/java/org/tutortoise/service/[feature]/[Feature]ServiceTest.java`.

### Frontend
- **Component tests** required for: auth flow, credit management UI, session booking flow.
- Use React Testing Library (not yet in `package.json` — must be added).
- Test files co-locate with components: `ComponentName.test.tsx`.

> ⚠️ GAP: No tests exist anywhere in the codebase currently. `src/test/` has only 2 items, likely boilerplate.  
> ✅ RECOMMENDATION: Add testing as a Phase 0 requirement for all new code.

---

## 9. Derived Rules (From Analysis Anti-Patterns)

1. **Remove hardcoded IDs** — Parent page hardcodes `parentId: 1`, `StudentContext` hardcodes `studentId: 7`. All IDs must come from the authenticated user context.
2. **Remove hardcoded tutor name filtering** — `tutor/page.tsx` filters sessions by `s.tutorName === "Tutor1 No1"`. Replace with authenticated tutor's ID.
3. **Eliminate `any` types** — Multiple components use `useState<any[]>([])`. Replace with proper TypeScript interfaces.
4. **Consistent error handling** — API client (`tutortoiseClient.ts`) catches errors with `console.error` and swallows them. Implement proper error state management.
5. **Remove dead/duplicate code** — `getSessionHistory()` and `getAllSessions()` in the API client hit the same endpoint. Consolidate.
6. **Fix proxy middleware** — `proxy.ts` uses a function named `proxy` but should export as `middleware` for Next.js middleware convention. The `/api/base` path handler is unreachable (falls through after the first `/api` match).
