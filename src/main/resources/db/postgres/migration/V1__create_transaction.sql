CREATE SEQUENCE tx_sequence
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE transactions (
    id BIGINT PRIMARY KEY DEFAULT nextval('tx_sequence'),
    from_address TEXT NOT NULL,
    to_address TEXT NOT NULL,
    amount NUMERIC(38, 18) NOT NULL
);

CREATE INDEX idx_from_address ON transactions(from_address);
CREATE INDEX idx_to_address ON transactions(to_address);
