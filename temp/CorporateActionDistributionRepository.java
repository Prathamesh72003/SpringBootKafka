import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.cad.entity.CorporateActionDistribution;

import java.util.List;

public interface CorporateActionDistributionRepository extends JpaRepository<CorporateActionDistribution, Long> {

    /**
     * Original simple method still retained
     */
    List<CorporateActionDistribution> findByTransactionId(String transactionId);

    /**
     * Single native query that fetches distribution rows plus required lookup columns
     * for building the message payload. It selects only columns needed to avoid extra queries.
     *
     * Columns returned (order matters for mapping in service):
     *  dist.id,
     *  dist.transaction_id,
     *  dist.account_id,
     *  dist.amount,
     *  dist.currency,
     *  dist.effective_date,
     *  dist.metadata,
     *  tx.transaction_type_abbreviation,
     *  acct.revenue_product_code,
     *  acct.system_account_id,
     *  legal.legal_entity_code,
     *  org.id as org_id
     */
    @Query(value =
        "SELECT dist.id, dist.transaction_id, dist.account_id, dist.amount, dist.currency, dist.effective_date, dist.metadata, " +
        " tx.transaction_type_abbreviation AS tx_abbrev, " +
        " acct.revenue_product_code AS prds_id, acct.system_account_id AS system_account_id, " +
        " legal.legal_entity_code AS legal_entity_code, org.id AS org_id " +
        "FROM corporate_action_distribution dist " +
        "LEFT JOIN transaction_type tx ON dist.transaction_type_id = tx.id " +
        "LEFT JOIN account acct ON dist.account_id = acct.id " +
        "LEFT JOIN legal_entity legal ON acct.legal_entity_id = legal.id " +
        "LEFT JOIN deal_party dp ON acct.deal_id = dp.deal_id " +
        "LEFT JOIN org ON dp.org_id = org.id " +
        "WHERE dist.transaction_id = :txId",
        nativeQuery = true)
    List<Object[]> findDistributionWithLookupsByTransactionId(@Param("txId") String transactionId);
}
