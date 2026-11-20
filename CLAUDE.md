# StudySync Project Rules
- **Project:** StudySync — Peer-to-Peer Study Room & Doubt Scheduler.
- **Structure:** Monorepo with `/backend` (Java 21 + Spring Boot + Maven) and `/frontend` (React + Vite + Tailwind CSS).
- **Database:** Embedded H2 in-memory DB pre-seeded via `data.sql` so the app runs out-of-the-box with zero external DB setup (keep commented MySQL config in `application.properties`).
- **API & CORS:** Backend runs on `http://localhost:8080`, Frontend on `http://localhost:5173`. Always configure CORS globally for `localhost:5173`.
- **Verification Rule:** After writing backend or frontend code, always run the build (`mvn test-compile` / `npm run build`) and fix any compilation or type errors before finishing.
