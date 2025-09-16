INSERT INTO corporate_action_distribution (transaction_id, account_id, amount, currency, effective_date, metadata)
VALUES
('tx-123', 'acct-1001', 100.50, 'USD', '2025-09-01 10:00:00', '{"note":"first sample"}'),
('tx-123', 'acct-1002', 50.00, 'USD', '2025-09-01 10:00:00', '{"note":"second sample"}'),
('tx-456', 'acct-2001', 200.00, 'EUR', '2025-08-25 09:30:00', '{"note":"another tx"}');
