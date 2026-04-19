CREATE TABLE sol.wallet (
   id BIGSERIAL PRIMARY KEY,
   address VARCHAR(44) NOT NULL,
   private_key BYTEA NOT NULL,
   prefix VARCHAR(3) NOT NULL,
   suffix VARCHAR(3) NOT NULL,

   CONSTRAINT uq_wallet_prefix_suffix UNIQUE (prefix, suffix)
);

CREATE INDEX idx_wallet_prefix_suffix ON sol.wallet(prefix, suffix);