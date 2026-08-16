# AIDevOps Module 3: `cursorreq` — Interview Preparation Guide

> [!IMPORTANT]
> This document covers **cursorreq** — the **AI-Powered Execution Engine** that implements **Spec-Driven Development (SDD)** using the Cursor Python SDK. It autonomously implements features from requirements, with human review gates, quality scoring, and Git lifecycle automation.

---

## 1. Project Overview

### What It Is
`cursorreq` is the most sophisticated module in the AIDevOps pipeline. It takes a free-text technical requirement (from a file or Jira ticket) and **autonomously implements the entire feature** — including production code, unit tests, compilation verification, and Git commit/push — all with human oversight at critical checkpoints.

### What Makes It Unique
Unlike `req2code` (which generates single method fragments), `cursorreq` implements **"Option A"** — a fundamentally different approach:

| Aspect | `cursorreq` (Option A) | `req2code` + JCS (Option B) |
|---|---|---|
| **LLM Engine** | Cursor Python SDK (`composer-2.5`) | AWS SageMaker endpoints |
| **Codebase Discovery** | Local agent searches disk via terminal tools | Neo4j Graph + FAISS vector search |
| **Code Modifications** | Agent directly edits `src/main` & `src/test` | JCS AST-based method insertions |
| **Verification** | Real `mvn test` execution + AI acceptance verification | JCS Eclipse JDT headless compilation |
| **Development Style** | **Spec-Driven Development** with human review gates | Direct prompt-to-AST method synthesis |
| **Infrastructure** | Zero (no DBs, no embeddings, no cloud endpoints) | High (JCS server, Neo4j, FAISS, SageMaker) |

### Problem It Solves
- **Requirements-to-code gap**: Manual implementation of features from Jira tickets takes hours
- **Quality assurance**: AI-generated code often compiles but doesn't pass tests or meet acceptance criteria
- **Governance**: No human oversight in fully automated code generation is risky for production systems
- **Git workflow overhead**: Branch creation, commit formatting, SSL auth, and push to GitLab are manual

### Key Source Path
All source files are under: [cursorreq/](file:///home/sridharp@ds.alacriti.com/Downloads/aidevops-main/cursorreq/)

---

## 2. Architecture & Design — The 6-Phase Pipeline

```mermaid
flowchart TD
    INPUT["📄 Requirement<br/>(File or Jira Ticket)"] --> P0

    subgraph "Phase 0: Git Setup"
        P0[Git Branch Setup] --> P0A[Stash dirty tree]
        P0A --> P0B[Fetch remote]
        P0B --> P0C["Checkout/create<br/>feature branch"]
    end

    P0C --> P1

    subgraph "Phase 1: Spec Generation"
        P1["🤖 AI Agent reads<br/>repo + AGENTS.md"] --> P1OUT["📋 SpecDocument<br/>(acceptance criteria,<br/>data models, API contracts)"]
    end

    P1OUT --> P1B

    subgraph "Phase 1b: Human Spec Review"
        P1B{"👤 Human Review Gate"}
        P1B -->|approve| P2
        P1B -->|"feedback"| P1REFINE["AI refines spec<br/>(up to 3 rounds)"]
        P1REFINE --> P1B
        P1B -->|reject| ABORT[❌ Pipeline Aborted]
    end

    subgraph "Phase 2: Implementation"
        P2["🤖 AI Agent implements:<br/>1. Discover existing code<br/>2. Edit src/main/java<br/>3. mvn compile<br/>4. Add src/test/java<br/>5. mvn test"]
    end

    P2 --> P3

    subgraph "Phase 3: Spec Verification"
        P3["🤖 AI checks git diff<br/>against each acceptance<br/>criterion"]
        P3 --> P3R{"All criteria PASS?"}
        P3R -->|"❌ No"| P2B["Re-implementation<br/>(up to 2 retries)"]
        P2B --> P3
        P3R -->|"✅ Yes"| P4
    end

    subgraph "Phase 4: Quality Gate"
        P4["🔬 Python Quality Assessment"]
        P4 --> P4A["Infer Maven module<br/>from git diff"]
        P4A --> P4B["Run mvn test"]
        P4B --> P4C["Parse Surefire XML<br/>Score 0-100"]
        P4C --> P4R{"Score ≥ min_score?"}
        P4R -->|"❌ No"| P4RETRY["Retry with Maven<br/>error excerpt"]
        P4RETRY --> P2
        P4R -->|"✅ Yes"| P5
    end

    subgraph "Phase 5: Human Final Review"
        P5{"👤 Final Review Gate<br/>quality score + diff stats<br/>+ criteria results"}
        P5 -->|approve| P6
        P5 -->|reject| ROLLBACK["↩️ Full Rollback"]
    end

    subgraph "Phase 6: Git Commit & Push"
        P6["Git stage & commit<br/>(\"AI: feature description\")"]
        P6 --> P6PUSH["Push to GitLab<br/>(corporate SSL)"]
        P6PUSH -->|"failure"| ROLLBACK2["↩️ Transactional Rollback<br/>(hard reset + branch restore<br/>+ stash pop)"]
    end

    P6PUSH -->|"success"| ARTIFACTS["📁 14+ Artifacts<br/>out/YYYYMMDDTHHMMSSZ/"]
```

---

## 3. Technology Stack

| Technology | Version | Why This Choice |
|---|---|---|
| **Cursor Python SDK** | `cursor-sdk>=0.1.7` with `composer-2.5` | Local AI agent with direct disk access. No need for external APIs — the agent runs terminal commands, reads files, and edits code directly in the target repo. Zero infrastructure. |
| **Python 3.10+** | Core language | Best ecosystem for AI tooling and scripting. |
| **httpx** | `>=0.27.0` | Async-ready HTTP client for Jira Cloud REST API v3. Better timeout handling than `requests`. |
| **PyYAML** | `>=6.0.1` | Human-readable configuration with environment variable interpolation. |
| **Git subprocess** | System git | Full Git workflow (branch, stash, commit, push) with corporate JKS/PEM SSL support via custom env. |
| **Maven** | External tool | Real `mvn test` execution with Surefire XML report parsing for quality scoring. |
| **pytest** | `>=8.0.0` | 13 comprehensive test files covering all components. |
| **ruff** + **black** + **isort** | Dev tools | Code formatting and linting standards. |

---

## 4. Module Deep Dive — 17 Source Files

### Entry Points & Configuration

| File | Purpose |
|---|---|
| [__main__.py](file:///home/sridharp@ds.alacriti.com/Downloads/aidevops-main/cursorreq/cursorreq/__main__.py) | `python -m cursorreq` entry point → calls `cli.main()` |
| [cli.py](file:///home/sridharp@ds.alacriti.com/Downloads/aidevops-main/cursorreq/cursorreq/cli.py) | argparse CLI: `--requirement`, `--jira`, `--repo`, `--maven-module`, `--skip-spec`, `--auto-approve`, `--spec-file`, `--no-final-review`, `--no-push`, `-v`. Exit codes: 0=success, 1=agent fail/quality gate, 2=config error, 3=Jira auth error |
| [config.py](file:///home/sridharp@ds.alacriti.com/Downloads/aidevops-main/cursorreq/cursorreq/config.py) | `AppConfig` with nested dataclasses: `CursorConfig` (API key, model, timeouts), `RepoConfig` (path), `MavenConfig` (module), `QualityConfig` (max_retries, min_score), `SpecConfig` (review mode, refinement rounds), `GitConfig` (auto_push, branch_prefix), `JiraConfig` (site_url, email, token) |
| [models.py](file:///home/sridharp@ds.alacriti.com/Downloads/aidevops-main/cursorreq/cursorreq/models.py) | `RunResult` dataclass: status, run/agent IDs, commit SHA, branch, push status, quality scores/grades, retry counts, transcript, spec text, acceptance criteria metrics, review flags |

### Requirement Ingestion

| File | Purpose |
|---|---|
| [requirement.py](file:///home/sridharp@ds.alacriti.com/Downloads/aidevops-main/cursorreq/cursorreq/requirement.py) | Reads requirement files, extracts optional `branch: <name>` headers |
| [requirement_source.py](file:///home/sridharp@ds.alacriti.com/Downloads/aidevops-main/cursorreq/cursorreq/requirement_source.py) | `RequirementInput` dispatcher: normalizes from `--requirement` (file) or `--jira` (ticket), resolves target branch name |
| [jira_client.py](file:///home/sridharp@ds.alacriti.com/Downloads/aidevops-main/cursorreq/cursorreq/jira_client.py) | `JiraClient` using `httpx.Client` for Jira Cloud REST API v3. Key feature: `adf_to_text()` — recursive converter turning Atlassian Document Format JSON (paragraphs, headings, lists, tables, codeblocks) into clean markdown |
| [branch_naming.py](file:///home/sridharp@ds.alacriti.com/Downloads/aidevops-main/cursorreq/cursorreq/branch_naming.py) | Generates git-safe branch names from Jira metadata: `PAYHUB-10412` + "Add weekend check" → `feature-10412-add_weekend_check` |

### Spec-Driven Development

| File | Purpose |
|---|---|
| [spec.py](file:///home/sridharp@ds.alacriti.com/Downloads/aidevops-main/cursorreq/cursorreq/spec.py) | `SpecDocument` dataclass: `feature_name`, `overview`, `data_models`, `api_contracts`, `business_rules`, `edge_cases_and_errors`, `acceptance_criteria` (list), `out_of_scope`, `dependencies_and_assumptions`, `implementation_targets`. Methods: `to_markdown()`, `from_markdown()` (regex section parser), `approve()`, `reject()`, `add_feedback()` |
| [spec_verifier.py](file:///home/sridharp@ds.alacriti.com/Downloads/aidevops-main/cursorreq/cursorreq/spec_verifier.py) | Parses AI verification output: `CriterionResult` (criterion, passed, evidence, issue) + `VerificationResult` (passed, criteria_results, summary). Matches structured format: `### Criterion N:`, `**Result:** PASS|FAIL`, `**Evidence:**`, `**Issue:**` |
| [human_gate.py](file:///home/sridharp@ds.alacriti.com/Downloads/aidevops-main/cursorreq/cursorreq/human_gate.py) | Interactive CLI checkpoints with colored ANSI output. `review_spec()` for Phase 1b (accepts: approve, reject reason, feedback notes). `final_review()` for Phase 5. Non-interactive bypass via `auto_approve=True` for CI/CD. Saves decisions to `spec_review.json`. |

### Quality Assessment

| File | Purpose |
|---|---|
| [quality.py](file:///home/sridharp@ds.alacriti.com/Downloads/aidevops-main/cursorreq/cursorreq/quality.py) | Complete quality pipeline: (1) `infer_module_paths_from_git()` — detects modified Maven module from git diff, (2) `run_maven_tests()` — executes `mvn -pl <module> test`, (3) `_parse_surefire_reports()` — reads `TEST-*.xml` from `target/surefire-reports`, (4) `score_quality()` — 0-100 scoring across 5 criteria, (5) `_grade_from_score()` — letter grades |

### Quality Scoring Breakdown

| Criterion | Max Points | How It's Measured |
|---|---|---|
| **Compile & Test Pass** | 30 | Maven build succeeds + all tests pass |
| **Spec Compliance** | 25 | Ratio of acceptance criteria marked PASS by AI verifier |
| **Unit Tests Generated** | 20 | Presence of new test files in the git diff |
| **Unit Tests Passed** | 15 | Test pass count from Surefire XML reports |
| **Minimal Focused Diff** | 10 | Changes stay within `max_lines_added` (120 default) |

**Letter Grades**: A ≥ 90, B ≥ 75, C ≥ 60, D ≥ 40, F < 40

### Git Lifecycle

| File | Purpose |
|---|---|
| [git_auth.py](file:///home/sridharp@ds.alacriti.com/Downloads/aidevops-main/cursorreq/cursorreq/git_auth.py) | `GitCredentials` (from `GIT_USERNAME`/`GIT_TOKEN` env vars). JKS-to-PEM truststore export via `keytool` (cached with `lru_cache`). SSL CA path resolution from `GIT_SSL_CAINFO`, `GIT_SSL_CA_FILE`, `GIT_SSL_TRUST_STORE`. Builds subprocess env with `GIT_TERMINAL_PROMPT=0`. |
| [git_manager.py](file:///home/sridharp@ds.alacriti.com/Downloads/aidevops-main/cursorreq/cursorreq/git_manager.py) | `GitManager`: branch ops (create, checkout, exists, update_from_remote), worktree ops (stash_if_dirty, has_uncommitted, reset_hard), network ops (fetch, push with authenticated remote URL injection/cleanup), commit ops (stage_and_commit, diff_stat), **transactional rollback** (hard reset → branch restore → stash pop). |

### Pipeline Orchestrator & Transcript

| File | Purpose |
|---|---|
| [pipeline.py](file:///home/sridharp@ds.alacriti.com/Downloads/aidevops-main/cursorreq/cursorreq/pipeline.py) | Master orchestrator: `_agent_session()` (Cursor SDK bridge launch), `_collect_agents_context()` (discovers repo + module-level `AGENTS.md` files), prompt builders (`build_spec_prompt`, `build_user_prompt`, `build_verify_prompt`, `build_retry_prompt`), `_write_artifacts()`, `run_pipeline()` |
| [transcript.py](file:///home/sridharp@ds.alacriti.com/Downloads/aidevops-main/cursorreq/cursorreq/transcript.py) | `TranscriptAggregator`: processes Cursor SDK token streams and tool execution deltas into `transcript.jsonl` (machine-readable) and `transcript.txt` (human-readable). Handles `TOOL`, `STATUS`, `ASSISTANT`, `THINKING` record types. |

---

## 5. Prompt Templates (8 files)

| Template | Phase | Purpose |
|---|---|---|
| `agent_spec_system.txt` | Phase 1 | Senior software architect role. Read-only repo analysis. Generate structured SpecDocument. |
| `agent_spec_user.txt` | Phase 1 | Injects requirement + codebase context (AGENTS.md) |
| `agent_spec_refine.txt` | Phase 1b | Address reviewer feedback, update spec sections |
| `agent_system.txt` | Phase 2 | 5-step phased workflow: Discover → Implement production code → Compile → Add unit tests → Run tests |
| `agent_user.txt` | Phase 2 | Requirement + approved spec contract + repo workspace paths + Maven hints |
| `agent_verify_spec.txt` | Phase 3 | Evaluate git diff against each acceptance criterion. Output structured PASS/FAIL + Evidence + Issue |
| `agent_retry.txt` | Phase 4 | Quality gate failure retry — supplies quality summary + Maven error excerpts |
| `agent_plan_system/user.txt` | Legacy | Planning templates (deprecated) |

---

## 6. Data Flow — Complete Example

```
Step 1: CLI Invocation
   $ cursorreq --jira PAYHUB-10412 --repo ~/bankrails

Step 2: Requirement Ingestion
   JiraClient fetches PAYHUB-10412 from Jira Cloud REST API v3
   → adf_to_text() converts ADF JSON → clean markdown
   → branch_naming: "PAYHUB-10412" + "Add weekend validation" 
     → feature-10412-add_weekend_check

Step 3: Phase 0 — Git Setup
   GitManager.prepare_branch():
   → stash_if_dirty() — saves uncommitted work
   → fetch("origin")
   → create_and_checkout_branch("feature-10412-add_weekend_check", from="master")

Step 4: Phase 1 — Spec Generation
   _collect_agents_context() reads:
   → /repo/AGENTS.md (project-wide coding standards)
   → /repo/bankrailsservice-base/AGENTS.md (module-specific context)
   
   Cursor SDK Agent:
   → Reads repository structure via terminal commands
   → Analyzes existing code patterns
   → Generates SpecDocument with:
     - 5 acceptance criteria
     - Data model changes
     - API contract updates
     - Business rules
     - Edge cases

Step 5: Phase 1b — Human Spec Review
   HumanGate.review_spec():
   Terminal displays spec markdown with colored formatting
   >>> approve / feedback "Add null check for date parameter" / reject
   
   If feedback → AI refines spec (up to 3 rounds) → re-review
   If approve → continue to Phase 2

Step 6: Phase 2 — Implementation
   Agent follows 5-step workflow:
   1. Discover: reads existing DateHelper.java, CalendarService.java
   2. Implement: edits DateHelper.java — adds isWeekendOrHoliday() method
   3. Compile: runs mvn compile → fixes any errors
   4. Add tests: creates DateHelperTest.java with 5 test cases
   5. Test: runs mvn test → all pass

Step 7: Phase 3 — Spec Verification
   build_verify_prompt() includes:
   → git diff output (all changes)
   → Each acceptance criterion from SpecDocument
   
   Agent evaluates:
   → Criterion 1: "Method handles null dates" → PASS (Evidence: null check at line 45)
   → Criterion 2: "Covers Saturday and Sunday" → PASS (Evidence: DayOfWeek enum check)
   → ... (all 5 criteria PASS)

Step 8: Phase 4 — Quality Gate
   quality.py:
   → infer_module_paths_from_git() → "bankrailsservice-base"
   → run_maven_tests("mvn -pl bankrailsservice-base test -Dtest=DateHelperTest")
   → _parse_surefire_reports() → 5 tests run, 5 passed, 0 failed
   → score_quality():
     - Compile & Test: 30/30
     - Spec Compliance: 25/25 (5/5 criteria passed)
     - Tests Generated: 20/20 (new test file in diff)
     - Tests Passed: 15/15 (5/5 passed)
     - Minimal Diff: 10/10 (85 lines added < 120 max)
     Total: 100/100 → Grade: A ✅

Step 9: Phase 5 — Human Final Review
   HumanGate.final_review():
   Terminal displays:
   → Quality Score: 100 (A)
   → Diff: +85/-0 lines (2 files changed)
   → All 5 criteria: PASS
   >>> approve

Step 10: Phase 6 — Git Commit & Push
   GitManager.stage_and_commit("AI: Add weekend validation to DateHelper [PAYHUB-10412]")
   GitManager.push() → authenticates via GIT_TOKEN → pushes to origin

Step 11: Artifact Generation
   out/20260816T081500Z/
   ├── requirement.txt           # Original Jira ticket text
   ├── requirement_source.txt    # "jira:PAYHUB-10412"
   ├── jira_issue.txt            # Full Jira issue data
   ├── spec.md                   # Approved specification
   ├── spec_prompt.txt           # Prompt sent for spec generation
   ├── spec_review.json          # Human review decisions audit trail
   ├── prompt.txt                # Implementation prompt
   ├── transcript.txt            # Human-readable agent log
   ├── transcript.jsonl          # Machine-readable agent log
   ├── changes.diff              # Complete git diff
   ├── verification.json         # Per-criterion PASS/FAIL results
   ├── verification_prompt.txt   # Verification prompt
   ├── quality.txt               # Quality score breakdown
   ├── quality.json              # Machine-readable quality data
   └── result.json               # Final status + metadata + commit SHA
```

---

## 7. Integration Points

### Cursor Python SDK
```python
# pipeline.py — _agent_session()
with Client.launch_bridge() as client:
    agent = Agent.create(local=LocalAgentOptions(cwd=repo_path))
    # Agent has full disk access — reads files, runs commands, edits code
    run = agent.run(prompt, model="composer-2.5")
    for message in run.stream():
        transcript.process(message)
```

### Jira Cloud REST API v3
```python
# jira_client.py
class JiraClient:
    def __init__(self, config: JiraConfig):
        self._client = httpx.Client(
            base_url=f"{config.site_url}/rest/api/3",
            auth=(config.email, config.api_token)  # Basic Auth
        )
    
    def get_issue(self, key: str) -> JiraIssue:
        resp = self._client.get(f"/issue/{key}")
        description = adf_to_text(resp.json()["fields"]["description"])
        return JiraIssue(key=key, summary=..., description=description)
```

### Git Operations (Corporate SSL)
```python
# git_auth.py — JKS to PEM conversion
def _export_jks_to_pem(jks_path, password):
    """Exports JKS truststore certificates to PEM format via keytool"""
    subprocess.run(["keytool", "-exportcert", ...])
    return pem_path

# git_manager.py — authenticated push
def push(self):
    self._use_authenticated_remote()  # Injects GIT_TOKEN into URL
    try:
        subprocess.run(["git", "push", ...], env=build_git_subprocess_env())
    finally:
        self._restore_remote_url()  # Removes credentials from URL
```

---

## 8. Configuration & Deployment

```yaml
# config.yaml
cursor:
  api_key: ${CURSOR_API_KEY}           # Cursor SDK authentication
  model: composer-2.5                   # AI model
  stream_timeout_seconds: 1800          # 30min — long implementation tasks
  unary_timeout_seconds: 120

repo:
  path: ${HOME}/jcs-workdir/bankrails/payhub-bankrails

maven:
  module_path: ""                       # Auto-detected from git diff

quality:
  max_retries: 1                        # Retry on quality gate failure
  test_timeout_seconds: 600             # 10min Maven test timeout
  min_score: 60                         # Minimum passing score
  max_lines_added: 120                  # Diff size penalty threshold

spec:
  enabled: true                         # Enable Spec-Driven Development
  human_review_mode: interactive        # "interactive" or "auto_approve"
  max_refinement_rounds: 3             # Feedback → refine cycles
  max_verification_retries: 2          # Re-implement if criteria fail
  verify_against_spec: true            # Phase 3 enabled
  final_review: true                   # Phase 5 enabled

jira:
  site_url: https://your-jira.atlassian.net
  email: user@example.com
  api_token: ${JIRA_API_TOKEN}
  timeout_seconds: 60
  extra_field_ids: []                  # Custom acceptance criteria fields

git:
  enabled: true
  auto_push: true
  remote: origin
  base_branch: master
  auto_branch_from_jira: true          # Auto-generate branch name from ticket
  branch_prefix: feature
  branch_id_separator: "-"
  branch_lowercase_slug: true
  max_branch_length: 60

output_dir: out
```

### CLI Usage
```bash
# From Jira ticket (auto-generates branch name)
cursorreq --jira PAYHUB-10412

# From requirement file
cursorreq --requirement fix_weekend_check.txt

# CI/CD mode (no human review gates)
cursorreq --jira PAYHUB-10412 --auto-approve --no-final-review

# Dry run (skip git push)
cursorreq --requirement req.txt --no-push

# Custom repo and module
cursorreq --jira PAYHUB-10412 --repo /path/to/repo --maven-module message-masker

# Skip spec generation (use pre-written spec)
cursorreq --requirement req.txt --skip-spec --spec-file existing_spec.md
```

---

## 9. How It Fits in the Overall Pipeline

```mermaid
graph TB
    JIRA["📋 Jira Ticket"] --> CURSORREQ
    FILE["📄 Requirement File"] --> CURSORREQ
    
    subgraph CURSORREQ["🚀 cursorreq (This Module)"]
        SPEC["Phase 1: Spec Gen"] --> REVIEW["Phase 1b: Human Review"]
        REVIEW --> IMPL["Phase 2: Implementation"]
        IMPL --> VERIFY["Phase 3: Verification"]
        VERIFY --> QUALITY["Phase 4: Quality Gate"]
        QUALITY --> FINAL["Phase 5: Final Review"]
        FINAL --> GIT["Phase 6: Git Push"]
    end
    
    CURSORREQ --> GITLAB["🔀 GitLab<br/>Feature Branch + Commit"]
    CURSORREQ --> ARTIFACTS["📁 14+ Artifacts"]
    
    style CURSORREQ fill:#4CAF50,color:#fff,stroke:#333,stroke-width:3px
```

> [!NOTE]
> `cursorreq` is the **self-contained end-to-end pipeline**. Unlike `req2code` (which depends on JCS for compilation and graph queries), `cursorreq` operates with zero external infrastructure — the Cursor SDK agent has direct disk access and can run `mvn compile`/`mvn test` locally.

---

## 10. Interview Q&A

### Architecture & Design

**Q1: What is Spec-Driven Development (SDD) and why did you implement it?**
> SDD means the AI first generates a specification (acceptance criteria, data models, API contracts) BEFORE writing any code. This creates a formal contract that the implementation is verified against. Without SDD, the AI might generate code that compiles but doesn't meet the requirement. The spec is reviewed by a human before implementation begins, catching misunderstandings early.

**Q2: Why use the Cursor Python SDK instead of calling the OpenAI API directly?**
> The Cursor SDK provides an **agent** — not just a chatbot. The agent has direct disk access (`cwd=repo_path`), can run terminal commands (`mvn compile`), read files, and edit code. With raw OpenAI, we'd need to build all of this infrastructure ourselves (file reading, command execution, diff application). The SDK handles the agentic loop natively.

**Q3: Explain the transactional rollback mechanism.**
> If any phase fails (compilation error, push failure, human rejection), `GitManager.rollback()` executes: (1) `git reset --hard` to undo all changes, (2) `git checkout` to the original branch, (3) `stash pop` to restore the user's uncommitted work. This ensures the repository is never left in a broken state — like a database transaction's ROLLBACK.

**Q4: Why separate Phase 3 (spec verification) from Phase 4 (quality gate)?**
> Phase 3 is **semantic verification** — does the code meet the requirement? An AI reads the git diff against each acceptance criterion. Phase 4 is **technical verification** — does the code compile and pass tests? A quality score is computed from real Maven test results. You can have code that passes all tests but doesn't address the requirement (Phase 3 catches this), or code that meets the requirement but has failing tests (Phase 4 catches this).

### Implementation Details

**Q5: How does the quality scoring work?**
> Five criteria weighted to 100 points: (1) Compile & Test Pass (30pts) — Maven build succeeds, (2) Spec Compliance (25pts) — ratio of acceptance criteria marked PASS, (3) Unit Tests Generated (20pts) — new test files in git diff, (4) Unit Tests Passed (15pts) — from Surefire XML reports, (5) Minimal Focused Diff (10pts) — changes stay under `max_lines_added`. Letter grades: A≥90, B≥75, C≥60, D≥40, F<40. The `min_score` config (default 60) determines the passing threshold.

**Q6: How does Maven module inference work?**
> `infer_module_paths_from_git()` analyzes the git diff to find which files changed, then walks up the directory tree looking for `pom.xml` files. It determines the Maven module path (e.g., `bankrailsservice-base`) so it can run targeted tests: `mvn -pl bankrailsservice-base test -Dtest=DateHelperTest` instead of running the entire project's tests.

**Q7: How does the ADF-to-text converter work?**
> Jira Cloud stores descriptions in Atlassian Document Format (ADF) — a complex JSON AST with nodes for paragraphs, headings, lists, tables, code blocks, inline marks, and media. `adf_to_text()` recursively traverses this tree, converting each node type to its Markdown equivalent. This is crucial because the AI needs clean text, not raw JSON.

**Q8: What are `AGENTS.md` files and how are they used?**
> `AGENTS.md` files are codebase-level context documents placed in the repository (at root and/or module level). They contain coding standards, architectural patterns, naming conventions, and domain-specific rules. `_collect_agents_context()` discovers and reads these files, injecting them into the spec generation prompt so the AI follows the project's conventions.

### Git & Security

**Q9: How do you handle corporate Git authentication with JKS truststores?**
> Corporate GitLab often uses certificates signed by internal CAs stored in Java KeyStore (JKS) files. `git_auth.py`'s `_export_jks_to_pem()` uses Java's `keytool` to export all certificates from the JKS into a temporary PEM bundle. This PEM is set as `GIT_SSL_CAINFO` in the subprocess environment, allowing standard `git push` to verify the corporate SSL chain. The result is cached with `lru_cache` to avoid repeated exports.

**Q10: How does credential injection work during push?**
> `_use_authenticated_remote()` temporarily modifies the Git remote URL to embed credentials: `https://username:token@gitlab.corp.com/repo.git`. After the push completes (success or failure), `_restore_remote_url()` immediately reverts to the original URL. This ensures credentials are never persisted in `.git/config`.

**Q11: How does the branch naming work?**
> `build_feature_branch()` takes a Jira key and summary: (1) `issue_number_from_key("PAYHUB-10412")` → `10412`, (2) `summary_to_slug("Add weekend check")` → `add_weekend_check`, (3) Combines with prefix: `feature-10412-add_weekend_check`, (4) Truncates to `max_branch_length` (60 chars). Special characters are replaced with underscores, and the slug is lowercased.

### Quality & Governance

**Q12: How does the human review gate work in CI/CD mode?**
> Setting `--auto-approve` bypasses both review gates (Phase 1b and Phase 5). The spec is automatically approved without human inspection, and the final review is skipped. Combined with `--no-final-review`, this enables fully autonomous operation in CI/CD pipelines. All decisions are still logged in `spec_review.json` for audit trails.

**Q13: What happens when the quality gate fails?**
> If `score < min_score` (default 60), `maven_error_excerpt()` extracts the failing test names and compiler error messages. `build_retry_prompt()` feeds this to the AI agent via the `agent_retry.txt` template. The agent reads the errors, fixes the code, and the quality assessment runs again. This loop continues up to `max_retries` (default 1).

**Q14: What are the 14+ output artifacts and why so many?**
> Complete audit trail: `requirement.txt` (input), `spec.md` (contract), `spec_review.json` (human decisions), `prompt.txt` (what the AI was told), `transcript.txt` (what the AI did), `changes.diff` (what changed), `verification.json` (criteria results), `quality.json` (score breakdown), `result.json` (final status). In regulated environments (banking/payments), this traceability is essential for compliance.

### Scaling & Architecture

**Q15: How does cursorreq compare to req2code architecturally?**
> `req2code` uses a precision approach — FAISS + Neo4j to find the exact class, generates one method, compiles via JCS, and injects via AST. It requires heavy infrastructure (JCS server, Neo4j, FAISS, SageMaker). `cursorreq` uses an agentic approach — the Cursor SDK agent explores the repo itself, writes entire files including tests, and verifies via real Maven builds. Zero infrastructure, but less precise and more token-intensive.

**Q16: What's the `stream_timeout_seconds: 1800` (30 minutes) for?**
> Implementation tasks (Phase 2) can be complex. The AI agent needs to read multiple files, understand the codebase, write production code, add tests, run `mvn compile`, and fix errors. For large features, this can take 10-20 minutes of continuous agent execution. The 30-minute timeout provides buffer for retries.

**Q17: Could you run cursorreq in parallel for multiple tickets?**
> Each run creates its own feature branch and output directory, so parallel execution is safe at the Git level. However, the Cursor SDK's bridge is a local process, so you'd need multiple bridge instances. In practice, you'd run multiple `cursorreq` processes with different `--jira` flags, each targeting a different branch. The `GitManager` handles dirty-tree stashing to prevent conflicts.

---

## 11. Key Design Decisions & Trade-offs

| Decision | Rationale | Trade-off |
|---|---|---|
| **Spec-Driven Development** | Creates verifiable contract before coding; catches misunderstandings early | Adds ~5 min latency; spec quality depends on AI's repo understanding |
| **Cursor SDK over raw API** | Agent has direct disk access, terminal execution — no tool-building needed | Vendor dependency on Cursor; requires local installation |
| **Interactive human gates** | Critical for regulated industries (banking/payments) | Blocks pipeline until human responds; not suitable for fully automated CI |
| **0-100 quality scoring** | Quantifiable, repeatable quality measurement | Scoring weights are somewhat arbitrary; may need tuning per project |
| **Transactional Git rollback** | Repository is never left in broken state | Adds complexity to Git operations; stash/pop can fail in edge cases |
| **JKS-to-PEM truststore export** | Enables Git push through corporate SSL | Requires Java's `keytool` on PATH; temporary PEM files on disk |
| **Per-criterion AI verification** | Specific feedback on what passed/failed | AI verifier can be wrong — not a substitute for human review |

---

## 12. Challenges Faced & Solutions

### Challenge 1: Agent Scope Creep
**Problem**: The AI agent would sometimes make changes beyond the requirement — refactoring unrelated code, updating configurations, or fixing pre-existing bugs.
**Solution**: The `agent_system.txt` prompt strictly scopes the agent to a 5-step workflow (Discover → Implement → Compile → Test → Verify). The `max_lines_added` quality criterion (10 points) penalizes overly large diffs. Phase 3 verification ensures changes align with acceptance criteria.

### Challenge 2: Corporate SSL Trust Chain
**Problem**: Git push fails behind corporate proxy because the CA certificate isn't in the system trust store. Java applications use JKS truststores, but Git CLI uses PEM format.
**Solution**: `git_auth.py` bridges the gap by exporting JKS certificates to PEM via `keytool`, setting the PEM as `GIT_SSL_CAINFO` in the subprocess environment. The export is cached with `lru_cache` to avoid repeated disk I/O.

### Challenge 3: Maven Module Detection
**Problem**: The target repository is a multi-module Maven project. Running `mvn test` at the root runs ALL tests (15+ minutes). We only need tests for the modified module.
**Solution**: `quality.py`'s `infer_module_paths_from_git()` analyzes the git diff file paths, walks up to find the nearest `pom.xml`, and runs `mvn -pl <module> test` with `-Dtest=<TargetTest>` for targeted, fast execution.

### Challenge 4: Atlassian Document Format Parsing
**Problem**: Jira Cloud returns descriptions in ADF JSON — a deeply nested format with inline marks, media, and tables — not plain text.
**Solution**: `jira_client.py`'s `adf_to_text()` is a recursive converter that handles all ADF node types (paragraph, heading, bulletList, orderedList, table, codeBlock, mediaSingle) and inline marks (bold, italic, code, link), producing clean Markdown.

### Challenge 5: Quality Gate Fairness
**Problem**: Initial scoring was binary (pass/fail on Maven build). This meant a single typo in a test assertion would give 0 points, ignoring otherwise excellent work.
**Solution**: Multi-dimensional 0-100 scoring with 5 weighted criteria. Even if tests fail (losing 30+15 points), the AI still gets credit for spec compliance (25), test generation (20), and diff quality (10). This more accurately reflects the quality of the AI's work and avoids discouraging good-but-imperfect implementations.

---

## 13. Test Suite Overview

The module has **13 comprehensive test files** using `pytest`:

| Test File | What It Tests |
|---|---|
| `test_branch_naming.py` | Jira key extraction, slug sanitization, branch name generation |
| `test_config.py` | Environment variable resolution, config loading/validation |
| `test_git_auth.py` | URL credential injection, JKS export, SSL CA path resolution |
| `test_git_manager.py` | Branch preparation, stashing, committing, rollback flows |
| `test_human_gate.py` | CLI review decision parsing, decision persistence |
| `test_jira_client.py` | ADF conversion, REST client methods, error handling |
| `test_jira_integration.py` | End-to-end Jira requirement loading |
| `test_plan.py` | SpecDocument Markdown parsing, rendering, status transitions |
| `test_quality.py` | Module inference from git diffs, Surefire XML parsing, 0-100 scoring |
| `test_requirement_branch.py` | Extracting `branch:` headers from requirement files |
| `test_spec_verifier.py` | Structured PASS/FAIL/Evidence parsing |
| `test_transcript.py` | Streaming delta aggregation, transcript formatting |
| `conftest.py` | Shared fixtures: mock configs, temporary repo environments |
