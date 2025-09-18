import java.time.OffsetDateTime;
import java.util.List;

public class MessageEnvelopeDto {
    private String messageId;
    private OffsetDateTime messageTimestamp;
    private String requestingSystemId;
    private DataWrapper data;

    public MessageEnvelopeDto() {}

    public static class DataWrapper {
        private List<CashflowDto> cashflows;
        public DataWrapper() {}
        public List<CashflowDto> getCashflows() { return cashflows; }
        public void setCashflows(List<CashflowDto> cashflows) { this.cashflows = cashflows; }
    }

    // getters / setters
    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }

    public OffsetDateTime getMessageTimestamp() { return messageTimestamp; }
    public void setMessageTimestamp(OffsetDateTime messageTimestamp) { this.messageTimestamp = messageTimestamp; }

    public String getRequestingSystemId() { return requestingSystemId; }
    public void setRequestingSystemId(String requestingSystemId) { this.requestingSystemId = requestingSystemId; }

    public DataWrapper getData() { return data; }
    public void setData(DataWrapper data) { this.data = data; }
}
