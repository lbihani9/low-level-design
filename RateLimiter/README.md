# Design a Rate Limiter

## Problem Statement: 
Implement a rate limiter that allows a maximum of N requests per second for a given user. If the user exceeds the limit, block further requests for the remainder of the second.

## Requirements:
1. Support multiple users.
2. Handle high traffic efficiently.
3. Ensure thread safety if applicable.

**Follow-up:** Extend the solution to support rate limiting over a sliding window (e.g., 100 requests per minute).