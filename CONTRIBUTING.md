# 🤝 Contributing to DVD Rental Data Migration Project

Thank you for your interest in contributing to our DVD Rental Data Migration project! This document provides guidelines and information for contributors.

## 🎯 How Can I Contribute?

### 🐛 Reporting Bugs
- Use the [GitHub Issues](https://github.com/prateek-eng/data-migration/issues) page
- Include detailed steps to reproduce the bug
- Provide system information (OS, Java version, database versions)
- Include error logs and stack traces when possible

### 💡 Suggesting Enhancements
- Open a new issue with the "enhancement" label
- Describe the feature and its benefits
- Provide use cases and examples
- Consider implementation complexity

### 🔧 Code Contributions
- Fork the repository
- Create a feature branch
- Make your changes
- Add tests if applicable
- Submit a pull request

## 🚀 Development Setup

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+
- MongoDB 5+
- Git
- Your preferred IDE (IntelliJ IDEA, Eclipse, VS Code)

### Local Development
1. **Fork and Clone**
   ```bash
   git clone https://github.com/YOUR_USERNAME/data-migration.git
   cd data-migration
   ```

2. **Add Upstream Remote**
   ```bash
   git remote add upstream https://github.com/prateek-eng/data-migration.git
   ```

3. **Create Feature Branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

4. **Install Dependencies**
   ```bash
   mvn clean install
   ```

5. **Configure Database**
   - Update `src/main/resources/application.properties`
   - Set up local PostgreSQL and MongoDB instances
   - Use the DVD rental sample database

## 📝 Coding Standards

### Java Code Style
- Follow Java naming conventions
- Use meaningful variable and method names
- Add Javadoc comments for public methods
- Keep methods focused and concise
- Use proper exception handling

### Spring Boot Best Practices
- Follow Spring Boot conventions
- Use dependency injection appropriately
- Implement proper service layer separation
- Use DTOs for data transfer
- Implement proper validation

### Database Operations
- Use prepared statements
- Implement proper transaction management
- Handle database errors gracefully
- Use appropriate indexes
- Follow database naming conventions

## 🧪 Testing

### Unit Tests
- Write tests for all new functionality
- Use JUnit 5 and Mockito
- Aim for high test coverage
- Test both positive and negative scenarios

### Integration Tests
- Test database operations
- Test API endpoints
- Test MCP integration
- Use test databases

### Manual Testing
- Test the web interface
- Verify database migrations
- Test cross-platform compatibility

## 📋 Pull Request Process

### Before Submitting
1. **Update your branch** with the latest main branch
2. **Run all tests** to ensure they pass
3. **Check code style** and formatting
4. **Update documentation** if needed
5. **Test manually** on your local environment

### Pull Request Guidelines
- Use a clear, descriptive title
- Provide a detailed description of changes
- Include screenshots for UI changes
- Reference related issues
- Add appropriate labels

### Review Process
- All PRs require at least one review
- Address review comments promptly
- Make requested changes in new commits
- Keep the PR focused on a single feature/fix

## 🏷️ Issue Labels

- `bug` - Something isn't working
- `enhancement` - New feature or request
- `documentation` - Improvements or additions to documentation
- `good first issue` - Good for newcomers
- `help wanted` - Extra attention is needed
- `question` - Further information is requested
- `wontfix` - This will not be worked on

## 📚 Documentation

### Code Documentation
- Add Javadoc comments for public APIs
- Document complex business logic
- Include examples in comments
- Update README for new features

### User Documentation
- Update API documentation
- Add usage examples
- Document configuration options
- Provide troubleshooting guides

## 🔄 Keeping Your Fork Updated

```bash
# Fetch upstream changes
git fetch upstream

# Switch to main branch
git checkout main

# Merge upstream changes
git merge upstream/main

# Push to your fork
git push origin main
```

## 🆘 Getting Help

- **GitHub Issues**: For bugs and feature requests
- **GitHub Discussions**: For questions and general discussion
- **Code Review**: Ask questions in PR comments
- **Documentation**: Check existing docs first

## 📜 Code of Conduct

- Be respectful and inclusive
- Focus on the code and technical aspects
- Provide constructive feedback
- Help newcomers learn and contribute

## 🎉 Recognition

Contributors will be recognized in:
- Project README
- Release notes
- GitHub contributors page
- Project documentation

## 📞 Contact

- **Maintainer**: [@prateek-eng](https://github.com/prateek-eng)
- **Repository**: [data-migration](https://github.com/prateek-eng/data-migration)

---

Thank you for contributing to making this project better! 🚀