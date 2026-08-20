# AutomationExercise Selenium Framework

![CI](https://github.com/JohnUnyime/AutomationExercise-Selenium-Framework/actions/workflows/ci.yml/badge.svg)

A Selenium WebDriver + TestNG test automation framework built against the [AutomationExercise](https://automationexercise.com) practice site, using the Page Object Model (POM) design pattern with JSON-driven test data.

## Tech Stack

- **Java**
- **Selenium WebDriver** — browser automation
- **TestNG** — test framework, assertions, data providers
- **Maven** — build and dependency management
- **Jackson (jackson-databind)** — JSON parsing for data-driven tests

## Framework Design

- **Page Object Model (POM)** — 8 page classes, each encapsulating locators and actions for a single page (Home, Login, Signup, Products, Cart, Checkout, Payment, Signup Success)
- **Fluent-style page methods** — actions return the resulting page object (e.g. `homePage.clickProducts()` returns `ProductsPage`), so tests read as a chained sequence of steps
- **JSON-driven data providers** — brand filters, category filters, and invalid-login scenarios are driven by JSON test data files and TestNG `@DataProvider`, so new test cases (e.g. a new brand) require adding a row to a JSON file, not new Java code
- **Explicit waits** — all interactions use `WebDriverWait` / `ExpectedConditions` rather than fixed sleeps, to avoid flaky, timing-dependent failures

## Browser Support

Supports **Chrome** and **Firefox**, selected via a `browser` parameter passed into the test run.

**Headless mode** is supported via a `headless` parameter (defaults to `false`), letting the full suite run without a visible browser window — useful for CI pipelines and faster local runs.

## Project Structure

```
src/
├── main/java/com/qa/automationexercise/
│   └── pages/              # Page Object classes
├── test/java/com/qa/automationexercise/
│   ├── tests/               # TestNG test classes
│   └── testdata/            # POJO classes matching JSON test data shape
└── test/resources/
    └── testdata/             # JSON test data files (brands.json, categories.json, invalidLogins.json)
```

## What's Covered

- **End-to-end purchase flow** — a logged-in user browsing, adding to cart, checking out, paying, and reaching order confirmation, as one complete journey
- **Product browsing** — brand filtering, category filtering (JSON-driven, data-driven via TestNG `@DataProvider`)
- **Login** — valid login, invalid credentials (wrong password, unregistered email — JSON-driven)
- **Signup** — new account creation, duplicate email rejection
- **Cart** — adding single/multiple products, accumulation across additions, product removal, empty-cart state, cart-to-checkout consistency
- **Payment** — client-side field validation (missing required field)
- **Order confirmation** — success state and download option after a completed payment
- **Smoke test** — basic search functionality, as a fast sanity check

The end-to-end test (`PurchaseFlowTest`) proves the full journey works together; the rest of the suite isolates specific behaviors and edge cases (empty states, validation errors, data variations) that a single E2E run wouldn't naturally exercise.

## Other Notable Design Pieces

- **Global retry logic** — a `GlobalRetryTransformer` automatically applies a retry analyzer to every `@Test` method project-wide, without needing `retryAnalyzer = RetryAnalyzer.class` added to each test by hand

## Running the Tests

```bash
mvn test
```

Run a specific browser headless:

```bash
mvn test -Dbrowser=chrome -Dheadless=true
```

## Notes

This framework was built as a self-directed learning project to practice Page Object Model design, data-driven testing, and explicit-wait strategies in Selenium/TestNG.