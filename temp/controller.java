public class controller {
    @PostMapping("/publish")
    @ResponseStatus(HttpStatus.OK)
    public PublishResponseDto publishByTransaction(@Valid @RequestBody TransactionPublishRequest request) {
        String txId = request.getTransactionId();
        log.info("Publish request received for transactionId={}", txId);

        int publishedCount = distributionService.publishByTransactionId(txId, topic);

        return new PublishResponseDto(publishedCount, "Published " + publishedCount + " messages to topic " + topic);
    }

}
