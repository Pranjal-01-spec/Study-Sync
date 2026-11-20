package com.studysync.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "doubt_ticket")
public class DoubtTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long askerId;
    private Long mentorId;
    private String subjectTag;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String codeSnippet;
    private String preferredSlot;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Transient
    private Boolean recommended = false;

    public enum TicketStatus {
        OPEN, IN_PROGRESS, RESOLVED
    }

    public DoubtTicket() {}

    public DoubtTicket(Long id, Long askerId, Long mentorId, String subjectTag, String title, String description, String codeSnippet, String preferredSlot, TicketStatus status) {
        this.id = id;
        this.askerId = askerId;
        this.mentorId = mentorId;
        this.subjectTag = subjectTag;
        this.title = title;
        this.description = description;
        this.codeSnippet = codeSnippet;
        this.preferredSlot = preferredSlot;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAskerId() {
        return askerId;
    }

    public void setAskerId(Long askerId) {
        this.askerId = askerId;
    }

    public Long getMentorId() {
        return mentorId;
    }

    public void setMentorId(Long mentorId) {
        this.mentorId = mentorId;
    }

    public String getSubjectTag() {
        return subjectTag;
    }

    public void setSubjectTag(String subjectTag) {
        this.subjectTag = subjectTag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCodeSnippet() {
        return codeSnippet;
    }

    public void setCodeSnippet(String codeSnippet) {
        this.codeSnippet = codeSnippet;
    }

    public String getPreferredSlot() {
        return preferredSlot;
    }

    public void setPreferredSlot(String preferredSlot) {
        this.preferredSlot = preferredSlot;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public Boolean getRecommended() {
        return recommended;
    }

    public void setRecommended(Boolean recommended) {
        this.recommended = recommended;
    }
}
