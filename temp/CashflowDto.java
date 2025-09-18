
public class CashflowDto {
    private String cashflowId;
    private String cashflowType;
    private String valueDate; // ISO date string
    private String prdsId;
    private PartyDto party;
    private InstrumentDto instrument;
    private Object additionalAttributes; // flexible

    // getters/setters
    public String getCashflowId() { return cashflowId; }
    public void setCashflowId(String cashflowId) { this.cashflowId = cashflowId; }
    public String getCashflowType() { return cashflowType; }
    public void setCashflowType(String cashflowType) { this.cashflowType = cashflowType; }
    public String getValueDate() { return valueDate; }
    public void setValueDate(String valueDate) { this.valueDate = valueDate; }
    public String getPrdsId() { return prdsId; }
    public void setPrdsId(String prdsId) { this.prdsId = prdsId; }
    public PartyDto getParty() { return party; }
    public void setParty(PartyDto party) { this.party = party; }
    public InstrumentDto getInstrument() { return instrument; }
    public void setInstrument(InstrumentDto instrument) { this.instrument = instrument; }
    public Object getAdditionalAttributes() { return additionalAttributes; }
    public void setAdditionalAttributes(Object additionalAttributes) { this.additionalAttributes = additionalAttributes; }
}
