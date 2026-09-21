What Will This IAM Server Provide?

The IAM server will provide the core identity and security capabilities that applications normally have to build themselves.

                         IAM SERVER
                              │
        ┌─────────────────────┼─────────────────────┐
        │                     │                     │
        ▼                     ▼                     ▼
   Authentication        Authorization         Identity
        │                     │                     │
   ┌────┼────┐          ┌─────┼─────┐        ┌──────┴──────┐
   │    │    │          │     │     │        │             │
Password MFA Passkey   Roles Permissions Policies     User Accounts
        │                     │
        └──────────┬──────────┘
                   ▼
              Sessions
                   │
                   ▼
             Tokens / OAuth
                   │
                   ▼
                OIDC

Main capabilities

Identity Management — users, accounts, lifecycle, and tenant information

Authentication — password, MFA, passkeys, and authentication policies

Authorization — roles, permissions, and policies

Session Management — login sessions, expiration, and revocation

Token Management — access tokens, refresh tokens, expiration, and revocation

OAuth 2.0 — application authorization flows

OpenID Connect — standardized user authentication and identity

Client Management — registration and configuration of applications using the IAM server

Registration & Recovery — user onboarding, password recovery, and account recovery

Audit — security and administrative activity tracking

How Other Applications Will Use It

The IAM server acts as a centralized security layer instead of every application implementing its own authentication system.

                         ┌──────────────────┐
                         │    IAM SERVER    │
                         │                  │
                         │ Authentication   │
                         │ Authorization    │
                         │ OAuth 2.0        │
                         │ OpenID Connect   │
                         │ Tokens           │
                         └────────┬─────────┘
                                  │
                       OAuth 2.0 / OIDC
                                  │
             ┌────────────────────┼────────────────────┐
             │                    │                    │
             ▼                    ▼                    ▼
      Video Streaming       Admin Application     Future Apps
        Application

An application does not need to directly manage the user's password.

Instead:

User
 │
 │ Login
 ▼
Application
 │
 │ Redirect
 ▼
IAM Server
 │
 │ Authenticate
 │
 │ MFA / Passkey / Password
 │
 │ Issue tokens
 ▼
Application
 │
 │ Authenticated requests
 ▼
Application Backend

This allows the IAM server to become the centralized identity and authentication system for the entire application ecosystem.

Future Video Streaming Integration

One of the main purposes of this IAM server is to integrate it with a future video streaming application.

The streaming application will use the IAM server for authentication and authorization rather than implementing its own complete identity system.

                    ┌─────────────────────┐
                    │      IAM SERVER     │
                    │                     │
                    │ Identity            │
                    │ Authentication      │
                    │ Authorization       │
                    │ OAuth 2.0           │
                    │ OpenID Connect      │
                    │ Token Service       │
                    └──────────┬──────────┘
                               │
                         OAuth / OIDC
                               │
                               ▼
                    ┌─────────────────────┐
                    │  VIDEO STREAMING    │
                    │     PLATFORM        │
                    │                     │
                    │ React Frontend      │
                    │ Spring Backend      │
                    │ Video Services      │
                    └─────────────────────┘

For example, a user can:

Open Streaming App
        ↓
      Login
        ↓
IAM Server authenticates user
        ↓
IAM Server issues tokens
        ↓
Streaming application receives identity
        ↓
Streaming backend checks authorization
        ↓
User accesses permitted content

The same IAM server can later be used by additional applications and microservices.

