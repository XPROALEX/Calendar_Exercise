package it.Ale.Calendar.attendee.util;

public class AttendeeDto {
    private long[] participantsId;
    private Status status;

    public AttendeeDto() {
    }

    public AttendeeDto(long[] participantsId, Status status) {
        this.participantsId = participantsId;
        this.status = status;
    }

    public long[] getParticipantsId() {
        return participantsId;
    }

    public void setParticipantsId(long[] participantsId) {
        this.participantsId = participantsId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
