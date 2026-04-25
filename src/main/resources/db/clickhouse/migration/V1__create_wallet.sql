CREATE TABLE wallets
(
    wallet String,
    prefix FixedString(3),
    suffix FixedString(3)
)
ENGINE = MergeTree
ORDER BY (prefix, suffix);