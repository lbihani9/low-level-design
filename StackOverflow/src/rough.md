Tag {
    name,
    referenceCount
}

Badge {
    name,
    description
}

Bounty {
    reputations,
    expiresAt
}

Comment {
    flaggedCount,
    upvotes,
    createdBy
}

Question {
    isDeleted
    isClosed,
    upvotes,
    downvotes,
    flaggedCount,
    bounty (default 0 means, no bounty assigned),
    createdBy,
    Tags[]
    Comments[]
    Answers[]
}

Answer {
    isDeleted,
    isAccepted,
    upvotes,
    downvotes,
    Comments[],
    flaggedCount,
    createdBy
}

Account {
    id,
    email,
    password,
    isLoggedIn,
    username
}

// If I had more information, Admin and Moderator classes could have also been included.
// Same for Badge

// Any user who has an account is a Member, otherwise they're Guest.
User {
    Account (nullable),
    name,
    reputation
}

Search {
    byTag,
    byUsername,
    search(text)
}

Notification {
    message,
    createdAt
}

Possible design patterns:
1. Observer pattern
2. Strategy pattern (if we include badges)