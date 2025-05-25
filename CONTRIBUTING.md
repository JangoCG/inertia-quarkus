# Contributing to Quarkus Inertia.js Extension

We love your input! We want to make contributing to the Quarkus Inertia.js Extension as easy and transparent as possible, whether it's:

- Reporting a bug
- Discussing the current state of the code
- Submitting a fix
- Proposing new features
- Becoming a maintainer

## Development Process

We use GitHub to host code, to track issues and feature requests, as well as accept pull requests.

## Pull Requests

Pull requests are the best way to propose changes to the codebase. We actively welcome your pull requests:

1. Fork the repo and create your branch from `main`.
2. If you've added code that should be tested, add tests.
3. If you've changed APIs, update the documentation.
4. Ensure the test suite passes.
5. Make sure your code lints.
6. Issue that pull request!

## Development Setup

### Prerequisites

- JDK 17+
- Maven 3.8+
- Node.js 18+ (for integration tests)

### Building the Project

```bash
git clone https://github.com/cengiz-io/quarkus-inertia.git
cd quarkus-inertia
./mvnw clean install
```

### Running Tests

```bash
# Run unit tests
./mvnw test

# Run integration tests
./mvnw verify -pl integration-tests

# Run with specific Quarkus version
./mvnw clean install -Dquarkus.version=3.22.3
```

### Code Style

We use standard Java conventions and Quarkus coding standards:

- Use 4 spaces for indentation
- Follow standard Java naming conventions
- Add Javadoc for public APIs
- Keep line length under 120 characters

You can check code formatting with:

```bash
./mvnw spotless:check
```

And apply formatting with:

```bash
./mvnw spotless:apply
```

### Project Structure

- `runtime/` - Runtime components (services, configurations, etc.)
- `deployment/` - Build-time processors and deployment logic
- `integration-tests/` - Complete integration tests with frontend examples

### Testing Guidelines

- Write unit tests for all public APIs
- Add integration tests for new features
- Test both JVM and native modes when applicable
- Include tests for error conditions

## Bug Reports

We use GitHub issues to track public bugs. Report a bug by [opening a new issue](https://github.com/cengiz-io/quarkus-inertia/issues).

**Great Bug Reports** tend to have:

- A quick summary and/or background
- Steps to reproduce
  - Be specific!
  - Give sample code if you can
- What you expected would happen
- What actually happens
- Notes (possibly including why you think this might be happening, or stuff you tried that didn't work)

## Feature Requests

Feature requests are welcome! Please provide:

- A clear description of the feature
- The motivation behind it
- Examples of how it would be used
- Whether you're willing to implement it

## License

By contributing, you agree that your contributions will be licensed under the MIT License.

## Questions?

Feel free to [open a discussion](https://github.com/cengiz-io/quarkus-inertia/discussions) if you have questions about contributing.

## Recognition

Contributors will be recognized in:

- The project README
- Release notes for significant contributions
- The project's documentation

Thank you for contributing! 🎉
