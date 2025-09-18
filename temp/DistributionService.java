import com.example.cad.dto.*;
import com.example.cad.producer.DistributionKafkaProducer;
import com.example.cad.repository.CorporateActionDistributionRepository;
import com.example.cad.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DistributionService {

    private static final Logger log = LoggerFactory.getLogger(DistributionService.class);
    private final CorporateActionDistributionRepository repo;
    private final ProducerService producerService;

    public DistributionService(CorporateActionDistributionRepository repo, ProducerService producerService) {
        this.repo = repo;
        this.producerService = producerService;
    }

    /**
     * Fetch joined rows in a single query and produce a message per row
     */
    public int publishByTransactionId(String transactionId, String topic) {
        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalArgumentException("transactionId required");
        }

        List<Object[]> rows = repo.findDistributionWithLookupsByTransactionId(transactionId);
        if (rows == null || rows.isEmpty()) {
            throw new NotFoundException("No distributions found for transactionId: " + transactionId);
        }

        List<MessageEnvelopeDto> envelopes = rows.stream()
                .map(this::mapRowToEnvelope)
                .collect(Collectors.toList());

        // Publish all messages (this blocks until confirms as before)
        producerService.publishEnvelopes(envelopes, topic);

        return envelopes.size();
    }

    private MessageEnvelopeDto mapRowToEnvelope(Object[] row) {
        // column order same as repository query
        // 0 dist.id
        // 1 dist.transaction_id
        // 2 dist.account_id
        // 3 dist.amount
        // 4 dist.currency
        // 5 dist.effective_date
        // 6 dist.metadata
        // 7 tx.transaction_type_abbreviation
        // 8 acct.revenue_product_code
        // 9 acct.system_account_id
        //10 legal.legal_entity_code
        //11 org.id

        Object distIdObj = row[0];
        String distId = distIdObj != null ? String.valueOf(distIdObj) : null;
        String txId = row[1] != null ? String.valueOf(row[1]) : null;
        String accountId = row[2] != null ? String.valueOf(row[2]) : null;
        Object effectiveDateObj = row[5];
        String effectiveDateStr = null;
        if (effectiveDateObj != null) {
            // format as ISO date (yyyy-MM-dd or yyyy-MM-dd'T'HH:mm:ss)
            effectiveDateStr = effectiveDateObj.toString();
        }

        String cashflowType = row[7] != null ? String.valueOf(row[7]) : null;
        String prdsId = row[8] != null ? String.valueOf(row[8]) : null;
        String systemAccountId = row[9] != null ? String.valueOf(row[9]) : null;
        String legalEntityCode = row[10] != null ? String.valueOf(row[10]) : null;
        String orgId = row[11] != null ? String.valueOf(row[11]) : null;

        // build cashflow
        CashflowDto cf = new CashflowDto();
        cf.setCashflowId(distId);
        cf.setCashflowType(cashflowType);
        cf.setValueDate(OffsetDateTime.now(ZoneOffset.UTC).toLocalDate().toString()); // valueDate = publish date (as requested)
        cf.setPrdsId(prdsId);

        PartyDto party = new PartyDto();
        party.setSystemAccountId(systemAccountId);
        party.setSystemId("CaSP"); // you said keep requestingSystemId = "CaSP"; systemId in party can be same or other if needed
        party.setEntityId(legalEntityCode);
        party.setCrdsId(orgId);

        cf.setParty(party);

        // instrument: you said take from distribution table itself; use placeholder for now
        InstrumentDto instr = new InstrumentDto();
        instr.setType("CUSIP"); // placeholder; swap with actual field later
        instr.setId(distId); // placeholder
        cf.setInstrument(instr);

        // additionalAttributes: try to parse metadata JSON if available
        Object metadata = row[6];
        if (metadata != null) {
            cf.setAdditionalAttributes(metadata); // JsonSerializer will write it as a JSON string or object depending on type; adjust if needed
        } else {
            cf.setAdditionalAttributes(Map.of());
        }

        MessageEnvelopeDto env = new MessageEnvelopeDto();
        env.setMessageId(distId);
        env.setMessageTimestamp(OffsetDateTime.now(ZoneOffset.UTC)); // publish time
        env.setRequestingSystemId("CaSP");

        MessageEnvelopeDto.DataWrapper wrapper = new MessageEnvelopeDto.DataWrapper();
        wrapper.setCashflows(List.of(cf));
        env.setData(wrapper);

        return env;
    }
}
