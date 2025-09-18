public class PartyDto {
    private String systemAccountId; // account.system_account_id
    private String systemId; // some constant or left blank
    private String entityId; // legal_entity.legal_entity_code
    private String crdsId; // org.id (as string)

    // getters/setters
    public String getSystemAccountId() { return systemAccountId; }
    public void setSystemAccountId(String systemAccountId) { this.systemAccountId = systemAccountId; }
    public String getSystemId() { return systemId; }
    public void setSystemId(String systemId) { this.systemId = systemId; }
    public String getEntityId() { return entityId; }
    public void setEntityId(String entityId) { this.entityId = entityId; }
    public String getCrdsId() { return crdsId; }
    public void setCrdsId(String crdsId) { this.crdsId = crdsId; }
}
