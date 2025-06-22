package model;

import java.sql.Date;

public class Request {
    private int requestId;
    private Date fromDate;
    private Date toDate;
    private String reason;
    private String status;
    private int createdBy;
    private Integer processedBy;
    private Date processedDate;
    private String comment;

    public Request() {}

    public Request(int requestId, Date fromDate, Date toDate, String reason, String status,
                   int createdBy, Integer processedBy, Date processedDate, String comment) {
        this.requestId = requestId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
        this.status = status;
        this.createdBy = createdBy;
        this.processedBy = processedBy;
        this.processedDate = processedDate;
        this.comment = comment;
    }

    public int getRequestId() { return requestId; }
    public void setRequestId(int requestId) { this.requestId = requestId; }

    public Date getFromDate() { return fromDate; }
    public void setFromDate(Date fromDate) { this.fromDate = fromDate; }

    public Date getToDate() { return toDate; }
    public void setToDate(Date toDate) { this.toDate = toDate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }

    public Integer getProcessedBy() { return processedBy; }
    public void setProcessedBy(Integer processedBy) { this.processedBy = processedBy; }

    public Date getProcessedDate() { return processedDate; }
    public void setProcessedDate(Date processedDate) { this.processedDate = processedDate; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}