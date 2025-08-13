# 🔒 Security Policy

## Supported Versions

Use this section to tell people about which versions of your project are currently being supported with security updates.

| Version | Supported          |
| ------- | ------------------ |
| 1.0.x   | :white_check_mark: |
| 0.9.x   | :white_check_mark: |
| 0.8.x   | :x:                |
| 0.7.x   | :x:                |
| < 0.7   | :x:                |

## Reporting a Vulnerability

We take the security of our project seriously. If you believe you have found a security vulnerability, please report it to us as described below.

**Please do not report security vulnerabilities through public GitHub issues.**

Instead, please report them via email to [security@example.com](mailto:security@example.com).

You should receive a response within 48 hours. If for some reason you do not, please follow up via email to ensure we received your original message.

Please include the requested information listed below (as much as you can provide) to help us better understand the nature and scope of the possible issue:

- Type of issue (buffer overflow, SQL injection, cross-site scripting, etc.)
- Full paths of source file(s) related to the vulnerability
- The location of the affected source code (tag/branch/commit or direct URL)
- Any special configuration required to reproduce the issue
- Step-by-step instructions to reproduce the issue
- Proof-of-concept or exploit code (if possible)
- Impact of the issue, including how an attacker might exploit it

This information will help us triage your report more quickly.

## Preferred Languages

We prefer all communications to be in English.

## Policy

We take security vulnerabilities seriously and will make every effort to address them in a timely manner. We appreciate your efforts to responsibly disclose your findings, and will make every effort to acknowledge your contributions.

## Security Best Practices

When using this project, please follow these security best practices:

### Database Security
- Use strong, unique passwords for database connections
- Restrict database access to only necessary users
- Regularly update database software and security patches
- Use encrypted connections (SSL/TLS) when possible
- Implement proper input validation and sanitization

### Application Security
- Keep dependencies updated to latest secure versions
- Use environment variables for sensitive configuration
- Implement proper authentication and authorization
- Validate and sanitize all user inputs
- Use HTTPS in production environments

### MCP Integration Security
- Secure MCP server connections
- Validate all MCP protocol messages
- Implement proper access controls for MCP operations
- Monitor MCP activity for suspicious behavior

## Disclosure Policy

When we receive a security bug report, we will:

1. Confirm the problem and determine the affected versions
2. Audit code to find any similar problems
3. Prepare fixes for all supported versions
4. Release a new version(s) with the fix
5. Publicly announce the vulnerability

## Security Updates

Security updates will be released as patch versions (e.g., 1.0.1, 1.0.2) for the current major version. Critical security vulnerabilities may result in immediate releases.

## Credits

We would like to thank all security researchers and contributors who responsibly disclose vulnerabilities to us. Your efforts help make our project more secure for everyone.

## Contact

- **Security Email**: [security@example.com](mailto:security@example.com)
- **PGP Key**: [Available upon request]
- **Security Team**: [@prateek-eng](https://github.com/prateek-eng)

---

**Note**: This security policy is adapted from the [GitHub Security Policy template](https://github.com/github/securitylab/blob/main/SECURITY.md).