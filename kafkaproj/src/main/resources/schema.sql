CREATE TABLE IF NOT EXISTS corporate_action_distribution (
    id BIGSERIAL PRIMARY KEY,
    transaction_id TEXT NOT NULL,
    account_id TEXT NOT NULL,
    amount NUMERIC(19,4) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    effective_date TIMESTAMP,
    metadata TEXT
);
